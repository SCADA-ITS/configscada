package com.revenga.rits.back.equipment.manager.executor.coordinator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.AlarmConfigMeasure;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementValueState;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.ThresholdOperation;
import com.revenga.rits.back.data.core.util.OperationProcessor;
import com.revenga.rits.back.equipment.manager.service.ElementMeasuresManager;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementValueCoordinator extends EntityCoordinator<List<ElementValue>> {

	public ElementValueCoordinator(Element element) {
		super(element);
	}

	public int update(List<ElementValue> newElementValues) {

		int changes = -1;
		List<ElementValue> elementValuesUpdated = null;

		try {

			if (!CollectionUtils.isEmpty(newElementValues)) {

				elementValuesUpdated = processUpdate(newElementValues);

				if (!CollectionUtils.isEmpty(elementValuesUpdated)) {

					changes = elementValuesUpdated.size();
				}
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return changes;
	}

	private List<ElementValue> processUpdate(List<ElementValue> newElementValues) {

		List<ElementValue> elementValuesUpdated = null;
		List<ElementValue> currentElementValues = element.getElementValues().stream()
				.filter(e -> e.getParamTypeId().equals(Long.valueOf(ParamType.MEASURE))).collect(Collectors.toList());

		if (!CollectionUtils.isEmpty(currentElementValues) && !CollectionUtils.isEmpty(newElementValues)) {

			List<Pair<ElementValue, ElementValue>> elementValuesToChange = getElementValuesToChange(
					currentElementValues, newElementValues, element.isForceEvaluateReceivedMeasures());

			if (!CollectionUtils.isEmpty(elementValuesToChange)) {
				
				log.debug(elementKey.getSimpleValue() + " => Changes detected in " + elementValuesToChange.size() + " measures of "
						+ newElementValues.size() + " received");
				
				if (element.isForceEvaluateReceivedMeasures()) {
				
					log.debug(elementKey.getSimpleValue()
							+ " => forced to evaluate all measures received even if they are unchanged");
				}

				procOutOfRange(elementValuesToChange);
				procAlarmsFromMeasures(elementValuesToChange);

				elementValuesUpdated = elementData.updateElementValues(
						elementValuesToChange.stream().map(Pair::getRight).collect(Collectors.toList()), element);
				
				procPersistDurable(elementValuesUpdated);
				
			} else {

				log.debug(elementKey.getSimpleValue() + " => No changes in " + newElementValues.size() + " measures received");
			}
		}

		return elementValuesUpdated;
	}

	private List<Pair<ElementValue, ElementValue>> getElementValuesToChange(List<ElementValue> currentElementValues,
			List<ElementValue> newElementValues, boolean forcedAll) {

		List<Pair<ElementValue, ElementValue>> elementValuesToChange = new ArrayList<>();

		for (ElementValue newElementValue : newElementValues) {

			ElementValue currentElementValue = currentElementValues.stream().filter(e -> e.getElementTypeParamId()
					.equals(newElementValue.getElementTypeParamId())
					&& e.getParamTypeId().equals(newElementValue.getParamTypeId())
					&& (e.getElementValueStateId() == null
							|| Long.valueOf(ElementValueState.STATE_INITIALIZED).equals(e.getElementValueStateId())
							|| forcedAll
							|| (!forcedAll && !StringUtils.equals(e.getValue(), newElementValue.getValue()))
							|| (this.element.getMeasureChangeAll() != null && this.element.getMeasureChangeAll().booleanValue())))
					.findFirst().orElse(null);

			if (currentElementValue != null) {

				String paramName = "UNKNOWN";
				ElementTypeParam elementTypeParam = currentElementValue.getElementTypeParam();

				if (elementTypeParam != null) {

					paramName = elementTypeParam.getAlias();
				}

				log.debug(elementKey.getSimpleValue() + " => " + (new Key(currentElementValue)).getSimpleValue() + " ("
						+ paramName + ") change from " + currentElementValue.getValue() + " to "
						+ newElementValue.getValue());

				elementValuesToChange.add(new ImmutablePair<>(currentElementValue, newElementValue));
			}
		}

		return elementValuesToChange;
	}

	private boolean procOutOfRange(List<Pair<ElementValue, ElementValue>> elementValuesToChange) {

		boolean changeAnyElementValueOutOfRange = false;

		if (!CollectionUtils.isEmpty(elementValuesToChange)) {

			for (Pair<ElementValue, ElementValue> pair : elementValuesToChange) {

				ElementValue currentElementValue = pair.getLeft();
				ElementValue newElementValue = pair.getRight();
				
				if (currentElementValue != null && newElementValue != null) {
					
					boolean newElementValueInRange = isElementValueInRange(newElementValue,
							currentElementValue.getElementTypeParam());

					if (newElementValueInRange) {
						
						newElementValue.setElementValueStateId(ElementValueState.STATE_ONLINE);
					}
					else {
						
						newElementValue.setElementValueStateId(ElementValueState.STATE_OUT_OF_RANGE);

						log.debug(elementKey.getSimpleValue() + " [" + (new Key(newElementValue)).getSimpleValue() + "] => "
								+ currentElementValue.getElementTypeParam().getAlias() + " = " + newElementValue.getValue()
								+ " out of range (" + currentElementValue.getElementTypeParam().getThreshold1() + " - "
								+ currentElementValue.getElementTypeParam().getThreshold2() + ")");

						changeAnyElementValueOutOfRange |= true;
					}
				}
			}
		}

		return changeAnyElementValueOutOfRange;
	}
	
	private boolean isElementValueInRange(ElementValue elementValue, ElementTypeParam elementTypeParam) {

		boolean elementValueInRange = true;

		if (elementTypeParam != null && elementValue != null && !StringUtils.isEmpty(elementTypeParam.getThreshold1())
				&& !StringUtils.isEmpty(elementTypeParam.getThreshold2())) {

			elementValueInRange = OperationProcessor.check(ThresholdOperation.OPERATION_BETWEEN,
					elementTypeParam.getThreshold1(), elementTypeParam.getThreshold2(), elementValue.getValue(),
					elementTypeParam.getDataTypeId()) > 0L;
		}

		return elementValueInRange;
	}

	private boolean procAlarmsFromMeasures(List<Pair<ElementValue, ElementValue>> elementValuesToChange) {

		boolean changeAnyAlarm = false;

		if (!CollectionUtils.isEmpty(element.getElementType().getAlarmConfigsWithMeasures())
				&& !CollectionUtils.isEmpty(elementValuesToChange)) {

			Set<AlarmConfig> alarmConfigWithMeasuresToCheck = new HashSet<>();

			for (Pair<ElementValue, ElementValue> pair : elementValuesToChange) {

				ElementValue currentElementValue = pair.getLeft();

				List<AlarmConfig> alarmConfigsInMeasureToCheck = currentElementValue.getAlarmConfigMeasureConfig();
				
				if (!CollectionUtils.isEmpty(alarmConfigsInMeasureToCheck)) {
					
					alarmConfigsInMeasureToCheck.forEach(alarmConfigWithMeasuresToCheck::add);
				}
			}

			if (!CollectionUtils.isEmpty(alarmConfigWithMeasuresToCheck)) {

				changeAnyAlarm = procAlarmConfigMeasures(
						alarmConfigWithMeasuresToCheck.stream().collect(Collectors.toList()), elementValuesToChange);
			}
		}

		return changeAnyAlarm;
	}

	private boolean procAlarmConfigMeasures(List<AlarmConfig> alarmConfigs,
			List<Pair<ElementValue, ElementValue>> elementValuesToChange) {

		boolean changeAnyAlarm = false;

		if (!CollectionUtils.isEmpty(alarmConfigs) && !CollectionUtils.isEmpty(elementValuesToChange)) {

			List<ElementValue> newElementValues = elementValuesToChange.stream().map(Pair::getRight)
					.collect(Collectors.toList());
			
			List<ElementValue> elementValuesChangedState = new ArrayList<>();

			for (AlarmConfig alarmConfig : alarmConfigs) {

				List<AlarmConfigMeasure> alarmConfigMeasures = alarmConfig.getAlarmConfigMeasures();

				if (!CollectionUtils.isEmpty(alarmConfigMeasures)) {

					boolean match = true;

					for (AlarmConfigMeasure alarmConfigMeasure : alarmConfigMeasures) {

						match &= matchAlarmConfigMeasure(alarmConfigMeasure, element.getElementValues(),
								newElementValues);

						if (!match) {

							break;
						}
					}

					updateElementValueState(alarmConfig, match, elementValuesToChange, elementValuesChangedState);

					changeAnyAlarm |= (procAlarm(alarmConfig, match) != null);
				}
			}
			
			if (changeAnyAlarm) {
				
				(new ElementStateCoordinator(element)).calculateState();
			}
		}

		return changeAnyAlarm;
	}
	
	private void procPersistDurable(List<ElementValue> elementValues) {
		
		if (!CollectionUtils.isEmpty(elementValues)) {
			
			List<ElementValue> elementValuesToPersist = elementValues.stream()
					.filter(e -> e.getElementTypeParam() != null && e.getElementTypeParam().getDurable() != null
							&& e.getElementTypeParam().getDurable().booleanValue())
					.collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(elementValuesToPersist)) {
			
				ElementMeasuresManager.getInstance().putElementValueWithChanges(elementValuesToPersist);
			}
		}
	}

	private void updateElementValueState(AlarmConfig alarmConfig, boolean match,
			List<Pair<ElementValue, ElementValue>> elementValuesToChange, List<ElementValue> elementValuesChangedState) {

		if (!CollectionUtils.isEmpty(elementValuesToChange)) {

			List<ElementValue> currentElementValues = elementValuesToChange.stream().map(Pair::getLeft)
					.collect(Collectors.toList());
			List<ElementValue> newElementValues = elementValuesToChange.stream().map(Pair::getRight)
					.collect(Collectors.toList());
			
			for (AlarmConfigMeasure alarmConfigMeasure : alarmConfig.getAlarmConfigMeasures()) {

				ElementTypeParam elementTypeParam = alarmConfigMeasure.getElementTypeParam();

				ElementValue currentElementValue = currentElementValues.stream()
						.filter(e -> e.getElementTypeParamId().equals(elementTypeParam.getId())
								&& e.getParamTypeId().equals(elementTypeParam.getParamTypeId()))
						.findFirst().orElse(null);

				ElementValue newElementValue = newElementValues.stream()
						.filter(e -> e.getElementTypeParamId().equals(elementTypeParam.getId())
								&& e.getParamTypeId().equals(elementTypeParam.getParamTypeId()))
						.findFirst().orElse(null);

				if (currentElementValue != null) {

					if (newElementValue == null) {

						try {

							newElementValue = (ElementValue) currentElementValue.clone();

						} catch (CloneNotSupportedException e) {

							log.error(e.getMessage());
							log.debug(ExceptionUtils.getStackTrace(e));
						}

						if (newElementValue != null) {

							elementValuesToChange.add(new ImmutablePair<>(currentElementValue, newElementValue));
						}
					}

					if (newElementValue != null) {

						boolean elementValueOutOfRange = (newElementValue.getElementValueStateId() != null)
								&& (newElementValue.getElementValueStateId()
										.equals(Long.valueOf(ElementValueState.STATE_OUT_OF_RANGE)));

						if (!elementValueOutOfRange) {
							
							boolean change = true;
							
							if (!CollectionUtils.isEmpty(elementValuesChangedState)) {
								
								ElementValue nev = newElementValue;

								change = (elementValuesChangedState.stream()
										.filter(e -> e.getElementTypeId().equals(nev.getElementTypeId())
												&& e.getElementId().equals(nev.getElementId())
												&& e.getElementTypeParamId().equals(nev.getElementTypeParamId())
												&& e.getParamTypeId().equals(nev.getParamTypeId()))
										.findFirst().orElse(null) == null);							
							}
							
							if (change) {
							
								newElementValue.setElementValueStateId(
										match ? ElementValueState.STATE_ALERTED : ElementValueState.STATE_ONLINE);
								
								if (match) {
								
									elementValuesChangedState.add(newElementValue);
								}
							}
						}
					}
				}
			}
		}
	}

	private boolean matchAlarmConfigMeasure(AlarmConfigMeasure alarmConfigMeasure,
			List<ElementValue> currentElementValues, List<ElementValue> newElementValues) {

		boolean matched = false;

		if (!CollectionUtils.isEmpty(currentElementValues) && !CollectionUtils.isEmpty(newElementValues)) {
			
			ElementValue currentElementValue = currentElementValues.stream()
					.filter(e -> e.getElementTypeParamId().equals(alarmConfigMeasure.getElementTypeParamId())
							&& e.getParamTypeId().equals(alarmConfigMeasure.getParamTypeId()))
					.findFirst().orElse(null);
			
			if (currentElementValue != null) {
			
				ElementValue elementValueToCheck = currentElementValue;
				
				ElementValue newElementValue = newElementValues.stream()
						.filter(e -> e.getElementTypeParamId().equals(alarmConfigMeasure.getElementTypeParamId())
								&& e.getParamTypeId().equals(alarmConfigMeasure.getParamTypeId()))
						.findFirst().orElse(null);
				
				if (newElementValue != null) {
				
					elementValueToCheck = newElementValue;
				}
				
				Long operation = alarmConfigMeasure.getAlarmMeasureOperationId();

				matched = (OperationProcessor.check(operation, alarmConfigMeasure.getThresholdVal1(),
						alarmConfigMeasure.getThresholdVal2(), elementValueToCheck.getValue(),
						alarmConfigMeasure.getElementTypeParam().getDataTypeId()) > 0L);
			}
		}

		return matched;
	}

	private Alarm procAlarm(AlarmConfig alarmConfig, boolean matched) {

		Alarm alarm = null;
		List<Alarm> currentAlarms = element.getAlarms();
		boolean mustCreate = false;
		boolean mustDelete = false;
		boolean mustUpdate = false;
		
		Alarm currentAlarm = null;
		

		if (!CollectionUtils.isEmpty(currentAlarms)) {

			currentAlarm = currentAlarms.stream()
					.filter(e -> e.getAlarmConfigId().equals(alarmConfig.getId()) && e.getDeactivation() == null)
					.findFirst().orElse(null);

			mustCreate = currentAlarm == null && matched;
			mustDelete = (currentAlarm != null && !matched
					&& !EntitiesManager.getInstance().addInMinTimeMap(currentAlarm))
					|| (currentAlarm == null && !matched);
			mustUpdate = currentAlarm != null && matched;
			
		} else {
			
			mustCreate = matched;
		}
		
		if (mustCreate) {

			alarm = procAlarmCreate(alarmConfig);
		} 
		else if (mustDelete) {
			
			if (currentAlarm != null) {
				
				alarm = procAlarmDelete(currentAlarm);
			}
			else {

				EntitiesManager.getInstance().removeFromThresholdMap(alarmConfig, element);
			}
			
		} else if (mustUpdate) {
			
			Alarm updateAlarm = EntitiesManager.getInstance().removeFromMinTimeMap(alarmConfig, element);
			
			if(updateAlarm != null){
				
				procAlarmUpdate(updateAlarm);
			}
		}
		else {
			
			EntitiesManager.getInstance().removeFromThresholdMap(alarmConfig, element);
		}

		return alarm;
	}
	
	private Alarm procAlarmCreate(AlarmConfig alarmConfig) {
		
		Alarm alarm = elementData.newAlarm(alarmConfig, element);
		
		if (alarmConfig.getThresholdTime() != null && alarmConfig.getThresholdTime() > 0) {
			
			EntitiesManager.getInstance().addInThresholdMap(alarm);
			
			alarm = null;
		}
		else {
			
			elementData.createAlarm(alarmConfig, element);
			
			log.debug(String.format("%s => Register Alarm(%d) Recognized: %s AlarmConfig %d, %s",
					(new Key(element)).getSimpleValue(), alarm.getId(), 
					alarm.getRecognized() != null,
					alarm.getAlarmConfig().getId(),
					alarm.getAlarmConfig().getAlias()));
		}
		
		return alarm;
	}
	
	private Alarm procAlarmDelete(Alarm alarmToDelete) {
		
		Alarm alarm = elementData.deleteAlarm(alarmToDelete, null, null);
		
		if (alarm != null) {
			
			log.debug(String.format("%s => Unregister Alarm(%d) Recognized: %s AlarmConfig %d, %s",
					(new Key(element)).getSimpleValue(), alarm.getId(), 
					alarm.getRecognized() != null,
					alarm.getAlarmConfig().getId(),
					alarm.getAlarmConfig().getAlias()));
		}
		
		return alarm;
	}

	private Alarm procAlarmUpdate(Alarm alarmUpAlarm) {

		Alarm alarm = elementData.updateAlarm(alarmUpAlarm);

		if (alarm != null) {

			log.debug(String.format("%s => Update Alarm(%d) AlarmConfig %d, %s", (new Key(element)).getSimpleValue(),
					alarm.getId(), alarm.getAlarmConfig().getId(), alarm.getAlarmConfig().getAlias()));
		}

		return alarm;
	}

}