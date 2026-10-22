package com.revenga.rits.back.manager.lib.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors;
import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Subscriptors.Subscriptor;
import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.command.Command;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ExecutorService {

	@Autowired
	RouterService routerService;
	
	public void load(Executors executors) {
		
		try {
			
			if (executors != null) {
				
				List<Executor> executorList = executors.getExecutor();
				
				if (!CollectionUtils.isEmpty(executorList)) {
										
					String threadNameRouterService = "Thread-" + routerService.getClass().getSimpleName();
					
					(new Thread(routerService, threadNameRouterService)).start();
				}
			}

		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public void stop() {

		try {
			
			routerService.stop();
			
			Instant start;
			Duration timeElapsed;
			boolean stopped;
			start = Instant.now();
			
			do {
				
				stopped = !routerService.isAlive();
		
				Thread.sleep(1L);
				
				timeElapsed = Duration.between(start, Instant.now());
				
			} while (timeElapsed.toMillis() < 5000L && !stopped);
			
		} catch (InterruptedException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}
	}
	
	public void send(Subscriptor subscriptor, String channel) {
		
		Object objFromChannel;
	
		try {
			
			objFromChannel = CacheManager.getInstance().getFromChannel(channel);
			
			if (objFromChannel != null) {
			
				if (objFromChannel instanceof BasicElement) {
					
					routerService.add(subscriptor, (BasicElement) objFromChannel);
				}
				else {
			
					throw new Exception ("Unknow object " + objFromChannel.getClass().getSimpleName() + " from channel = " + channel);
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public void send(Subscriptor subscriptor, byte[] channel) {
		
		Object objFromChannel;
	
		try {
			
			objFromChannel = CacheManager.getInstance().getFromChannel(channel);
			
			if (objFromChannel != null) {
			
				if (objFromChannel instanceof BasicElement) {
					
					routerService.add(subscriptor, (BasicElement) objFromChannel);
				}
				else {
			
					throw new Exception("Unknow object " + objFromChannel.getClass().getSimpleName()
							+ " from channel = " + new String(channel));
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public void send(Subscriptor subscriptor, Class<? extends Command> clazz, String message) {
		
		Object objFromChannel;
	
		try {
			
			objFromChannel = CacheManager.getInstance().getMessage(clazz, message);
			
			if (objFromChannel != null) {
			
				if (objFromChannel instanceof Command) {
					
					routerService.add(subscriptor, objFromChannel);
				}
				else {
			
					throw new Exception ("Unknow object " + objFromChannel.getClass().getSimpleName() + " from channel = " + clazz.getSimpleName());
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public void send(Subscriptor subscriptor, Class<? extends Command> clazz, byte[] message) {
		
		Object objFromChannel;
	
		try {
			
			objFromChannel = CacheManager.getInstance().getMessage(message);
			
			if (objFromChannel != null) {
			
				if (objFromChannel instanceof Command) {
					
					routerService.add(subscriptor, objFromChannel);
				}
				else {
			
					throw new Exception("Unknow object " + objFromChannel.getClass().getSimpleName()
							+ " from channel = " + objFromChannel.getClass().getSimpleName());
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
}
