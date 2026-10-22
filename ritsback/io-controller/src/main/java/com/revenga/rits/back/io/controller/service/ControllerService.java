package com.revenga.rits.back.io.controller.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ControllerAdapterV1.Mods;
import com.revenga.rits.back.adapter.lib.model.ControllerAdapterV1.Mods.Mod;
import com.revenga.rits.back.adapter.lib.model.ControllerAdapterV1.Subscriptors.Subscriptor;
import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.service.impl.CommandCache;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.io.controller.model.ControllerModel;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ControllerService {

	@Getter
	ControllerModel controllerModel;
	@Getter
	RouteService routeService;
	
	SubscriptorService subscriptorService;
	BinarySubscriptorService binarySubscriptorService;
	
	private boolean commandCacheBin = false;
	
	public Object getSubscriptorService() {
		
		if (commandCacheBin) {
			
			return binarySubscriptorService;
		}
		else {
			return subscriptorService;
		}
	}
	
	public ControllerService(SubscriptorService subscriptorService, ControllerModel controllerModel) {
		
		this.commandCacheBin = (new CommandCache(RedisConnection.getInstance())).isCommandCacheBin();
		
		this.controllerModel = controllerModel;
		this.subscriptorService = subscriptorService;
	}
	
	public ControllerService(BinarySubscriptorService binarySubscriptorService, ControllerModel controllerModel) {
		
		this.commandCacheBin = (new CommandCache(RedisConnection.getInstance())).isCommandCacheBin();
		
		this.controllerModel = controllerModel;
		this.binarySubscriptorService = binarySubscriptorService;
	}
	
	public void load(Mods mods) {
		
		try {
			
			if (mods != null) {
				
				List<Mod> modules = mods.getMod();
				
				if (!CollectionUtils.isEmpty(modules)) {
										
					String threadNameRouteService = "Thread-" + RouteService.class.getSimpleName();
					
					routeService = new RouteService(controllerModel);
					
					(new Thread(routeService, threadNameRouteService)).start();
				}
			}

		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public void stop() {

		try {
			
			routeService.stop();
			
			Instant start;
			Duration timeElapsed;
			boolean stopped;
			start = Instant.now();
			
			do {
				
				stopped = !routeService.isAlive();
		
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
			
				if (objFromChannel instanceof Command) {
					
					routeService.add(subscriptor, (Command) objFromChannel);
					
					log.debug("Add to route => " + ((Command) objFromChannel).getClass().getSimpleName());
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
			
				if (objFromChannel instanceof Command) {
					
					routeService.add(subscriptor, (Command) objFromChannel);
					
					log.debug("Add to route => " + ((Command) objFromChannel).getClass().getSimpleName());
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
					
					routeService.add(subscriptor, (Command) objFromChannel);
					
					log.debug("Add to route => " + ((Command) objFromChannel).getClass().getSimpleName());
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
					
					routeService.add(subscriptor, (Command) objFromChannel);
					
					log.debug("Add to route => " + ((Command) objFromChannel).getClass().getSimpleName());
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
