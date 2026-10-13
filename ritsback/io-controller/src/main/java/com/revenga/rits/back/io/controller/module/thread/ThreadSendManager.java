package com.revenga.rits.back.io.controller.module.thread;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.IOControllerModule;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThreadSendManager {

	@Data
	private class SendThread {
	
		private ThreadSendAlarms threadSendAlarms;
		private ThreadSendMeasures threadSendMeasures;
	}
	
	private Map<IOControllerModule, SendThread> sendThreads = Collections.synchronizedMap(new HashMap<>());
		
	private static ThreadSendManager instance;

	public static ThreadSendManager getInstance() {

		if (instance == null) {

			instance = new ThreadSendManager();
		}

		return instance;
	}
	
	private ThreadSendManager() {
		
		super();
	}
	
	public void stopThreads(IOControllerModule ioControllerModule) {
		
		SendThread sendThread = null;
		
		try {
			
			sendThread = sendThreads.get(ioControllerModule);
			
			if (sendThread != null) {
				
				if (sendThread.getThreadSendAlarms() != null) {
					
					sendThread.getThreadSendAlarms().stop();
				}
				
				if (sendThread.getThreadSendMeasures() != null) {
				
					sendThread.getThreadSendMeasures().stop();
				}
				
				Instant start = Instant.now();
				Duration timeElapsed;
				
				boolean isAnyRunning = false;
				
				do {
					
					isAnyRunning = false;
					
					if (sendThread.getThreadSendAlarms() != null) {
						
						isAnyRunning |= sendThread.getThreadSendAlarms().isAlive();
					}
					
					if (sendThread.getThreadSendMeasures() != null) {
					
						isAnyRunning |= sendThread.getThreadSendMeasures().isAlive();
					}
					
					Thread.sleep(1L);
					
					timeElapsed = Duration.between(start, Instant.now());
					
				} while (timeElapsed.toMillis() < 5000L && isAnyRunning);
				
				sendThreads.remove(ioControllerModule);
			}
		
		} catch (InterruptedException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}
	}
	
	public void addActivateAlarm(IOControllerModule ioControllerModule, Element element, AlarmConfig alarmConfig) {
		
		SendThread sendThread = null;
		
		sendThread = sendThreads.get(ioControllerModule);
		
		if (sendThread == null) {
			
			sendThread = new SendThread();
			
			sendThreads.put(ioControllerModule, sendThread);
		}

		ThreadSendAlarms threadSendAlarms = sendThread.getThreadSendAlarms();
		
		if (threadSendAlarms == null) {
			
			threadSendAlarms = new ThreadSendAlarms(ioControllerModule);
		
			sendThread.setThreadSendAlarms(threadSendAlarms);
			(new Thread(sendThread.getThreadSendAlarms(), ThreadSendAlarms.class.getSimpleName() + "_" + ioControllerModule.getAlias())).start();
		}
		
		threadSendAlarms.addActivateAlarm(element, alarmConfig);
	}
	
	public void addDeactivateAlarm(IOControllerModule ioControllerModule, Element element, AlarmConfig alarmConfig) {
		
		SendThread sendThread = null;
		
		sendThread = sendThreads.get(ioControllerModule);
		
		if (sendThread == null) {
			
			sendThread = new SendThread();
			
			sendThreads.put(ioControllerModule, sendThread);
		}

		ThreadSendAlarms threadSendAlarms = sendThread.getThreadSendAlarms();
		
		if (threadSendAlarms == null) {
			
			threadSendAlarms = new ThreadSendAlarms(ioControllerModule);
		
			sendThread.setThreadSendAlarms(threadSendAlarms);
			(new Thread(sendThread.getThreadSendAlarms(), ThreadSendAlarms.class.getSimpleName() + "_" + ioControllerModule.getAlias())).start();
		}
		
		threadSendAlarms.addDeactivateAlarm(element, alarmConfig);
	}
	
	public void addMeasure(IOControllerModule ioControllerModule, Element element) {
		
		SendThread sendThread = null;
		
		sendThread = sendThreads.get(ioControllerModule);
		
		if (sendThread == null) {
			
			sendThread = new SendThread();
			
			sendThreads.put(ioControllerModule, sendThread);
		}

		ThreadSendMeasures threadSendMeasures = sendThread.getThreadSendMeasures();
		
		if (threadSendMeasures == null) {
			
			threadSendMeasures = new ThreadSendMeasures(ioControllerModule);
		
			sendThread.setThreadSendMeasures(threadSendMeasures);
			(new Thread(sendThread.getThreadSendMeasures(), ThreadSendMeasures.class.getSimpleName() + "_" + ioControllerModule.getAlias())).start();
		}
		
		threadSendMeasures.add(element);
	}
}
