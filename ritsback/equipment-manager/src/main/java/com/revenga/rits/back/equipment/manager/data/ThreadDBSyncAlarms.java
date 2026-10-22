package com.revenga.rits.back.equipment.manager.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.dao.impl.ObjectDaoImpl;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThreadDBSyncAlarms extends ThreadDataGeneric {

	public static final long PERSIST_TIME_MS = Long.parseLong(
			ConfigProperty.getInstance().getProperty("application.properties", "thread.dbSyncAlarms.persist_time_ms"));

	private RootData rootData;
	private ObjectDaoImpl objectDao;
	
	private final Object lock = new Object();

	public ThreadDBSyncAlarms(RootData rootData) {

		super();

		this.rootData = rootData;
		this.objectDao = new ObjectDaoImpl(Alarm.class);
	}

	@Override
	public void run() {

		this.alive = true;
		log.info(Thread.currentThread().getName() + " is running...");

		Instant instantKeepAlive = Instant.now();
		Instant instantInit = Instant.now();

		synchronized (lock) {
		
			try {

				while (!stop) {

					if (Duration.between(instantKeepAlive, Instant.now()).toMillis() > KEEP_ALIVE_DEBUG_MS) {

						instantKeepAlive = Instant.now();

						log.debug(Thread.currentThread().getName() + " is alive.");
					}

					if (Duration.between(instantInit, Instant.now()).toMillis() > PERSIST_TIME_MS) {

						instantInit = Instant.now();
						
						int activeAlarms = 0;
						int noRecognizeAlarms = 0;
						int filteredAlarms = 0;
						int totalAlarms = 0;
						
						for (Element element : rootData.getElements()) {
						
							activeAlarms += element.getActiveAlarms();
							noRecognizeAlarms += element.getNoRecognizeAlarms();
							filteredAlarms += element.getFilteredAlarms();
							totalAlarms += element.getAlarms() != null ? element.getAlarms().size() : 0;
									
						}

						syncAlarmsWithDB();
					}

					lock.wait(1000);
				}

			} catch (InterruptedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
			}
		}

		this.alive = false;
		log.info(Thread.currentThread().getName() + " is stopped");
	}

	private void syncAlarmsWithDB() {

		Connection connection = null;
		int alarmsDeleted = 0;
		int alarmsInserted = 0;
		int alarmsUpdated = 0;
		Set<Alarm> alarmsToTreat = new HashSet<>();
		Instant instantInit = Instant.now();

		try {

			log.info(Thread.currentThread().getName() + " => Init sync alarms with db at " + instantInit.toString());

			connection = DataSourceConnection.getInstance().getConnection();
			connection.setAutoCommit(false);

			objectDao.load(connection);

			List<Alarm> currentAlarms = this.rootData.getAlarms();

			if (!CollectionUtils.isEmpty(currentAlarms)) {

				currentAlarms.forEach(alarm -> {

					AlarmConfig almConfig = alarm.getAlarmConfig();

					if (almConfig != null && (alarm.getDeactivation() != null && alarm.getRecognized() == null)) {

						alarmsToTreat.add(alarm);
					}
				});
			}

			List<Alarm> alarmsToDeleteFromDB = new ArrayList<>();
			List<Alarm> alarmsKeepFromDB = new ArrayList<>();
			List<Alarm> alarmsToUpdateFromDB = new ArrayList<>();

			if (!CollectionUtils.isEmpty(objectDao.getList())) {

				for (Object objCurrentAlarmInDB : objectDao.getList()) {

					if (this.stop) {

						break;
					}

					if (objCurrentAlarmInDB instanceof Alarm) {

						Alarm currentAlarmInDB = (Alarm) objCurrentAlarmInDB;

						if (CollectionUtils.isEmpty(alarmsToTreat)) {

							alarmsToDeleteFromDB.add(currentAlarmInDB);

						} else {

							Alarm almToTreat = alarmsToTreat.stream()
									.filter(e -> e.getId().equals(currentAlarmInDB.getId())).findFirst().orElse(null);

							if (almToTreat != null) {

								boolean anyAlarmModified = false;

								if (currentAlarmInDB.getRecognizedComments() == null
										&& almToTreat.getRecognizedComments() != null) {

									currentAlarmInDB.setRecognizedComments(almToTreat.getRecognizedComments());
									anyAlarmModified = true;
								}

								if (currentAlarmInDB.getRecognizedUserId() == null
										&& almToTreat.getRecognizedUserId() != null) {

									currentAlarmInDB.setRecognizedUserId(almToTreat.getRecognizedUserId());
									anyAlarmModified = true;
								}

								if (currentAlarmInDB.getDeactivationComments() == null
										&& almToTreat.getDeactivationComments() != null) {

									currentAlarmInDB.setDeactivationComments(almToTreat.getDeactivationComments());
									anyAlarmModified = true;
								}

								if (currentAlarmInDB.getDeactivationUserId() == null
										&& almToTreat.getDeactivationUserId() != null) {

									currentAlarmInDB.setDeactivationUserId(almToTreat.getDeactivationUserId());
									anyAlarmModified = true;
								}

								if (anyAlarmModified) {

									alarmsToUpdateFromDB.add(currentAlarmInDB);

								} else {

									alarmsKeepFromDB.add(currentAlarmInDB);
								}
							} else {

								alarmsToDeleteFromDB.add(currentAlarmInDB);
							}
						}
					}
				}

				// Delete Alarms
				for (Alarm alarmToDelete : alarmsToDeleteFromDB) {

					if (this.stop) {

						break;
					}

					Alarm alarmDeleted = null;

					alarmDeleted = (Alarm) objectDao.delete(connection, alarmToDelete);

					if (alarmDeleted != null) {

						alarmsDeleted++;
						log.debug((new Key(alarmDeleted)).getSimpleValue() + " deleted from db");
					}
				}

				if (alarmsDeleted > 0) {

					log.debug("Deleted " + alarmsDeleted + "alarms from db");
				}
			}

			if (!CollectionUtils.isEmpty(alarmsToTreat)) {

				for (Alarm alarmToTreat : alarmsToTreat) {

					if (this.stop) {

						break;
					}

					// Update alarms
					if (!CollectionUtils.isEmpty(alarmsToUpdateFromDB) && alarmsToUpdateFromDB.stream()
							.filter(e -> e.getId().equals(alarmToTreat.getId())).count() == 1) {

						Alarm alarmUpdated = null;

						alarmUpdated = (Alarm) objectDao.update(connection, alarmToTreat);

						if (alarmUpdated != null) {

							alarmsUpdated++;
							log.debug((new Key(alarmUpdated)).getSimpleValue() + " -> updated in db");

						} else {

							log.error((new Key(alarmUpdated)).getSimpleValue() + " -> Error updating in db");
						}

						// Insert new alarms
					} else if (CollectionUtils.isEmpty(alarmsKeepFromDB) || alarmsKeepFromDB.stream()
							.filter(e -> e.getId().equals(alarmToTreat.getId())).count() == 0) {

						Alarm alarmInserted = null;

						alarmInserted = (Alarm) objectDao.insert(connection, alarmToTreat);

						if (alarmInserted != null) {

							alarmsInserted++;
							log.debug((new Key(alarmInserted)).getSimpleValue() + " -> inserted in db");

						} else {

							log.error((new Key(alarmToTreat)).getSimpleValue() + " -> Error inserting in db");
						}
					}

				}
			}

			if (alarmsUpdated > 0) {

				log.debug("Updated " + alarmsUpdated + " alarms into db");
			}

			if (alarmsInserted > 0) {

				log.debug("Inserted " + alarmsInserted + " alarms into db");
			}

			connection.commit();

		} catch (SQLException | IOException e) {

			try {

				connection.rollback();

			} catch (SQLException e1) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}

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

		log.info(Thread.currentThread().getName() + " => Total time sync alarms with db = "
				+ Duration.between(instantInit, Instant.now()).toMillis() + " ms.");
	}
}
