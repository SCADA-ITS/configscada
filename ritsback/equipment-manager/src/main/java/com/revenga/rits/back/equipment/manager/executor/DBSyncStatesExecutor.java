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
import com.revenga.rits.back.equipment.manager.executor.scheduler.ScheduledDBSyncStates;
import com.revenga.rits.back.equipment.manager.service.ElementStatesManager;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DBSyncStatesExecutor extends GenericExecutor {

	private static final String CRON_SAVE_ELEMENT_STATES = "cron_save_element_states";

	private String cronSaveElementStates = "";

	public DBSyncStatesExecutor(Executor executor, short threadNum) {
		super(executor, threadNum);
	}

	@Override
	protected Object prepare(Object queueItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object publish() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void evaluate() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initialize() {

		if (threadNum == 0) {

			ElementStatesManager.getInstance().init();
			launchDBSyncStates();
		}

		log.debug(this.getClass().getSimpleName() + "[" + threadNum + "] now is running");
	}

	@Override
	protected void end() {

		if (threadNum == 0) {

			try {

				log.debug("Shutdown scheduled jobs ...");

				EntitiesManager.getInstance().getScheduler().shutdown(true);

				log.debug("Shutdown Complete");
				log.debug("Executed "
						+ EntitiesManager.getInstance().getScheduler().getMetaData().getNumberOfJobsExecuted()
						+ " jobs.");

			} catch (SchedulerException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

	}
	
	private void launchDBSyncStates() {

		try {

			cronSaveElementStates = getParamValue(CRON_SAVE_ELEMENT_STATES);

			if (!StringUtils.isEmpty(cronSaveElementStates)) {

				EntitiesManager.getInstance().getScheduler().getContext().put(this.getClass().getSimpleName(), this);
				createJob(ScheduledDBSyncStates.class, cronSaveElementStates);

				// All of the jobs have been added to the scheduler, but none of the jobs
				// will run until the scheduler has been started
				if (!EntitiesManager.getInstance().getScheduler().isStarted()) {

					EntitiesManager.getInstance().getScheduler().start();
				}
			} else {

				log.warn("Sync states in db is disabled");
			}

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

	}

	private void createJob(Class<? extends Job> jobClass, String cronExpression) {

		try {

			// jobs can be scheduled before sched.start() has been called
			// job 1 will run every cron value

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
