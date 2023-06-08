package com.revenga.rits.front.openits.service;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.bo.SessionBO;
import com.revenga.rits.front.openits.scheduler.ScheduledSessionsJob;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SessionManager extends FrontManager {
	
	private static final String SESSION_ENABLED = props.get("session.enabled");
	private static final String SESSION_CRON = props.get("session.cron");
	
	@PostConstruct
	private void afterLoad() {
		
		this.enabled = StringUtils.equals(SESSION_ENABLED, Boolean.TRUE.toString());
		this.cronExpression = SESSION_CRON;
	}
	
	@Autowired
	SessionBO sessionBO;
	
	@Override
	protected void launchScheduled() {
		
		try {
			
			// First we must get a reference to a scheduler

			scheduler.getContext().put(this.getClass().getSimpleName(), this);
			createJob(ScheduledSessionsJob.class, this.cronExpression);
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void afterStart() {
		// TODO Auto-generated method stub
	}
	
	public void maintenance() {
		
		sessionBO.maintenanceSessions();
	}	
}
