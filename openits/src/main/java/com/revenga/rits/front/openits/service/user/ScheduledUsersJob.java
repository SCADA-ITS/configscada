package com.revenga.rits.front.openits.service.user;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScheduledUsersJob implements Job {
	
	public ScheduledUsersJob() {

		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		SchedulerContext schedulerContext = null;
		
		try {
			
			log.debug(this.getClass().getSimpleName() + "Execute at " + context.getFireTime()); ;
			
			schedulerContext = context.getScheduler().getContext();
			
			SessionService sessionService = (SessionService) schedulerContext
					.get(SessionService.class.getSimpleName());

			sessionService.maintenanceSessions();

			log.debug(this.getClass().getSimpleName() + "Next fire time at " + context.getNextFireTime()); ;			
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		
	}
}
