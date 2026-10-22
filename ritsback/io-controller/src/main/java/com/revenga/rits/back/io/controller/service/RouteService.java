package com.revenga.rits.back.io.controller.service;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.adapter.lib.model.ControllerAdapterV1.Mods.Mod;
import com.revenga.rits.back.adapter.lib.model.ControllerAdapterV1.Subscriptors.Subscriptor;
import com.revenga.rits.back.adapter.lib.service.SessionAdapter;
import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementIOControllerModule;
import com.revenga.rits.back.data.core.model.IOControllerModule;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.RequestCommand;
import com.revenga.rits.back.data.core.model.command.RequestCommand.RequestCommandElement;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.entities.lib.repository.ElementIOControllerModuleRepository;
import com.revenga.rits.back.io.controller.driver.GenericDriver;
import com.revenga.rits.back.io.controller.model.ControllerModel;
import com.revenga.rits.back.io.controller.module.GenericModule;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RouteService implements Runnable {
	
	ControllerModel controllerModel;
		
	ConcurrentLinkedQueue<Pair<Subscriptor, Command>> queueCommands;
	
	Map<Mod, List<GenericModule>> moduleInstances = new HashMap<>();
	
	Map<Key, GenericModule> elementGenericModuleMap = Collections.synchronizedMap(new HashMap<>());
	
	Random rand;
		
	SessionAdapter sessionAdapter;
	
	boolean stop;
	@Getter
	boolean alive;
	
	private final Object lock = new Object();
	
	public RouteService(ControllerModel controllerModel) {
		
		super();
		
		this.controllerModel = controllerModel;
		
		this.queueCommands = new ConcurrentLinkedQueue<>();
		this.stop = false;
		this.alive = false;
		
		this.rand = new Random();
			
		sessionAdapter = new SessionAdapter(); 
	}
	
	public void stop() {
		
		stopModules();
		
		this.stop = true;
	}

	public void add(Subscriptor subcriptor, Command cmd) {
		
		if (cmd instanceof SignallingCommand || cmd instanceof RequestCommand) {
			
			this.queueCommands.add(new MutablePair<>(subcriptor, cmd));
			
			Long createdAt = cmd.getCreatedAt();
			Long dequeuedTime = Duration.between(Instant.ofEpochMilli(createdAt), Instant.now()).toMillis();			

			log.debug("Route::add() " + cmd.getClass().getSimpleName() + " received in " + dequeuedTime + " ms. Global queue size = "
					+ this.queueCommands.size());
		}
		else {
			
			log.error("Subscriptor " + subcriptor.getEntity() + "instance of " + cmd.getClass().getSimpleName() + "It's not instance of SignallingCommand or RequestCommand");
		}
	}
	
	public Element addElement(Element element) {
		
		Element newElement = EntitiesManager.getInstance().getElementFilled(element.getElementTypeId(), element.getId());
		
		if (newElement != null) {
		
			// Add to new ControllerModule
			addToControllerModule(newElement);
		}
		
		return newElement;
	}
	
	public Element updateElement(Element element) {
		
		Element updatedElement = EntitiesManager.getInstance().getElementFilled(element.getElementTypeId(), element.getId());
		
		// Delete from old ControllerModule
		deleteFromControllerModule(updatedElement);
		
		// Add to new ControllerModule
		addToControllerModule(updatedElement);
		
		return updatedElement;
	}
	
	public Element deleteElement(Element element) {
		
		// Delete from old ControllerModule
		deleteFromControllerModule(element);
		
		return element;
	}
	
	private void addToControllerModule(Element newElement) {
		
		Key elementKey = new Key(newElement);
		GenericModule genericModuleInstanceMainThread = null;
		
		List<ElementIOControllerModule> newElementIOControllerModules = ((ElementIOControllerModuleRepository) EntitiesManager
				.getInstance().getRepositories().get(ElementIOControllerModule.class))
				.gets(newElement.getElementTypeId(), newElement.getId(), false);
		
		if (!CollectionUtils.isEmpty(newElementIOControllerModules)) {
			
			for (ElementIOControllerModule newElementIOControllerModule : newElementIOControllerModules) {
				
				newElementIOControllerModule.setElement(newElement);
				
				for (Map.Entry<Mod, List<GenericModule>> entry : moduleInstances.entrySet()) {
					
					Mod mod = entry.getKey();
					
					if (mod.getId() == newElementIOControllerModule.getModuleId() &&
						mod.getType() == newElementIOControllerModule.getModuleTypeId()) {
						
						List<GenericModule> genericModuleInstances = entry.getValue();
						
						if (!CollectionUtils.isEmpty(genericModuleInstances)) {
							
							genericModuleInstanceMainThread = genericModuleInstances.stream()
									.filter(e -> e.getThreadNum() == GenericModule.MAIN_THREAD).findFirst().orElse(null);
							
							if (genericModuleInstanceMainThread != null) {
								
								this.elementGenericModuleMap.put(elementKey, genericModuleInstanceMainThread);
							}
							
							for (GenericModule genericModuleInstance : genericModuleInstances) {
								
								genericModuleInstance.addElement(newElement);
							}
						}
					}
					
					IOControllerModule ioControllerModule = EntitiesManager.getInstance().getIOControllerModule(mod.getType(), mod.getId());
					
					if (ioControllerModule != null) {
						
						ioControllerModule.getModuleElements().add(newElementIOControllerModule);
					}
				}
				
//				if (genericModuleInstanceMainThread != null) {
//					
//					genericModuleInstanceMainThread.requestState(newElement);
//				}
			}
		}
	}
	
	private void deleteFromControllerModule(Element currentElement) {
		
		Key elementKey = new Key(currentElement);
		GenericModule moduleCurrentElement = getModule(currentElement);
		
		if (moduleCurrentElement.getIoControllerModule() != null) {
			
			List<ElementIOControllerModule> currentModuleElements = moduleCurrentElement.getIoControllerModule().getModuleElements();
			
			if (!CollectionUtils.isEmpty(currentModuleElements)) {
				
				ElementIOControllerModule currentModuleElement = currentModuleElements.stream()
						.filter(e -> e.getElementTypeId().equals(currentElement.getElementTypeId())
								&& e.getElementId().equals(currentElement.getId()))
						.findFirst().orElse(null);
				
				if (currentModuleElement != null) {
					
					currentModuleElements.remove(currentModuleElement);
					
					this.elementGenericModuleMap.remove(elementKey);
					
					List<GenericModule> genericModuleInstances =  moduleInstances.get(moduleCurrentElement.getMod());
					
					if (!CollectionUtils.isEmpty(genericModuleInstances)) {
						
						for (GenericModule genericModuleInstance : genericModuleInstances) {
							
							genericModuleInstance.deleteElement(currentElement);
						}
					}
				}
			}
			
			IOControllerModule ioControllerModule = EntitiesManager.getInstance().getIOControllerModule(
					moduleCurrentElement.getMod().getType(), moduleCurrentElement.getMod().getId());

			if (ioControllerModule != null && !CollectionUtils.isEmpty(ioControllerModule.getModuleElements())) {

				ioControllerModule.getModuleElements()
						.removeIf(e -> e.getElementTypeId().equals(currentElement.getElementTypeId())
								&& e.getElementId().equals(currentElement.getId()));
			}
		}
	}
	
	@Override
	public void run() {
		
		startModules();
		
		this.alive = true;
		
		log.info(Thread.currentThread().getName() + " is running...");

		synchronized (lock) {
			
			try {
				
				while (!stop) {
				
					Pair<Subscriptor, Command> pairCommand = this.queueCommands.poll();
					
					while (pairCommand != null) {
						
						route(pairCommand.getLeft(), pairCommand.getRight());
						
						pairCommand = this.queueCommands.poll();
					}
					
					lock.wait(1000);
				}
				
			} catch (InterruptedException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
			}
		}
		
		this.alive = false;
		
		log.info(Thread.currentThread().getName() + " is stopped");
	}
	
	private void route(Subscriptor subscriptor, Command cmd) {
		
		List<Mod> mods = getMods(cmd);
		
		if (!CollectionUtils.isEmpty(mods)) {
			
			for (Mod mod : mods) {
				
				if (mod.getSubscriptorsRef() != null && !CollectionUtils.isEmpty(mod.getSubscriptorsRef().getSubscriptorIdRef())) {
					
					List<GenericModule> modInstances = this.moduleInstances.get(mod);
					
					if (!CollectionUtils.isEmpty(modInstances)) {
					
						for (Short subscriptorIdRef : mod.getSubscriptorsRef().getSubscriptorIdRef()) {
							
							if (subscriptorIdRef.equals(subscriptor.getId())) {
								
								routeInsideGenericModules(modInstances, cmd);
							}
						}
					}
				}
			}
		}
		else {
			
			log.error("Check xml adapter. Not found executor for subscriptor id = " + subscriptor.getId());
		}
	}
	
	private void routeInsideGenericModules(List<GenericModule> modInstances, Command cmd) {
	
		if (cmd instanceof RequestCommand) {
			
			routeInsideGenericModulesFromRequest(modInstances, cmd);
		}
		else if (cmd instanceof SignallingCommand) {
			
			routeInsideGenericModulesFromSignalling(modInstances, cmd);
		}
	}
	
	private void routeInsideGenericModulesFromSignalling(List<GenericModule> modInstances, Command cmd) {
	
		Element element = getElement(cmd);
		
		if (!CollectionUtils.isEmpty(modInstances) && element != null) {
			
			GenericModule module = modInstances.get(0);
			
			IOControllerModule ioControllerModule = module.getIoControllerModule();
			
			if (ioControllerModule != null &&
					ioControllerModule.getModuleElements() != null) {
				
				List<ElementIOControllerModule> moduleElements = ioControllerModule.getModuleElements();
				
				ElementIOControllerModule moduleElement = moduleElements.stream()
						.filter(e -> e.getElementTypeId().equals(element.getElementTypeId())
								&& e.getElementId().equals(element.getId()))
						.findFirst().orElse(null);
				
				if (moduleElement != null) {
					
					GenericModule genericModule = getLessBusyModInstance(modInstances);
					
					if (genericModule != null) {
						
//						log.debug("Route into module command " + cmd.getClass().getSimpleName() + " => " + genericModule.getIoControllerModule().getAlias()
//								+ "_Thread_" + genericModule.getThreadNum() + ". Global queue size = "
//								+ queueCommands.size());
						
						genericModule.add(cmd);
					}
				}
			}
		}
	}
		
	private void routeInsideGenericModulesFromRequest(List<GenericModule> modInstances, Command cmd) {
		
		RequestCommand requestCommandModule = new RequestCommand();
		
		if (cmd instanceof RequestCommand) {
			
			RequestCommand requestCommand = (RequestCommand) cmd;
			
			if (!CollectionUtils.isEmpty(requestCommand.getRequestCommandElements())) {
				
				for (RequestCommandElement requestCommandElement : requestCommand.getRequestCommandElements()) {
					
					Element element = getElement(requestCommandElement);
					
					if (!CollectionUtils.isEmpty(modInstances) && element != null) {
						
						GenericModule module = modInstances.get(0);
						
						IOControllerModule ioControllerModule = module.getIoControllerModule();
						
						if (ioControllerModule != null  &&
								ioControllerModule.getModuleElements() != null) {
							
							List<ElementIOControllerModule> moduleElements = ioControllerModule.getModuleElements();
							
							ElementIOControllerModule moduleElement = moduleElements.stream()
									.filter(e -> e.getElementTypeId().equals(element.getElementTypeId())
											&& e.getElementId().equals(element.getId()))
									.findFirst().orElse(null);
							
							if (moduleElement != null) {
								
								requestCommandModule.setCreatedAt(cmd.getCreatedAt());
								requestCommandModule.add(element, requestCommandElement.getRequestTypeId());
							}
						}
					}
				}
								
				if (!CollectionUtils.isEmpty(requestCommandModule.getRequestCommandElements())) {
					
					GenericModule genericModule = getLessBusyModInstance(modInstances);	

					if (genericModule != null) {
						
						log.debug("Route into module command " + cmd.getClass().getSimpleName() + " => " + genericModule.getIoControllerModule().getAlias()
								+ "_Thread_" + genericModule.getThreadNum() + ". Global queue size = "
								+ queueCommands.size());
						
						genericModule.add(requestCommandModule);
					}

				}
			}
			else {
				
				if (requestCommand.getRequestTypeId() != null && !CollectionUtils.isEmpty(modInstances)) {
					
					GenericModule genericModule = getLessBusyModInstance(modInstances);
					
					if (genericModule != null) {
						
						log.debug("Route into module command " + cmd.getClass().getSimpleName() + " => " + genericModule.getIoControllerModule().getAlias()
								+ "_Thread_" + genericModule.getThreadNum() + ". Global queue size = "
								+ queueCommands.size());
						
						requestCommandModule.setCreatedAt(cmd.getCreatedAt());
						requestCommandModule.setRequestTypeId(requestCommand.getRequestTypeId());
						
						genericModule.add(requestCommandModule);
					}
				}
			}
		}
	}
			
	private GenericModule getLessBusyModInstance(List<GenericModule> modInstances) {
		
		GenericModule genericModule = null;
		
		if (!CollectionUtils.isEmpty(modInstances)) {
			
			// 1st priority => Only one module instance
			if (modInstances.size() == 1) {
				
				genericModule = modInstances.get(0);
			}
			else {
			
				// 2nd priority => Select first not working module instance and queued is empty
				GenericModule genericModuleNotWorking = modInstances.stream().filter(e -> !e.isWorking() && e.getQueueCommands().isEmpty()).findFirst().orElse(null);
				
				if (genericModuleNotWorking != null) {
					
					genericModule = genericModuleNotWorking; 
				}
				// 3rd priority => All module instance are working. Select module instance with the least queue size
				else {
					
					Comparator<GenericModule> compareBySize = (GenericModule o1, GenericModule o2) -> Integer
							.valueOf(o1.getQueueCommands().size())
							.compareTo(Integer.valueOf(o2.getQueueCommands().size()));
					
					Collections.sort(modInstances, compareBySize);
									
					genericModule = modInstances.get(0);
					
					Comparator<GenericModule> compareByThreadNum = (GenericModule o1, GenericModule o2) -> Integer
							.valueOf(o1.getThreadNum())
							.compareTo(Integer.valueOf(o2.getThreadNum()));
					
					Collections.sort(modInstances, compareByThreadNum);
				}
			}
		}
	
		return genericModule;
	}
	
	private Element getElement(Command cmd) {
		
		Element element = null;
		
		if (cmd instanceof SignallingCommand) {
		
			SignallingCommand signallingCommand = (SignallingCommand) cmd;	
			
			element = EntitiesManager.getInstance().getElement(signallingCommand.getElementTypeId(), signallingCommand.getElementId());
		}
		
		return element;
	}
	
	private Element getElement(RequestCommandElement requestCommandElement) {
		
		Element element = null;
		
		element = EntitiesManager.getInstance().getElement(requestCommandElement.getElementTypeId(), requestCommandElement.getElementId());
	
		return element;
	}
	
	private void startModules() {
		
		try {
			
			for (Mod mod : getMods()) {
			
				Instant initInstant = Instant.now();
				IOControllerModule ioControllerModule = EntitiesManager.getInstance().getIOControllerModule(mod.getType(), mod.getId());
				
				if (ioControllerModule != null ) {
					
					log.debug("Time to load Mod = " + mod.getId() + " is " + Duration.between(initInstant, Instant.now()).toMillis() + " ms.");
					
					short threads = (mod.getThreads() != null) ? mod.getThreads() : 1;  
					
					Class<?> driverClass = controllerModel.getDriverClass(mod.getType());
					
					GenericDriver driver = null;
					
					if (driverClass != null) {
						
						driver = (GenericDriver) driverClass.getDeclaredConstructor(IOControllerModule.class)
								.newInstance(ioControllerModule);
					}
					
					for (short thread = 1; thread <= threads ; thread++) {
						
						GenericModule genericModule;
						
						genericModule = (GenericModule) controllerModel.getModuleClass(mod.getType())
								.getDeclaredConstructor(Mod.class, IOControllerModule.class, GenericDriver.class, short.class)
								.newInstance(mod, ioControllerModule, driver, thread);
											
						String threadNameGenericManager = "mod_" + mod.getType() + "_" + mod.getId() + "_" + "Thread-" + thread;
						
						(new Thread(genericModule, threadNameGenericManager)).start();
						
						List<GenericModule> genericModules = moduleInstances.get(mod);
						
						if (genericModules == null) {
							
							genericModules = new ArrayList<>();
							
							moduleInstances.put(mod, genericModules);
						}

						genericModules.add(genericModule);
						
						if (thread == GenericModule.MAIN_THREAD && !CollectionUtils.isEmpty(ioControllerModule.getModuleElements())) {
							
							for (ElementIOControllerModule elementIOControllerModule : ioControllerModule.getModuleElements()) {
								
								if (elementIOControllerModule.getElement() != null) {
								
									elementGenericModuleMap.put(new Key(elementIOControllerModule.getElement()), genericModule);
								}
							}
						}
					}
					
					if (driver != null) {
						
						driver.start();
					}
				}
			}
			
			new Thread("Check Modules Status") {
				
				public void run() {
					
					setProcessReady();
				}
			}.start();
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void stopModules() {
				
		if (!MapUtils.isEmpty(moduleInstances)) {
		
			for (Entry<Mod, List<GenericModule>> entry : moduleInstances.entrySet()) {
				
				List<GenericModule> genericModules = entry.getValue();
				
				if (!CollectionUtils.isEmpty(genericModules)) {
					
					for (GenericModule genericModule : genericModules) {
				
						genericModule.stop();
					}
				}
			}
			
			waitToStopModules();
		}
			
	}
	
	private void waitToStopModules() {
		
		Instant start;
		Duration timeElapsed;
		boolean stopped;
		start = Instant.now();
		
		try {
			
			if (!MapUtils.isEmpty(moduleInstances)) {
			
				do {
					
					stopped = true;
				
					for (Entry<Mod, List<GenericModule>> entry : moduleInstances.entrySet()) {
						
						List<GenericModule> genericModules = entry.getValue();
						
						if (!CollectionUtils.isEmpty(genericModules)) {
							
							for (GenericModule genericModule : genericModules) {
						
								stopped &= !genericModule.isAlive();
								
								if (!stopped) break;
							}
						}
						
						if (!stopped) break;
					}
					
					Thread.sleep(1L);
					
					timeElapsed = Duration.between(start, Instant.now());
					
				} while (timeElapsed.toMillis() < 5000L && !stopped);
			}
			
		} catch (InterruptedException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}
		
	}
	
	private void setProcessReady() {

		if (!MapUtils.isEmpty(moduleInstances)) {

			for (Entry<Mod, List<GenericModule>> entry : moduleInstances.entrySet()) {

				List<GenericModule> genericModules = entry.getValue();

				if (!CollectionUtils.isEmpty(genericModules)) {

					for (GenericModule genericModule : genericModules) {

						try {
							while (!genericModule.isReady()) {

								Thread.sleep(1L);
							}
						} catch (InterruptedException e) {

							log.error(e.getMessage());
							log.debug(ExceptionUtils.getStackTrace(e));
							Thread.currentThread().interrupt();
						}
					}
				}
			}
		}
		
		RedisConnection redisConnection = RedisConnection.getInstance();
		redisConnection.getProcessMonitor().getProcessInfo().setReady(true);
	}
	
	public GenericModule getModule(Element element) {
		
		return elementGenericModuleMap.get(new Key(element));
	}
	
	private List<Mod> getMods() {
		
		List<Mod> mods = null;
		
		if (controllerModel.getControllerAdapterV1() != null && controllerModel.getControllerAdapterV1().getMods() != null
				&& !CollectionUtils.isEmpty(controllerModel.getControllerAdapterV1().getMods().getMod())) {
			
			mods = controllerModel.getControllerAdapterV1().getMods().getMod().stream().filter(
					mod -> EntitiesManager.getInstance().getIOControllerModule(mod.getType(), mod.getId()) != null)
					.collect(Collectors.toList());
		}
		
		return mods;
	}
	
	private List<Mod> getMods(Command command) {
		
		List<Mod> mods = getMods();
		List<Mod> returnModules = new ArrayList<>();
		
		for (Mod mod : mods) {
			
			if (mod.getSubscriptorsRef() != null) {
			
				List<Short> subscriptorRefs = mod.getSubscriptorsRef().getSubscriptorIdRef();
				
				if (!CollectionUtils.isEmpty(subscriptorRefs)) {
				
					for (Short subscriptorRef : subscriptorRefs) {
						
						Class<?> clazz = getClass(subscriptorRef, Command.class);
						
						if (command.getClass() == clazz) {
							
							returnModules.add(mod);		
						}
					}
				}
			}
		}
		
		return returnModules;
	}
	
	private Class<?> getClass(Short subscriptorRef, Class<?> clazzRef) {
		
		List<Subscriptor> subscriptors;
		Class<?> clazz = null;
		
		if (controllerModel.getControllerAdapterV1().getSubscriptors() != null) {
		
			subscriptors = controllerModel.getControllerAdapterV1().getSubscriptors().getSubscriptor();
			
			for (Subscriptor subscriptor : subscriptors) {
				
				if (subscriptor.getId() == subscriptorRef) {
						
					try {
						
						if (clazzRef == Command.class) {
							
							clazz =  ClassesUtil.getModelClassBySimpleClassName(subscriptor.getEntity());
						}
					
					} catch (ClassNotFoundException e) {

					}
					
					if (clazz != null) {
						
						break;
					}
				}
			}
		}
		
		return clazz;
	}
}
