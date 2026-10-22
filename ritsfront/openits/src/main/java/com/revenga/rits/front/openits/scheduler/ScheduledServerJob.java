package com.revenga.rits.front.openits.scheduler;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.revenga.rits.front.openits.service.ServerManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScheduledServerJob implements Job {
	
	public ScheduledServerJob() {

		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		SchedulerContext schedulerContext = null;
		
		try {
			
			log.debug(this.getClass().getSimpleName() + "Execute at " + context.getFireTime()); ;
			
			schedulerContext = context.getScheduler().getContext();
			
			ServerManager serverManager = (ServerManager) schedulerContext
					.get(ServerManager.class.getSimpleName());

			serverManager.maintenance();

			log.debug(this.getClass().getSimpleName() + "Next fire time at " + context.getNextFireTime()); ;			
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		
	}
}
