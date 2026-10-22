package com.revenga.rits.back.io.controller.module.scheduler;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.revenga.rits.back.io.controller.module.GenericModule;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class ScheduledJob {

	protected ScheduledJob() {

		super();
	}

	protected GenericModule getGenericModule(JobExecutionContext context) {
		
		GenericModule genericModule = null;
			
		try {
			
			String jobName = context.getJobDetail().getKey().getName();
			
			String[] jobNameSplitted = jobName.split(":");
			
			if (!ArrayUtils.isEmpty(jobNameSplitted) && jobNameSplitted.length == 2 && !StringUtils.isEmpty(jobNameSplitted[1])) {
				
				SchedulerContext schedulerContext = null;
				
				schedulerContext = context.getScheduler().getContext();
				
				genericModule = (GenericModule) schedulerContext
						.get(jobNameSplitted[1]);
			}
		} 
		catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return genericModule;
	}
}
