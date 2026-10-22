package com.revenga.rits.back.incident.manager.executor.scheduler;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.incident.manager.executor.service.IncidentService;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SchedulerDeleteIncidentsJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		log.info("- EXECUTE SCHEDULED DELETE INCIDENT JOB -");

		SchedulerContext schedulerContext;

		try {
			schedulerContext = context.getScheduler().getContext();
			IncidentService incidentService = (IncidentService) schedulerContext
					.get(IncidentService.class.getSimpleName());

			if (!incidentService.isRunning()) {

				incidentService.setRunning(true);
				List<ImsIncidentReport> incidentReports = incidentService.checkIncidentPendingToDelete();

				IncidentEntitiesManager.getInstance().publishIncidentReports(Command.ACTION_DELETE, incidentReports);
				incidentService.setRunning(false);
				
			} else {

				log.warn(this.getClass().getSimpleName() + " => The previous scheduler is still running...");
			}

		} catch (

		SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

	}

}
