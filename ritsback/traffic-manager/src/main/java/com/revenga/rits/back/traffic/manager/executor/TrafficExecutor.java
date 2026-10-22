package com.revenga.rits.back.traffic.manager.executor;

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
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;
import com.revenga.rits.back.traffic.manager.executor.scheduler.SchedulerPeriodTrafficDataJob;
import com.revenga.rits.back.traffic.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TrafficExecutor extends GenericExecutor {

	public TrafficExecutor(Executor executor, short thread) {

		super(executor, thread);
	}
	
	@Override
	public boolean isWorking() {
		
		return this.working;
	}
	
	public void setWorking(boolean value) {
		
		this.working = value;
	}

	@Override
	protected Object prepare(Object queueItem) {

		return null;
	}

	@Override
	protected Object publish() {

		return null;
	}

	@Override
	protected void initialize() {

		if (threadNum == 0) {

			launchPeriodTrafficData();
		}
	}

	private void launchPeriodTrafficData() {

		try {
			String cronPeriodTrafficData = EntitiesManager.getInstance().getCronExpression();

			if (!StringUtils.isEmpty(cronPeriodTrafficData)) {

				EntitiesManager.getInstance().getScheduler().getContext().put(this.getClass().getSimpleName(), this);
				createJob(SchedulerPeriodTrafficDataJob.class, cronPeriodTrafficData);

				if (!EntitiesManager.getInstance().getScheduler().isStarted()) {

					EntitiesManager.getInstance().getScheduler().start();
				}
			} else {

				log.error("Not found  cronPeriodTrafficData");
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

	@Override
	protected void evaluate() {
		// TODO Auto-generated method stub

	}

}
