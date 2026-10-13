package com.revenga.rits.back.io.controller.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;

import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.IOControllerModule;
import com.revenga.rits.back.data.core.model.IOControllerModuleValue;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.VmsGraphicGraphicGroupValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.repository.AlarmConfigRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementTypeRepository;
import com.revenga.rits.back.entities.lib.repository.ElementHierarchiesRepository;
import com.revenga.rits.back.entities.lib.repository.ElementRepository;
import com.revenga.rits.back.entities.lib.repository.ElementStateRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeRepository;
import com.revenga.rits.back.entities.lib.repository.ElementValueRepository;
import com.revenga.rits.back.entities.lib.repository.IOControllerModuleRepository;
import com.revenga.rits.back.entities.lib.repository.IOControllerModuleValueRepository;
import com.revenga.rits.back.entities.lib.repository.VmsGraphicGraphicGroupValueRepository;
import com.revenga.rits.back.io.controller.module.GenericModule;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class EntitiesManager {

	private static final String FILE_APP_PROPS = "application.properties";
	
	private static final Long CACHE_RETENTION_TIME_CONF = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "entities.manager.retention_time_conf"));

	private static final Long CACHE_RETENTION_TIME_MASTER = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "entities.manager.retention_time_master"));

	
	ElementRepository elementRepository;
	ElementTypeRepository elementTypeRepository;
	ElementTypeParamRepository elementTypeParamRepository;
	ElementValueRepository elementValueRepository;
	ElementValueRepository elementValueRepositoryConfig;
	ElementStateRepository elementStateRepository;
	IOControllerModuleRepository ioControllerModuleRepository;
	IOControllerModuleValueRepository ioControllerModuleValueRepository;
	AlarmConfigRepository alarmConfigRepository;
	VmsGraphicGraphicGroupValueRepository vmsGraphicGraphicGroupValueRepository;
	ElementHierarchiesRepository elementHierarchiesRepository;
	CommandElementTypeRepository commandElementTypeRepository;

	List<ElementValue> elementValuesCached;
	Map<Pair<Long, Long>, IOControllerModule> ioControllerModules = new HashMap<>();
	
	Long lastUpdatedDatapoints = 0L;

	private static EntitiesManager instance;

	public static EntitiesManager getInstance() {

		if (instance == null) {

			instance = new EntitiesManager();
		}

		return instance;
	}

	private EntitiesManager() {
		this.elementRepository = new ElementRepository(true, CACHE_RETENTION_TIME_CONF);
		this.elementTypeRepository = new ElementTypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.elementTypeParamRepository = new ElementTypeParamRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.elementValueRepository = new ElementValueRepository(false);
		this.elementValueRepositoryConfig = new ElementValueRepository(true, CACHE_RETENTION_TIME_CONF);
		this.elementStateRepository = new ElementStateRepository(false);
		this.ioControllerModuleRepository = new IOControllerModuleRepository(true, CACHE_RETENTION_TIME_CONF);
		this.ioControllerModuleValueRepository = new IOControllerModuleValueRepository(true, CACHE_RETENTION_TIME_CONF);
		this.alarmConfigRepository = new AlarmConfigRepository(true, CACHE_RETENTION_TIME_CONF);
		this.elementHierarchiesRepository = new ElementHierarchiesRepository(true, CACHE_RETENTION_TIME_CONF);
		this.commandElementTypeRepository = new CommandElementTypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.vmsGraphicGraphicGroupValueRepository = new VmsGraphicGraphicGroupValueRepository(true, CACHE_RETENTION_TIME_CONF);
	}

	public List<Element> getElements(Long elementTypeId) {

		return elementRepository.gets(elementTypeId, false);
	}

	public Element getElement(Long elementTypeId, Long elementId) {

		return elementRepository.get(elementTypeId, elementId, false);
	}

	public List<ElementTypeParam> getElementTypeParams(Long elementTypeId) {

		return elementTypeParamRepository.gets(elementTypeId, false);
	}

	public List<ElementType> getElementTypes() {

		return elementTypeRepository.getAll(false);
	}

	public ElementType getElementType(Long elementTypeId) {

		return elementTypeRepository.get(elementTypeId, false);
	}

	public ElementTypeParam getElementTypeParam(Long elementTypeId, Long paramTypeId, Long id) {

		return elementTypeParamRepository.get(elementTypeId, paramTypeId, id, false);
	}

	public ElementTypeParam getElementTypeParam(ElementValue elementValue) {

		return elementTypeParamRepository.get(elementValue.getElementTypeId(), elementValue.getParamTypeId(),
				elementValue.getElementTypeParamId(), false);
	}

	public CommandElementType getCommandElementType(Long id, Long elementTypeId, Boolean fillObject) {

		return commandElementTypeRepository.get(id, elementTypeId, fillObject);
	}

	public String getDataPoint(Element element, Long elementTypeParamId, Long paramTypeId) {

		if (Duration.between(Instant.ofEpochMilli(lastUpdatedDatapoints), Instant.now()).toMillis() > 60000L) {

			elementValuesCached = elementValueRepository.getAll(false);

			lastUpdatedDatapoints = Instant.now().toEpochMilli();
		}

		if (!CollectionUtils.isEmpty(elementValuesCached)) {

			ElementValue datapointElementValue = elementValuesCached.stream()
					.filter(e -> e.getElementTypeId().equals(element.getElementTypeId())
							&& e.getElementId().equals(element.getId())
							&& e.getElementTypeParamId().equals(elementTypeParamId)
							&& e.getParamTypeId().equals(paramTypeId))
					.findFirst().orElse(null);

			if (datapointElementValue != null) {

				return datapointElementValue.getValue();
			}
		}

		return null;
	}

	public ElementValue getElementValue(Element element, Long elementTypeParamId, Long paramTypeId) {

		return elementValueRepository.get(element.getElementTypeId(), element.getId(), elementTypeParamId, paramTypeId,
				false);
	}

	public ElementValue getElementValueConfig(Element element, Long elementTypeParamId) {

		return elementValueRepositoryConfig.get(element.getElementTypeId(), element.getId(), elementTypeParamId,
				ParamType.CONFIG, false);
	}

	public List<ElementValue> getElementValue(Long elementTypeId, Long elementTypeParamId, Long paramTypeId) {

		return elementValueRepository.getByElementTypeParam(elementTypeId, elementTypeParamId, paramTypeId, false);
	}

	public boolean putElementValues(List<ElementValue> elementValues) {

		return (elementValueRepository.put(elementValues) != null);
	}

//	public List<ElementState> getElementStates() {
//
//		return elementStateRepository.getAll(false);
//	}
//
//	public ElementState getElementState(Long elementTypeId, Long elementId) {
//
//		return elementStateRepository.get(elementTypeId, elementId, false);
//	}

	public IOControllerModule getIOControllerModule(Long moduleTypeId, Long id) {
		
		IOControllerModule ioControllerModule = null;
		Pair<Long, Long> pairIOControllerModule = new ImmutablePair<>(moduleTypeId, id);
		
		ioControllerModule = ioControllerModules.get(pairIOControllerModule); 
		
		if (ioControllerModule == null) {
			
			ioControllerModule = ioControllerModuleRepository.get(moduleTypeId, id, true);
			
			if (ioControllerModule != null) {
				
				ioControllerModules.put(pairIOControllerModule, ioControllerModule);
			}
		}

		return ioControllerModule;
	}

	public IOControllerModuleValue getIOControllerModuleValue(IOControllerModule ioControllerModule,
			Long moduleTypeParamId) {

		return ioControllerModuleValueRepository.get(ioControllerModule.getModuleTypeId(), ioControllerModule.getId(),
				moduleTypeParamId, false);
	}

	public AlarmConfig getAlarmConfig(Long alarmConfigId) {

		return alarmConfigRepository.get(alarmConfigId, false);
	}

	public Command sendCommand(Command command) {

		return CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME);
	}

	public boolean receivedMeasures(Element element, ElementState currentElementState,
			List<ElementValue> elementValues) {
		boolean resultado = false;
		GenericModule genericModule = ControllerManager.getInstance().getControllerService().getRouteService()
				.getModule(element);

		if (!CollectionUtils.isEmpty(elementValues)) {

			if (!currentElementState.getStateId().equals(element.getElementType().getDisconnectedElementTypeStateId())
					&& !currentElementState.getStateId()
							.equals(element.getElementType().getInitialElementTypeStateId())) {

				genericModule.receiveMeasures(element, elementValues);
				resultado = true;
			} else {
				log.debug((new Key(element)).getSimpleValue() + " => Discarting upload measures while current state = "
						+ currentElementState.getStateId());
			}
		}
		return resultado;
	}

	public List<ElementHierarchy> getsByParent(Long parentElementTypeId, Long parentElementId) {

		return elementHierarchiesRepository.getsByParent(parentElementTypeId, parentElementId, false);
	}

	public List<ElementHierarchy> getsByChild(Long childElementTypeId, Long childElementId) {

		return elementHierarchiesRepository.getsByChild(childElementTypeId, childElementId, false);
	}

	public List<VmsGraphicGraphicGroupValue> getByGroup(Long graphicGroupId) {

		return vmsGraphicGraphicGroupValueRepository.getByGroup(graphicGroupId, false);
	}
}
