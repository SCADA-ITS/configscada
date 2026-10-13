package com.revenga.rits.back.equipment.manager.data;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.AlarmConfigMeasure;
import com.revenga.rits.back.data.core.model.AlarmLevel;
import com.revenga.rits.back.data.core.model.AlarmType;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementGroup;
import com.revenga.rits.back.data.core.model.ElementStateAlarm;
import com.revenga.rits.back.data.core.model.ElementSubtype;
import com.revenga.rits.back.data.core.model.ElementSubtypeActiveParam;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementTypeParamUnit;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementTypeStateTransition;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RootDataLoader {

	private EntitiesManager em;
	private Map<Class<?>, List<BasicElement>> mapCollections;
	private Map<Long, Pair<ElementType, ElementData>> elementTypesMap;
	private ThreadDataWriter threadDataWriter;
	private ThreadGenericData threadGenericData;
	
	public RootDataLoader(EntitiesManager em, Map<Long, Pair<ElementType, ElementData>> elementTypesMap,
			ThreadDataWriter threadDataWriter, ThreadGenericData threadGenericData) {
		
		super();
		
		this.em = em;
		this.mapCollections = em.getRepositoryData().getMapCollections();
		this.elementTypesMap = elementTypesMap;
		this.threadDataWriter = threadDataWriter;
		this.threadGenericData = threadGenericData;
	}
	
	public void load() {

		Instant instantInit = Instant.now();

		List<BasicElement> bes = this.mapCollections.get(ElementType.class);

		if (!CollectionUtils.isEmpty(bes)) {

			for (BasicElement be : bes) {
				
				if (be instanceof ElementType) {

					ElementType elementType = (ElementType) be;
					ElementData elementData = new ElementData(this.em, this.threadDataWriter, this.threadGenericData);

					this.elementTypesMap.put(be.getId(), new MutablePair<>(elementType, elementData));

					fillElementTypeParams(elementType);
					fillElementSubtypes(elementType);
					assignElementGroup(elementType);
					fillAlarmConfigs(elementType);
					fillElementTypeStates(elementType);
					assignAlarmConfigsWithMeasures(elementType);
					
					elementData.load(elementType);
				}
			}
		}
		
		log.info("All items filled in "
				+ Duration.between(instantInit, Instant.now()).toMillis() + " ms.");
	}
	
	public void fill(BasicElement basicElement) {
		
		if (basicElement instanceof AlarmConfig) {
		
			AlarmConfig alarmConfig = (AlarmConfig) basicElement;
			
			Pair<ElementType, ElementData> elementTypeElementData = elementTypesMap.get(alarmConfig.getElementTypeId());
			
			if (elementTypeElementData != null) {
				
				fillElementTypeAlarmConfig(elementTypeElementData, basicElement);
			}
		}
		else if (basicElement instanceof AlarmConfigMeasure) {
			
			AlarmConfigMeasure alarmConfigMeasure = (AlarmConfigMeasure) basicElement;
			
			Pair<ElementType, ElementData> elementTypeElementData = elementTypesMap.get(alarmConfigMeasure.getElementTypeId());
			
			if (elementTypeElementData != null) {
				
				fillElementTypeAlarmConfig(elementTypeElementData, basicElement);
			}
		}
	}
	
	private void fillElementTypeAlarmConfig(Pair<ElementType, ElementData> elementTypeElementData, BasicElement basicElement) {
		
		if (elementTypeElementData != null) {
			
			ElementType elementType = elementTypeElementData.getLeft();
			ElementData elementData = elementTypeElementData.getRight();
			
			if (elementType != null) {
				
				this.em.getRootData().lock(elementType.getId());
				
				fillAlarmConfigs(elementType);
				fillElementTypeStates(elementType);
				assignAlarmConfigsWithMeasures(elementType);
				
				if (elementData != null) {
					
					elementData.fill(basicElement, elementType);
				}
				
				this.em.getRootData().unlock(elementType.getId());
			}
		}
	}

	private void fillElementTypeParams(ElementType elementType) {
	
		List<BasicElement> bes = this.mapCollections.get(ElementTypeParam.class);
		
		if (!CollectionUtils.isEmpty(bes)) {
			
			List<ElementTypeParam> elementTypeParams = bes.stream().map(ElementTypeParam.class::cast)
					.collect(Collectors.toList());

			elementTypeParams = elementTypeParams.stream().filter(e -> e.getElementTypeId().equals(elementType.getId()))
					.collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(elementTypeParams)) {
				
				elementType.setElementTypeParams(elementTypeParams);
				
				elementTypeParams.forEach(this::assignElementTypeParamUnit);
			}
		}
	}

	private void assignElementTypeParamUnit(ElementTypeParam elementTypeParam) {

		List<BasicElement> bes = this.mapCollections.get(ElementTypeParamUnit.class);

		if (!CollectionUtils.isEmpty(bes)) {

			List<ElementTypeParamUnit> elementTypeParamUnits = bes.stream().map(ElementTypeParamUnit.class::cast)
					.collect(Collectors.toList());

			ElementTypeParamUnit elementTypeParamUnit = elementTypeParamUnits.stream()
					.filter(e -> e.getId().equals(elementTypeParam.getElementTypeParamUnitId())).findFirst()
					.orElse(null);

			elementTypeParam.setElementTypeParamUnit(elementTypeParamUnit);
		}
	}
	
	private void fillElementSubtypes(ElementType elementType) {
		
		List<BasicElement> bes = this.mapCollections.get(ElementSubtype.class);
		
		if (!CollectionUtils.isEmpty(bes)) {
			
			List<ElementSubtype> elementSubtypes = bes.stream().map(ElementSubtype.class::cast)
					.collect(Collectors.toList());

			elementSubtypes = elementSubtypes.stream().filter(e -> e.getElementTypeId().equals(elementType.getId()))
					.collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(elementSubtypes)) {
				
				elementType.setElementSubtypes(elementSubtypes);
				
				elementSubtypes.forEach(elementSubtype -> fillElementSubtypeActiveParam(elementSubtype, elementType));
			}
		}
	}
	
	private void fillElementSubtypeActiveParam(ElementSubtype elementSubtype, final ElementType elementType) {
		
		List<BasicElement> bes = this.mapCollections.get(ElementSubtypeActiveParam.class);
		
		if (!CollectionUtils.isEmpty(bes)) {
			
			List<ElementSubtypeActiveParam> elementSubtypeActiveParams = bes.stream().map(ElementSubtypeActiveParam.class::cast)
					.collect(Collectors.toList());

			elementSubtypeActiveParams = elementSubtypeActiveParams.stream()
					.filter(e -> e.getElementTypeId().equals(elementSubtype.getElementTypeId()) &&
							e.getElementSubtypeId().equals(elementSubtype.getId()))
					.collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(elementSubtypeActiveParams)) {
				
				elementSubtype.setElementSubtypeActiveParams(elementSubtypeActiveParams);
				
				elementSubtypeActiveParams
						.forEach(elementSubtypeActiveParam -> assignElementSubtypeActiveParamElementTypeParam(
								elementSubtypeActiveParam, elementType));
			}
		}
	}
	
	private void assignElementSubtypeActiveParamElementTypeParam(ElementSubtypeActiveParam elementSubtypeActiveParam,
			final ElementType elementType) {
		
		List<ElementTypeParam> elementTypeParams = elementType.getElementTypeParams();
		
		if (!CollectionUtils.isEmpty(elementTypeParams)) {
			
			ElementTypeParam elementTypeParam = elementTypeParams.stream()
					.filter(e -> e.getElementTypeId().equals(elementSubtypeActiveParam.getElementTypeId())
							&& e.getParamTypeId().equals(elementSubtypeActiveParam.getParamTypeId())
							&& e.getId().equals(elementSubtypeActiveParam.getId()))
					.findFirst().orElse(null);
			
			elementSubtypeActiveParam.setElementTypeParam(elementTypeParam);
		}
	}
	
	private void assignElementGroup(ElementType elementType) {
		
		List<BasicElement> bes = this.mapCollections.get(ElementGroup.class);
		
		if (!CollectionUtils.isEmpty(bes)) {
			
			List<ElementGroup> elementGroups = bes.stream().map(ElementGroup.class::cast)
					.collect(Collectors.toList());

			ElementGroup elementGroup = elementGroups.stream()
					.filter(e -> e.getId().equals(elementType.getElementGroupId())).findFirst().orElse(null);
			
			elementType.setElementGroup(elementGroup);
		}
	}
	
	private void fillElementTypeStates(ElementType elementType) {
		
		List<BasicElement> bes = this.mapCollections.get(ElementTypeState.class);
		
		if (!CollectionUtils.isEmpty(bes)) {
			
			List<ElementTypeState> elementTypeStates = bes.stream().map(ElementTypeState.class::cast)
					.collect(Collectors.toList());

			List<ElementTypeState> elementTypeStatesFiltered = elementTypeStates.stream().filter(e -> e.getElementTypeId().equals(elementType.getId()))
					.collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(elementTypeStatesFiltered)) {
				
				elementType.setElementTypeStates(elementTypeStatesFiltered);
				
				elementTypeStatesFiltered.forEach(elementTypeState -> fillElementTypeStateTransitions(elementTypeState, elementTypeStatesFiltered));
				elementTypeStatesFiltered.forEach(elementTypeState -> fillElementTypeStateElementStateAlarms(elementTypeState, elementType));
			}
		}
	}
	
	private void fillElementTypeStateTransitions(ElementTypeState elementTypeState,
			List<ElementTypeState> elementTypeStates) {

		List<BasicElement> bes = this.mapCollections.get(ElementTypeStateTransition.class);

		if (!CollectionUtils.isEmpty(bes)) {

			List<ElementTypeStateTransition> elementTypeStateTransitions = bes.stream()
					.map(ElementTypeStateTransition.class::cast).collect(Collectors.toList());

			List<ElementTypeStateTransition> elementTypeStateTransitionsFiltered = elementTypeStateTransitions.stream()
					.filter(e -> e.getElementTypeId().equals(elementTypeState.getElementTypeId())
							&& e.getCurrentElementTypeStateId().equals(elementTypeState.getStateId()))
					.collect(Collectors.toList());

			if (!CollectionUtils.isEmpty(elementTypeStateTransitionsFiltered)) {

				List<ElementTypeState> nextElementTypeStates = elementTypeStates.stream()
						.filter(ets -> (elementTypeStateTransitionsFiltered.stream()
								.filter(etst -> etst.getNextElementTypeStateId().equals(ets.getStateId())).findFirst()
								.orElse(null)) != null

						).collect(Collectors.toList());

				elementTypeState.setTransitions(nextElementTypeStates);
			}
		}
	}
	
	private void fillElementTypeStateElementStateAlarms(ElementTypeState elementTypeState, ElementType elementType) {

		List<BasicElement> bes = this.mapCollections.get(ElementStateAlarm.class);

		if (!CollectionUtils.isEmpty(bes)) {

			List<ElementStateAlarm> elementStateAlarms = bes.stream()
					.map(ElementStateAlarm.class::cast).collect(Collectors.toList());

			elementStateAlarms = elementStateAlarms.stream()
					.filter(e -> e.getElementTypeId().equals(elementTypeState.getElementTypeId())
							&& e.getStateId().equals(elementTypeState.getStateId()))						
							.collect(Collectors.toList());

			if (!CollectionUtils.isEmpty(elementStateAlarms)) {

				elementTypeState.setElementStateAlarms(elementStateAlarms);
				
				elementStateAlarms.forEach(elementStateAlarm -> assignElementStateAlarmAlarmConfig(elementStateAlarm, elementType));
			}
		}
	}
	
	private void assignElementStateAlarmAlarmConfig(ElementStateAlarm elementStateAlarm, ElementType elementType) {
		
		if (!CollectionUtils.isEmpty(elementType.getAlarmConfigs())) {
			
			AlarmConfig alarmConfig = elementType.getAlarmConfigs().stream()
					.filter(e -> e.getId().equals(elementStateAlarm.getAlarmConfigId())).findFirst().orElse(null);
			
			elementStateAlarm.setAlarmConfig(alarmConfig);
		}
	}
	
	private void fillAlarmConfigs(ElementType elementType) {
		
		List<BasicElement> bes = this.mapCollections.get(AlarmConfig.class);
		
		if (!CollectionUtils.isEmpty(bes)) {
			
			List<AlarmConfig> alarmConfigs = bes.stream().map(AlarmConfig.class::cast)
					.collect(Collectors.toList());

			alarmConfigs = alarmConfigs.stream().filter(e -> e.getElementTypeId().equals(elementType.getId()))
					.collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(alarmConfigs)) {
				
				elementType.setAlarmConfigs(alarmConfigs);
				
				alarmConfigs.forEach(this::assignAlarmTypeAlarmConfig);
				alarmConfigs.forEach(this::assignAlarmLevelAlarmConfig);
				alarmConfigs.forEach(alarmConfig -> alarmConfig.setElementType(elementType));
				alarmConfigs.forEach(alarmConfig -> fillAlarmConfigAlarmMeasures(alarmConfig, elementType));
			}
		}
	}
	
	private void assignAlarmConfigsWithMeasures(ElementType elementType) {
		
		if (!CollectionUtils.isEmpty(elementType.getAlarmConfigs())) {
			
			List<AlarmConfig> alarmConfigsWithMeasures = elementType.getAlarmConfigs().stream()
					.filter(e -> !CollectionUtils.isEmpty(e.getAlarmConfigMeasures())).collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(alarmConfigsWithMeasures)) {
				
				elementType.setAlarmConfigsWithMeasures(alarmConfigsWithMeasures);			
			}
		}
	}
	
	private void assignAlarmTypeAlarmConfig(AlarmConfig alarmConfig) {
		
		List<BasicElement> bes = this.mapCollections.get(AlarmType.class);
		
		if (!CollectionUtils.isEmpty(bes)) {
			
			List<AlarmType> alarmTypes = bes.stream().map(AlarmType.class::cast)
					.collect(Collectors.toList());

			AlarmType alarmType = alarmTypes.stream()
					.filter(e -> e.getId().equals(alarmConfig.getAlarmTypeId())).findFirst().orElse(null);
			
			alarmConfig.setAlarmType(alarmType);
		}
	}
	
	private void assignAlarmLevelAlarmConfig(AlarmConfig alarmConfig) {
		
		List<BasicElement> bes = this.mapCollections.get(AlarmLevel.class);
		
		if (!CollectionUtils.isEmpty(bes)) {
			
			List<AlarmLevel> alarmLevels = bes.stream().map(AlarmLevel.class::cast)
					.collect(Collectors.toList());

			AlarmLevel alarmLevel = alarmLevels.stream()
					.filter(e -> e.getId().equals(alarmConfig.getAlarmLevelId())).findFirst().orElse(null);
			
			alarmConfig.setAlarmLevel(alarmLevel);
		}
	}
	
	private void fillAlarmConfigAlarmMeasures(AlarmConfig alarmConfig, ElementType elementType) {
		
		List<BasicElement> bes = this.mapCollections.get(AlarmConfigMeasure.class);
		
		if (!CollectionUtils.isEmpty(bes)) {
			
			List<AlarmConfigMeasure> alarmConfigMeasures = bes.stream().map(AlarmConfigMeasure.class::cast)
					.collect(Collectors.toList());

			alarmConfigMeasures = alarmConfigMeasures.stream().filter(e -> e.getAlarmConfigId().equals(alarmConfig.getId()))
					.collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(alarmConfigMeasures)) {
				
				alarmConfig.setAlarmConfigMeasures(alarmConfigMeasures);
				
				alarmConfigMeasures.forEach(
						alarmConfigMeasure -> fillAlarmMeasure(alarmConfigMeasure, alarmConfig, elementType));
			}
		}
	}
	
	private void fillAlarmMeasure(AlarmConfigMeasure alarmConfigMeasure, AlarmConfig alarmConfig, ElementType elementType) {
	
		List<ElementTypeParam> elementTypeParams = elementType.getElementTypeParams();
		
		if (!CollectionUtils.isEmpty(elementTypeParams)) {
			
			ElementTypeParam elementTypeParam = elementTypeParams.stream()
					.filter(e -> e.getElementTypeId()
							.equals(alarmConfigMeasure.getElementTypeId())
									&& e.getParamTypeId().equals(alarmConfigMeasure.getParamTypeId())
									&& e.getId().equals(alarmConfigMeasure.getElementTypeParamId()))
					.findFirst().orElse(null);						
			
			alarmConfigMeasure.setElementTypeParam(elementTypeParam);		
		}
		
		alarmConfigMeasure.setAlarmConfig(alarmConfig);
	}
}
