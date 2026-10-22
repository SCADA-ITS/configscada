package com.revenga.rits.back.historical.manager.executor;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.historical.manager.executor.service.PeriodDataMangerDB;

import lombok.extern.log4j.Log4j2;

@Deprecated
@Log4j2
public class PeriodDataThread implements Runnable {

	private PeriodDataMangerDB historicMangerDB;
	private Map<Long, Map<String, String>> elementValues;

	private Long integrationPeriod;
	private boolean stop;
	private boolean alive;

	@Override
	public void run() {

		Map<Long, Map<String, String>> values = null;
		Long timeEndPeriod;
		Long now;

		try {

			values = new HashMap<>();

			synchronized (this.elementValues) {

				for (Long e : this.elementValues.keySet()) {

					values.put(e, new HashMap<String, String>());
				}
			}

			this.setAlive(true);

			log.info(Thread.currentThread().getName() + " is running...");

			now = Instant.now().toEpochMilli();
			timeEndPeriod = (now - (now % integrationPeriod)) + integrationPeriod;

			// Discard the first period

			while (!stop && Instant.now().toEpochMilli() < timeEndPeriod) {
				Thread.sleep(1);
			}

			while (!stop) {

				cleanValues();

				now = Instant.now().toEpochMilli();
				timeEndPeriod = (now - (now % integrationPeriod)) + integrationPeriod;

				while (!stop && Instant.now().toEpochMilli() < timeEndPeriod) {
					Thread.sleep(1);
				}

				if (!stop) {

					synchronized (this.elementValues) {

						for (Entry<Long, Map<String, String>> e1 : this.elementValues.entrySet()) {

							values.get(e1.getKey()).clear();

							for (Entry<String, String> e2 : e1.getValue().entrySet()) {

								values.get(e1.getKey()).put(e2.getKey(), e2.getValue());
							}
						}
					}
					historicMangerDB.insertHistoric(values, timeEndPeriod - integrationPeriod);
				}
				Thread.sleep(1);
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} finally {

			this.setAlive(false);

			log.info(Thread.currentThread().getName() + " is stopped");
		}
	}

	private void cleanValues() throws IOException {

		if (elementValues == null) {

			throw new IOException("elementValues null");
		}

		synchronized (elementValues) {

			for (Entry<Long, Map<String, String>> e : elementValues.entrySet()) {

				e.getValue().clear();
			}
		}

	}

	public void stop() {

		this.stop = true;
	}

	public void setIntegrationPeriod(Long integrationPeriod) {

		this.integrationPeriod = integrationPeriod;
	}

	public void setHistoricMangerDB(PeriodDataMangerDB historicMangerDB) {

		this.historicMangerDB = historicMangerDB;
	}

	public void setElementValues(Map<Long, Map<String, String>> elementValues) {

		this.elementValues = elementValues;

	}

	public boolean isAlive() {

		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
