package com.revenga.rits.front.openits.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.revenga.rits.front.openits.util.ConfigProperty;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class FrontManager {
	
	protected static final String FILE_DATASOURCES_CONSTANTS = "datasources.properties";
	
	protected static Map<String, String> props = ConfigProperty.getInstance().getProperties(FILE_DATASOURCES_CONSTANTS);

	protected Scheduler scheduler;
	protected boolean enabled;
	protected String cronExpression;
		
	public void start() {
		
		log.log((this.enabled ? Level.INFO : Level.WARN),
				this.getClass().getSimpleName() + (this.enabled ? " enabled" : " disabled"));
		
		if (this.enabled) {
			
			load();
			
			if (!StringUtils.isEmpty(cronExpression)) {
				
				initQuartzScheduler();
				
				launchScheduled();
				
				// All of the jobs have been added to the scheduler, but none of the jobs
			    // will run until the scheduler has been started
				try {
					if (!scheduler.isStarted()) {
						
						scheduler.start();
					}
				} catch (SchedulerException e1) {
					log.error(e1.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e1));
				}
			}
			
			afterStart();
		}
		else {
			
			log.warn("Disabled " + this.getClass().getSimpleName());
		}
	}
	
	private void initQuartzScheduler() {
		
		try {
			
			SchedulerFactory sf = new StdSchedulerFactory();
			scheduler = sf.getScheduler();
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	protected void createJob(Class<? extends Job> jobClass, String cronExpression) {
		
		try {
			
			// jobs can be scheduled before sched.start() has been called
			// job 1 will run every cron value
			
			JobDetail job = newJob(jobClass).withIdentity("job" + jobClass.getSimpleName(), "group" + jobClass.getSimpleName()).build();
			
			if (!StringUtils.isEmpty(cronExpression)) {
				
				CronTrigger trigger = newTrigger().withIdentity("trigger" + jobClass.getSimpleName(), "group" + jobClass.getSimpleName())
						.withSchedule(cronSchedule(cronExpression)).build();
				
				Date dt = scheduler.scheduleJob(job, trigger);
				
				log.debug(job.getKey() + " has been scheduled to run at: " + dt + " and repeat based on expression: "
						+ trigger.getCronExpression());
			}
			else {
				
				log.debug(job.getKey() + " is not scheduled");
			}
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	protected abstract void load();
	protected abstract void launchScheduled();
	protected abstract void afterStart();
}
