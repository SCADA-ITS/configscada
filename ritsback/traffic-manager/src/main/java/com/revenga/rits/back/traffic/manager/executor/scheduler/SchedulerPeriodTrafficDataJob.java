package com.revenga.rits.back.traffic.manager.executor.scheduler;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.revenga.rits.back.traffic.manager.executor.TrafficExecutor;
import com.revenga.rits.back.traffic.manager.executor.processor.TrafficDataProcessor;
import com.revenga.rits.back.traffic.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SchedulerPeriodTrafficDataJob implements Job {

	private TrafficExecutor trafficExecutor = null;
	
	public SchedulerPeriodTrafficDataJob() {

		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		SchedulerContext schedulerContext = null;

		try {
			
			schedulerContext = context.getScheduler().getContext();

			trafficExecutor = (TrafficExecutor) schedulerContext.get(TrafficExecutor.class.getSimpleName());

			if (trafficExecutor != null) {
				
				if (!trafficExecutor.isWorking()) {
					
					trafficExecutor.setWorking(true);
					
					(new TrafficDataProcessor(trafficExecutor)).processTrafficData();
					
					trafficExecutor.setWorking(false);
				}
				else {
					
					log.warn(this.getClass().getSimpleName() + " => The previous scheduler is still running...");
				}
			}
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			
			if (trafficExecutor != null) {
				
				trafficExecutor.setWorking(false);
			}
		}
	}

}
