package com.revenga.rits.front.openits.scheduler;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.revenga.rits.front.openits.backoffice.BackOfficeException;
import com.revenga.rits.front.openits.backoffice.BackOfficeUtil;
import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeGroovy;
import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeTask;
import com.revenga.rits.front.openits.model.backoffice.CronTask;
import com.revenga.rits.front.openits.service.BackOfficeManager;
import com.revenga.rits.front.openits.util.GroovyManager;
import com.revenga.rits.front.openitsdao.model.BackOffice;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScheduledBackOfficeTaskJob implements Job {
	
	public ScheduledBackOfficeTaskJob() {

		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		SchedulerContext schedulerContext = null;
		
		try {
			
			log.debug(this.getClass().getSimpleName() + " Execute at " + context.getFireTime());
			
			schedulerContext = context.getScheduler().getContext();
			
			BackOfficeManager backOfficeManager = (BackOfficeManager) schedulerContext
					.get(BackOfficeManager.class.getSimpleName());
			
			if (backOfficeManager != null) {
			
				JobDetail jobDetail = context.getJobDetail();
				
				if (jobDetail != null) {
					
					BackOffice backOffice = backOfficeManager.getBackOffice(jobDetail.getKey().getName());
					CronTask cronTask = backOfficeManager.getCronTask(jobDetail.getKey().getName());
					GroovyManager groovyManager = backOfficeManager.getGroovyManager();
					String baseDir = backOfficeManager.getBaseDir();
					
					log.debug(String.format("%s task for backoffice %d executing...", cronTask.getName(), backOffice.getId()));
					
					if (ObjectUtils.allNotNull(backOffice, cronTask, groovyManager, baseDir)) {
						
						onExecute(backOffice, cronTask, groovyManager, baseDir);
					}
					
					log.debug(String.format("%s task for backoffice %d finished", cronTask.getName(), backOffice.getId()));
				}
			}
			
			log.debug(this.getClass().getSimpleName() + " Next fire time at " + context.getNextFireTime());			
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	@NonNull
	private boolean onExecute(BackOffice backOffice, CronTask cronTask, GroovyManager groovyManager, String baseDir) {
		
		boolean res = false;
		
		try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
				AES.decrypt(backOffice.getPassword()))) {
			
			AbstractBackOfficeGroovy backOfficeGroovy = BackOfficeUtil.newGroovyInstance(
					groovyManager, cronTask, conn, backOffice.getSchema(), null,
					null, null);
			
			if (backOfficeGroovy instanceof AbstractBackOfficeTask) {
				
				AbstractBackOfficeTask backOfficeTaskCloned = (AbstractBackOfficeTask) backOfficeGroovy.clone();
				
				backOfficeTaskCloned.setBackOffice(backOffice);
				backOfficeTaskCloned.setBaseDir(baseDir);
				backOfficeTaskCloned.setParams(cronTask.getParams());
				
				res = backOfficeTaskCloned.onExecute();
									
				if (!res) {
					
					throw new BackOfficeException(
							String.format("Task %s on backoffice: %d fails.", cronTask.getName(),
									backOffice.getId()));
				}
			}
			
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return res;
	}
}
