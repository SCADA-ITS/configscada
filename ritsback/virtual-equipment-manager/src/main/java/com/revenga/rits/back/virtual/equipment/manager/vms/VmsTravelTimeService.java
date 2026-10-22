package com.revenga.rits.back.virtual.equipment.manager.vms;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.revenga.rits.back.manager.lib.executor.GenericExecutor;
import com.revenga.rits.back.virtual.equipment.manager.vms.scheduler.ScheduledVmsTravelTimeJob;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class VmsTravelTimeService {

	protected static final String FILE_CONSTANTS = "constants.properties";
	
	@Getter
	@Setter
	private boolean running;

	private Scheduler scheduler;

	GenericExecutor genericExecutor;
	@Getter
	Long queueId;

	public VmsTravelTimeService(VmsTravelTimeExecutor vmsExecutor) {

		this.genericExecutor = vmsExecutor;
		
	}
	
	public Boolean init(String cronExpression) {

		Boolean ok = true;
		
		launchJob(cronExpression);

		return ok;
	}

	public Boolean deInit() {

		Boolean ok = true;

		try {
			if (scheduler != null) {

				log.debug("Shutdown scheduled jobs ...");
				scheduler.shutdown(true);
				log.debug("Shutdown Complete");
				log.debug("Executed " + scheduler.getMetaData().getNumberOfJobsExecuted() + " jobs.");
			}

		} catch (SchedulerException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			ok = false;
		}

		return ok;
	}
	
	private void launchJob(String cronExpression) {

		if (!StringUtils.isEmpty(cronExpression)) {

			if (scheduler == null) {

				initQuartzScheduler();
			}
			createJob(ScheduledVmsTravelTimeJob.class, cronExpression);

		} else {

			log.error("Not found cronMaintenacePmQueue");
		}
	}

	private boolean initQuartzScheduler() {

		Boolean ok = true;

		SchedulerFactory sf = new StdSchedulerFactory();
		try {

			scheduler = sf.getScheduler();
			scheduler.getContext().put(this.getClass().getSimpleName(), this);
			scheduler.start();

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			ok = false;
		}

		return ok;
	}

	private void createJob(Class<? extends Job> jobClass, String cronExpression) {

		try {
			JobDetail job = newJob(jobClass)
					.withIdentity("job_" + jobClass.getSimpleName(), "group_" + jobClass.getSimpleName()).build();

			if (!StringUtils.isEmpty(cronExpression)) {

				CronTrigger trigger = newTrigger()
						.withIdentity("trigger_" + jobClass.getSimpleName(), "group_" + jobClass.getSimpleName())
						.withSchedule(cronSchedule(cronExpression)).build();

				Date dt = scheduler.scheduleJob(job, trigger);

				log.info(job.getKey() + " has been scheduled to run at: " + dt + " and repeat based on expression: "
						+ trigger.getCronExpression());

			} else {

				log.debug(job.getKey() + " is not scheduled");
			}

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
}
