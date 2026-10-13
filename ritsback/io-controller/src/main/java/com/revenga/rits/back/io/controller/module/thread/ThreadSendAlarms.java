package com.revenga.rits.back.io.controller.module.thread;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.IOControllerModule;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public class ThreadSendAlarms implements Runnable {

	BlockingQueue<Pair<Element, AlarmConfig>> queueActivateAlarms;
	BlockingQueue<Pair<Element, AlarmConfig>> queueDeactivateAlarms;
	Queue<Pair<Element, AlarmConfig>> queuePreparedActivateAlarms;
	Queue<Pair<Element, AlarmConfig>> queuePreparedDeactivateAlarms;
	
	private static final String FILE_APP_PROPS = "application.properties";
	private static final Long MAX_MESSAGES_QUEUED = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "threadSendAlarms.max_messages_queued"));
	private static final Long MERGE_TIME_MS = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "threadSendAlarms.merge_time_ms"));
	
	boolean stop;
	boolean alive;
	
	IOControllerModule ioControllerModule;
	
	
	public ThreadSendAlarms(IOControllerModule ioControllerModule) {
		super();
		
		queueActivateAlarms = new LinkedBlockingQueue<>();
		queueDeactivateAlarms = new LinkedBlockingQueue<>();
		queuePreparedActivateAlarms = new LinkedList<>();
		queuePreparedDeactivateAlarms = new LinkedList<>();
		
		this.ioControllerModule = ioControllerModule;
	}
	
	public void stop() {
		
		this.stop = true;
	}
	
	public void addActivateAlarm(Element element, AlarmConfig alarmConfig) {
		
		this.queueActivateAlarms.add(new ImmutablePair<>(element, alarmConfig));
	}
	
	public void addDeactivateAlarm(Element element, AlarmConfig alarmConfig) {
		
		this.queueDeactivateAlarms.add(new ImmutablePair<>(element, alarmConfig));
	}
	
	@Override
	public void run() {
		
		Instant start;
		Instant end;
		Duration timeElapsed;
		
		this.alive = true;
		
		log.info(Thread.currentThread().getName() + " is running...");
		
			
		while (!stop) {
			
			try {
	            	
				start = Instant.now();		
				
				do {
					
					Pair<Element, AlarmConfig> queueActivateAlarm = queueActivateAlarms.poll();
					
					if (queueActivateAlarm != null) {
					
						queuePreparedActivateAlarms.add(queueActivateAlarm);
					}
					
					Pair<Element, AlarmConfig> queueDeactivateAlarm = queueDeactivateAlarms.poll();
					
					if (queueDeactivateAlarm != null) {
					
						queuePreparedDeactivateAlarms.add(queueDeactivateAlarm);
					}
					
					Thread.sleep(0, 1);
					
					end = Instant.now();
					timeElapsed = Duration.between(start, end);
					
				} while ((timeElapsed.toMillis() < MERGE_TIME_MS) && (queuePreparedActivateAlarms.size() + queuePreparedDeactivateAlarms.size()) < MAX_MESSAGES_QUEUED && !stop);
				
				if (!stop && (!queuePreparedActivateAlarms.isEmpty() || !queuePreparedDeactivateAlarms.isEmpty())) {
					
					sendPreparedAlarms();
				}
				
				Thread.sleep(1);
				
	        } catch (InterruptedException e) {
	            
	        	log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
	        }
		}
		
		this.alive = false;
		
		log.info(Thread.currentThread().getName() + " is stopped");
	}
	
	private void sendPreparedAlarms() {
		
		SetActivationAlarmsCommand setActivationAlarmsCommandList = new SetActivationAlarmsCommand();
		SetDeactivationAlarmsCommand setDeactivationAlarmsCommandList = new SetDeactivationAlarmsCommand();

		int alarms = 0;		
		
		if (!queuePreparedActivateAlarms.isEmpty()) {
			
			while (!queuePreparedActivateAlarms.isEmpty()) {
				
				Pair<Element, AlarmConfig> queueActivateAlarm = queuePreparedActivateAlarms.poll();
						
				setActivationAlarmsCommandList.add(queueActivateAlarm.getLeft(), queueActivateAlarm.getRight());
				
				alarms++;
			}
			
			EntitiesManager.getInstance().sendCommand(setActivationAlarmsCommandList);
			
			log.debug("ThreadSendAlarms::sendPreparedAlarms => setActivationAlarmsCommandList with "
					+ setActivationAlarmsCommandList.getMapElementAlarmConfigs().size() + " elements and " + alarms
					+ " alarms activated");
		}
		
		alarms = 0;
		
		if (!queuePreparedDeactivateAlarms.isEmpty()) {
		
			while (!queuePreparedDeactivateAlarms.isEmpty()) {
				
				Pair<Element, AlarmConfig> queueDeactivateAlarm = queuePreparedDeactivateAlarms.poll();
				
				setDeactivationAlarmsCommandList.add(queueDeactivateAlarm.getLeft(), queueDeactivateAlarm.getRight());
				
				alarms++;
			}
			
			setDeactivationAlarmsCommandList.setDeactivationUserId( User.USER_SYSTEM);
			setDeactivationAlarmsCommandList.setDeactivationComments("LBL_DEACTIVATED_FOR_RULE");
						
			EntitiesManager.getInstance().sendCommand(setDeactivationAlarmsCommandList);
			
			log.debug("ThreadSendAlarms::sendPreparedAlarms => setDeactivationAlarmsCommandList with "
					+ setDeactivationAlarmsCommandList.getMapElementAlarmConfigs().size() + " elements and " + alarms
					+ " alarms deactivated");
		}
	}
}

