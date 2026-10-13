package com.revenga.rits.back.io.controller.module;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ControllerAdapterV1.Mods.Mod;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementIOControllerModule;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.IOControllerModule;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.io.controller.driver.GenericDriver;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public abstract class GenericModule implements Runnable, GenericModuleInterface {
	
	public static final short MAIN_THREAD = 1;
	
	protected BlockingQueue<Command> queueCommands;
	protected Mod mod;
	protected IOControllerModule ioControllerModule;
	protected GenericDriver driver;
	protected Class<?> threadRedirectCriteriaClass;
	protected Field threadRedirectCriteriaField;
	
	protected boolean working;
	protected boolean stop;
	protected boolean alive;
	protected short threadNum;
	
	protected boolean ready;
	
	protected GenericModule(Mod mod, IOControllerModule ioControllerModule, GenericDriver driver, short threadNum) {
		super();
		
		this.mod = mod;
		this.ioControllerModule = ioControllerModule;
		this.driver = driver;		
		this.queueCommands = new LinkedBlockingQueue<>();
		this.threadNum = threadNum;
		this.ready = false;
		
		loadModuleElements();
		this.ready = true;
	}
	
	private void loadModuleElements() {
		
		if (this.ioControllerModule != null && !CollectionUtils.isEmpty(this.ioControllerModule.getModuleElements())) {
			
			Instant initInstant = Instant.now();
			
			for (ElementIOControllerModule moduleElement : this.ioControllerModule.getModuleElements()) {
				
				Element element = EntitiesManager.getInstance().getElement(moduleElement.getElementTypeId(), moduleElement.getElementId());
				
				if (element != null) {
					
					ElementType elementType = EntitiesManager.getInstance().getElementType(element.getElementTypeId());
					
					if (elementType != null) {
						
						elementType.setElementTypeParams(EntitiesManager.getInstance().getElementTypeParams(elementType.getId()));
					
						element.setElementType(elementType);
						
						
					}

					moduleElement.setElement(element);
				}
			}
			
			log.debug("Time to load all elements of mod = " + mod.getId() + " is " + Duration.between(initInstant, Instant.now()).toMillis() + " ms.");
		}
	}
	
	public String getName() {
		
		return this.getClass().getSimpleName() + ":" + this.ioControllerModule.getModuleTypeId() + ":"
				+ this.ioControllerModule.getId(); 
	}
	
	public void stop() {
		
		this.stop = true;
	}
	
	public void add(Command queueCommand) {
		
		this.queueCommands.add(queueCommand);
	}
	
	public void run() {
			
		this.alive = true;
		this.working = false;
		
		log.info(Thread.currentThread().getName() + " is running...");
		
		initialize();
			
		while (!stop) {
			
			try {
	            	
				Command queueCommand = queueCommands.poll();
				
				if (queueCommand != null) {
					
					this.working = true;
					
					String className = queueCommand.getClass().getSimpleName();
					Long createdAt = queueCommand.getCreatedAt();
					Long dequeuedTime = Duration.between(Instant.ofEpochMilli(createdAt), Instant.now()).toMillis();
				
					log.debug(className + " dequeued in " + dequeuedTime + " ms. Processing...");
					
					Instant instant = Instant.now();
					
					process(queueCommand);
					
					log.debug(className + " processed in " + Duration.between(instant, Instant.now()).toMillis()
							+ " ms. Total time to process item from queue = "
							+ Duration.between(Instant.ofEpochMilli(createdAt), Instant.now()).toMillis()
							+ " ms. Queue size to be processed = " + queueCommands.size() + " items.");
					
					this.working = false;
				}
				
				Thread.sleep(0, 1);
					
	        } catch (InterruptedException e) {
	            
	        	log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
	        }
		}
		
		end();
		
		this.alive = false;
		
		log.info(Thread.currentThread().getName() + " is stopped");
	}
	
	public String getParamValue(String param) {
		
		if (mod != null && mod.getModParams() != null) {
			
			List<String> paramValues = mod.getModParams().getModParamValue();
			
			if (!CollectionUtils.isEmpty(paramValues)) {
				
				for (String paramValue : paramValues) {
					
					if (paramValue.contains("=")) {
						
						int idx = paramValue.indexOf('=');
						String key = paramValue.substring(0, idx);
						
						if (key.equals(param)) {
							
							return paramValue.substring(idx + 1, paramValue.length());
						}
					}
				}
			}
		}
		
		return null;
	}

	protected abstract void initialize();
	
	protected abstract void process(Command queueCommand);
	
	protected abstract void end();
}
