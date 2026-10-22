package com.revenga.rits.back.io.controller.module.citilog;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.revenga.rits.back.adapter.lib.model.ControllerAdapterV1.Mods.Mod;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementIOControllerModule;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.IOControllerModule;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.RequestCommand;
import com.revenga.rits.back.data.core.model.command.RequestCommand.RequestCommandElement;
import com.revenga.rits.back.data.core.model.command.SetElementStatesChangeCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.io.controller.driver.GenericDriver;
import com.revenga.rits.back.io.controller.driver.citilog.CitilogDriver;
import com.revenga.rits.back.io.controller.driver.citilog.CitilogSocketClient;
import com.revenga.rits.back.io.controller.module.GenericModule;
import com.revenga.rits.back.io.controller.module.scheduler.ScheduledRequestAlarmsJob;
import com.revenga.rits.back.io.controller.module.scheduler.ScheduledRequestKeepAliveJob;
import com.revenga.rits.back.io.controller.module.scheduler.ScheduledRequestMeasuresJob;
import com.revenga.rits.back.io.controller.module.scheduler.ScheduledRequestStateJob;
import com.revenga.rits.back.io.controller.module.thread.ThreadSendManager;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CitilogModule extends GenericModule {
	
	private static final String FILE_APP_PROPS = "application.properties";
	private static final Long PARAM_INITIAL_REQUEST_STATE_ACTIVE = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.citilog.request.state.active"));
	private static final Long PARAM_REQUEST_STATE_CRON_EXPRESSION = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.citilog.request.state.cron"));
	private static final Long PARAM_INITIAL_REQUEST_MEASURES_ACTIVE = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.citilog.request.measures.active"));
	private static final Long PARAM_REQUEST_MEASURES_CRON_EXPRESSION = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.citilog.request.measures.cron"));
	private static final Long PARAM_INITIAL_REQUEST_ALARMS_ACTIVE = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.citilog.request.alarms.active"));
	private static final Long PARAM_REQUEST_ALARMS_CRON_EXPRESSION = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.citilog.request.alarms.cron"));
	private static final Long PARAM_INITIAL_REQUEST_KEEP_ALIVE_ACTIVE = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.citilog.request.keepAlive.active"));
	private static final Long PARAM_REQUEST_KEEP_ALIVE_CRON_EXPRESSION = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.citilog.request.keepAlive.cron"));
	
	private static final String IDENTITY_JOB = "job";
	private static final String IDENTITY_GROUP = "group";
	private static final String IDENTITY_TRIGGER = "trigger";
	
	Scheduler scheduler;
	CitilogModuleRequestProcessor citilogModuleRequestProcessor;
	ThreadPoolExecutor executor;
	
	@Getter
	List<Element> cameras = new ArrayList<>();
	
	String modRef;
	
	public CitilogModule(Mod mod, IOControllerModule ioControllerModule, GenericDriver driver, short threadNum) {
		
		super(mod, ioControllerModule, driver, threadNum);
		
		this.citilogModuleRequestProcessor = new CitilogModuleRequestProcessor(this);
		
		modRef = "mod_" + mod.getType() + "_" + mod.getId();
		
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	}
	
	@Override
	protected void initialize() {
		
		if (this.driver instanceof CitilogDriver) {
			
			CitilogDriver citilogDriver = (CitilogDriver) driver;
			CitilogSocketClient citilogSocketClient = citilogDriver.getCitilogSocketClient();
			
			if (citilogSocketClient != null) {
				
				loadCameras();
				
				if (this.threadNum == MAIN_THREAD) {
					
					initModuleKeepAliveElements();
				}

				log.debug("Waiting to connect citilog: " + citilogDriver.getHost() + ":" + citilogDriver.getPort() + " ...");
				
				while (!citilogSocketClient.isConnected()) {
					
					try {
						
						//Keep Alive Calculation
						if (keepAliveIntervalMs != null && this.threadNum == MAIN_THREAD) {
		   					
	    					long diffDuration = Duration.between(lastKeepAliveSent, Instant.now()).toMillis();
	    					
	    					if (diffDuration > keepAliveIntervalMs) {
	    						
	    						sendKeepAliveInfo();
	    						lastKeepAliveSent = Instant.now();
	    					}
	    				}
						
						Thread.sleep(5000);
						
					} catch (InterruptedException e) {
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
						Thread.currentThread().interrupt();
					}
				}
				
				log.debug("Connected to citilog: " + citilogDriver.getHost() + ":" + citilogDriver.getPort());
				
				if (threadNum == MAIN_THREAD) {
					
					launchScheduler();
					launchInitialRequest();
				}
			}
		}
	}
	
	private void loadCameras() {
		
		List<ElementIOControllerModule> elementsModule = ioControllerModule.getModuleElements().stream()
				.filter(e -> e.getElementTypeId().equals(ElementType.ELEMENT_TYPE_DAI)).collect(Collectors.toList());
		
		if (!CollectionUtils.isEmpty(elementsModule)) {
			
			for (ElementIOControllerModule elementModule : elementsModule) {
				
				Element element = elementModule.getElement();
				
				if (element != null) {
					
					List<ElementHierarchy> citilogHierarchies = EntitiesManager.getInstance()
							.getsByParent(element.getElementTypeId(), element.getId());
					
					if (!CollectionUtils.isEmpty(citilogHierarchies)) {
						
						List<ElementHierarchy> camerasInDaiHierarchies = citilogHierarchies.stream()
								.filter(e -> e.getChildElementTypeId().equals(ElementType.ELEMENT_TYPE_CAMERA))
								.collect(Collectors.toList());
						
						if (!CollectionUtils.isEmpty(camerasInDaiHierarchies)) {
							
							ElementType elementTypeCamera = EntitiesManager.getInstance().getElementType(ElementType.ELEMENT_TYPE_CAMERA);
							
							if (elementTypeCamera != null) {
								
								for (ElementHierarchy camerasInDaiHierarchy : camerasInDaiHierarchies) {
									
									Element camera = EntitiesManager.getInstance().getElement(camerasInDaiHierarchy.getChildElementTypeId(), camerasInDaiHierarchy.getChildElementId());
									
									if (camera != null && elementTypeCamera.getId().equals(camera.getElementTypeId())) {
											
										camera.setElementType(elementTypeCamera);
										
										cameras.add(camera);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	protected void process(Command queueCommand) {
		
		if (queueCommand instanceof SignallingCommand) {
			
			// Not implemented
		}
		else if (queueCommand instanceof RequestCommand) {
			
			RequestCommand requestCommand = (RequestCommand) queueCommand;
					
			if (!CollectionUtils.isEmpty(ioControllerModule.getModuleElements()) && 
				!CollectionUtils.isEmpty(requestCommand.getRequestCommandElements())) {
					
				log.debug("MangoModule::process => RequestCommand with "
						+ requestCommand.getRequestCommandElements().size() + " items");
				
				for (RequestCommandElement requestCommandElement : requestCommand.getRequestCommandElements()) {
					
					ElementIOControllerModule elementModule = ioControllerModule.getModuleElements().stream()
							.filter(e -> e.getElementTypeId().equals(requestCommandElement.getElementTypeId())
									&& e.getElementId().equals(requestCommandElement.getElementId()))
							.findFirst().orElse(null);
					
					if (elementModule != null) {
				
						Element element = elementModule.getElement();
						
						if (element != null) {
							
							if (requestCommandElement.getRequestTypeId().equals(RequestCommand.REQ_STATE)) {
								
								requestState(element);
							}
							else if (requestCommandElement.getRequestTypeId().equals(RequestCommand.REQ_MEASURES)) {
								
								// Not implemented
							}
							else if (requestCommandElement.getRequestTypeId().equals(RequestCommand.REQ_ALARMS)) {
								
								requestAlarms(element);
							}
							else {
								
								log.warn("Unknown command " + queueCommand.getClass().getSimpleName());
							}
						}
					}
				}
			}
		}
		else {
			
			log.warn("Unknown command " + queueCommand.getClass().getSimpleName());
		}
	}
	
	@Override
	protected void end() {
		
		if (threadNum == MAIN_THREAD) {
		
			try {
				
				log.debug("Shutdown scheduled jobs ...");

				scheduler.shutdown(true);

				log.debug("Shutdown Complete");
				log.debug("Executed " + scheduler.getMetaData().getNumberOfJobsExecuted() + " jobs.");
				
			} catch (SchedulerException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		
		ThreadSendManager.getInstance().stopThreads(ioControllerModule);
	}
	
	@Override
	public void requestState(Element element) {
		
		citilogModuleRequestProcessor.requestState(element);
	}

	@Override
	public void requestAllStates() {
		
		citilogModuleRequestProcessor.requestStates();
	}
	
	@Override
	public void requestMeasures(Element element) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void requestAllMeasures() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void requestAlarms(Element element) {
		
		citilogModuleRequestProcessor.requestAlarm(element);
	}
	
	@Override
	public void requestAllAlarms() {
		
		citilogModuleRequestProcessor.requestAlarms();
	}
	
	@Override
	public void requestKeepAlive() {
		
		citilogModuleRequestProcessor.requestKeepAlive();
	}
	
	@Override
	public void sendCommand(SignallingCommand cmd) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void receiveMeasures(Element element, List<ElementValue> elementValue) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void receiveState(ElementState elementState) {
		
		try {
			
			SetElementStatesChangeCommand setElementStatesChangeCommand = new SetElementStatesChangeCommand();
			
			setElementStatesChangeCommand.addElementState(elementState);
			
			EntitiesManager.getInstance().sendCommand(setElementStatesChangeCommand);
		
		} catch (Exception e) {
			
			log.error((new Key(elementState)).getSimpleValue() + " => " + e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	@Override
	public void receiveStates(List<ElementState> elementStates) {
		
		try {
			
			if (!CollectionUtils.isEmpty(elementStates)) {
			
				SetElementStatesChangeCommand setElementStatesChangeCommand = new SetElementStatesChangeCommand();
				
				for (ElementState elementState : elementStates) {
			
					setElementStatesChangeCommand.addElementState(elementState);
				}
				
				EntitiesManager.getInstance().sendCommand(setElementStatesChangeCommand);
			}

		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	@Override
	public void receiveAlarm(Element element, Pair<AlarmConfig, Boolean> alarmConfigActivation) {
		
		AlarmConfig alarmConfig = null;
		
		try {

			alarmConfig = alarmConfigActivation.getLeft();
			
			if (alarmConfigActivation.getRight().booleanValue()) {
			
				ThreadSendManager.getInstance().addActivateAlarm(ioControllerModule, element, alarmConfig);
			}
			else {
				
				ThreadSendManager.getInstance().addDeactivateAlarm(ioControllerModule, element, alarmConfig);
			}
		
		} catch (Exception e) {
			
			log.error((new Key(alarmConfig)).getSimpleValue() + " => " + e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	@Override
	public void receiveAlarms(Element element, List<Pair<AlarmConfig, Boolean>> alarmConfigActivations) {
			
		try {
			
			if (!CollectionUtils.isEmpty(alarmConfigActivations)) {
								
				alarmConfigActivations.forEach(e -> receiveAlarm(element, e));
			}

		} catch (Exception e) {
			
			log.error(alarmConfigActivations + " => " + e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void launchScheduler() {
		
		String cronExpressionStates;
		String cronExpressionMeasures;
		String cronExpressionAlarms;
		String cronExpressionKeepAlive;
		
		try {
			
			// First we must get a reference to a scheduler
			SchedulerFactory sf = new StdSchedulerFactory();
			scheduler = sf.getScheduler();

			scheduler.getContext().put(modRef, this);
			
			IOControllerModule module = EntitiesManager.getInstance().getIOControllerModule(Long.valueOf(mod.getType()), Long.valueOf(mod.getId()));
			
			cronExpressionStates = EntitiesManager.getInstance()
					.getIOControllerModuleValue(module, PARAM_REQUEST_STATE_CRON_EXPRESSION).getValue();
			
			cronExpressionMeasures = EntitiesManager.getInstance()
					.getIOControllerModuleValue(module, PARAM_REQUEST_MEASURES_CRON_EXPRESSION).getValue();
			
			cronExpressionAlarms = EntitiesManager.getInstance()
					.getIOControllerModuleValue(module, PARAM_REQUEST_ALARMS_CRON_EXPRESSION).getValue();
			
			cronExpressionKeepAlive = EntitiesManager.getInstance()
					.getIOControllerModuleValue(module, PARAM_REQUEST_KEEP_ALIVE_CRON_EXPRESSION).getValue();
			
			if (!StringUtils.isEmpty(cronExpressionStates)) {
			
				createJob(ScheduledRequestStateJob.class, cronExpressionStates);
			}
			
			if (!StringUtils.isEmpty(cronExpressionMeasures)) {
				
				createJob(ScheduledRequestMeasuresJob.class, cronExpressionMeasures);
			}
			
			if (!StringUtils.isEmpty(cronExpressionAlarms)) {
				
				createJob(ScheduledRequestAlarmsJob.class, cronExpressionAlarms);
			}
			
			if (!StringUtils.isEmpty(cronExpressionKeepAlive)) {
				
				createJob(ScheduledRequestKeepAliveJob.class, cronExpressionKeepAlive);
			}
			
			// All of the jobs have been added to the scheduler, but none of the
		    // jobs
		    // will run until the scheduler has been started
			scheduler.start();
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void launchInitialRequest() {
		
		boolean requestInitialStateEnabled = false;
		boolean requestInitialMeasuresEnabled = false;
		boolean requestInitialAlarmsEnabled = false;
		boolean requestInitialKeepAliveEnabled = false;
		
		requestInitialStateEnabled = StringUtils.equalsAny(
				EntitiesManager.getInstance()
						.getIOControllerModuleValue(ioControllerModule, PARAM_INITIAL_REQUEST_STATE_ACTIVE).getValue(),
				"1", "true");
		
		requestInitialMeasuresEnabled = StringUtils.equalsAny(
				EntitiesManager.getInstance()
						.getIOControllerModuleValue(ioControllerModule, PARAM_INITIAL_REQUEST_MEASURES_ACTIVE).getValue(),
				"1", "true");
		
		requestInitialAlarmsEnabled = StringUtils.equalsAny(
				EntitiesManager.getInstance()
						.getIOControllerModuleValue(ioControllerModule, PARAM_INITIAL_REQUEST_ALARMS_ACTIVE).getValue(),
				"1", "true");
		
		requestInitialKeepAliveEnabled = StringUtils.equalsAny(
				EntitiesManager.getInstance()
						.getIOControllerModuleValue(ioControllerModule, PARAM_INITIAL_REQUEST_KEEP_ALIVE_ACTIVE).getValue(),
				"1", "true");

		if (requestInitialStateEnabled) {

			requestAllStates();
		}
	
		if (requestInitialMeasuresEnabled) {

			requestAllMeasures();
		}
		
		if (requestInitialAlarmsEnabled) {
		
			requestAllAlarms();
		}
		
		if (requestInitialKeepAliveEnabled) {
			
			requestKeepAlive();
		}
	}
	
	private void createJob(Class<? extends Job> jobClass, String cronExpression) {
		
		try {
			
			// jobs can be scheduled before sched.start() has been called
			// job 1 will run every cron value
			
			String name = jobClass.getSimpleName() + ":" + modRef;
			
			JobDetail job = newJob(jobClass).withIdentity(IDENTITY_JOB + name, IDENTITY_GROUP + name).build();
			
			if (!StringUtils.isEmpty(cronExpression)) {
				
				CronTrigger trigger = newTrigger().withIdentity(IDENTITY_TRIGGER + name, IDENTITY_GROUP + name)
						.withSchedule(cronSchedule(cronExpression)).build();
				
				Date dt = scheduler.scheduleJob(job, trigger);
				
				log.info(jobClass.getSimpleName() + " with cron (" + cronExpression + ") has been scheduled to run at: " + dt);
			}
			else {
				
				log.info(jobClass.getSimpleName() + " is not scheduled");
			}
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	@Override
	public void initModuleKeepAliveElements() {
		
		List<ElementIOControllerModule> elementsModule = ioControllerModule.getModuleElements().stream()
				.filter(e -> e.getElementTypeId().equals(ElementType.ELEMENT_TYPE_DAI)).collect(Collectors.toList());
		
		if (!CollectionUtils.isEmpty(elementsModule)) {
			
			for (ElementIOControllerModule elementModule : elementsModule) {
				
				Element element = elementModule.getElement();
				
				moduleKeepAliveElements.add(new Key(element).getSimpleValue());
			}
		}
		
		if (!CollectionUtils.isEmpty(cameras)) {
			
			cameras.forEach(camera -> {
				
				moduleKeepAliveElements.add(new Key(camera).getSimpleValue());
			});		
		}
	}

//	@Override
//	public void initModuleKeepAliveElements() {
//		
//		List<ElementIOControllerModule> elementsModule = ioControllerModule.getModuleElements().stream()
//				.filter(e -> e.getElementTypeId().equals(ElementType.ELEMENT_TYPE_DAI)).collect(Collectors.toList());
//		
//		if (!CollectionUtils.isEmpty(elementsModule)) {
//			
//			for (ElementIOControllerModule elementModule : elementsModule) {
//				
//				Element element = elementModule.getElement();
//				
//				if (element != null) {
//					
//					String elementId = new Key(element).getSimpleValue();
//					
//					if (StringUtils.isNotEmpty(elementId)) {
//						
//						this.moduleKeepAliveElements.add(elementId);
//					}
//					
//					List<ElementHierarchy> citilogHierarchies = EntitiesManager.getInstance()
//							.getsByParent(element.getElementTypeId(), element.getId());
//					
//					if (!CollectionUtils.isEmpty(citilogHierarchies)) {
//						
//						List<ElementHierarchy> camerasInDaiHierarchies = citilogHierarchies.stream()
//								.filter(e -> e.getChildElementTypeId().equals(ElementType.ELEMENT_TYPE_CAMERA))
//								.collect(Collectors.toList());
//						
//						if (!CollectionUtils.isEmpty(camerasInDaiHierarchies)) {
//							
//							ElementType elementTypeCamera = EntitiesManager.getInstance().getElementType(ElementType.ELEMENT_TYPE_CAMERA);
//							
//							if (elementTypeCamera != null) {
//								
//								for (ElementHierarchy camerasInDaiHierarchy : camerasInDaiHierarchies) {
//									
//									Element camera = EntitiesManager.getInstance().getElement(camerasInDaiHierarchy.getChildElementTypeId(), camerasInDaiHierarchy.getChildElementId());
//									
//									if (camera != null && elementTypeCamera.getId().equals(camera.getElementTypeId())) {
//											
//										camera.setElementType(elementTypeCamera);
//										
//										String cameraId = new Key(element).getSimpleValue();
//										
//										if (StringUtils.isNotEmpty(cameraId)) {
//											
//											this.moduleKeepAliveElements.add(cameraId);
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//	}
}
