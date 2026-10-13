package com.revenga.rits.back.historical.manager.executor.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.revenga.rits.back.adapter.lib.model.HistoricalElementParserV1;
import com.revenga.rits.back.adapter.lib.model.HistoricalElementParserV1.Fields.Field;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.historical.manager.executor.util.ExecutorUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementService {

	public static final String ELEMENT_TYPE_ID = "element_type_id";

	Scheduler scheduler;

	public ElementService() {

	}

	private boolean createTable(final HistoricalElementParserV1 adapter, final List<ElementTypeParam> elementTypeParams) {
		boolean createTable = true;

		if (adapter != null && adapter.getTableSpace() != null && adapter.getTargetTable() != null
				&& adapter.getFieldElementId() != null && !CollectionUtils.isEmpty(adapter.getFields().getField())
				&& !CollectionUtils.isEmpty(elementTypeParams)) {

			List<HistoricalElementParserV1.Fields.Field> lPk = new ArrayList<>();
			List<HistoricalElementParserV1.Fields.Field> lIndexed = new ArrayList<>();

			Connection connection = null;
			StringBuilder createTableQuery = new StringBuilder();
			StringBuilder createIndexQuery = new StringBuilder();
			String alterTableQuery = null;
			String tableName = null;
			Statement stmt = null;
			String[] arryString = adapter.getTargetTable().split("\\.");

			if (arryString.length == 2) {

				tableName = arryString[1];

			} else {

				tableName = adapter.getTargetTable();
			}

			// CREATE TABLE
			createTableQuery.append("CREATE TABLE " + adapter.getTargetTable() + " ( ");
			createTableQuery.append(adapter.getFieldElementId() + " int8 NOT NULL, ");

			if (adapter.getFieldTimestamp() != null) {

				createTableQuery.append(adapter.getFieldTimestamp() + " timestamptz NOT NULL, ");
			}

			for (Field field : adapter.getFields().getField()) {

				if (field != null && field.getElementTypeParam() != null) {

					ElementTypeParam elementTypeParam = elementTypeParams.stream().filter(
							e -> Long.valueOf(field.getElementTypeParam().getElementTypeParamId()).equals(e.getId())
									&& Long.valueOf(field.getElementTypeParam().getParamTypeId())
											.equals(e.getParamTypeId())
									&& Long.valueOf(adapter.getElementTypeId()).equals(e.getElementTypeId()))
							.findFirst().orElse(null);

					if (elementTypeParam != null) {
						createTableQuery.append(field.getName() + " "
								+ ExecutorUtil.getDataType(elementTypeParam.getDataTypeId()) + " NULL, ");

						if (field.isPk() != null && field.isPk().booleanValue()) {

							lPk.add(field);
						}

						if (field.isIndexed() != null && field.isIndexed().booleanValue()) {

							lIndexed.add(field);
						}
					}
				}
			}

			createTableQuery.append(" created_at timestamptz NOT NULL, ");
			createTableQuery.append(" CONSTRAINT pk_" + tableName + " PRIMARY KEY (" + adapter.getFieldElementId());

			if (adapter.getFieldTimestamp() != null) {

				createTableQuery.append(", " + adapter.getFieldTimestamp());
			}

			for (int i = 0; i < lPk.size(); i++) {

				createTableQuery.append(", " + lPk.get(i).getName());
			}

			createTableQuery.append(" )");
			createTableQuery.append(" );");

			// SET TABLESPACE
			alterTableQuery = "ALTER TABLE " + adapter.getTargetTable() + " SET TABLESPACE " + adapter.getTableSpace()
					+ ";";

			// INDEX
			createIndexQuery.append("CREATE INDEX idx_" + tableName + adapter.getFieldElementId() + " ON "
					+ adapter.getTargetTable() + " USING btree (" + adapter.getFieldElementId() + ");");

			for (Field field : lIndexed) {

				createIndexQuery.append("CREATE INDEX idx_" + tableName + field.getName() + " ON "
						+ adapter.getTargetTable() + " USING btree (" + field.getName() + ");");
			}

			try {
				connection = DataSourceConnection.getInstance().getConnection();

				connection.setAutoCommit(false);

				stmt = connection.createStatement();

				stmt.executeUpdate(createTableQuery.toString());
				stmt.executeUpdate(alterTableQuery);
				stmt.executeUpdate(createIndexQuery.toString());

				connection.commit();

				log.info("Table: " + adapter.getTargetTable() + " create succesfull");

			} catch (SQLException | IOException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				createTable = false;

				try {
					if (connection != null)
						connection.rollback();

				} catch (SQLException e1) {

					log.error(e1.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e1));
				}

			} finally {
				try {

					if (stmt != null) {
						stmt.close();
					}

					if (connection != null) {
						connection.close();
					}

				} catch (SQLException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}

		} else {
			log.error("createTable null value");
			createTable = false;
		}

		return createTable;
	}

	private void launchJob(String cronExpression, short elementId) {

		if (!StringUtils.isEmpty(cronExpression)) {

			if (scheduler == null) {

				start();
			}
			createJob(ScheduledPeriodDataJob.class, cronExpression, elementId);

		} else {

			log.error("Not found cronPeriodTrafficData");
		}
	}

	private void createJob(Class<? extends Job> jobClass, String cronExpression, short elementId) {

		try {
			JobDetail job = newJob(jobClass)
					.withIdentity("job" + jobClass.getSimpleName() + elementId, "group" + jobClass.getSimpleName())
					.build();

			job.getJobDataMap().put(ELEMENT_TYPE_ID, elementId);

			if (!StringUtils.isEmpty(cronExpression)) {

				CronTrigger trigger = newTrigger().withIdentity("trigger" + jobClass.getSimpleName() + elementId,
						"group" + jobClass.getSimpleName()).withSchedule(cronSchedule(cronExpression)).build();

				Date dt = scheduler.scheduleJob(job, trigger);

				log.info(job.getKey() + " has been scheduled to run at: " + dt + " and repeat based on expression: "
						+ trigger.getCronExpression());
			} else {

				log.debug(job.getKey() + " is not scheduled");
			}

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	public boolean start() {

		Boolean ok = true;

		SchedulerFactory sf = new StdSchedulerFactory();
		try {

			scheduler = sf.getScheduler();
			scheduler.getContext().put(this.getClass().getSimpleName(), this);
			scheduler.start();

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			ok = false;
		}

		return ok;
	}

	public boolean insertHistoric(final HistoricalElementParserV1 adapter, final List<ElementTypeParam> elementTypeParams, final List<Element> elements) {

		Boolean saveHistorc = true;

		if (elements != null && adapter != null && adapter.getTargetTable() != null
				&& adapter.getFieldElementId() != null && !CollectionUtils.isEmpty(elements)
				&& !CollectionUtils.isEmpty(elementTypeParams)) {

			Connection connection = null;
			StringBuilder insertTableQuery = new StringBuilder();
			PreparedStatement pstmt = null;
			String string = "";
			Long now = null;

			insertTableQuery.append("INSERT INTO " + adapter.getTargetTable());
			insertTableQuery.append(" ( " + adapter.getFieldElementId());

			if (adapter.getFieldTimestamp() != null) {

				now = Instant.now().toEpochMilli();
				insertTableQuery.append(", " + adapter.getFieldTimestamp());
			}

			for (Field field : adapter.getFields().getField()) {

				if (field != null && field.getElementTypeParam() != null) {

					ElementTypeParam elementTypeParam = elementTypeParams.stream().filter(
							e -> Long.valueOf(field.getElementTypeParam().getElementTypeParamId()).equals(e.getId())
									&& Long.valueOf(field.getElementTypeParam().getParamTypeId())
											.equals(e.getParamTypeId())
									&& Long.valueOf(adapter.getElementTypeId()).equals(e.getElementTypeId()))
							.findFirst().orElse(null);

					if (elementTypeParam != null) {

						insertTableQuery.append(", " + field.getName());
						string += " ?, ";
					}
				}
			}

			insertTableQuery.append(" , created_at)");

			if (now != null)
				insertTableQuery.append(" VALUES(?, ?, " + string);
			else
				insertTableQuery.append(" VALUES(?, " + string);

			insertTableQuery.append("CURRENT_TIMESTAMP);");

			try {

				connection = DataSourceConnection.getInstance().getConnection();
				connection.setAutoCommit(false);
				pstmt = connection.prepareStatement(insertTableQuery.toString());

				for (Element element : elements) {

					int index = 1;

					try {

						if (element.getId() != null && !CollectionUtils.isEmpty(element.getElementValues())) {

							ExecutorUtil.setValue(pstmt, index++, element.getId().toString());

							if (now != null)
								ExecutorUtil.setValue(pstmt, index++, now.toString());

							for (Field field : adapter.getFields().getField()) {

								if (field != null && field.getElementTypeParam() != null) {

									ElementTypeParam elementTypeParam = elementTypeParams.stream().filter(e -> Long
											.valueOf(field.getElementTypeParam().getElementTypeParamId())
											.equals(e.getId())
											&& Long.valueOf(field.getElementTypeParam().getParamTypeId())
													.equals(e.getParamTypeId())
											&& Long.valueOf(adapter.getElementTypeId()).equals(e.getElementTypeId()))
											.findFirst().orElse(null);

									if (elementTypeParam != null) {

										ElementValue elementValue = element.getElementValues().stream().filter(e -> Long
												.valueOf(field.getElementTypeParam().getElementTypeParamId())
												.equals(e.getElementTypeParamId())
												&& Long.valueOf(field.getElementTypeParam().getParamTypeId())
														.equals(e.getParamTypeId())
												&& Long.valueOf(adapter.getElementTypeId()).equals(e.getElementTypeId())
												&& element.getId().equals(e.getElementId())).findFirst().orElse(null);

										if (elementValue != null) {

											ExecutorUtil.setValue(pstmt, index++, elementValue.getValue());

										} else {

											ExecutorUtil.setValue(pstmt, index++, null);
										}
									}
								}
							}
							pstmt.executeUpdate();
						}
					} catch (Exception e) {
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}
				connection.commit();

			} catch (SQLException | IOException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				saveHistorc = false;

			} finally {
				try {

					if (pstmt != null) {
						pstmt.close();
					}

					if (connection != null) {
						connection.close();
					}

				} catch (SQLException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}

		} else {
			log.error("saveHistorc null value");
		}

		return saveHistorc;
	}

	public boolean init(final HistoricalElementParserV1 adapter, final  List<ElementTypeParam> elementTypeParams) {

		boolean ok = false;

		if (ExecutorUtil.existTable(adapter.getTargetTable()) || createTable(adapter, elementTypeParams)) {

			if (adapter.getCronExpression() != null) {

				launchJob(adapter.getCronExpression(), adapter.getElementTypeId());
				ok = true;
				
			} else {

				ok = true;
			}
		}
		return ok;
	}

	public boolean deInit(HistoricalElementParserV1 adapter, List<ElementTypeParam> elementTypeParams) {

		boolean ok = false;

		if (adapter != null && adapter.getCronExpression() != null && scheduler != null) {

			JobKey jobKey = new JobKey(
					"job" + ScheduledPeriodDataJob.class.getSimpleName() + adapter.getElementTypeId(),
					"group" + ScheduledPeriodDataJob.class.getSimpleName());
			try {

				if (scheduler.deleteJob(jobKey)) {

					log.info(jobKey + ": deleted");
					ok = true;

				} else {

					log.debug(jobKey + " is not deleted");
				}

			} catch (SchedulerException | RuntimeException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}

		} else {

			ok = true;
		}
		return ok;
	}

	public boolean stop() {

		Boolean ok = true;

		if (scheduler != null) {

			try {

				log.debug("Shutdown scheduled jobs ...");

				scheduler.shutdown(true);

				log.debug("Shutdown Complete");
				log.debug("Executed " + scheduler.getMetaData().getNumberOfJobsExecuted() + " jobs.");

			} catch (SchedulerException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		return ok;
	}

}
