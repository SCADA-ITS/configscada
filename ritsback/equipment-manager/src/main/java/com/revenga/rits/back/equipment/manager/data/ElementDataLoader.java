package com.revenga.rits.back.equipment.manager.data;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.AlarmConfigMeasure;
import com.revenga.rits.back.data.core.model.AlarmFiltered;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementSubtype;
import com.revenga.rits.back.data.core.model.ElementSubtypeActiveParam;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.ManagementAreaElement;
import com.revenga.rits.back.entities.lib.repository.ElementStateRepository;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

public class ElementDataLoader {

	private EntitiesManager em;
	private Map<Class<?>, List<BasicElement>> mapCollections;
	private Map<Long, Pair<Element, Semaphore>> elementsMap;
	private ElementType elementType;
	
	public ElementDataLoader(EntitiesManager em, Map<Long, Pair<Element, Semaphore>> elementsMap, ElementType elementType) {
		
		super();
		
		this.em = em;
		this.mapCollections = em.getRepositoryData().getMapCollections();
		this.elementsMap = elementsMap;
		this.elementType = elementType;
	}

	public void load() {
		
		List<BasicElement> bes = this.mapCollections.get(Element.class);

		if (!CollectionUtils.isEmpty(bes)) {
			
			List<Element> elements = bes.stream().map(Element.class::cast)
					.collect(Collectors.toList());

			elements = elements.stream().filter(e -> e.getElementTypeId().equals(elementType.getId()))
					.collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(elements)) {
				
				for (Element element : elements) {
					
					load(element);
				}
			}
		}
	}
	
	public void load(Element element) {
		
		if (element != null) {
			
			this.elementsMap.put(element.getId(), new MutablePair<>(element, new Semaphore(1)));
			
			element.setElementType(this.elementType);
			assignElementSubtype(element);
			assignLocation(element);
			fillElementValues(element);
			assignElementState(element);
			fillAlarms(element);
			fillAlarmsFiltered(element);
			fillManagementAreaElement(element);
		}
	}
	
	public void delete(Element element) {
		
		if (element != null) {
		
			this.elementsMap.remove(element.getId());
		}
	}
	
	public void fill(BasicElement basicElement) {
		
		List<BasicElement> bes = this.mapCollections.get(Element.class);

		if (!CollectionUtils.isEmpty(bes)) {
			
			List<Element> elements = bes.stream().map(Element.class::cast)
					.collect(Collectors.toList());

			elements = elements.stream().filter(e -> e.getElementTypeId().equals(elementType.getId()))
					.collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(elements)) {
				
				for (Element element : elements) {
					
					if (basicElement instanceof AlarmConfig ||
						basicElement instanceof AlarmConfigMeasure) {
						
						element.getElementValues().forEach(this::assignElementValueAlarmConfigMeasure);
					}
				}
			}
		}
	}
	
	private void assignElementSubtype(Element element) {
		
		if (element.getElementSubtypeId() != null) {

			List<ElementSubtype> elementSubtypes = element.getElementType().getElementSubtypes();
			
			if (!CollectionUtils.isEmpty(elementSubtypes)) {
				
				ElementSubtype elementSubtype = elementSubtypes.stream().filter(e -> e.getId().equals(element.getElementSubtypeId())).findFirst().orElse(null);
				
				element.setElementSubtype(elementSubtype);
			}
		}
	}
	
	private void assignLocation(Element element) {
		
		if (element.getLocationId() != null) {
			
			List<BasicElement> bes = this.mapCollections.get(Location.class);

			if (!CollectionUtils.isEmpty(bes)) {
				
				List<Location> locations = bes.stream().map(Location.class::cast)
						.collect(Collectors.toList());

				Location location = locations.stream().filter(e -> e.getId().equals(element.getLocationId()))
						.findFirst().orElse(null);
				
				element.setLocation(location);
			}
		}
	}
	
	private void fillElementValues(Element element) {
		
		List<BasicElement> bes = this.mapCollections.get(ElementValue.class);
		
		if (!CollectionUtils.isEmpty(bes)) {
			
			List<ElementValue> elementValues = bes.stream().map(ElementValue.class::cast)
					.collect(Collectors.toList());
			
			elementValues = elementValues.stream()
					.filter(e -> e.getElementTypeId().equals(this.elementType.getId())
							&& e.getElementId().equals(element.getId()))
					.collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(elementValues)) {
				
				ElementSubtype elementSubtype = element.getElementSubtype(); 
				
				if (elementSubtype == null ) {
					
					element.setElementValues(elementValues);				
				}
				else {
					
					fillElementValuesForSubtype(element, elementSubtype, elementValues);
				}
				
				elementValues.forEach(elementValue -> elementValue.setElement(element));
				elementValues.forEach(this::assignElementValueElementTypeParam);
				elementValues.forEach(this::assignElementValueAlarmConfigMeasure);
			}
		}
	}
	
	private void fillElementValuesForSubtype(Element element, ElementSubtype elementSubtype, List<ElementValue> elementValues) {
		
		List<ElementSubtypeActiveParam> elementSubtypeActiveParams = elementSubtype.getElementSubtypeActiveParams();
		
		if (!CollectionUtils.isEmpty(elementValues) && !CollectionUtils.isEmpty(elementSubtypeActiveParams)) {
			elementValues = elementValues.stream()
					.filter(ev -> (elementSubtypeActiveParams.stream()
							.filter(esap -> esap.getParamTypeId().equals(ev.getParamTypeId())
									&& esap.getId().equals(ev.getElementTypeParamId()))
							.findFirst().orElse(null)) != null)
					.collect(Collectors.toList());
			
			element.setElementValues(elementValues); 
		}
	}
	
	private void assignElementValueElementTypeParam(ElementValue elementValue) {
		
		List<ElementTypeParam> elementTypeParams = this.elementType.getElementTypeParams();
		
		if (!CollectionUtils.isEmpty(elementTypeParams)) {
			
			ElementTypeParam elementTypeParam = elementTypeParams.stream().filter(e -> e.getParamTypeId().equals(elementValue.getParamTypeId()) && e.getId().equals(elementValue.getElementTypeParamId())).findFirst().orElse(null);
			
			elementValue.setElementTypeParam(elementTypeParam);		
		}
	}
	
	private void assignElementValueAlarmConfigMeasure(ElementValue elementValue) {
		
		List<AlarmConfig> alarmConfigsWithMeasures = this.elementType.getAlarmConfigsWithMeasures();
		
		if (!CollectionUtils.isEmpty(alarmConfigsWithMeasures)) {			
			
			Element element = elementValue.getElement();
			
			if (element != null) {
				
				List<AlarmConfig> alarmConfigMeasureConfig = null;
				
				if (element.getElementSubtype() == null) {
					
					alarmConfigMeasureConfig = alarmConfigsWithMeasures.stream()
							.filter(ac -> ac.getElementSubtypeId() == null && (ac.getAlarmConfigMeasures().stream()
									.filter(acm -> acm.getParamTypeId().equals(elementValue.getParamTypeId())
											&& acm.getElementTypeParamId().equals(elementValue.getElementTypeParamId()))
									.findFirst().orElse(null)) != null)
							.collect(Collectors.toList());
				}
				else {
					
					alarmConfigMeasureConfig = alarmConfigsWithMeasures.stream()
							.filter(ac -> (ac.getElementSubtypeId() == null || ac.getElementSubtypeId().equals(element.getElementSubtypeId()))
									&& (ac.getAlarmConfigMeasures().stream()
									.filter(acm -> acm.getParamTypeId().equals(elementValue.getParamTypeId())
											&& acm.getElementTypeParamId().equals(elementValue.getElementTypeParamId()))
									.findFirst().orElse(null)) != null)
							.collect(Collectors.toList());
				}
				
				elementValue.setAlarmConfigMeasureConfig(alarmConfigMeasureConfig);
			}
		}
	}
	
	private void assignElementState(Element element) {
		
		List<BasicElement> bes = this.mapCollections.get(ElementState.class);

		if (!CollectionUtils.isEmpty(bes)) {
			
			List<ElementState> elementStates = bes.stream().map(ElementState.class::cast)
					.collect(Collectors.toList());

			ElementState elementState = elementStates.stream()
					.filter(e -> e.getElementTypeId().equals(element.getElementTypeId())
							&& e.getElementId().equals(element.getId()))
					.findFirst().orElse(null);
			
			if (elementState == null) {
				
				List<ElementState> elementStatesCreated = this.em.getInitializedElementStates(Arrays.asList(element));
				ElementStateRepository elementStateRepository = (ElementStateRepository) this.em.getRepositoryData()
						.getRepository(ElementState.class);
				
				if (elementStateRepository != null && !CollectionUtils.isEmpty(elementStatesCreated)) {
					
					elementStateRepository.put(elementStatesCreated);
					
					elementState = elementStatesCreated.get(0);
				}
			}
			
			element.setElementState(elementState);
			
			if (elementState != null) {
				
				assignElementTypeStateElementState(elementState);
				
				elementState.setElement(element);			
			}
		}
	}

	private void assignElementTypeStateElementState(ElementState elementState) {
		
		if (elementState != null) {
		
			List<ElementTypeState> elementTypeStates = this.elementType.getElementTypeStates();
			
			if (!CollectionUtils.isEmpty(elementTypeStates)) {
			
				if (elementState.getPreviousStateId() != null) {
					
					elementState.setPreviousState(elementTypeStates.stream()
							.filter(e -> e.getStateId().equals(elementState.getPreviousStateId())).findFirst().orElse(null));
				}
				
				if (elementState.getStateId() != null) {
					
					elementState.setState(elementTypeStates.stream()
							.filter(e -> e.getStateId().equals(elementState.getStateId())).findFirst().orElse(null));
				}
			}
		}
	}
	
	private void fillAlarms(Element element) {
		
		List<BasicElement> bes = this.mapCollections.get(Alarm.class);
		
		if (!CollectionUtils.isEmpty(bes)) {
			
			List<Alarm> alarms = bes.stream().map(Alarm.class::cast)
					.collect(Collectors.toList());
			
			alarms = alarms.stream().filter(e -> e.getTargetElementTypeId().equals(element.getElementTypeId())
					&& e.getTargetElementId().equals(element.getId())).collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(alarms)) {
				
				element.setAlarms(alarms);
				
				alarms.forEach(this::assignAlarmConfigAlarm);
				alarms.forEach(alarm -> alarm.setSourceElement(element));
				alarms.forEach(alarm -> alarm.setTargetElement(element));
			}
		}
	}
	
	private void assignAlarmConfigAlarm(Alarm alarm) {
		
		List<AlarmConfig> alarmConfigs = this.elementType.getAlarmConfigs();
		
		if (!CollectionUtils.isEmpty(alarmConfigs)) {
			
			AlarmConfig alarmConfig = alarmConfigs.stream().filter(e -> e.getId().equals(alarm.getAlarmConfigId())).findFirst().orElse(null);
			
			alarm.setAlarmConfig(alarmConfig);		
		}
	}
	
	private void fillAlarmsFiltered(Element element) {
		
		List<BasicElement> bes = this.mapCollections.get(AlarmFiltered.class);
		
		if (!CollectionUtils.isEmpty(bes)) {
			
			List<AlarmFiltered> alarmsFiltered = bes.stream().map(AlarmFiltered.class::cast)
					.collect(Collectors.toList());
			
			alarmsFiltered = alarmsFiltered.stream().filter(e -> e.getElementTypeId().equals(element.getElementTypeId())
					&& e.getElementId().equals(element.getId())).collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(alarmsFiltered)) {
				
				element.setAlarmsFiltered(alarmsFiltered);
				element.setFilteredAlarms(alarmsFiltered.stream().filter(
						e -> e.getFilteredUntil() != null && e.getFilteredUntil() > Instant.now().toEpochMilli())
						.count());
				
				alarmsFiltered.forEach(alarm -> alarm.setElement(element));
				alarmsFiltered.forEach(this::assignAlarmConfigAlarmFiltered);
			}
		}
	}
	
	private void fillManagementAreaElement(Element element) {
		
		List<BasicElement> bes = this.mapCollections.get(ManagementAreaElement.class);
		
		if (!CollectionUtils.isEmpty(bes)) {
			
			List<ManagementAreaElement> managementAreaElements = bes.stream().map(ManagementAreaElement.class::cast)
					.collect(Collectors.toList());
			
			managementAreaElements = managementAreaElements.stream().filter(e -> e.getElementTypeId().equals(element.getElementTypeId())
					&& e.getElementId().equals(element.getId())).collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(managementAreaElements)) {
				
				element.setManagementAreaElement(managementAreaElements);
			}
		}
	}
	
	private void assignAlarmConfigAlarmFiltered(AlarmFiltered alarmFiltered) {
		
		List<AlarmConfig> alarmConfigs = this.elementType.getAlarmConfigs();
		
		if (!CollectionUtils.isEmpty(alarmConfigs)) {
			
			AlarmConfig alarmConfig = alarmConfigs.stream().filter(e -> e.getId().equals(alarmFiltered.getAlarmConfigId())).findFirst().orElse(null);
			
			alarmFiltered.setAlarmConfig(alarmConfig);		
		}
	}
}

