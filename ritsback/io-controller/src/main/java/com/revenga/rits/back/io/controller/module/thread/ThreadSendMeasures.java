package com.revenga.rits.back.io.controller.module.thread;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.IOControllerModule;
import com.revenga.rits.back.data.core.model.command.SetElementMeasuresChangeCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public class ThreadSendMeasures implements Runnable {

	BlockingQueue<BasicElement> queueMeasures;
	Queue<BasicElement> queuePreparedMeasures;
	
	private static final String FILE_APP_PROPS = "application.properties";
	private static final Long MAX_MESSAGES_QUEUED = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "threadSendMeasures.max_messages_queued"));
	private static final Long MERGE_TIME_MS = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "threadSendMeasures.merge_time_ms"));
	
	boolean stop;
	boolean alive;
	
	IOControllerModule ioControllerModule;
	
	public ThreadSendMeasures(IOControllerModule ioControllerModule) {
		super();
		
		queueMeasures = new LinkedBlockingQueue<>();
		queuePreparedMeasures = new LinkedList<>();
		
		this.ioControllerModule = ioControllerModule;
	}
	
	public void stop() {
		
		this.stop = true;
	}
	
	public void add(BasicElement queueMeasure) {
		
		this.queueMeasures.add(queueMeasure);
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
					
					BasicElement queueMeasure = queueMeasures.poll();
					
					if (queueMeasure != null) {
					
						queuePreparedMeasures.add(queueMeasure);
					}
					
					Thread.sleep(0, 1);
					
					end = Instant.now();
					timeElapsed = Duration.between(start, end);
					
				} while ((timeElapsed.toMillis() < MERGE_TIME_MS) && queuePreparedMeasures.size() < MAX_MESSAGES_QUEUED && !stop);
				
				if (!stop && !queuePreparedMeasures.isEmpty()) {
					
					sendPreparedMeasures();
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
	
	private void sendPreparedMeasures() {
		
		SetElementMeasuresChangeCommand setElementMeasuresChangeCommand = new SetElementMeasuresChangeCommand();
		int measures = 0;		
		
		while (!queuePreparedMeasures.isEmpty()) {
			
			Element elem = (Element) queuePreparedMeasures.poll();
			
			measures += elem.getElementValues().size();
			
			setElementMeasuresChangeCommand.addElement(elem);
		}
		
		EntitiesManager.getInstance().sendCommand(setElementMeasuresChangeCommand);
		
		log.debug("ThreadSendMeasures::sendPreparedMeasures => setElementMeasuresChangeCommand with "
				+ setElementMeasuresChangeCommand.getElements().size() + " elements and " + measures
				+ " measures changed");
	}
}
