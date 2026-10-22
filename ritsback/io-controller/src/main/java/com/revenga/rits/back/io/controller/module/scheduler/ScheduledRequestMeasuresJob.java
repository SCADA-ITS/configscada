package com.revenga.rits.back.io.controller.module.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScheduledRequestMeasuresJob extends ScheduledJob implements Job {

	public ScheduledRequestMeasuresJob() {

		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		log.debug("- EXECUTE SCHEDULED REQUEST ALL MEASURES -");
		
		getGenericModule(context).requestAllMeasures();
	}
}
