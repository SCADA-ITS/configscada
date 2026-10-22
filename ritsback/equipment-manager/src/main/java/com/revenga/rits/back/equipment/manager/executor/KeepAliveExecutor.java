package com.revenga.rits.back.equipment.manager.executor;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.command.SetKeepAliveDriverInstanceCommand;
import com.revenga.rits.back.equipment.manager.executor.scheduler.ScheduledCheckKeepAlive;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class KeepAliveExecutor extends GenericExecutor {
	
	private static final String CRON_CHECK_KEEP_ALIVE = "cron_check_keep_alive";

	private String cronCheckKeepALive = "";
	
	public KeepAliveExecutor(Executor executor, short thread) {

		super(executor, thread);
		
	}

	@Override
	protected Object prepare(Object queueItem) {

		try {

			if (queueItem instanceof SetKeepAliveDriverInstanceCommand) {

				SetKeepAliveDriverInstanceCommand setKeepAliveDriverInstanceCommand = (SetKeepAliveDriverInstanceCommand) queueItem;

				EntitiesManager.getInstance().updateKeepAliveDriverData(setKeepAliveDriverInstanceCommand);
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return null;
	}

	@Override
	protected Object publish() {
			
		return null;
	}

	@Override
	protected void initialize() {
		
		log.debug(this.getClass().getSimpleName() + "[" + threadNum + "] now is running");
		launchKeepAliveScheduller();
		
	}
	
	@Override
	protected void end() {
		
		// Without implementation
	}

	@Override
	protected void evaluate() {
		
		// Without implementation
	}
	
	private void launchKeepAliveScheduller() {

		try {

			cronCheckKeepALive = getParamValue(CRON_CHECK_KEEP_ALIVE);

			if (!StringUtils.isEmpty(cronCheckKeepALive)) {

				EntitiesManager.getInstance().getScheduler().getContext().put(this.getClass().getSimpleName(), this);
				createJob(ScheduledCheckKeepAlive.class, cronCheckKeepALive);

				// All of the jobs have been added to the scheduler, but none of the jobs
				// will run until the scheduler has been started
				if (!EntitiesManager.getInstance().getScheduler().isStarted()) {

					EntitiesManager.getInstance().getScheduler().start();
				}
			} else {

				log.warn("Check Keep Alive is disabled");
			}

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

	}
	
	private void createJob(Class<? extends Job> jobClass, String cronExpression) {

		try {

			JobDetail job = newJob(jobClass)
					.withIdentity("job" + jobClass.getSimpleName(), "group" + jobClass.getSimpleName()).build();

			if (!StringUtils.isEmpty(cronExpression)) {

				CronTrigger trigger = newTrigger()
						.withIdentity("trigger" + jobClass.getSimpleName(), "group" + jobClass.getSimpleName())
						.withSchedule(cronSchedule(cronExpression)).build();

				Date dt = EntitiesManager.getInstance().getScheduler().scheduleJob(job, trigger);

				log.debug(job.getKey() + " has been scheduled to run at: " + dt + " and repeat based on expression: "
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