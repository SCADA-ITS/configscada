package com.revenga.rits.back.equipment.manager.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementPrecondition;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementSubtypeActiveParam;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementValueState;
import com.revenga.rits.back.data.core.model.ManagementArea;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.PmDisabledQueuePriority;
import com.revenga.rits.back.data.core.model.PmElementTypeQueue;
import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetAlarmsPublishCommand;
import com.revenga.rits.back.data.core.model.command.SetElementStatesPublishCommand;
import com.revenga.rits.back.data.core.model.command.SetElementValuesPublishCommand;
import com.revenga.rits.back.data.core.model.command.SetHistoricElementCommand;
import com.revenga.rits.back.data.core.model.command.SetKeepAliveDriverInstanceCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetAlarmsToIlluminationCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetAlarmsToVentilationCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementStatesToIlluminationCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementStatesToVentilationCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementValuesToIlluminationCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementValuesToVentilationCommand;
import com.revenga.rits.back.entities.lib.repository.AlarmConfigRepository;
import com.revenga.rits.back.entities.lib.repository.AlarmRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementPreconditionRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementTypeRepository;
import com.revenga.rits.back.entities.lib.repository.ElementHierarchiesRepository;
import com.revenga.rits.back.entities.lib.repository.ElementRepository;
import com.revenga.rits.back.entities.lib.repository.ElementStateRepository;
import com.revenga.rits.back.entities.lib.repository.ElementSubtypeActiveParamRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeRepository;
import com.revenga.rits.back.entities.lib.repository.ElementValueRepository;
import com.revenga.rits.back.entities.lib.repository.ManagementAreaRepository;
import com.revenga.rits.back.entities.lib.repository.PmCommandElementRepository;
import com.revenga.rits.back.entities.lib.repository.PmCommandElementValueRepository;
import com.revenga.rits.back.entities.lib.repository.PmDisabledQueuePriorityRepository;
import com.revenga.rits.back.entities.lib.repository.PmElementTypeQueueRepository;
import com.revenga.rits.back.entities.lib.repository.PmQueuePriorityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.equipment.manager.advisor.ThreadEquipmentAdvisor;
import com.revenga.rits.back.equipment.manager.advisor.model.EquipmentChangeAlarmMessage;
import com.revenga.rits.back.equipment.manager.advisor.model.EquipmentChangeMeasureMessage;
import com.revenga.rits.back.equipment.manager.advisor.model.EquipmentChangeStateMessage;
import com.revenga.rits.back.equipment.manager.advisor.model.EquipmentCreateAlarmMessage;
import com.revenga.rits.back.equipment.manager.data.RepositoryData;
import com.revenga.rits.back.equipment.manager.data.RootData;
import com.revenga.rits.back.equipment.manager.executor.coordinator.ElementStateCoordinator;
import com.revenga.rits.back.equipment.manager.service.virtual.ThreadSetAlarmsToIlluminationCommand;
import com.revenga.rits.back.equipment.manager.service.virtual.ThreadSetAlarmsToVentilationCommand;
import com.revenga.rits.back.equipment.manager.service.virtual.ThreadSetElementStatesToIlluminationCommand;
import com.revenga.rits.back.equipment.manager.service.virtual.ThreadSetElementStatesToVentilationCommand;
import com.revenga.rits.back.equipment.manager.service.virtual.ThreadSetElementValuesToIlluminationCommand;
import com.revenga.rits.back.equipment.manager.service.virtual.ThreadSetElementValuesToVentilationCommand;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EntitiesManager {

	Map<Element, Set<Element>> childInheritableParents;
	Map<Element, Set<Pair<Element, Long>>> parentChildInheritables;
	Long lastUpdatedParentChildRel = 0L;
	
	// Key = IO-Controler Module-id
	// Value = List of equipments and date updated
	Map<Long, Pair<List<String>, Long>> keepAliveDriversData;

	@Getter
	Scheduler scheduler;
	
	@Getter
	private RepositoryData repositoryData = new RepositoryData();
	
	@Getter
	private RootData rootData;
	
	@Getter
	@Setter
	boolean runningJob;

	@Getter
	// ElementTypeId, ElementId
	Map<Pair<Long, Long>, Semaphore> elementsMutex;

	ThreadSendRequestCommand threadSendRequestCommand;
	ThreadSendSetAlarmsPublishCommand threadSendSetAlarmsPublishCommand;
	ThreadSendSetElementStatesPublishCommand threadSendSetElementStatesPublishCommand;
	ThreadSendSetElementValuesPublishCommand threadSendSetElementValuesPublishCommand;
	ThreadEquipmentAdvisor threadEquipmentAdvisor;

	ThreadSetHistoricCommand threadSetHistoricCommand;

	ThreadSetElementStatesToVentilationCommand threadSetElementStatesToVentilationCommand;
	ThreadSetElementStatesToIlluminationCommand threadSetElementStatesToIlluminationCommand;
	ThreadSetElementValuesToVentilationCommand threadSetElementValuesToVentilationCommand;
	ThreadSetElementValuesToIlluminationCommand threadSetElementValuesToIlluminationCommand;
	ThreadSetAlarmsToVentilationCommand threadSetAlarmsToVentilationCommand;
	ThreadSetAlarmsToIlluminationCommand threadSetAlarmsToIlluminationCommand;

	ThreadManagerAlarms threadManagerAlarms;

	private AtomicBoolean dbSyncPmQueueFlag;

	@Getter
	private boolean initialized = false;

	private static boolean initializing = false;
	private static EntitiesManager instance;

	public static EntitiesManager getInstance() {

		if (instance == null) {
			
			while (initializing && instance == null) {
				
				try {
					
					log.debug("Waiting to EntitiesManager instance first time...");
					
					Thread.sleep(1000);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (instance == null) {
			
				initializing = true;
				
				instance = new EntitiesManager();
				
				initializing = false;
				
				log.debug("EntitiesManager is instanced");
			}
		}

		return instance;
	}

	private EntitiesManager() {

		Instant instant = Instant.now();

		dbSyncPmQueueFlag = new AtomicBoolean(false);
		
		this.repositoryData.load();
		
		initStates();
		initAlarms();
		initMeasures();
		
		this.rootData = new RootData(this);
		this.rootData.load();
		
		childInheritableParents = Collections.synchronizedMap(new HashMap<>());
		parentChildInheritables = Collections.synchronizedMap(new HashMap<>());
		keepAliveDriversData = Collections.synchronizedMap(new HashMap<>());
		
		initQuartzScheduler();
		initThreadSendRequestCommand();
		initThreadSendSetAlarmsPublishCommand();
		initThreadSendSetElementStatesPublishCommand();
		initThreadSendSetElementValuesPublishCommand();
		initThreadEquipmentAdvisor();
		initThreadSetHistoricCommand();
		initThreadToServiceVirtual();
		initThreadManagerAlarms();

		sendSetElementStatesPublishCommand(this.rootData.getElementStates());
		sendSetAlarmsPublishCommand(this.rootData.getAlarms());

		// Init elementsMutex
		elementsMutex = new HashedMap<>();
		
		List<PmElementTypeQueue> pmElementTypeQueues = ((PmElementTypeQueueRepository) repositoryData.getRepository(PmElementTypeQueue.class)).getAll(false);

		if (CollectionUtils.isNotEmpty(pmElementTypeQueues)) {

			for (PmElementTypeQueue pmElementTypeQueue : pmElementTypeQueues) {

				if (pmElementTypeQueue.getElementTypeId() != null) {

					List<Element> elements = ((ElementRepository) repositoryData.getRepository(Element.class)).gets(pmElementTypeQueue.getElementTypeId(), false);

					if (CollectionUtils.isNotEmpty(elements)) {

						elements.forEach(element -> elementsMutex
								.put(new MutablePair<>(element.getElementTypeId(), element.getId()), new Semaphore(1)));
					}
				}
			}
		}
		
		this.repositoryData.startSubscriptions();
		this.rootData.start();
		
		initialized = true;

		log.info("EntitiesManager loaded in " + Duration.between(instant, Instant.now()).toMillis() + " ms.");
	}
	
	private void initStates() {
		
		List<ElementState> elementStates = ((ElementStateRepository) repositoryData.getRepository(ElementState.class)).getAll(false);
		
		if (CollectionUtils.isNotEmpty(elementStates)) {
			
			for (ElementState elementState : elementStates) {
				
				ElementType elementType = getElementType(elementState.getElementTypeId());
				
				if (!elementState.getStateId().equals(ElementTypeState.STATE_MAINTENANCE)) {
					
					elementState.setStateId(elementType.getInitialElementTypeStateId());
				}
			}
			
			((ElementStateRepository) repositoryData.getRepository(ElementState.class)).put(elementStates);
			
			log.debug("Initialized " + elementStates.size() + " element states to default values except elements on Maintenance State");
		}
	}
	
	public void updateKeepAliveDriverData(SetKeepAliveDriverInstanceCommand setKeepAliveDriverInstanceCommand) {

		if (setKeepAliveDriverInstanceCommand.getDriverId() != null
				&& setKeepAliveDriverInstanceCommand.getLastUpdate() != null && keepAliveDriversData != null) {

			keepAliveDriversData.computeIfAbsent(setKeepAliveDriverInstanceCommand.getDriverId(),
					k -> new MutablePair<>(new ArrayList<>(), null));

			if (CollectionUtils.isNotEmpty(setKeepAliveDriverInstanceCommand.getElementIds())) {

				keepAliveDriversData.put(setKeepAliveDriverInstanceCommand.getDriverId(),
						new MutablePair<>(setKeepAliveDriverInstanceCommand.getElementIds(),
								setKeepAliveDriverInstanceCommand.getLastUpdate()));
			}
		}
	}
	
	public void checkKeepAlive(Long maxTimeOutAllowedSeconds) {
		
		Long currentTime = Instant.now().toEpochMilli();
		
		Long maxTimeOutAllowed = currentTime - (maxTimeOutAllowedSeconds * 1000);
		
		if (keepAliveDriversData != null) {
			
			for (Map.Entry<Long, Pair<List<String>, Long>> entry : keepAliveDriversData.entrySet()) {
				
				Long lastUpdate = entry.getValue().getRight();
				
				if (lastUpdate != null && lastUpdate < maxTimeOutAllowed) {
					
					log.info("Driver "+entry.getKey()+" does not respond");
					initStates(entry.getValue().getLeft(), currentTime);
				}
			}
		}
	}
	
	private void initStates(List<String> elementIds, Long currentUpdate) {
		
		if (CollectionUtils.isNotEmpty(elementIds)) {
			
			for (String elementId : elementIds) {
				
				Key key = new Key(elementId);
				
				Element elementTemp = new Element();
				elementTemp.setPks(key.getIds());

				ElementType elementType = getElementType(elementTemp.getElementTypeId());
				
				if (elementType != null) {
					
					ElementState elementState  = new ElementState();
					
					elementState.setElementId(elementTemp.getId());
					elementState.setElementTypeId(elementTemp.getElementTypeId());
					elementState.setStateId(elementType.getInitialElementTypeStateId());
					
					Element element = EntitiesManager.getInstance().getRootData()
							.getElement(elementState.getElementTypeId(), elementState.getElementId());

					if (element != null) {
						
						EntitiesManager.getInstance().getRootData().lock(element.getElementTypeId(), element.getId());
						
						boolean isOnMaintenance = false;
						
						if (element.getElementState() != null && element.getElementState().getStateId().equals(elementType.getMaintenanceElementTypeStateId())) {
							
							isOnMaintenance = true;
							
						} 
						
						try {
							
							if (element.getLastUpdatedState() < currentUpdate && !isOnMaintenance) {
								
								element.setLastUpdatedState(currentUpdate);
								
								ElementStateCoordinator elementStateCoordinator = new ElementStateCoordinator(element);

								elementStateCoordinator.update(elementState);
							}
							
						} catch (Exception e) {
							
							log.error(e.getMessage());
							log.debug(ExceptionUtils.getStackTrace(e));
						}
						
						EntitiesManager.getInstance().getRootData().unlock(element.getElementTypeId(), element.getId());
					}
				}
			}
		}
	}

	private void initAlarms() {
		
		List<Alarm> alarmsToDelete;
		AlarmRepository alarmRepository = (AlarmRepository) repositoryData.getRepository(Alarm.class);
		
		log.debug("Initializing alarms");
		
		if (alarmRepository != null) {
		
			List<Alarm> alarms = alarmRepository.getAll(false);
			
			if (!CollectionUtils.isEmpty(alarms)) {
				
				alarmsToDelete = alarms.stream()
						.filter(e -> e.getAlarmConfigId() == null || 
									 getAlarmConfig(e.getAlarmConfigId()) == null ||
									 !(e.getDeactivation() != null && e.getRecognized() == null))
						.collect(Collectors.toList());
				
				if (!CollectionUtils.isEmpty(alarmsToDelete)) {
					
					List<Alarm> alarmsToDeleted = alarmRepository.del(alarmsToDelete);
					
					log.debug("Initialized " + (!CollectionUtils.isEmpty(alarmsToDeleted) ? alarmsToDeleted.size() : 0)
							+ " alarms deleted");
				}
			}
		}
	}
	
	private void initMeasures() {
		
		List<Element> elements = this.repositoryData.getCollection(Element.class).stream().map(Element.class::cast)
				.collect(Collectors.toList());
		
		List<ElementTypeParam> elementTypeParams = this.repositoryData.getCollection(ElementTypeParam.class).stream()
				.map(ElementTypeParam.class::cast).collect(Collectors.toList());

		List<ElementValue> elementValues = null;
		
		log.debug("Initializing element measures");
		
		if (!CollectionUtils.isEmpty(elements) && !CollectionUtils.isEmpty(elementTypeParams)) {
			
			elementValues = getInitializedElementValues(elements);
		}
		
		if (!CollectionUtils.isEmpty(elementValues)) {
			
			((ElementValueRepository) repositoryData.getRepository(ElementValue.class)).put(elementValues);
			
			log.debug("Initialized " + elementValues.size() + " measures in " + elements.size() + " elements to default values");
		}
		else {
			
			log.warn("There are no elements to initialize measures");
		}
	}
	
	private void initQuartzScheduler() {

		try {

			SchedulerFactory sf = new StdSchedulerFactory();
			scheduler = sf.getScheduler();

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	private void initThreadSendRequestCommand() {

		this.threadSendRequestCommand = new ThreadSendRequestCommand();

		(new Thread(threadSendRequestCommand, ThreadSendRequestCommand.class.getSimpleName())).start();
	}

	private void initThreadSendSetAlarmsPublishCommand() {

		this.threadSendSetAlarmsPublishCommand = new ThreadSendSetAlarmsPublishCommand();

		(new Thread(threadSendSetAlarmsPublishCommand, ThreadSendSetAlarmsPublishCommand.class.getSimpleName()))
				.start();
	}

	private void initThreadSendSetElementStatesPublishCommand() {

		this.threadSendSetElementStatesPublishCommand = new ThreadSendSetElementStatesPublishCommand();

		(new Thread(threadSendSetElementStatesPublishCommand,
				ThreadSendSetElementStatesPublishCommand.class.getSimpleName())).start();
	}
	
	private void initThreadSendSetElementValuesPublishCommand() {

		this.threadSendSetElementValuesPublishCommand = new ThreadSendSetElementValuesPublishCommand();

		(new Thread(threadSendSetElementValuesPublishCommand,
				ThreadSendSetElementValuesPublishCommand.class.getSimpleName())).start();
	}

	private void initThreadEquipmentAdvisor() {

		this.threadEquipmentAdvisor = new ThreadEquipmentAdvisor();

		(new Thread(threadEquipmentAdvisor, ThreadEquipmentAdvisor.class.getSimpleName())).start();
	}

	private void initThreadSetHistoricCommand() {

		this.threadSetHistoricCommand = new ThreadSetHistoricCommand();

		(new Thread(threadSetHistoricCommand, ThreadSetHistoricCommand.class.getSimpleName())).start();
	}

	private void initThreadToServiceVirtual() {

		this.threadSetElementStatesToIlluminationCommand = new ThreadSetElementStatesToIlluminationCommand();

		(new Thread(threadSetElementStatesToIlluminationCommand,
				ThreadSetElementStatesToIlluminationCommand.class.getSimpleName())).start();

		this.threadSetElementStatesToVentilationCommand = new ThreadSetElementStatesToVentilationCommand();

		(new Thread(threadSetElementStatesToVentilationCommand,
				ThreadSetElementStatesToVentilationCommand.class.getSimpleName())).start();

		this.threadSetElementValuesToIlluminationCommand = new ThreadSetElementValuesToIlluminationCommand();

		(new Thread(threadSetElementValuesToIlluminationCommand,
				ThreadSetElementValuesToIlluminationCommand.class.getSimpleName())).start();

		this.threadSetElementValuesToVentilationCommand = new ThreadSetElementValuesToVentilationCommand();

		(new Thread(threadSetElementValuesToVentilationCommand,
				ThreadSetElementValuesToVentilationCommand.class.getSimpleName())).start();

		this.threadSetAlarmsToIlluminationCommand = new ThreadSetAlarmsToIlluminationCommand();

		(new Thread(threadSetAlarmsToIlluminationCommand, ThreadSetAlarmsToIlluminationCommand.class.getSimpleName()))
				.start();

		this.threadSetAlarmsToVentilationCommand = new ThreadSetAlarmsToVentilationCommand();

		(new Thread(threadSetAlarmsToVentilationCommand, ThreadSetAlarmsToVentilationCommand.class.getSimpleName()))
				.start();
	}

	private void initThreadManagerAlarms() {

		this.threadManagerAlarms = new ThreadManagerAlarms();

		(new Thread(threadManagerAlarms, ThreadManagerAlarms.class.getSimpleName())).start();
	}

	/**
    * @deprecated (Replace for GroovyHelper.getsByParent)
    */
	@Deprecated(forRemoval=true)
	public List<ElementHierarchy> getsByParent(Long parentElementTypeId, Long parentElementId, Boolean fillObject) {

		List<BasicElement> basicElements = ((ElementHierarchiesRepository) repositoryData.getRepository(ElementHierarchy.class)).getsFromCache(
				new String[] { String.valueOf(parentElementTypeId), String.valueOf(parentElementId), null, null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementHierarchy.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<Element> getElements() {

		return ((ElementRepository) repositoryData.getRepository(Element.class)).getAll(false);
	}

	public List<Element> getElements(Long elementTypeId) {

		return ((ElementRepository) repositoryData.getRepository(Element.class)).gets(elementTypeId, false);
	}

	public Element getElement(Long elementTypeId, Long elementId) {

		return ((ElementRepository) repositoryData.getRepository(Element.class)).get(elementTypeId, elementId, false);
	}

	public Element getElement(ElementValue elementValue) {

		return ((ElementRepository) repositoryData.getRepository(Element.class)).get(elementValue.getElementTypeId(), elementValue.getElementId(), false);
	}

	public List<ElementType> getElementsType() {

		return ((ElementTypeRepository) repositoryData.getRepository(ElementType.class)).getAll(false);
	}

	public ElementType getElementType(Long elementTypeId) {

		return ((ElementTypeRepository) repositoryData.getRepository(ElementType.class)).get(elementTypeId, false);
	}

	public List<ManagementArea> getManagementAreas() {

		return ((ManagementAreaRepository) repositoryData.getRepository(ManagementArea.class)).getAll(false);
	}
	
	public List<ElementState> getElementStates() {

		return rootData.getElementStates();		
	}

	public ElementState getElementState(Long elementTypeId, Long elementId) {

		ElementState elementState = null;
		
		Element element = rootData.getElement(elementTypeId, elementId);
		
		if (element != null) {
			
			elementState = element.getElementState();
		}
		
		return elementState;
	}

	public AlarmConfig getAlarmConfig(Long alarmConfigId) {

		return ((AlarmConfigRepository) repositoryData.getRepository(AlarmConfig.class)).get(alarmConfigId, false);
	}

	public ElementTypeParam getElementTypeParam(ElementValue elementValue) {

		return ((ElementTypeParamRepository) repositoryData.getRepository(ElementTypeParam.class)).get(elementValue.getElementTypeId(), elementValue.getParamTypeId(),
				elementValue.getElementTypeParamId(), false);
	}

	public List<ElementTypeParam> getElementTypeParams(Long elementTypeId) {

		return ((ElementTypeParamRepository) repositoryData.getRepository(ElementTypeParam.class)).gets(elementTypeId, false);
	}

	public List<ElementTypeParam> getElementTypeParams() {

		return ((ElementTypeParamRepository) repositoryData.getRepository(ElementTypeParam.class)).getAll(false);
	}

	public List<ElementSubtypeActiveParam> getElementSubtypeActiveParams(Long elementSubtypeId, Long elementTypeId) {

		return ((ElementSubtypeActiveParamRepository) repositoryData.getRepository(ElementSubtypeActiveParam.class)).gets(elementTypeId, elementSubtypeId, false);
	}

	public List<ElementSubtypeActiveParam> getElementSubtypeActiveParams() {

		return ((ElementSubtypeActiveParamRepository) repositoryData.getRepository(ElementSubtypeActiveParam.class)).getAll(false);
	}

	public List<ElementValue> getElementValues() {

		return ((ElementValueRepository) repositoryData.getRepository(ElementValue.class)).getAll(false);
	}

	public List<ElementValue> getElementMeasures() {

		return ((ElementValueRepository) repositoryData.getRepository(ElementValue.class)).getByParamType(ParamType.MEASURE, false);
	}

	public List<ElementValue> getElementMeasures(Element element) {

		return ((ElementValueRepository) repositoryData.getRepository(ElementValue.class)).gets(element.getElementTypeId(), element.getId(), ParamType.MEASURE, false);
	}

	public List<ElementValue> getElementValues(Long elementTypeId, Long elementId) {

		return ((ElementValueRepository) repositoryData.getRepository(ElementValue.class)).gets(elementTypeId, elementId, false);
	}

	public ElementValue getElementValueMeasure(Element element, Long elementTypeParamId) {

		return ((ElementValueRepository) repositoryData.getRepository(ElementValue.class)).get(element.getElementTypeId(), element.getId(), elementTypeParamId,
				ParamType.MEASURE, false);
	}

	public ElementValue getElementValueConfig(Element element, Long elementTypeParamId) {

		return ((ElementValueRepository) repositoryData.getRepository(ElementValue.class)).get(element.getElementTypeId(), element.getId(), elementTypeParamId,
				ParamType.CONFIG, false);
	}

	/**
	 * @deprecated (Replace to GroovyHelper.putElementValues
	 */
	@Deprecated(forRemoval=true)
	public boolean putElementValues(ElementValue[] elementValues) {

		boolean res = false;

		res = (((ElementValueRepository) repositoryData.getRepository(ElementValue.class)).put(Arrays.asList(elementValues)) != null);

		return res;
	}

	public List<ElementValue> getInitializedElementValues(List<Element> elements) {

		List<ElementValue> elementValues = new ArrayList<>();

		List<ElementTypeParam> elementTypeParams = getElementTypeParams();
		List<ElementSubtypeActiveParam> elementSubTypeActiveParams = null;

		List<ElementValue> elementMeasures = getElementMeasures();

		for (Element element : elements) {

			List<ElementTypeParam> elementTypeParamsByElement = elementTypeParams.stream()
					.filter(e -> e.getElementTypeId().equals(element.getElementTypeId())).collect(Collectors.toList());

			// Filter elementSubtype measures
			if (element.getElementSubtypeId() != null) {

				if (elementSubTypeActiveParams == null) {

					elementSubTypeActiveParams = getElementSubtypeActiveParams();
				}

				if (!CollectionUtils.isEmpty(elementSubTypeActiveParams)) {
					List<ElementSubtypeActiveParam> elementSubtypeActiveParamByElement = elementSubTypeActiveParams
							.stream()
							.filter(e -> e.getElementSubtypeId().equals(element.getElementSubtypeId())
									&& e.getElementTypeId().equals(element.getElementTypeId()))
							.collect(Collectors.toList());

					elementTypeParamsByElement = elementTypeParamsByElement.stream()
							.filter(e -> elementSubtypeActiveParamByElement.stream()
									.filter(f -> f.getParamTypeId().equals(e.getParamTypeId())
											&& f.getId().equals(e.getId()))
									.findFirst().orElse(null) != null)
							.collect(Collectors.toList());
				}

			}

			// Filter durable measures
			if (!CollectionUtils.isEmpty(elementMeasures)) {

				List<ElementValue> measures = elementMeasures.stream()
						.filter(e -> e.getElementTypeId().equals(element.getElementTypeId())
								&& e.getElementId().equals(element.getId()))
						.collect(Collectors.toList());

				if (!CollectionUtils.isEmpty(measures)) {

					elementTypeParamsByElement = elementTypeParamsByElement.stream()
							.filter(e -> measures.stream()
									.filter(measure -> measure.getParamTypeId().equals(e.getParamTypeId())
											&& measure.getElementTypeParamId().equals(e.getId()))
									.findFirst().orElse(null) == null || e.getDurable() == null
									|| !e.getDurable().booleanValue())
							.collect(Collectors.toList());
				}
			}

			if (!CollectionUtils.isEmpty(elementTypeParamsByElement)) {

				for (ElementTypeParam elementTypeParam : elementTypeParamsByElement) {

					if (elementTypeParam.getParamTypeId().equals(Long.valueOf(ParamType.MEASURE))) {

						ElementValue elementValue = new ElementValue();

						elementValue.setElementTypeId(element.getElementTypeId());
						elementValue.setElementId(element.getId());
						elementValue.setElementTypeParamId(elementTypeParam.getId());
						elementValue.setParamTypeId(elementTypeParam.getParamTypeId());
						elementValue.setElementValueStateId(ElementValueState.STATE_INITIALIZED);

						elementValue.setValue(elementTypeParam.getDefaultValue());

						elementValues.add(elementValue);
					}
				}
			}
		}

		return elementValues;
	}
	
	public List<ElementState> getInitializedElementStates(List<Element> elements) {
		
		List<ElementState> elementStates = new ArrayList<>();
		
		for (Element element : elements) {
			
			ElementType elementType = getElementType(element.getElementTypeId());
			
			if (elementType != null) {
			
				ElementState elementState = new ElementState();
				
				elementState.setElementTypeId(elementType.getId());
				elementState.setElementId(element.getId());
				elementState.setStateId(elementType.getInitialElementTypeStateId());

				elementStates.add(elementState);
			}
		}

		return elementStates;
	}

	public Command sendSetAlarmsPublishCommand(List<Alarm> alarms) {

		SetAlarmsPublishCommand setAlarmsPublishCommand = null;

		if (!CollectionUtils.isEmpty(alarms)) {

			setAlarmsPublishCommand = new SetAlarmsPublishCommand();

			for (Alarm alarm : alarms) {

				setAlarmsPublishCommand.addAlarm(alarm);
			}

			threadSendSetAlarmsPublishCommand.add(setAlarmsPublishCommand);
		}

		return setAlarmsPublishCommand;
	}

	public Command sendSetElementStatesPublishCommand(List<ElementState> elementStates) {

		SetElementStatesPublishCommand setElementStatesPublishCommand = null;

		if (!CollectionUtils.isEmpty(elementStates)) {

			setElementStatesPublishCommand = new SetElementStatesPublishCommand();

			for (ElementState elementState : elementStates) {

				setElementStatesPublishCommand.addElementState(elementState);
			}

			threadSendSetElementStatesPublishCommand.add(setElementStatesPublishCommand);
		}

		return setElementStatesPublishCommand;
	}
	
	public Command sendSetElementValuesPublishCommand(List<ElementValue> elementValues) {
		
		if (!CollectionUtils.isEmpty(elementValues)) {
			
			SetElementValuesPublishCommand setElementValuesPublishCommand = new SetElementValuesPublishCommand();
			
			elementValues.forEach(setElementValuesPublishCommand::addElementValue);
			
			this.threadSendSetElementValuesPublishCommand.add(setElementValuesPublishCommand);
			
			return setElementValuesPublishCommand;
		}
		
		return null;
				
	}
	
	public Command sendSetElementValuesPublishCommand(SetElementValuesPublishCommand setElementValuesPublishCommand) {
		
		if (setElementValuesPublishCommand != null) {
			
			this.threadSendSetElementValuesPublishCommand.add(setElementValuesPublishCommand);
			
			return setElementValuesPublishCommand;
		}
		
		return null;
				
	}
	
	public void sendOnChangeMeasures(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {

		if (!StringUtils.isEmpty(element.getOnChangeMeasure()) && !CollectionUtils.isEmpty(elementValues)) {

			EquipmentChangeMeasureMessage equipmentChangeMeasureMessage = new EquipmentChangeMeasureMessage(element,
					elementValues);

			threadEquipmentAdvisor.add(equipmentChangeMeasureMessage);
		}
	}

	public void sendOnChangeState(Element element, ElementState elementState) {

		if (!StringUtils.isEmpty(element.getOnChangeState()) && elementState != null) {

			EquipmentChangeStateMessage equipmentChangeStateMessage = new EquipmentChangeStateMessage(element,
					elementState);

			threadEquipmentAdvisor.add(equipmentChangeStateMessage);
		}
	}

	public void sendOnChangeAlarms(List<Alarm> alarms) {

		EquipmentChangeAlarmMessage equipmentChangeAlarmMessage = new EquipmentChangeAlarmMessage(alarms);

		threadEquipmentAdvisor.add(equipmentChangeAlarmMessage);

	}
	
	public void sendOnCreateAlarm(Alarm alarm) {

		if (alarm.getAlarmConfig() != null && !StringUtils.isEmpty(alarm.getAlarmConfig().getOnCreateExecute())) {
			
			EquipmentCreateAlarmMessage equipmentCreateAlarmMessage = new EquipmentCreateAlarmMessage(alarm);

			threadEquipmentAdvisor.add(equipmentCreateAlarmMessage);
		}
	}

	public Alarm removeFromMinTimeMap(AlarmConfig alarmConfig, Element element) {

		if (alarmConfig != null && element != null) {

			return threadManagerAlarms.removeFromMinTimeMap(alarmConfig, element);
		}

		return null;
	}

	public boolean addInMinTimeMap(Alarm alarm) {

		if (alarm != null) {

			return threadManagerAlarms.addInMinTimeMap(alarm);
		}

		return false;
	}
	
	public Alarm removeFromThresholdMap(AlarmConfig alarmConfig, Element element) {

		if (alarmConfig != null && element != null) {

			return threadManagerAlarms.removeFromThresholdMap(alarmConfig, element);
		}

		return null;
	}

	public boolean addInThresholdMap(Alarm alarm) {

		if (alarm != null) {

			return threadManagerAlarms.addInThresholdMap(alarm);
		}

		return false;
	}
	
	public Command sendCommand(Command command) {
		
		return CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME);
	}

	/**
	 * 
	 * @param childElementTypeId
	 * @param childElementId
	 * @deprecated Change for GroovyHelper.getsByChild
	 * @return
	 */
	@Deprecated(forRemoval = true)
	public List<ElementHierarchy> getsByChild(Long childElementTypeId, Long childElementId) {

		return ((ElementHierarchiesRepository) repositoryData.getRepository(ElementHierarchy.class)).getsByChild(childElementTypeId, childElementId, false);
	}

	public Command sendElementToHistoriclManager(Element element) {

		SetHistoricElementCommand commandResult = null;

		if (element != null && element.getId() != null && element.getElementTypeId() != null
				&& !CollectionUtils.isEmpty(element.getElementValues()) && this.threadSetHistoricCommand != null) {
			
			commandResult = new SetHistoricElementCommand();
			commandResult.addElement(element);

			this.threadSetHistoricCommand.add(commandResult);
		}

		return commandResult;
	}

	public Command sendElementStateToIllumination(ElementState elementState) {

		SetElementStatesToIlluminationCommand commandResult = null;

		if (elementState != null && this.threadSetElementStatesToIlluminationCommand != null) {

			commandResult = new SetElementStatesToIlluminationCommand();
			commandResult.addElementState(elementState);

			this.threadSetElementStatesToIlluminationCommand.add(commandResult);
		}

		return commandResult;
	}

	public Command sendElementStateToVentilation(ElementState elementState) {

		SetElementStatesToVentilationCommand commandResult = null;

		if (elementState != null && this.threadSetElementStatesToVentilationCommand != null) {

			commandResult = new SetElementStatesToVentilationCommand();
			commandResult.addElementState(elementState);

			this.threadSetElementStatesToVentilationCommand.add(commandResult);
		}

		return commandResult;
	}

	public Command sendElementValuesToIllumination(List<ElementValue> elementValues) {

		SetElementValuesToIlluminationCommand commandResult = null;

		if (!CollectionUtils.isEmpty(elementValues) && this.threadSetElementValuesToIlluminationCommand != null) {
			commandResult = new SetElementValuesToIlluminationCommand();

			for (ElementValue elementValue : elementValues) {

				commandResult.addElementValue(elementValue);
			}

			this.threadSetElementValuesToIlluminationCommand.add(commandResult);
		}

		return commandResult;
	}

	public Command sendElementValuesToVentilation(List<ElementValue> elementValues) {

		SetElementValuesToVentilationCommand commandResult = null;

		if (!CollectionUtils.isEmpty(elementValues) && this.threadSetElementValuesToVentilationCommand != null) {

			commandResult = new SetElementValuesToVentilationCommand();

			for (ElementValue elementValue : elementValues) {

				commandResult.addElementValue(elementValue);
			}
			this.threadSetElementValuesToVentilationCommand.add(commandResult);
		}

		return commandResult;
	}

	public Command sendAlarmToIllumination(Alarm alarm) {

		SetAlarmsToIlluminationCommand commandResult = null;

		if (alarm != null && this.threadSetAlarmsToIlluminationCommand != null) {

			commandResult = new SetAlarmsToIlluminationCommand();
			commandResult.addAlarm(alarm);

			this.threadSetAlarmsToIlluminationCommand.add(commandResult);
		}

		return commandResult;
	}

	public Command sendAlarmToVentilation(Alarm alarm) {

		SetAlarmsToVentilationCommand commandResult = null;

		if (alarm != null && this.threadSetAlarmsToVentilationCommand != null) {

			commandResult = new SetAlarmsToVentilationCommand();
			commandResult.addAlarm(alarm);

			this.threadSetAlarmsToVentilationCommand.add(commandResult);
		}

		return commandResult;
	}

	public List<CommandElementPrecondition> validateCommandElement(CommandElement commandElement) {

		return ((CommandElementPreconditionRepository) repositoryData.getRepository(CommandElementPrecondition.class)).validate(commandElement);
	}

	public CommandElementType getCommandElementType(Long id, Long elementTypeId) {

		return ((CommandElementTypeRepository) repositoryData.getRepository(CommandElementType.class)).get(id, elementTypeId, false);
	}

	public PmElementTypeQueue getPmElementTypeQueue(Long elementTypeId, Long queueId) {

		return ((PmElementTypeQueueRepository) repositoryData.getRepository(PmElementTypeQueue.class)).get(elementTypeId, queueId, false);
	}

	public CommandElementTypeParam getCommandElementTypeParam(Long commandElementTypeId, Long elementTypeId,
			Long commandElementTypeParamId) {

		return ((CommandElementTypeParamRepository) repositoryData.getRepository(CommandElementTypeParam.class)).get(commandElementTypeId, elementTypeId,
				commandElementTypeParamId, false);
	}

	public PmDisabledQueuePriority createPmDisabledQueuePriority(PmDisabledQueuePriority pmDisabledQueuePriority) {

		return ((PmDisabledQueuePriorityRepository) repositoryData.getRepository(PmDisabledQueuePriority.class)).create(pmDisabledQueuePriority);
	}

	public PmDisabledQueuePriority deletePmDisabledQueuePriority(PmDisabledQueuePriority pmDisabledQueuePriority) {

		return ((PmDisabledQueuePriorityRepository) repositoryData.getRepository(PmDisabledQueuePriority.class)).delete(pmDisabledQueuePriority);
	}

	public PmDisabledQueuePriority getPmDisabledQueuePriority(PmCommandElement pmCommandElement) {

		return ((PmDisabledQueuePriorityRepository) repositoryData.getRepository(PmDisabledQueuePriority.class)).get(pmCommandElement.getQueueId(),
				pmCommandElement.getPriorityId(), pmCommandElement.getElementTypeId(), pmCommandElement.getElementId(),
				false);
	}

	public PmQueuePriority getPmQueuePriority(Long queueId, Long priorityId) {

		return ((PmQueuePriorityRepository) repositoryData.getRepository(PmQueuePriority.class)).get(queueId, priorityId, false);
	}

	public List<PmCommandElement> putPmCommandElement(List<PmCommandElement> entities) {

		dbSyncPmQueueFlag.compareAndSet(false, true);
		return ((PmCommandElementRepository) repositoryData.getRepository(PmCommandElement.class)).put(entities);
	}

	public List<PmCommandElementValue> putPmCommandElementValue(List<PmCommandElementValue> entities) {

		dbSyncPmQueueFlag.compareAndSet(false, true);
		return ((PmCommandElementValueRepository) repositoryData.getRepository(PmCommandElementValue.class)).put(entities);
	}

	public List<PmCommandElement> delPmCommandElement(List<PmCommandElement> entities) {

		dbSyncPmQueueFlag.compareAndSet(false, true);
		return ((PmCommandElementRepository) repositoryData.getRepository(PmCommandElement.class)).del(entities);
	}

	public List<PmCommandElementValue> delPmCommandElementValue(List<PmCommandElementValue> entities) {

		dbSyncPmQueueFlag.compareAndSet(false, true);
		return ((PmCommandElementValueRepository) repositoryData.getRepository(PmCommandElementValue.class)).del(entities);
	}

	public PmCommandElement putPmCommandElement(PmCommandElement entity) {

		dbSyncPmQueueFlag.compareAndSet(false, true);
		return ((PmCommandElementRepository) repositoryData.getRepository(PmCommandElement.class)).put(entity);
	}

	public PmCommandElementValue putPmCommandElementValue(PmCommandElementValue entity) {

		dbSyncPmQueueFlag.compareAndSet(false, true);
		return ((PmCommandElementValueRepository) repositoryData.getRepository(PmCommandElementValue.class)).put(entity);
	}

	public PmCommandElement delPmCommandElement(PmCommandElement entity) {

		dbSyncPmQueueFlag.compareAndSet(false, true);
		return ((PmCommandElementRepository) repositoryData.getRepository(PmCommandElement.class)).del(entity);
	}

	public PmCommandElementValue delPmCommandElementValue(PmCommandElementValue entity) {

		dbSyncPmQueueFlag.compareAndSet(false, true);
		return ((PmCommandElementValueRepository) repositoryData.getRepository(PmCommandElementValue.class)).del(entity);
	}

	public PmCommandElement enablePmCommandElement(PmCommandElement pmCommandElement) {

		dbSyncPmQueueFlag.compareAndSet(false, true);
		return ((PmCommandElementRepository) repositoryData.getRepository(PmCommandElement.class)).enable(pmCommandElement);
	}

	public PmCommandElement disablePmCommandElement(PmCommandElement pmCommandElement) {

		dbSyncPmQueueFlag.compareAndSet(false, true);
		return ((PmCommandElementRepository) repositoryData.getRepository(PmCommandElement.class)).disable(pmCommandElement);
	}

	public List<PmCommandElement> getsPmCommandElement(Long queueId, Long elementTypeId, Long elementId,
			Long priorityId) {

		return ((PmCommandElementRepository) repositoryData.getRepository(PmCommandElement.class)).gets(queueId, elementTypeId, elementId, priorityId, false);
	}

	public List<PmCommandElement> getsPmCommandElement(Long queueId, Long elementTypeId, Long elementId) {

		return ((PmCommandElementRepository) repositoryData.getRepository(PmCommandElement.class)).gets(queueId, elementTypeId, elementId, false);
	}
	
	public List<PmCommandElement> getsPmCommandElement() {

		return ((PmCommandElementRepository) repositoryData.getRepository(PmCommandElement.class)).getAll(false);
	}

	public List<PmCommandElement> getsPmCommandElementBySource(String source) {

		return ((PmCommandElementRepository) repositoryData.getRepository(PmCommandElement.class)).getBySource(source, false);
	}

	public List<PmCommandElementValue> getsPmCommandElementValues(final PmCommandElement pmCommandElement) {

		return ((PmCommandElementValueRepository) repositoryData.getRepository(PmCommandElementValue.class)).getsByPmCommandElement(pmCommandElement.getQueueId(),
				pmCommandElement.getCommandElementTypeId(), pmCommandElement.getId(),
				pmCommandElement.getElementTypeId(), pmCommandElement.getElementId(), false);
	}

	public List<PmElementTypeQueue> getPmElementTypeQueues() {

		return ((PmElementTypeQueueRepository) repositoryData.getRepository(PmElementTypeQueue.class)).getAll(false);
	}

	public void dbSyncPmCommand() {

		if (dbSyncPmQueueFlag.get()) {

			((PmCommandElementRepository) repositoryData.getRepository(PmCommandElement.class)).dbSync();
		}
		dbSyncPmQueueFlag.compareAndSet(true, false);
	}
	
	public EntityRepository getRepository(Class<?> clazz) {
		
		return repositoryData.getRepository(clazz);
	}
}
