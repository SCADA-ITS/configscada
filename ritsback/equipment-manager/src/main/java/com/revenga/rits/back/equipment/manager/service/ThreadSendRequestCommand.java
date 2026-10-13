package com.revenga.rits.back.equipment.manager.service;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.RequestCommand;
import com.revenga.rits.back.data.core.model.command.RequestCommand.RequestCommandElement;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThreadSendRequestCommand implements Runnable {

	BlockingQueue<Command> queueCommands;
	Queue<Command> queuePreparedCommands;
	
	public static final long MAX_COMMANDS_QUEUED = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send.requestCommand.max_commands_queued"));
	public static final long MAX_MERGE_TIME_MS = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send.requestCommand.merge_time_ms"));
	
	boolean stop;
	boolean alive;
	
	public ThreadSendRequestCommand() {
		super();
		
		queueCommands = new LinkedBlockingQueue<>();
		queuePreparedCommands = new LinkedList<>();
	}
	
	public void stop() {
		
		this.stop = true;
	}
	
	public void add(Command queueCommand) {
		
		this.queueCommands.add(queueCommand);
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
					
					Command queueCommand = queueCommands.poll();
					
					if (queueCommand != null) {
						
						queuePreparedCommands.add(queueCommand);
					}
					
					Thread.sleep(0, 1);
					
					end = Instant.now();
					timeElapsed = Duration.between(start, end);
					
				} while ((timeElapsed.toMillis() < MAX_MERGE_TIME_MS) && queuePreparedCommands.size() < MAX_COMMANDS_QUEUED && !stop);
				
				if (!stop && !queuePreparedCommands.isEmpty()) {
					
					sendPreparedCommands();
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
	
	private void sendPreparedCommands() {
		
		RequestCommand requestCommandResult = new RequestCommand();
	
		while (!queuePreparedCommands.isEmpty()) {
			
			RequestCommand requestCommand = (RequestCommand) queuePreparedCommands.poll();
			
			if (!CollectionUtils.isEmpty(requestCommand.getRequestCommandElements())) {
				
				for (RequestCommandElement requestCommandElement : requestCommand.getRequestCommandElements()) {
					
					requestCommandResult.add(requestCommandElement);
				}
			}
		}
		
		if (!CollectionUtils.isEmpty(requestCommandResult.getRequestCommandElements())) {
		
			EntitiesManager.getInstance().sendCommand(requestCommandResult);
			
			log.debug("ThreadSendRequestCommand::sendPreparedCommands => RequestCommand with "
					+ requestCommandResult.getRequestCommandElements().size() + " items");
		}
	}
}
