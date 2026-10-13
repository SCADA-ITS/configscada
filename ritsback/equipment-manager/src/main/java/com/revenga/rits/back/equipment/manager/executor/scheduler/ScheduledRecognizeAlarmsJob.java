package com.revenga.rits.back.equipment.manager.executor.scheduler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.dao.impl.ObjectDaoImpl;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.equipment.manager.data.RootData;
import com.revenga.rits.back.equipment.manager.executor.UnrecognizedAlarmsExecutor;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScheduledRecognizeAlarmsJob implements Job {

	private RootData rootData;
	private Long maxUnrecognizedAlarmsAllowed;
	private ObjectDaoImpl objectDao;
	GenericExecutor genericExecutor = null;

	protected static final String FILE_CONSTANTS = "application.properties";
	protected static final String AUTO_RECOGNIZED_COMMENTS = "Recognized automatically by the system.";
	protected static final String MAX_UNRECOGNIZED_ALARMS_ALLOWED = "maxUnrecognizedAlarmsAllowed";

	public ScheduledRecognizeAlarmsJob() {

		super();

		initialize();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		Connection connection = null;

		SchedulerContext schedulerContext = null;

		try {

			schedulerContext = context.getScheduler().getContext();

			genericExecutor = (UnrecognizedAlarmsExecutor) schedulerContext
					.get(UnrecognizedAlarmsExecutor.class.getSimpleName());

			if (!EntitiesManager.getInstance().isRunningJob() && genericExecutor != null) {

				EntitiesManager.getInstance().setRunningJob(true);

				maxUnrecognizedAlarmsAllowed = context.getScheduler().getContext()
						.getLong(MAX_UNRECOGNIZED_ALARMS_ALLOWED);

				connection = DataSourceConnection.getInstance().getConnection();
				connection.setAutoCommit(false);

				objectDao.load(connection);

				if (rootData != null && maxUnrecognizedAlarmsAllowed != null) {

					List<Alarm> alarms = rootData.getAlarms();

					if (!CollectionUtils.isEmpty(alarms)) {

						List<Alarm> unrecognizedAlarms = alarms.stream().filter(e -> (e.getRecognized() == null))
								.collect(Collectors.toList());

						if (!CollectionUtils.isEmpty(unrecognizedAlarms)
								&& unrecognizedAlarms.size() > maxUnrecognizedAlarmsAllowed) {

							unrecognizedAlarms.sort(Comparator.comparing(Alarm::getActivation));

							Long nAlarmsToRecognize = unrecognizedAlarms.size() - maxUnrecognizedAlarmsAllowed;

							for (int i = 0; i < nAlarmsToRecognize; i++) {

								unrecognizedAlarms.get(i).setRecognized(Instant.now().toEpochMilli());

								unrecognizedAlarms.get(i).setRecognizedComments(AUTO_RECOGNIZED_COMMENTS);

								Alarm alarmUpdated = (Alarm) objectDao.update(connection, unrecognizedAlarms.get(i));

								if (alarmUpdated != null) {

									log.debug((new Key(alarmUpdated)).getSimpleValue() + " -> updated in db");
									log.info("Alarm: " + unrecognizedAlarms.get(i).getId() + " "
											+ AUTO_RECOGNIZED_COMMENTS);

								} else {

									log.error((new Key(alarmUpdated)).getSimpleValue() + " -> Error updating in db");
								}
							}
						}
					}
				}

				connection.commit();

				EntitiesManager.getInstance().setRunningJob(false);

			} else {

				log.warn(this.getClass().getSimpleName() + " => The previous scheduler is still running...");
			}

		} catch (SQLException | IOException e) {

			try {

				connection.rollback();

			} catch (SQLException e1) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

		} finally {

			try {

				if (connection != null) {

					connection.close();
				}

			} catch (SQLException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
	}

	public void initialize() {

		rootData = EntitiesManager.getInstance().getRootData();

		this.objectDao = new ObjectDaoImpl(Alarm.class);

	}

}
