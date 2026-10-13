package com.revenga.rits.back.equipment.manager.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.dao.impl.ObjectDaoImpl;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.AlarmFiltered;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.equipment.manager.executor.coordinator.AlarmFilterCoordinator;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThreadSyncAlarmsFiltered extends ThreadDataGeneric {

	public static final long PERIOD_MS = Long.parseLong(
			ConfigProperty.getInstance().getProperty("application.properties", "thread.syncAlarmsFiltered.period_ms"));

	private RootData rootData;
	private ObjectDaoImpl objectDao;

	public ThreadSyncAlarmsFiltered(RootData rootData) {

		super();

		this.rootData = rootData;
		this.objectDao = new ObjectDaoImpl(AlarmFiltered.class);
	}

	@Override
	public void run() {

		this.alive = true;
		log.info(Thread.currentThread().getName() + " is running...");

		Instant instantKeepAlive = Instant.now();
		Instant instantInit = Instant.now();

		try {

			while (!stop) {

				try {

					if (Duration.between(instantKeepAlive, Instant.now()).toMillis() > KEEP_ALIVE_DEBUG_MS) {

						instantKeepAlive = Instant.now();

						log.debug(Thread.currentThread().getName() + " is alive.");
					}

					if (Duration.between(instantInit, Instant.now()).toMillis() > PERIOD_MS) {

						instantInit = Instant.now();
						
						unFilterAlarmsByTimeExpired();

						syncAlarmsFilteredWithDB();
					}

					Thread.sleep(1000);

				} catch (InterruptedException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
					Thread.currentThread().interrupt();
				}

				Thread.sleep(1000);
			}

		} catch (InterruptedException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}

		this.alive = false;
		log.info(Thread.currentThread().getName() + " is stopped");
	}
	
	private void unFilterAlarmsByTimeExpired() {
		
		Instant instantInit = Instant.now();
		int alarmsFilteredUpdated = 0;
		
		log.info(Thread.currentThread().getName() + " => Init sync alarms filtered at " + instantInit.toString());
		
		for (Element element : this.rootData.getElements()) {
			
			List<AlarmFiltered> alarmsFiltered = element.getAlarmsFiltered();
			
			if (!CollectionUtils.isEmpty(alarmsFiltered)) {
				
				AlarmFilterCoordinator alarmFilterCoordinator = new AlarmFilterCoordinator(element);
				
				alarmsFiltered = alarmsFiltered.stream().filter(e -> e.getFilteredUntil() < Instant.now().toEpochMilli()).collect(Collectors.toList());
				
				if (!CollectionUtils.isEmpty(alarmsFiltered)) {
					
					for (AlarmFiltered alarmFiltered : alarmsFiltered) {
						
						alarmFilterCoordinator.update(new ImmutablePair<>(alarmFiltered, false));
						
						log.debug((new Key(alarmFiltered)).getSimpleValue() + " removed");
						
						alarmsFilteredUpdated++;
					}
				}
			}
		}
		
		if (alarmsFilteredUpdated > 0) {

			log.debug("Updated " + alarmsFilteredUpdated + "alarms filtered");
		}
		
		log.info(Thread.currentThread().getName() + " => Total time sync alarms filtered = "
				+ Duration.between(instantInit, Instant.now()).toMillis() + " ms.");
	}

	private void syncAlarmsFilteredWithDB() {

		Connection connection = null;
		Instant instantInit = Instant.now();
		int alarmsFilteredUpdated = 0;

		try {

			log.info(Thread.currentThread().getName() + " => Init sync alarms filtered with db at " + instantInit.toString());

			connection = DataSourceConnection.getInstance().getConnection();
			connection.setAutoCommit(false);

			objectDao.load(connection);
			
			if (!CollectionUtils.isEmpty(objectDao.getList())) {

				for (Object objCurrentAlarmInDB : objectDao.getList()) {
					
					objectDao.delete(connection, objCurrentAlarmInDB);
				}
			}
			
			for (Element element : this.rootData.getElements()) {
				
				List<AlarmFiltered> alarmsFiltered = element.getAlarmsFiltered();
				
				if (!CollectionUtils.isEmpty(alarmsFiltered)) {
					
					alarmsFilteredUpdated += alarmsFiltered.size();
					
					for (AlarmFiltered alarmFiltered : alarmsFiltered) {
						
						objectDao.insert(connection, alarmFiltered);
					}
				}
			}

			if (alarmsFilteredUpdated > 0) {

				log.debug("Updated " + alarmsFilteredUpdated + "alarms filtered into db");
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

		log.info(Thread.currentThread().getName() + " => Total time sync alarms filtered with db = "
				+ Duration.between(instantInit, Instant.now()).toMillis() + " ms.");
	}
}
