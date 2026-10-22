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
import com.revenga.rits.back.data.core.model.SystemParamValue;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.repository.SystemParamValueRepository;
import com.revenga.rits.back.equipment.manager.executor.scheduler.ScheduledRecognizeAlarmsJob;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UnrecognizedAlarmsExecutor extends GenericExecutor {

	private static final String CRON_RECOGNIZE_ALARMS = "cron_recognize_alarms";
	
	protected static final String MAX_UNRECOGNIZED_ALARMS_ALLOWED = "maxUnrecognizedAlarmsAllowed";
	
	protected static final String FILE_CONSTANTS = "application.properties";
	
	private static final long MAX_UNRECOGNIZED_ALARMS_ALLOWED_SYSTEM_PARAM_ID = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(FILE_CONSTANTS, "max_unrecognized_alarms_allowed"));

	public UnrecognizedAlarmsExecutor(Executor executor, short threadNum) {
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

			launchRecognizeAlarms();
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
	
	private void launchRecognizeAlarms() {

		try {

			String cronRecognizeAlarms = getParamValue(CRON_RECOGNIZE_ALARMS);

			Long maxUnrecognizedAlarmsAllowed = getMaxUnrecognizedAlarmsAllowed();
			
			if (!StringUtils.isEmpty(cronRecognizeAlarms) && maxUnrecognizedAlarmsAllowed != null) {
				EntitiesManager.getInstance().getScheduler().getContext().put(this.getClass().getSimpleName(), this);
				EntitiesManager.getInstance().getScheduler().getContext().put(MAX_UNRECOGNIZED_ALARMS_ALLOWED, maxUnrecognizedAlarmsAllowed);
				createJob(ScheduledRecognizeAlarmsJob.class, cronRecognizeAlarms);

				// will run until the scheduler has been started
				if (!EntitiesManager.getInstance().getScheduler().isStarted()) {

					EntitiesManager.getInstance().getScheduler().start();
				}
			} else {

				log.warn("Auto recognize alarms is disabled");
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

				log.info(job.getKey() + " is not scheduled");
			}

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public Long getMaxUnrecognizedAlarmsAllowed() {
		
		SystemParamValueRepository systemParamValueRepository = (SystemParamValueRepository) EntitiesManager.getInstance()
				.getRepository(SystemParamValue.class);
		
		Long maxUnrecognizedAlarmsAllowed = null;
		
		if (systemParamValueRepository != null) {

			SystemParamValue systemParamValue = systemParamValueRepository
					.get(MAX_UNRECOGNIZED_ALARMS_ALLOWED_SYSTEM_PARAM_ID, false);
			
			if (systemParamValue != null && systemParamValue.getValue() != null) {
				
				try {

					maxUnrecognizedAlarmsAllowed = Long.valueOf(systemParamValue.getValue());
					
				} catch(NumberFormatException e) {
					
					log.error("Cannot get maxUnrecognizedAlarmsAllowed, wrong number format");
				}
			}	
		}
		
		return maxUnrecognizedAlarmsAllowed;
	}
}
