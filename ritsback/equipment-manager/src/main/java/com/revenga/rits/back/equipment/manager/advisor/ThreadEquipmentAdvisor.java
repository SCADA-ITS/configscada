package com.revenga.rits.back.equipment.manager.advisor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.equipment.manager.advisor.model.EquipmentMessage;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThreadEquipmentAdvisor implements Runnable {

	BlockingQueue<EquipmentMessage> queueEquipmentMessages;
		
	boolean stop;
	boolean alive;
	
	public ThreadEquipmentAdvisor() {
		super();
		
		queueEquipmentMessages = new LinkedBlockingQueue<>();
	}
	
	public void stop() {
		
		this.stop = true;
	}
	
	public void add(EquipmentMessage equipmentMessage) {
		
		try {
			
			EquipmentMessage equipmentMessageCloned = SerializationUtils.clone(equipmentMessage);
			
			if (equipmentMessageCloned != null) {
				
				this.queueEquipmentMessages.add(equipmentMessageCloned);
			}
		
		} catch (SerializationException  e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	@Override
	public void run() {
		
		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		this.alive = true;
		EquipmentAdvisorProcessor equipmentAdvisorProcessor = new EquipmentAdvisorProcessor();
		
		log.info(Thread.currentThread().getName() + " is running...");
		
		while (!stop) {
			
			try {
	            	
				EquipmentMessage equipmentMessage = queueEquipmentMessages.poll();
				
				if (equipmentMessage != null) {
					
					Runnable task = () -> equipmentAdvisorProcessor.process(equipmentMessage);
					
					executor.submit(task);
				}
				
				Thread.sleep(0, 1);
				
	        } catch (InterruptedException e) {
	            
	        	log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
	        }
		}
		
		this.alive = false;
		
		log.info(Thread.currentThread().getName() + " is stopped");
	}
}
