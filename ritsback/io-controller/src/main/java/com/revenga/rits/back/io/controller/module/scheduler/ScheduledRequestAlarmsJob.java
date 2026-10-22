package com.revenga.rits.back.io.controller.module.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScheduledRequestAlarmsJob extends ScheduledJob implements Job {

	public ScheduledRequestAlarmsJob() {

		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		log.debug("- EXECUTE SCHEDULED REQUEST ALL ALARMS -");
		
		getGenericModule(context).requestAllAlarms();
	}
}
