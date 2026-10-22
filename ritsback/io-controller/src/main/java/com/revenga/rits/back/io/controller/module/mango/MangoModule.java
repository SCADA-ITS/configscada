package com.revenga.rits.back.io.controller.module.mango;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

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
import com.revenga.rits.back.data.core.model.ElementIOControllerModule;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.IOControllerModule;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.RequestCommand;
import com.revenga.rits.back.data.core.model.command.RequestCommand.RequestCommandElement;
import com.revenga.rits.back.data.core.model.command.SetElementStatesChangeCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.io.controller.driver.GenericDriver;
import com.revenga.rits.back.io.controller.module.GenericModule;
import com.revenga.rits.back.io.controller.module.scheduler.ScheduledRequestAlarmsJob;
import com.revenga.rits.back.io.controller.module.scheduler.ScheduledRequestKeepAliveJob;
import com.revenga.rits.back.io.controller.module.scheduler.ScheduledRequestMeasuresJob;
import com.revenga.rits.back.io.controller.module.scheduler.ScheduledRequestStateJob;
import com.revenga.rits.back.io.controller.module.thread.ThreadSendManager;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MangoModule extends GenericModule {
	
	private static final String FILE_APP_PROPS = "application.properties";
	private static final Long PARAM_INITIAL_REQUEST_STATE_ACTIVE = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.mango.request.state.active"));
	private static final Long PARAM_REQUEST_STATE_CRON_EXPRESSION = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.mango.request.state.cron"));
	private static final Long PARAM_INITIAL_REQUEST_MEASURES_ACTIVE = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.mango.request.measures.active"));
	private static final Long PARAM_REQUEST_MEASURES_CRON_EXPRESSION = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.mango.request.measures.cron"));
	private static final Long PARAM_INITIAL_REQUEST_ALARMS_ACTIVE = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.mango.request.alarms.active"));
	private static final Long PARAM_REQUEST_ALARMS_CRON_EXPRESSION = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.mango.request.alarms.cron"));
	private static final Long PARAM_INITIAL_REQUEST_KEEPALIVE_ACTIVE = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.mango.request.keepalive.active"));
	private static final Long PARAM_REQUEST_KEEPALIVE_CRON_EXPRESSION = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.mango.request.keepalive.cron"));
	
	private static final String IDENTITY_JOB = "job";
	private static final String IDENTITY_GROUP = "group";
	private static final String IDENTITY_TRIGGER = "trigger";
	
	Scheduler scheduler;
	MangoModuleRequestProcessor mangoModuleRequestProcessor;
	MangoModuleSignallingProcessor mangoModuleSignallingProcessor;
	ThreadPoolExecutor executor;
	
	String modRef;
	
	public MangoModule(Mod mod, IOControllerModule ioControllerModule, GenericDriver driver, short threadNum) {
		
		super(mod, ioControllerModule, driver, threadNum);
		
		this.mangoModuleRequestProcessor = new MangoModuleRequestProcessor(this);
		this.mangoModuleSignallingProcessor = new MangoModuleSignallingProcessor(this);
		
		modRef = "mod_" + mod.getType() + "_" + mod.getId();
		
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	}
	
	@Override
	protected void initialize() {
		
		if (threadNum == MAIN_THREAD) {
		
			launchScheduler();
			launchInitialRequest();
		}
	}

	@Override
	protected void process(Command queueCommand) {
		
		if (queueCommand instanceof SignallingCommand) {
			
			SignallingCommand signallingCommand = (SignallingCommand) queueCommand;
			
			sendCommand(signallingCommand);
		}
		else if (queueCommand instanceof RequestCommand) {
			
			RequestCommand requestCommand = (RequestCommand) queueCommand;
					
			if (!CollectionUtils.isEmpty(ioControllerModule.getModuleElements())) {
				
				if (!CollectionUtils.isEmpty(requestCommand.getRequestCommandElements())) {
					
					log.debug("MangoModule::process => RequestCommand with "
							+ requestCommand.getRequestCommandElements().size() + " items");
					
					for (RequestCommandElement requestCommandElement : requestCommand.getRequestCommandElements()) {
						
						ElementIOControllerModule elementModule = ioControllerModule.getModuleElements().stream()
								.filter(e -> e.getElementTypeId().equals(requestCommandElement.getElementTypeId())
										&& e.getElementId().equals(requestCommandElement.getElementId()))
								.findFirst().orElse(null);
						
						if (elementModule != null) {
					
							Element element = elementModule.getElement();
							
							if (element != null && requestCommandElement.getRequestTypeId() != null) {
								
								if (requestCommandElement.getRequestTypeId().equals(RequestCommand.REQ_STATE)) {
									
									requestState(element);
								}
								else if (requestCommandElement.getRequestTypeId().equals(RequestCommand.REQ_MEASURES)) {
									
									requestMeasures(element);
								}
								else if (requestCommandElement.getRequestTypeId().equals(RequestCommand.REQ_ALARMS)) {
									
									requestAlarms(element);
								}
								else {
									
									log.warn(String.format("Unknown request type id %d for element %s",
											requestCommandElement.getRequestTypeId(),
											(new Key(element)).getSimpleValue()));
								}
							}
						}
					}
				}
				else {
					
					if (requestCommand.getRequestTypeId() != null) {
						
						log.debug("MangoModule::process => RequestCommand for all items");
						
						if (requestCommand.getRequestTypeId().equals(RequestCommand.REQ_STATE)) {
							
							requestAllStates();
						}
						else if (requestCommand.getRequestTypeId().equals(RequestCommand.REQ_MEASURES)) {
							
							requestAllMeasures();
						}
						else if (requestCommand.getRequestTypeId().equals(RequestCommand.REQ_ALARMS)) {
							
							requestAllAlarms();
						}
						else {
							
							log.warn(String.format("Unknown request type id %d",
									requestCommand.getRequestTypeId()));
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
		
		try {
			
			ElementState elementState = mangoModuleRequestProcessor.requestState(element);
			
			if (elementState != null) {
			
				receiveState(elementState);
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	@Override
	public void requestAllStates() {
		
		if (!CollectionUtils.isEmpty(ioControllerModule.getModuleElements())) {
	
			Instant initInstant = Instant.now();
			
			List<ElementState> statesChanged = mangoModuleRequestProcessor.requestStates();
			
			log.debug("Request all states in " + Duration.between(initInstant, Instant.now()).toMillis() + " ms.");
			
			if (!CollectionUtils.isEmpty(statesChanged)) {
				
				receiveStates(statesChanged);
			}
		}
	}
	
	@Override
	public void requestMeasures(Element element) {
		
		try {
			
			List<ElementValue> elementValues = mangoModuleRequestProcessor.requestMeasures(element);
			
			if (!CollectionUtils.isEmpty(elementValues)) {
				
				receiveMeasures(element, elementValues);	
			}
		
		} catch (Exception e) {
			
			log.error((new Key(element)).getSimpleValue() + " => " + e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	@Override
	public void requestAllMeasures() {
		
		List<Future<?>> futures = new ArrayList<>();
		
		List<ElementIOControllerModule> moduleElements = ioControllerModule
				.getModuleElements();
		
		if (!CollectionUtils.isEmpty(moduleElements)) {
					
			for (ElementIOControllerModule elementIOControllerModule : moduleElements) {
				
				Runnable task = () -> requestMeasures(elementIOControllerModule.getElement());
				
				Future<?> f = executor.submit(task);
				
				futures.add(f);
			}
		}
	}
	
	@Override
	public void requestAlarms(Element element) {
		
		try {
			
			List<Pair<AlarmConfig, Boolean>> alarmConfigActivations = mangoModuleRequestProcessor.requestAlarms(element);
			
			if (!CollectionUtils.isEmpty(alarmConfigActivations)) {
				
				for (Pair<AlarmConfig, Boolean> alarmConfigActivation : alarmConfigActivations) {
					
					receiveAlarm(element, alarmConfigActivation);
				}
			}
		} catch (Exception e) {
			
			log.error((new Key(element)).getSimpleValue() + " => " + e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	@Override
	public void requestAllAlarms() {
		
		List<ElementIOControllerModule> moduleElements = ioControllerModule
				.getModuleElements();
		List<Future<?>> futures = new ArrayList<>();
		
		
		if (!CollectionUtils.isEmpty(moduleElements)) {
		
			for (ElementIOControllerModule elementIOControllerModule : moduleElements) {
				
				Runnable task = () -> requestAlarms(elementIOControllerModule.getElement());
				
				Future<?> f = executor.submit(task);
				
				futures.add(f);
			}
		}
	}
	
	@Override
	public void sendCommand(SignallingCommand cmd) {
		
		mangoModuleSignallingProcessor.process(cmd);
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
	public void receiveMeasures(Element element, List<ElementValue> elementValues) {
		
		try {
			
			if (element != null && !CollectionUtils.isEmpty(elementValues)) {
			
				Element elementToSend = new Element();
				
				elementToSend.setElementTypeId(element.getElementTypeId());
				elementToSend.setId(element.getId());
				
				List<ElementValue> elementValuesToSend = new ArrayList<>();
				
				for (ElementValue elementValue : elementValues) {
					
					ElementValue elementValueToSend = new ElementValue();
					
					elementValueToSend.setElementTypeId(elementValue.getElementTypeId());
					elementValueToSend.setElementId(elementValue.getElementId());
					elementValueToSend.setElementTypeParamId(elementValue.getElementTypeParamId());
					elementValueToSend.setParamTypeId(elementValue.getParamTypeId());
					elementValueToSend.setValue(elementValue.getValue());
					
					elementValuesToSend.add(elementValueToSend);
				}
				
				elementToSend.setElementValues(elementValuesToSend);

				ThreadSendManager.getInstance().addMeasure(ioControllerModule, elementToSend);
			}
		
		} catch (Exception e) {
			
			log.error((new Key(element)).getSimpleValue() + " => " + e.getMessage());
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
					.getIOControllerModuleValue(module, PARAM_REQUEST_KEEPALIVE_CRON_EXPRESSION).getValue();
			
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
						.getIOControllerModuleValue(ioControllerModule, PARAM_INITIAL_REQUEST_KEEPALIVE_ACTIVE).getValue(),
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
	public void requestKeepAlive() {
		
		boolean wasConnected = mangoModuleRequestProcessor.isConnected();
		
		List<ElementState> elementStates = mangoModuleRequestProcessor.requestKeepAlive();
		
		boolean isConnected = CollectionUtils.isEmpty(elementStates);
		
		// Request all states when recover connection 
		if (!wasConnected && isConnected) {
			
			requestAllStates();	
		}
		// Send states to disconnected
		else if (wasConnected && !isConnected) {
			
			receiveStates(elementStates);
		}
	}

	@Override
	public void initModuleKeepAliveElements() {

		List<ElementIOControllerModule> moduleElements = ioControllerModule
				.getModuleElements();

		if (!CollectionUtils.isEmpty(moduleElements)) {

			for (ElementIOControllerModule moduleElement : moduleElements) {

				Element element = moduleElement.getElement();

				if (element != null) {

					if (element != null) {
						
						String elementId = new Key(element).getSimpleValue();
						
						if (StringUtils.isNotEmpty(elementId)) {
							
							this.moduleKeepAliveElements.add(elementId);
						}
					}
				}
			}
		}
	}
	
	@Override
	public Element addElement(Element element) { 

		return this.mangoModuleRequestProcessor.addElement(element);
	}
	
	@Override
	public Element deleteElement(Element element) { 
		
		return this.mangoModuleRequestProcessor.deleteElement(element); 
	}
}
