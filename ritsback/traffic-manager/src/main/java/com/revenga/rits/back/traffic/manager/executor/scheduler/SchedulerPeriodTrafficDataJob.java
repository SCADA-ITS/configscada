package com.revenga.rits.back.traffic.manager.executor.scheduler;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.revenga.rits.back.manager.lib.executor.GenericExecutor;
import com.revenga.rits.back.traffic.manager.executor.TrafficExecutor;
import com.revenga.rits.back.traffic.manager.executor.processor.TrafficDataProcessor;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class SchedulerPeriodTrafficDataJob implements Job {

	private GenericExecutor genericExecutor = null;
	private TrafficDataProcessor trafficDataProcessor;
	
	public SchedulerPeriodTrafficDataJob() {

		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		SchedulerContext schedulerContext = null;

		try {
			schedulerContext = context.getScheduler().getContext();

			genericExecutor = (TrafficExecutor) schedulerContext.get(TrafficExecutor.class.getSimpleName());

			if (genericExecutor != null) {

				trafficDataProcessor = new TrafficDataProcessor((TrafficExecutor) genericExecutor);
				
				trafficDataProcessor.processTrafficData();
			}
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

	}

}
