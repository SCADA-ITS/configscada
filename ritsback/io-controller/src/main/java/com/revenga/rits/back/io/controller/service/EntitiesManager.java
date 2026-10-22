package com.revenga.rits.back.io.controller.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

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
import com.revenga.rits.back.entities.lib.annotation.EntityRepositoryParam;
import com.revenga.rits.back.entities.lib.helper.EntitiesManagerHelper;
import com.revenga.rits.back.entities.lib.repository.AlarmConfigRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementTypeRepository;
import com.revenga.rits.back.entities.lib.repository.ElementHierarchiesRepository;
import com.revenga.rits.back.entities.lib.repository.ElementIOControllerModuleRepository;
import com.revenga.rits.back.entities.lib.repository.ElementRepository;
import com.revenga.rits.back.entities.lib.repository.ElementStateRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeRepository;
import com.revenga.rits.back.entities.lib.repository.ElementValueRepository;
import com.revenga.rits.back.entities.lib.repository.IOControllerModuleRepository;
import com.revenga.rits.back.entities.lib.repository.IOControllerModuleValueRepository;
import com.revenga.rits.back.entities.lib.repository.VmsGraphicGraphicGroupValueRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.io.controller.data.maintenance.ElementOnChange;
import com.revenga.rits.back.io.controller.module.GenericModule;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EntitiesManager {
	
	@EntityRepositoryParam(subscribeChanges = true, entityOnChange = ElementOnChange.class)
	private ElementRepository elementRepository;
	private ElementTypeRepository elementTypeRepository;
	private ElementTypeParamRepository elementTypeParamRepository;
	@EntityRepositoryParam(buffered = false)
	private ElementValueRepository elementValueRepository;
	private ElementValueRepository elementValueRepositoryConfig;
	@EntityRepositoryParam(buffered = false)
	private ElementStateRepository elementStateRepository;
	private IOControllerModuleRepository ioControllerModuleRepository;
	private IOControllerModuleValueRepository ioControllerModuleValueRepository;
	private AlarmConfigRepository alarmConfigRepository;
	private VmsGraphicGraphicGroupValueRepository vmsGraphicGraphicGroupValueRepository;
	private ElementHierarchiesRepository elementHierarchiesRepository;
	private CommandElementTypeRepository commandElementTypeRepository;
	@EntityRepositoryParam(subscribeChanges = true)
	private  ElementIOControllerModuleRepository elementIOControllerModuleRepository;

	private List<ElementValue> elementValuesCached;
	private Map<Pair<Long, Long>, IOControllerModule> ioControllerModules = new HashMap<>();
	
	@Getter
	private Map<Class<?>, EntityRepository> repositories = new HashMap<>();
	private List<EntityRepository> repositoryList = new ArrayList<>();
	
	private Long lastUpdatedDatapoints = 0L;

	private static EntitiesManager instance;

	public static EntitiesManager getInstance() {

		if (instance == null) {

			instance = new EntitiesManager();
		}

		return instance;
	}

	private EntitiesManager() {
	
		EntitiesManagerHelper.initialize(this, repositoryList, repositories);
		
		EntitiesManagerHelper.startSubscriptions(repositoryList);
	}

	public List<Element> getElements(Long elementTypeId) {

		return elementRepository.gets(elementTypeId, false);
	}

	public Element getElement(Long elementTypeId, Long elementId) {

		return elementRepository.get(elementTypeId, elementId, false);
	}
	
	public Element getElementFilled(Long elementTypeId, Long elementId) {

		return elementRepository.get(elementTypeId, elementId, true);
	}
	
	public Element putElement(Element element) {

		return elementRepository.put(element);
	}
	
	public Element delElement(Element element) {

		return elementRepository.del(element);
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

		String dataPoint = null;
		
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

				dataPoint = datapointElementValue.getValue();
			}
			else {
				
				ElementValue dataPointElementValue = getElementValue(element, elementTypeParamId, paramTypeId);
				
				if (dataPointElementValue != null && !StringUtils.isEmpty(dataPointElementValue.getValue())) {
				
					dataPoint = dataPointElementValue.getValue();
				}
			}
		}

		return dataPoint;
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
