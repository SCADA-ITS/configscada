package com.revenga.rits.front.openits.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.bo.ServerBO;
import com.revenga.rits.front.openits.bo.SessionBO;
import com.revenga.rits.front.openits.model.ServerTimeUI;
import com.revenga.rits.front.openits.scheduler.ScheduledServerJob;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ServerManager extends FrontManager {
	
	private static final String SERVER_ENABLED = props.get("server.enabled");
	private static final String SERVER_CRON = props.get("server.cron");
	private static final String SERVER_REQUIRED_PROCCESES = props.get("server.required_processes");
	private static final String SERVER_OPTIONAL_PROCCESES = props.get("server.optional_processes");
	
	private static final long DIFF_SERVER_AND_PROCESSES_TIME_MS = 60000;
	
	@Autowired
	ServerBO serverBO;
	
	
	@PostConstruct
	private void afterLoad() {
		
		this.enabled = StringUtils.equals(SERVER_ENABLED, Boolean.TRUE.toString());
		this.cronExpression = SERVER_CRON;
	}
	
	@Autowired
	SessionBO sessionBO;
	
	@Override
	protected void launchScheduled() {
		
		try {
			
			// First we must get a reference to a scheduler

			scheduler.getContext().put(this.getClass().getSimpleName(), this);
			createJob(ScheduledServerJob.class, this.cronExpression);
			
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
		
		Boolean serverAvailable = checkRequiredProcesses(); 
		
		sessionBO.maintenanceServer(serverAvailable);
		
		if (!serverAvailable.booleanValue()) {
			
			log.error("Some required processes is not working. Check: " + SERVER_REQUIRED_PROCCESES);
		}
		
		if (!checkOptionalProcesses()) {
			
			log.warn("Some optional processes is not working. Check: " + SERVER_OPTIONAL_PROCCESES);
		}
	}
	
	private boolean checkRequiredProcesses() {
		
		boolean res = true;
		
		ServerTimeUI serverTime = serverBO.getServerTime();
		List<Map<String, Object>> processesInfo = serverBO.getProcessesInfo();
		List<String> requiredProcesses = Arrays.asList(SERVER_REQUIRED_PROCCESES.split(","));
		
		if (!CollectionUtils.isEmpty(processesInfo) && !CollectionUtils.isEmpty(requiredProcesses)) {
			
			for (int i = 0; res && i < requiredProcesses.size(); i++) {
				
				String requiredProcess = requiredProcesses.get(i); 
				
				res &= (processesInfo.stream()
						.filter(processInfoMap -> StringUtils
								.containsIgnoreCase((String) processInfoMap.get(ServerBO.FIELD_MAIN), requiredProcess)
								&& ((Long) processInfoMap.get(ServerBO.FIELD_UPDATED_AT)
										+ DIFF_SERVER_AND_PROCESSES_TIME_MS) > serverTime.getEpochMillis())
						.findFirst().orElse(null) != null);
			}
		}
		
		return res;
	}
	
	private boolean checkOptionalProcesses() {
		
		boolean res = true;
		
		ServerTimeUI serverTime = serverBO.getServerTime();
		List<Map<String, Object>> processesInfo = serverBO.getProcessesInfo();
		List<String> optionalProcesses = Arrays.asList(SERVER_OPTIONAL_PROCCESES.split(","));
		
		if (!CollectionUtils.isEmpty(processesInfo) && !CollectionUtils.isEmpty(optionalProcesses)) {
			
			for (int i = 0; res && i < optionalProcesses.size(); i++) {
				
				String requiredProcess = optionalProcesses.get(i); 
				
				res &= (processesInfo.stream()
						.filter(processInfoMap -> StringUtils
								.containsIgnoreCase((String) processInfoMap.get(ServerBO.FIELD_MAIN), requiredProcess)
								&& ((Long) processInfoMap.get(ServerBO.FIELD_UPDATED_AT)
										+ DIFF_SERVER_AND_PROCESSES_TIME_MS) > serverTime.getEpochMillis())
						.findFirst().orElse(null) != null);
			}
		}
		
		return res;
	}
}
