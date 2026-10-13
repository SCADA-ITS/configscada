package com.revenga.rits.back.incident.manager.executor.service;

import java.time.Instant;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.SystemParam;
import com.revenga.rits.back.data.core.model.SystemParamValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.entities.lib.repository.SystemParamValueRepository;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MaintenacePendingIncidentsThread implements Runnable {

	private IncidentService incidentService;
	private boolean stop;
	private boolean alive;

	public MaintenacePendingIncidentsThread(IncidentService incidentService) {

		super();
		this.incidentService = incidentService;
	}

	@Override
	public void run() {

		Long incidentPendingToConfirm = null;

		try {

			log.info(Thread.currentThread().getName() + " is running");

			SystemParamValue systemParamValuePeriod = (new SystemParamValueRepository(false))
					.get(Long.valueOf(SystemParam.SYSTEM_PARAM_WAITING_TIME_UNTIL_AUTO_CONFIRM_INCIDENTS), false);

			Long timeToConfirmIncidentsS = systemParamValuePeriod != null && systemParamValuePeriod.getValue() != null
					? Long.parseLong(systemParamValuePeriod.getValue())
					: null;

			Long count = 0L;

			while (!stop) {

				Long now = Instant.now().getEpochSecond();
				
				if (count % 60 == 0) {

					List<ImsIncidentReport> incidentsExpired = incidentService
							.checkIncidentExpired(now);

					IncidentEntitiesManager.getInstance().publishIncidentReports(Command.ACTION_UPDATE,
							incidentsExpired);

					count = 0L;
				}
				count++;

				List<ImsIncidentReport> incidentReports = incidentService
						.checkIncidentPendingToConfirm(now, timeToConfirmIncidentsS);

				IncidentEntitiesManager.getInstance().publishIncidentReports(Command.ACTION_UPDATE, incidentReports);

				Long newIncidentPendingToConfirm = IncidentEntitiesManager.getInstance().getNumIncidentsPendingToConfirm();

				if (newIncidentPendingToConfirm != null
						&& !newIncidentPendingToConfirm.equals(incidentPendingToConfirm)) {

					incidentPendingToConfirm = newIncidentPendingToConfirm;

					IncidentEntitiesManager.getInstance().putGenericData(incidentPendingToConfirm.toString());
				}

				Thread.sleep(1000);
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		    Thread.currentThread().interrupt();

		} finally {

			this.setAlive(false);

			log.info(Thread.currentThread().getName() + " is stopped");
		}
	}

	public void stop() {

		this.stop = true;
	}

	public boolean isAlive() {

		return alive;
	}

	public void setAlive(boolean alive) {

		this.alive = alive;
	}

	public boolean getStop() {

		return this.stop;
	}

}
