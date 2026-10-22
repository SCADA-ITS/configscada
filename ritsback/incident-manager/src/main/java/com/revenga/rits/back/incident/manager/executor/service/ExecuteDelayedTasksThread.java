package com.revenga.rits.back.incident.manager.executor.service;

import java.time.Instant;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ExecuteDelayedTasksThread implements Runnable {

	private IncidentService incidentService;
	private boolean stop;
	private boolean alive;

	public ExecuteDelayedTasksThread(IncidentService incidentService) {

		super();
		this.incidentService = incidentService;
	}

	@Override
	public void run() {

		try {

			log.info(Thread.currentThread().getName() + " is running");

			while (!stop) {

				List<ImsIncidentReport> incidentReports = incidentService.checkTasksPendingToFinalize(Instant.now().toEpochMilli());
				
				IncidentEntitiesManager.getInstance().publishIncidentReports(Command.ACTION_UPDATE, incidentReports);				

				Thread.sleep(500);
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
