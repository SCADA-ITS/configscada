package com.revenga.rits.back.equipment.manager.data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.AlarmFiltered;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetElementValuesPublishCommand;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementData extends EntityData<Element, ElementType> {

	private Map<Long, Pair<Element, Semaphore>> elementsMap = new HashMap<>();
	private ThreadDataWriter threadDataWriter;
	private ThreadGenericData threadGenericData;

	public ElementData(EntitiesManager em, ThreadDataWriter threadDataWriter, ThreadGenericData threadGenericData) {
		
		super(em);

		this.threadDataWriter = threadDataWriter;
		this.threadGenericData = threadGenericData;
	}
	
	public List<Element> getAll() {
		
		return this.elementsMap.values().stream().map(Pair::getLeft).collect(Collectors.toList());
	}
	
	public int size() {
		
		return this.elementsMap.size();
	}
	
	public Element get(Long elementId) {
		
		return this.elementsMap.get(elementId).getLeft();
	}

	@Override
	public void load() {
			
		throw new NotImplementedException("Without implementation");
	}
	
	@Override
	public void load(final ElementType elementType) {
		
		(new ElementDataLoader(this.mapCollections, this.elementsMap, elementType)).load();
		
		List<Element> elements = getAll();
		
		if (!CollectionUtils.isEmpty(elements)) {
			
			elements.forEach(this::updateAlarmCounters);
		}
	}
	
	public void fill(BasicElement basicElement, ElementType elementType) {
		
		(new ElementDataLoader(this.mapCollections, this.elementsMap, elementType)).fill(basicElement);
	}
	
	public ElementValue updateElementValue(@NonNull ElementTypeParam elementTypeParam, @NonNull Element element, String value) {
		
		return updateElementValue(elementTypeParam, element, value, null);
	}
	
	public ElementValue updateElementValue(@NonNull ElementTypeParam elementTypeParam, @NonNull Element element, String value, Long elementValueStateId) {
		
		ElementValue elementValue = null;
		ElementValue oldElementValue = null;
		
		try {
		
			elementValue = element.getElementValues().stream()
					.filter(e -> e.getElementTypeParamId().equals(elementTypeParam.getId())
							&& e.getParamTypeId().equals(elementTypeParam.getParamTypeId()))
					.findFirst().orElse(null);
			
			if (elementValue != null) {
				
				oldElementValue = (ElementValue) elementValue.clone();
				
				elementValue.setValue(value);
				
				if (elementValueStateId != null) {
					
					elementValue.setElementValueStateId(elementValueStateId);
				}
				
				put(elementValue);
				publishElementValues(Arrays.asList(elementValue));
				this.em.sendOnChangeMeasures(element, Arrays.asList(new ImmutablePair<>(oldElementValue, elementValue)));
			}
		
		} catch (CloneNotSupportedException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return elementValue;
	}
	
	public List<ElementValue> updateElementValues(@NonNull final List<ElementValue> newElementValues, @NonNull Element element) {
		
		List<Pair<ElementValue, ElementValue>> pairElementValues = new ArrayList<>();
		List<ElementValue> updatedElementValues = null;
			
		try {
			
			if (element.isForceEvaluateReceivedMeasures()) {
				
				element.setForceEvaluateReceivedMeasures(false);
			}
			
			if (!CollectionUtils.isEmpty(newElementValues)) {
				
				for (ElementValue newElementValue : newElementValues) {
					
					try {
						
						ElementValue elementValueUpdated = element.getElementValues().stream()
								.filter(e -> e.getElementTypeParamId().equals(newElementValue.getElementTypeParamId())
										&& e.getParamTypeId().equals(newElementValue.getParamTypeId()))
								.findFirst().orElse(null);
						
						if (elementValueUpdated != null) {
							
							ElementValue oldElementValue = (ElementValue) elementValueUpdated.clone();
							
							if (oldElementValue != null) {
								
								if (!StringUtils.equals(elementValueUpdated.getValue(), newElementValue.getValue())) {
									
									elementValueUpdated.setValue(newElementValue.getValue());
								}

								if (newElementValue.getElementValueStateId() != null) {

									elementValueUpdated.setElementValueStateId(newElementValue.getElementValueStateId());
								}
								
								pairElementValues.add(new ImmutablePair<>(oldElementValue, elementValueUpdated));
							}
						}
						
					} catch (CloneNotSupportedException e) {
						
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}
				
				if (!CollectionUtils.isEmpty(pairElementValues)) {
					
					updatedElementValues = pairElementValues.stream().map(Pair::getRight).collect(Collectors.toList()); 
					
					put(updatedElementValues.stream().map(BasicElement.class::cast).collect(Collectors.toList()));
					publishElementValues(updatedElementValues);
					this.em.sendOnChangeMeasures(element, pairElementValues);
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return updatedElementValues;
	}
	
	private Command publishElementValues(List<ElementValue> elementValues) {
		
		Command command = null;
		
		if (!CollectionUtils.isEmpty(elementValues)) {
			
			SetElementValuesPublishCommand setElementValuesPublishCommand = new SetElementValuesPublishCommand();
			
			for (ElementValue elementValue : elementValues) {
				
				setElementValuesPublishCommand.addElementValue(elementValue);
			}
			
			command = this.em.sendCommand(setElementValuesPublishCommand);
		}
		
		return command;
	}
	
	public ElementState updateElementState(@NonNull Element element, @NonNull Long stateId) {
		
		ElementState elementState = element.getElementState();
		
		try {
			
			if (elementState != null) {
				
				ElementTypeState currentState = element.getElementState().getState();
				
				if (currentState != null && !currentState.getStateId().equals(stateId)) {
					
					ElementTypeState nextState = currentState.getTransitions().stream()
							.filter(e -> e.getStateId().equals(stateId)).findFirst().orElse(null);
					
					if (nextState != null) {
					
						elementState.setPreviousState(currentState);
						elementState.setPreviousStateId(currentState.getStateId());
						
						elementState.setStateId(nextState.getStateId());
						elementState.setState(nextState);	
						
						put(elementState);		
						
						this.em.sendSetElementStatesPublishCommand(Arrays.asList(elementState));
						this.em.sendOnChangeState(element, elementState);
					}
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return elementState;
	}
	
	public Alarm createAlarm(@NonNull AlarmConfig alarmConfig, @NonNull Element element) {
	
		Alarm alarm = null;
		Alarm alarmExists = null;
		
		try {
			
			List<Alarm> alarms = element.getAlarms();
			
			if (!CollectionUtils.isEmpty(alarms)) {
							
				alarmExists = (alarms.stream().filter(e -> e.getAlarmConfigId().equals(alarmConfig.getId())
						&& e.getActivation() != null && e.getDeactivation() == null)).findFirst().orElse(null);
				
			}
			
			if (alarmExists == null) {
			
				alarm = new Alarm();
				
				alarm.setAlarmConfigId(alarmConfig.getId());
				alarm.setAlarmConfig(alarmConfig);
				alarm.setSourceElement(element);
				alarm.setSourceElementId(element.getId());
				alarm.setSourceElementTypeId(element.getElementTypeId());
				alarm.setTargetElement(element);
				alarm.setTargetElementId(element.getId());
				alarm.setTargetElementTypeId(element.getElementTypeId());
				alarm.setActivation(Instant.now().toEpochMilli());
				alarm.setCreationUserId(User.USER_SYSTEM);
				alarm.setActivationComments("LBL_ACTIVATED_FOR_RULE");

				if (!alarmConfig.getTreatment().booleanValue()) {

					alarm.setRecognized(Instant.now().toEpochMilli());
					alarm.setRecognizedUserId(User.USER_SYSTEM);
					alarm.setRecognizedComments("LBL_AUTOMATICALLY_TREATED");
				}
				
				alarm.generateId();
				
				if (assignFiltered(alarm) != null) {
				
					if (alarms == null) {
						
						alarms = new ArrayList<>();
						element.setAlarms(alarms);
					}
					
					alarms.add(alarm);
					
					put(alarm);
					
					this.em.sendSetAlarmsPublishCommand(Arrays.asList(alarm));
					this.em.sendOnChangeAlarms(Arrays.asList(alarm));			
					updateAlarmCounters(element);
				}
				else {
					
					alarm = null;
				}
			}
			else {
				
				log.warn(String.format("Alarm %d of type %d already exists for element %s => %s", alarmExists.getId(),
						alarmConfig.getId(), (new Key(element)).getSimpleValue(), alarmExists.toString()));
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return alarm;
	}
	
	private Alarm assignFiltered(Alarm alarm) {

		Alarm alarmReturned = alarm;

		AlarmConfig alarmConfig = alarm.getAlarmConfig();
		Element element = alarm.getTargetElement();

		if (alarmConfig != null && element != null) {

			List<AlarmFiltered> alarmsFiltered = element.getAlarmsFiltered();

			if (!CollectionUtils.isEmpty(alarmsFiltered)) {

				AlarmFiltered alarmFiltered = alarmsFiltered.stream()
						.filter(e -> e.getAlarmConfigId().equals(alarmConfig.getId())).findFirst().orElse(null);

				if (alarmFiltered != null) {

					Long filteredUntil = alarmFiltered.getFilteredUntil();

					if (filteredUntil != null && filteredUntil > Instant.now().toEpochMilli()) {

						if (alarmFiltered.getAlarmDisabled() != null
								&& alarmFiltered.getAlarmDisabled().booleanValue()) {

							log.debug(String.format("AlarmConfig %d disabled for element %s", alarmConfig.getId(),
									(new Key(element)).getSimpleValue()));

							alarmReturned = null;
						} 
						else {

							alarm.setFilteredUntil(filteredUntil);
							
							if (alarm.getRecognized() == null) {

								alarm.setRecognizedUserId(User.USER_SYSTEM);
								alarm.setRecognized(Instant.now().toEpochMilli());
								alarm.setRecognizedComments("LBL_FOR_FILTERED_ALARMS_RULES");
							}
						}
					}
				}
			}
		}

		return alarmReturned;
	}
	
	public Alarm deleteAlarm(@NonNull Alarm alarm) {
		
		return deleteAlarm(alarm, User.USER_SYSTEM, "");
	}
	
	public Alarm deleteAlarm(@NonNull Alarm alarm, Long deactivationUserId, String deactivationComments) {
		
		try {
			
			alarm.setDeactivation(Instant.now().toEpochMilli());
			alarm.setDeactivationUserId(deactivationUserId);
			alarm.setDeactivationComments(deactivationComments);
					
			if (alarm.getRecognized() != null
					|| (alarm.getFilteredUntil() != null && alarm.getFilteredUntil() > Instant.now().toEpochMilli())) {
				
				removeAlarm(alarm);
			}
			else {
				
				put(alarm);
			}
			
			this.em.sendSetAlarmsPublishCommand(Arrays.asList(alarm));
			this.em.sendOnChangeAlarms(Arrays.asList(alarm));
			
			updateAlarmCounters(alarm.getTargetElement());
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return alarm;
	}
	
	public Alarm recognizeAlarm(@NonNull Alarm alarm, Long recognizedUserId, String recognizedComments, Boolean disagree) {
		
		try {
			
			alarm.setRecognized(Instant.now().toEpochMilli());
			alarm.setRecognizedUserId(recognizedUserId);
			alarm.setRecognizedComments(recognizedComments);
			alarm.setDisagree(disagree);
			
			if (alarm.getDeactivation() != null) {
				
				removeAlarm(alarm);
			}
			else {
				
				put(alarm);
			}
			
			this.em.sendSetAlarmsPublishCommand(Arrays.asList(alarm));
			this.em.sendOnChangeAlarms(Arrays.asList(alarm));
			
			updateAlarmCounters(alarm.getTargetElement());
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return null;
	}
	
	public Alarm filterAlarm(@NonNull Alarm alarm, @NonNull Long filteredUntil) {
		
		try {
			
			alarm.setFilteredUntil(filteredUntil);
			
//			if (alarm.getRecognized() == null) {
//
//				alarm.setRecognizedUserId(User.USER_SYSTEM);
//				alarm.setRecognized(Instant.now().toEpochMilli());
//				alarm.setRecognizedComments("LBL_FOR_FILTERED_ALARMS_RULES");
//			}
			
			if (alarm.getDeactivation() != null) {
				
				removeAlarm(alarm);
			}
			else {
				
				put(alarm);
			}
			
			this.em.sendSetAlarmsPublishCommand(Arrays.asList(alarm));
			this.em.sendOnChangeAlarms(Arrays.asList(alarm));
			
			updateAlarmCounters(alarm.getTargetElement());
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return alarm;
	}
	
	public Alarm unfilterAlarm(@NonNull Alarm alarm) {
		
		try {
			
			alarm.setFilteredUntil(null);
			
//			if (alarm.getAlarmConfig() != null && alarm.getAlarmConfig().getTreatment().booleanValue()) {
//
//				alarm.setRecognized(null);
//				alarm.setRecognizedUserId(null);
//				alarm.setRecognizedComments(null);
//			}
			
			if (alarm.getDeactivation() != null && alarm.getRecognized() != null) {
				
				removeAlarm(alarm);
			}
			else {
				
				put(alarm);
			}
			
			this.em.sendSetAlarmsPublishCommand(Arrays.asList(alarm));
			this.em.sendOnChangeAlarms(Arrays.asList(alarm));
			
			updateAlarmCounters(alarm.getTargetElement());
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return alarm;
	}
	
	public AlarmFiltered createAlarmConfigFiltered(@NonNull AlarmFiltered alarmFiltered) {
		
		AlarmFiltered alarmConfigFiltered = null;

		try {
			
			List<AlarmFiltered> alarmsFiltered = alarmFiltered.getElement().getAlarmsFiltered();
			
			if (alarmsFiltered == null) {
				
				alarmsFiltered = new ArrayList<>();
				
				alarmFiltered.getElement().setAlarmsFiltered(alarmsFiltered);			
			}
			else {
					
				if (!CollectionUtils.isEmpty(alarmsFiltered)) {
					
					alarmConfigFiltered = alarmsFiltered.stream()
							.filter(e -> e.getAlarmConfigId().equals(alarmFiltered.getAlarmConfig().getId())).findFirst()
							.orElse(null);
				}
			}
			
			if (alarmConfigFiltered == null) {
				
				alarmConfigFiltered = alarmFiltered;

				alarmsFiltered.add(alarmConfigFiltered);
				put(alarmConfigFiltered);
				
				updateAlarmCounters(alarmConfigFiltered.getElement());
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return alarmConfigFiltered;
	}
	
	public AlarmFiltered removeAlarmFiltered(@NonNull AlarmFiltered alarmFiltered) {
		
		AlarmFiltered alarmConfigFiltered = null;
		Element element = alarmFiltered.getElement();
		
		try {
			
			List<AlarmFiltered> alarmsFiltered = alarmFiltered.getElement().getAlarmsFiltered();
			
			if (!CollectionUtils.isEmpty(alarmsFiltered)) {
					
				alarmConfigFiltered = alarmsFiltered.stream()
							.filter(e -> e.getAlarmConfigId().equals(alarmFiltered.getAlarmConfig().getId())).findFirst()
							.orElse(null);
			}
			
			if (alarmConfigFiltered != null) {
				
				alarmsFiltered.remove(alarmConfigFiltered);

				delete(alarmConfigFiltered);
				
				if (!CollectionUtils.isEmpty(alarmConfigFiltered.getAlarmConfig().getAlarmConfigMeasures())) {
					
					element.setForceEvaluateReceivedMeasures(true);				
					
				}
				
				updateAlarmCounters(alarmConfigFiltered.getElement());
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
			
		return alarmConfigFiltered;
	}
	
	private Alarm removeAlarm(@NonNull Alarm alarm) {
		
		Alarm alarmDeleted = null;
		List<Alarm> alarms = alarm.getTargetElement().getAlarms();
		
		if (!CollectionUtils.isEmpty(alarms)) {
			
			alarmDeleted = alarms.stream().filter(e -> e.getId().equals(alarm.getId())).findFirst().orElse(alarm);
			
			if (alarmDeleted != null && alarms.remove(alarmDeleted)) {
				
				delete(alarmDeleted);
			}
		}
		
		return alarmDeleted;
	}
	
	public void lock(Long id) {
		
		Semaphore mutex = this.elementsMap.get(id).getRight();
		
		try {
			
			if (mutex != null) {
				
				mutex.acquire();
			}
		} 
		catch (InterruptedException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}
	}
	
	public void unlock(Long id) {
		
		Semaphore mutex = this.elementsMap.get(id).getRight();
		
		if (mutex != null) {
		
			mutex.release();
		}
	}
	
	private void put(BasicElement be) {

		this.threadDataWriter.add(be, ThreadDataWriter.Action.PUT);
	}
	
	private void put(List<BasicElement> bes) {
		
		if (!CollectionUtils.isEmpty(bes)) {
			
			for (BasicElement be : bes) {
				
				this.threadDataWriter.add(be, ThreadDataWriter.Action.PUT);
			}
		}
	}
	
	private void delete(BasicElement be) {
		
		this.threadDataWriter.add(be, ThreadDataWriter.Action.DEL);
	}
	
	public void updateElementStats(Element element) {
		
		lock(element.getId());
		
		updateAlarmCounters(element);
		
		unlock(element.getId());
	}
	
	private void updateAlarmCounters(Element element) {

		long activeAlarms = 0L;
		long noRecognizeAlarms = 0L;
		long filteredAlarms = 0L;
		
		List<Alarm> alarms = element.getAlarms();
		
		if (!CollectionUtils.isEmpty(alarms)) {
			
			activeAlarms = alarms.stream()
					.filter(e -> e.getDeactivation() == null && e.getActivation() != null
							&& (e.getFilteredUntil() == null || e.getFilteredUntil() < Instant.now().toEpochMilli()))
					.count();
			
			noRecognizeAlarms = alarms.stream()
					.filter(e -> e.getRecognized() == null
							&& (e.getFilteredUntil() == null || e.getFilteredUntil() < Instant.now().toEpochMilli()))
					.count();
		}
		
		List<AlarmFiltered> alarmsFiltered = element.getAlarmsFiltered();
		
		if (!CollectionUtils.isEmpty(alarmsFiltered)) {
			
			filteredAlarms = alarmsFiltered.stream().filter(
					e -> e.getFilteredUntil() != null && e.getFilteredUntil() > Instant.now().toEpochMilli())
					.count();
		}
		
		element.setFilteredAlarms(filteredAlarms);
		element.setActiveAlarms(activeAlarms);
		element.setNoRecognizeAlarms(noRecognizeAlarms);
		
		
		if (this.threadGenericData != null) {
			
			this.threadGenericData.requestUpdate();
		}
	}
}
