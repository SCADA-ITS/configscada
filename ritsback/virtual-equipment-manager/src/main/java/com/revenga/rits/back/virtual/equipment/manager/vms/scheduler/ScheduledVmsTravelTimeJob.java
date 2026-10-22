package com.revenga.rits.back.virtual.equipment.manager.vms.scheduler;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;

import com.revenga.rits.back.virtual.equipment.manager.vms.VmsTravelTimeService;
import com.revenga.rits.back.virtual.equipment.manager.vms.processor.VmsTravelTimeProcessor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScheduledVmsTravelTimeJob implements Job {

	VmsTravelTimeService vmsTravelTimeService = null;

	public ScheduledVmsTravelTimeJob() {

		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		SchedulerContext schedulerContext = null;

		try {

			schedulerContext = context.getScheduler().getContext();

			vmsTravelTimeService = (VmsTravelTimeService) schedulerContext
					.get(VmsTravelTimeService.class.getSimpleName());

			if (vmsTravelTimeService != null) {

				if (!vmsTravelTimeService.isRunning()) {

					vmsTravelTimeService.setRunning(true);

					Instant instant = Instant.now();

					VmsTravelTimeProcessor vmsTravelTimeProcessor = new VmsTravelTimeProcessor();
					
					vmsTravelTimeProcessor.process();

					log.info(this.getClass().getSimpleName() + " => Total time for process vms travel time = "
							+ Duration.between(instant, Instant.now()).toMillis() + " ms.");

					vmsTravelTimeService.setRunning(false);

				} else {

					log.warn(this.getClass().getSimpleName() + " => The previous scheduler is still running...");
				}
			}

		} catch (Exception e) {
			
			if (vmsTravelTimeService != null) {
				
				vmsTravelTimeService.setRunning(false);
			}

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
}
