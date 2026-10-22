package com.revenga.rits.back.io.controller.module.rtms;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;
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
import org.quartz.impl.StdSchedulerFactory;

import com.revenga.rits.back.adapter.lib.model.ControllerAdapterV1.Mods.Mod;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementIOControllerModule;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.IOControllerModule;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.RequestCommand;
import com.revenga.rits.back.data.core.model.command.SetElementStatesChangeCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.io.controller.adapter.RtmsAdapter;
import com.revenga.rits.back.io.controller.driver.GenericDriver;
import com.revenga.rits.back.io.controller.module.GenericModule;
import com.revenga.rits.back.io.controller.module.scheduler.ScheduledRequestMeasuresJob;
import com.revenga.rits.back.io.controller.module.thread.ThreadSendManager;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RtmsModule extends GenericModule {
	
	private static final String FILE_APP_PROPS = "application.properties";
	private static final Long PARAM_INITIAL_REQUEST_MEASURES_ACTIVE = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.rtms.request.measures.active"));
	private static final Long PARAM_REQUEST_MEASURES_CRON_EXPRESSION = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.rtms.request.measures.cron"));
	private static final Long PARAM_REQUEST_MEASURES_DIFF_TIME_MIN = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "module.rtms.request.measures.diff.time.min"));
	private static final String IDENTITY_JOB = "job";
	private static final String IDENTITY_GROUP = "group";
	private static final String IDENTITY_TRIGGER = "trigger";	
	private static final String REQUEST_MEASURES_TEMPLATE = "interval-data?start-time=%s";
	
	static final String DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	static final String DATEFORMAT_RTMS = "yyyy-MM-dd'T'HH:mm:ss'+00'";

	Scheduler scheduler= null;
	List<RtmsHttpSession> assignedRmtss;
	int responseCount;
	
	String modRef;
	
	public RtmsModule(Mod mod, IOControllerModule ioControllerModule, GenericDriver driver, short threadNum) {
		
		super(mod, ioControllerModule, driver, threadNum);
		
		modRef = "mod_" + mod.getType() + "_" + mod.getId();
	}
	
	@Override
	protected void initialize() {
		
		List<Element> rmtss = getAllRtms();
		assignedRmtss = new ArrayList<>();

		for (Element rtms : rmtss) {

			if ((rtms.getId() % this.getMod().getThreads().longValue()) == this.getThreadNum() - 1) {

				RtmsHttpSession rtmsConnector = new RtmsHttpSession(rtms);
				assignedRmtss.add(rtmsConnector);
			}
		}
		
		if (!CollectionUtils.isEmpty(assignedRmtss)) {
			
			launchScheduler();
			launchInitialRequest();
		}
	}
	
	private List<Element> getAllRtms(){
		
		List<Element> rtmss = new ArrayList<>();
		
		List<ElementIOControllerModule> elementsModule = ioControllerModule.getModuleElements().stream()
				.filter(e -> e.getElementTypeId().equals(ElementType.ELEMENT_TYPE_RTMS)).collect(Collectors.toList());
		
		if (!CollectionUtils.isEmpty(elementsModule)) {
			
			for (ElementIOControllerModule elementModule : elementsModule) {
				
				Element element = elementModule.getElement();
				
				if (element != null) {
					
					rtmss.add(element);
				}
			}
		}
		
		return rtmss;
	}
	
	public List<Element> getRtmsDetectors(Element rtms){
		
		List<Element> rtmsDetectors = new ArrayList<>();
		
		List<ElementHierarchy> rtmsHierarchies = EntitiesManager.getInstance()
				.getsByParent(rtms.getElementTypeId(), rtms.getId());
		
		if (!CollectionUtils.isEmpty(rtmsHierarchies)) {
			
			List<ElementHierarchy> detectorsInDaiHierarchies = rtmsHierarchies.stream()
					.filter(e -> e.getChildElementTypeId().equals(ElementType.ELEMENT_TYPE_DETECTOR))
					.collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(detectorsInDaiHierarchies)) {
				
				ElementType elementTypeDetector = EntitiesManager.getInstance().getElementType(ElementType.ELEMENT_TYPE_DETECTOR);
				
				if (elementTypeDetector != null) {
					
					for (ElementHierarchy detectorInDaiHierarchy : detectorsInDaiHierarchies) {
						
						Element detector = EntitiesManager.getInstance().getElement(detectorInDaiHierarchy.getChildElementTypeId(), detectorInDaiHierarchy.getChildElementId());
						
						if (detector != null && elementTypeDetector.getId().equals(detector.getElementTypeId())) {
								
							detector.setElementType(elementTypeDetector);
							
							detector.getElementType().setElementTypeParams(EntitiesManager.getInstance().getElementTypeParams(detector.getElementTypeId()));
							
							rtmsDetectors.add(detector);
						}
					}
				}
			}
		}
		
		return rtmsDetectors;
	}
	
	@Override
	protected void process(Command queueCommand) {
		
		if (queueCommand instanceof SignallingCommand) {
			
			// Not implemented
		}
		else if (queueCommand instanceof RequestCommand) {
			
			// Not implemented
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

	}

	@Override
	public void requestAllStates() {
		
	}
	
	@Override
	public void requestMeasures(Element element) {

	}
	
	@Override
	public void requestAllMeasures() {

		for (RtmsHttpSession targetRtms : assignedRmtss) {
			
			String response = null;
			Instant instant = Instant.now();
			
			String preparedQuery = prepareRtmsQuery();
			
			if (preparedQuery != null) {
				
				response = targetRtms.requestRtmsMeasures(preparedQuery);
			}
			
			if (!StringUtils.isEmpty(response) ) {
				
				log.info("[" + this.getName() + "](#" + (++responseCount) + ")[RequestMeasures - "
						+ (new Key(targetRtms.getTargetRtms())).getSimpleValue() + "] ("
						+ Duration.between(instant, Instant.now()).toMillis() + " ms.) << " + response);
				
				sendState(targetRtms.getTargetRtms(), ElementTypeState.STATE_UP);
				
				List<Element> rtmsDetectors = getRtmsDetectors(targetRtms.getTargetRtms());
				
				for (Element rtmsDetector : rtmsDetectors) {
					
					if (rtmsDetector != null) {
						
						List<ElementValue> finalListElementValues = new ArrayList<>();
						
						List<List<ElementValue>> listElementValues = RtmsAdapter.getInstance().getElementValues(rtmsDetector, response);
						
						if (!CollectionUtils.isEmpty(listElementValues)) {
							
							sendState(rtmsDetector, ElementTypeState.STATE_UP);
							
							if (RtmsAdapter.getInstance().processGroovyAdapter(rtmsDetector, listElementValues, finalListElementValues)) {
							
								receiveMeasures(rtmsDetector, finalListElementValues);
							}
							
						} else {
							
							sendState(rtmsDetector, ElementTypeState.STATE_DOWN);
						}
					}
				}
				
			} else {
				
				log.error("Can not find RTMS measures");
				sendState(targetRtms.getTargetRtms(), ElementTypeState.STATE_DOWN);
			}
		}
	}
	
	@Override
	public void requestAlarms(Element element) {
		
	}
	
	@Override
	public void requestAllAlarms() {
		
	}
	
	@Override
	public void requestKeepAlive() {
		
	}
	
	@Override
	public void sendCommand(SignallingCommand cmd) {
		// TODO Auto-generated method stub
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
	

	private void sendState(Element element, Long stateId) {
		
		ElementState elementState =  new ElementState();
		
		try {
			elementState.setElement(element);
			elementState.setElementId(element.getId());
			elementState.setStateId(stateId);
			elementState.setElementTypeId(element.getElementTypeId());
			
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
	
	private boolean launchScheduler() {
		
		String cronExpressionMeasures;
		
		try {

			Properties properties = new Properties();
			properties.setProperty("org.quartz.threadPool.threadCount", String.valueOf(this.getThreadNum()));
			properties.setProperty("org.quartz.scheduler.instanceName", String.valueOf(this.getThreadNum()));

			scheduler = new StdSchedulerFactory(properties).getScheduler();

			scheduler.getContext().put(modRef, this);

			IOControllerModule module = EntitiesManager.getInstance().getIOControllerModule(Long.valueOf(mod.getType()), Long.valueOf(mod.getId()));
			
			cronExpressionMeasures = EntitiesManager.getInstance()
					.getIOControllerModuleValue(module, PARAM_REQUEST_MEASURES_CRON_EXPRESSION).getValue();
			
			if (!StringUtils.isEmpty(cronExpressionMeasures)) {
				
				createJob(ScheduledRequestMeasuresJob.class, cronExpressionMeasures);
			}

			scheduler.start();
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return true;
	}
	
	private void createJob(Class<? extends Job> jobClass, String cronExpression) {
		
		try {

			String name = jobClass.getSimpleName() + ":" + modRef;
			
			JobDetail job = newJob(jobClass).withIdentity(IDENTITY_JOB+this.getThreadNum() + name, IDENTITY_GROUP+this.getThreadNum() + name).build();
			
			if (!StringUtils.isEmpty(cronExpression)) {
				
				CronTrigger trigger = newTrigger().withIdentity(IDENTITY_TRIGGER+this.getThreadNum() + name, IDENTITY_GROUP+this.getThreadNum() + name)
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
	
	private String prepareRtmsQuery() {
		
		/// Example of String format for RTMS API.
		/// interval-data?start-time=2022-08-17T09:30:00Z&end-time=2022-08-17T09:33:00Z
		String preparedValues = null;
		try {

			Date currentTime = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentTime);
			Integer diffTimeMinValue = Integer.valueOf(EntitiesManager.getInstance()
					.getIOControllerModuleValue(ioControllerModule, PARAM_REQUEST_MEASURES_DIFF_TIME_MIN).getValue());
			calendar.add(Calendar.MINUTE, -diffTimeMinValue);
			calendar.set(Calendar.SECOND, 0);
			Date startTimeDate = calendar.getTime();

			SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

			preparedValues = String.format(REQUEST_MEASURES_TEMPLATE, sdf.format(startTimeDate));

		} catch (Exception e) {

			log.error("Can not get Interval Time");
			e.printStackTrace();
		}
		return preparedValues;
	}

	private void launchInitialRequest() {
	
		boolean requestInitialMeasuresEnabled = false;

		requestInitialMeasuresEnabled = StringUtils.equalsAny(
				EntitiesManager.getInstance()
						.getIOControllerModuleValue(ioControllerModule, PARAM_INITIAL_REQUEST_MEASURES_ACTIVE).getValue(),
				"1", "true");

		if (requestInitialMeasuresEnabled) {

			requestAllMeasures();
		}
	}

	@Override
	public void receiveAlarms(Element element, List<Pair<AlarmConfig, Boolean>> alarmConfigActivations) {

		
	}

	@Override
	public void receiveAlarm(Element element, Pair<AlarmConfig, Boolean> alarmConfigActivation) {

		
	}

	@Override
	public void receiveState(ElementState elementState) {

		
	}

	@Override
	public void initModuleKeepAliveElements() {
		
		List<Element> rtmss = getAllRtms();
		
		for (Element element : rtmss) {
			
			if (element != null) {
				
				initModuleKeepAliveElement(element);
			}
		}
	}
	
	private Element initModuleKeepAliveElement(Element element) {
		
		String elementId = new Key(element).getSimpleValue();
		
		if (StringUtils.isNotEmpty(elementId)) {
			
			this.moduleKeepAliveElements.add(elementId);
		}
		
		List<Element> rtmsDetectors = getRtmsDetectors(element);
		
		if (CollectionUtils.isNotEmpty(rtmsDetectors)) {
			
			for (Element detector : rtmsDetectors) {
				
				if (detector != null) {
					
					String detectorId = new Key(element).getSimpleValue();
					
					if (StringUtils.isNotEmpty(detectorId)) {
						
						this.moduleKeepAliveElements.add(detectorId);
					}
				}
			}
		}
		
		return element;
	}

	@Override
	public Element addElement(Element element) {
		
		if ((element.getId() % this.getMod().getThreads().longValue()) == this.getThreadNum() - 1) {

			RtmsHttpSession rtmsConnector = new RtmsHttpSession(element);
			assignedRmtss.add(rtmsConnector);
			
			initModuleKeepAliveElement(element);
			
			return element;
		}
		
		return null;
	}

	@Override
	public Element deleteElement(Element element) {
		
		if ((element.getId() % this.getMod().getThreads().longValue()) == this.getThreadNum() - 1) {
			
			if (assignedRmtss.removeIf(e -> e.getTargetRtms().getElementTypeId().equals(element.getElementTypeId()) && e.getTargetRtms().getId().equals(element.getId()))) {
				
				return element;
			}
		}
		
		return null;
	}
	
}


