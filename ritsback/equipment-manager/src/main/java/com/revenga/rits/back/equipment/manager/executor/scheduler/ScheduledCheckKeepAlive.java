package com.revenga.rits.back.equipment.manager.executor.scheduler;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.revenga.rits.back.equipment.manager.executor.KeepAliveExecutor;
import com.revenga.rits.back.equipment.manager.service.ElementStatesManager;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScheduledCheckKeepAlive implements Job {

	GenericExecutor genericExecutor = null;

	private static final String MAX_TIME_OUT_ALLOWED_SECONDS = "max_time_out_allowed_seconds";
	
	public ScheduledCheckKeepAlive() {

		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		SchedulerContext schedulerContext = null;

		try {

			schedulerContext = context.getScheduler().getContext();

			genericExecutor = (KeepAliveExecutor) schedulerContext
					.get(KeepAliveExecutor.class.getSimpleName());

			if (!ElementStatesManager.getInstance().isRunning() && genericExecutor != null) {

				ElementStatesManager.getInstance().setRunning(true);
				Instant instant = Instant.now();

				process();

				ElementStatesManager.getInstance().setRunning(false);
				
				log.info(this.getClass().getSimpleName() + " => Total time for check Keep Alive  = "
						+ Duration.between(instant, Instant.now()).toMillis() + " ms.");
			} else {

					log.warn(this.getClass().getSimpleName() + " => The previous scheduler is still running...");
			}

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			ElementStatesManager.getInstance().setRunning(false);
		}
	}

	private void process() {

		String maxTimeAllowed = genericExecutor.getParamValue(MAX_TIME_OUT_ALLOWED_SECONDS);
		
		if (maxTimeAllowed != null) {
			
			try {
				
				Long maxTimeOutAllowedSeconds = Long.valueOf(maxTimeAllowed);
				
				if (maxTimeOutAllowedSeconds != null) {
					
					EntitiesManager.getInstance().checkKeepAlive(maxTimeOutAllowedSeconds);
				}
				
			} catch(NumberFormatException e) {
				
				log.error("Max Time Out Allowed Seconds to check keep alive not valid Value.");
			}
		}
	}
	
}
