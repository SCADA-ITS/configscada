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
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementStateAlarm;
import com.revenga.rits.back.data.core.model.ElementSubtypeActiveParam;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementValueState;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.command.RequestCommand;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementStateCoordinator extends EntityCoordinator<ElementState> {

	public ElementStateCoordinator(Element element) {
		super(element);
	}

	public int update(ElementState newElementState) {

		int changes = -1;

		ElementState currentElementState = element.getElementState();

		ElementType elementType = element.getElementType();

		Long newAlarmLevelId = calculateMaxAlarmLevel();

		Long elementTypeMaintenanceStateId = elementType.getMaintenanceElementTypeStateId();

		if (currentElementState != null && newElementState != null
				&& (!currentElementState.getStateId().equals(newElementState.getStateId()) || (newAlarmLevelId != null
						&& !newAlarmLevelId.equals(currentElementState.getMaxAlarmLevelId())))) {

			Long oldElementStateId = currentElementState.getStateId();
			Long newElementStateId = newElementState.getStateId();

			boolean fromDisconnectedToConnected = ((currentElementState.getStateId()
					.equals(elementType.getInitialElementTypeStateId())
					|| currentElementState.getStateId().equals(elementType.getDisconnectedElementTypeStateId()))
					&& !newElementState.getStateId().equals(elementType.getDisconnectedElementTypeStateId())
					&& !newElementState.getStateId().equals(elementType.getInitialElementTypeStateId())
					&& (elementTypeMaintenanceStateId == null || (elementTypeMaintenanceStateId != null
							&& !elementTypeMaintenanceStateId.equals(newElementStateId))));

			boolean fromConnectedToDisconnected = (!currentElementState.getStateId()
					.equals(elementType.getDisconnectedElementTypeStateId())
					&& newElementState.getStateId().equals(elementType.getDisconnectedElementTypeStateId()));

			boolean elementTypeIsLogic = elementType.getIsLogic() != null && elementType.getIsLogic().booleanValue();

			if (fromDisconnectedToConnected && !elementTypeIsLogic) {

				newElementStateId = onConnect();

			} else if (fromConnectedToDisconnected) {

				newElementStateId = onDisconnect();
			}

			if (newElementStateId != null) {

				if (!elementTypeIsLogic && (fromDisconnectedToConnected
						|| newElementStateId.equals(elementType.getConnectedElementTypeStateId()))) {

					newElementStateId = recoverElementStateWithAlarm();
				}

				ElementState changedElementState = elementData.updateElementState(element, newElementStateId,
						newAlarmLevelId, newElementState.getMaintenanceComment());

				if (changedElementState.getStateId().equals(newElementStateId)) {

					if (fromDisconnectedToConnected) {

						requestMeasuresAndAlarms();
					}

					Pair<Integer, Integer> pairAlarmsCreatedAndDeleted = updateAlarmsToStateChanges(newElementStateId);

					String strAlarms = "";

					if (pairAlarmsCreatedAndDeleted != null) {

						strAlarms = String.format(" [Alarms created: %d; Alarms deleted: %d]",
								pairAlarmsCreatedAndDeleted.getLeft(), pairAlarmsCreatedAndDeleted.getRight());
					}

					log.debug(String.format("%s => State from %d to %d%s", (new Key(element)).getSimpleValue(),
							oldElementStateId, newElementStateId, strAlarms));

					changes = 1;

					if (pairAlarmsCreatedAndDeleted != null && (pairAlarmsCreatedAndDeleted.getLeft() > 0
							|| pairAlarmsCreatedAndDeleted.getRight() > 0)) {
						calculateState();
					}

				}
			}
		}

		return changes;
	}

	public void calculateState() {

		List<Alarm> alarms = null;
		ElementState newElementState = null;
		ElementState currentElementState = null;

		try {

			currentElementState = element.getElementState();

			if (currentElementState != null && currentElementState.getStateId() != null
					&& !currentElementState.getStateId().equals(element.getElementType().getDisconnectedElementTypeStateId())
					&& !currentElementState.getStateId().equals(element.getElementType().getInitialElementTypeStateId())
					&& !currentElementState.getStateId().equals(element.getElementType().getMaintenanceElementTypeStateId())) {
				
				alarms = element.getAlarms();
				newElementState = (ElementState) currentElementState.clone();

				if (!CollectionUtils.isEmpty(alarms)) {

					Alarm alarmThatChangeState = alarms
							.stream().filter(e -> e.getAlarmConfig().getElementTypeStateId() != null
									&& e.getActivation() != null && e.getDeactivation() == null)
							.findFirst().orElse(null);

					if (alarmThatChangeState != null) {

						if (!currentElementState.getStateId()
								.equals(element.getElementType().getDisconnectedElementTypeStateId())
								&& !currentElementState.getStateId()
										.equals(element.getElementType().getInitialElementTypeStateId())) {

							newElementState.setStateId(alarmThatChangeState.getAlarmConfig().getElementTypeStateId());
						}

					} else {

						if (!currentElementState.getStateId()
								.equals(element.getElementType().getDisconnectedElementTypeStateId())) {

							newElementState.setStateId(element.getElementType().getConnectedElementTypeStateId());
						}
					}

				} else {

					// Element without alarms
					newElementState.setStateId(element.getElementType().getConnectedElementTypeStateId());
				}

				update(newElementState);
			}

		} catch (CloneNotSupportedException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	private Long calculateMaxAlarmLevel() {

		Long maxAlarmLevel = null;
		List<Alarm> alarms = element.getAlarms();

		if (!CollectionUtils.isEmpty(alarms)) {

			for (Alarm alarm : alarms) {

				if (alarm != null && alarm.getAlarmConfig() != null && alarm.getAlarmConfig().getAlarmLevelId() != null
						&& alarm.getDeactivation() == null && alarm.getActivation() != null) {

					Long alarmLevel = alarm.getAlarmConfig().getAlarmLevelId();

					if (maxAlarmLevel == null || alarmLevel > maxAlarmLevel) {

						maxAlarmLevel = alarmLevel;
					}
				}
			}
		}
		return maxAlarmLevel;
	}

	private Long onConnect() {

		return element.getElementType().getConnectedElementTypeStateId();
	}

	private Long onDisconnect() {

		Long newElementStateId = element.getElementType().getDisconnectedElementTypeStateId();

		if (element.getKeepValuesOnDisconnected() != null && element.getKeepValuesOnDisconnected().booleanValue()) {

			updateElementValueStatesOffline();

		} else {

			updateElementValueStatesInitialized();
		}

		return newElementStateId;
	}

	private Long recoverElementStateWithAlarm() {

		List<Alarm> alarms = element.getAlarms();
		Long newElementStateId = element.getElementType().getConnectedElementTypeStateId();

		if (!CollectionUtils.isEmpty(alarms)) {

			List<Alarm> activeAlarmsThatChangeState = alarms.stream()
					.filter(e -> e.getAlarmConfig().getElementTypeStateId() != null && e.getActivation() != null
							&& e.getDeactivation() == null)
					.collect(Collectors.toList());

			if (!CollectionUtils.isEmpty(activeAlarmsThatChangeState)) {

				AlarmConfig alarmConfig = activeAlarmsThatChangeState.get(0).getAlarmConfig();

				Long alarmStateid = alarmConfig.getElementTypeStateId();

				if (alarmStateid != null) {

					newElementStateId = alarmStateid;
				}
			}
		}

		return newElementStateId;
	}

	private void requestMeasuresAndAlarms() {

		RequestCommand requestCommand = new RequestCommand();

		requestCommand.add(element, RequestCommand.REQ_MEASURES);
		requestCommand.add(element, RequestCommand.REQ_ALARMS);

		EntitiesManager.getInstance().sendCommand(requestCommand);
	}

	private List<ElementValue> updateElementValueStatesInitialized() {

		List<ElementValue> updatedElementValues = null;
		List<ElementValue> newElementValues = new ArrayList<>();
		List<ElementValue> currentElementValues = element.getElementValues();
		List<ElementTypeParam> elementTypeParamsByElement = getElementTypeParamsMeasuresByElement();

		if (!CollectionUtils.isEmpty(elementTypeParamsByElement) && !CollectionUtils.isEmpty(currentElementValues)) {

			for (ElementTypeParam elementTypeParam : elementTypeParamsByElement) {

				boolean changed = false;
				try {

					ElementValue oldElementValue = currentElementValues.stream()
							.filter(e -> e.getElementTypeParamId().equals(elementTypeParam.getId())
									&& e.getParamTypeId().equals(elementTypeParam.getParamTypeId()))
							.findFirst().orElse(null);

					if (oldElementValue != null) {

						ElementValue newElementValue = (ElementValue) oldElementValue.clone();

						if (!StringUtils.equals(oldElementValue.getValue(), elementTypeParam.getDefaultValue())) {

							newElementValue.setValue(elementTypeParam.getDefaultValue());

							changed = true;
						}

						if (!Long.valueOf(ElementValueState.STATE_INITIALIZED)
								.equals(oldElementValue.getElementValueStateId())) {

							newElementValue.setElementValueStateId(ElementValueState.STATE_INITIALIZED);

							changed = true;
						}

						if (changed) {

							newElementValues.add(newElementValue);
						}
					}

				} catch (CloneNotSupportedException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}

		if (!CollectionUtils.isEmpty(newElementValues)) {

			updatedElementValues = elementData.updateElementValues(newElementValues, element);
		}

		return updatedElementValues;
	}

	private List<ElementValue> updateElementValueStatesOffline() {

		List<ElementValue> updatedElementValues = null;
		List<ElementValue> newElementValues = new ArrayList<>();
		List<ElementValue> currentElementValues = element.getElementValues();
		List<ElementTypeParam> elementTypeParamsByElement = getElementTypeParamsMeasuresByElement();

		if (!CollectionUtils.isEmpty(elementTypeParamsByElement) && !CollectionUtils.isEmpty(currentElementValues)) {

			for (ElementTypeParam elementTypeParam : elementTypeParamsByElement) {

				try {

					ElementValue oldElementValue = currentElementValues.stream()
							.filter(e -> e.getElementTypeParamId().equals(elementTypeParam.getId())
									&& e.getParamTypeId().equals(elementTypeParam.getParamTypeId())
									&& (e.getElementValueStateId() == null || e.getElementValueStateId() != null
											&& !e.getElementValueStateId().equals(ElementValueState.STATE_INITIALIZED)))
							.findFirst().orElse(null);

					if (oldElementValue != null) {

						ElementValue newElementValue = (ElementValue) oldElementValue.clone();

						newElementValue.setElementValueStateId(ElementValueState.STATE_OFFLINE);

						newElementValues.add(newElementValue);
					}

				} catch (CloneNotSupportedException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}

		if (!CollectionUtils.isEmpty(newElementValues)) {

			updatedElementValues = elementData.updateElementValues(newElementValues, element);
		}

		return updatedElementValues;
	}

	private List<ElementTypeParam> getElementTypeParamsMeasuresByElement() {

		List<ElementTypeParam> elementTypeParamsByElement = element.getElementType().getElementTypeParams();

		if (!CollectionUtils.isEmpty(elementTypeParamsByElement)) {

			elementTypeParamsByElement = elementTypeParamsByElement.stream()
					.filter(e -> e.getParamTypeId().equals(Long.valueOf(ParamType.MEASURE)))
					.collect(Collectors.toList());

			if (!CollectionUtils.isEmpty(elementTypeParamsByElement) && element.getElementSubtypeId() != null
					&& element.getElementTypeId() != null) {

				List<ElementSubtypeActiveParam> elementSubTypeActiveParams = element.getElementSubtype()
						.getElementSubtypeActiveParams();

				if (!CollectionUtils.isEmpty(elementSubTypeActiveParams)) {

					elementTypeParamsByElement = elementTypeParamsByElement.stream()
							.filter(e -> elementSubTypeActiveParams.stream()
									.filter(f -> f.getParamTypeId().equals(e.getParamTypeId())
											&& f.getId().equals(e.getId()))
									.findFirst().orElse(null) != null
									&& (e.getDurable() == null || !e.getDurable().booleanValue()))
							.collect(Collectors.toList());
				}
			}
		}

		return elementTypeParamsByElement;
	}

	private Pair<Integer, Integer> updateAlarmsToStateChanges(Long newElementStateId) {

		List<ElementTypeState> elementTypeStates = this.element.getElementType().getElementTypeStates();
		Pair<Integer, Integer> pairAlarmsCreatedAndDeleted = null;

		if (!CollectionUtils.isEmpty(elementTypeStates)) {

			List<ElementTypeState> elementTypeStatesFiltered = elementTypeStates.stream()
					.filter(e -> !CollectionUtils.isEmpty(e.getElementStateAlarms())).collect(Collectors.toList());

			// Element type has states that generate alarms
			if (!CollectionUtils.isEmpty(elementTypeStatesFiltered)) {

				Set<AlarmConfig> alarmConfigsToCreate = new HashSet<>();
				Set<AlarmConfig> alarmConfigsToRemove = new HashSet<>();

				for (ElementTypeState elementTypeState : elementTypeStatesFiltered) {

					List<ElementStateAlarm> elementStateAlarms = elementTypeState.getElementStateAlarms();

					if (!CollectionUtils.isEmpty(elementStateAlarms)) {

						List<AlarmConfig> alarmConfigs = elementStateAlarms.stream()
								.filter(e -> e.getAlarmConfig() != null).map(ElementStateAlarm::getAlarmConfig)
								.collect(Collectors.toList());

						if (!CollectionUtils.isEmpty(alarmConfigs)) {

							// Alarms to create
							if (elementTypeState.getStateId().equals(newElementStateId)) {

								alarmConfigsToCreate.addAll(alarmConfigs);
							}
							// Alarms to remove
							else {

								alarmConfigsToRemove.addAll(alarmConfigs);
							}
						}
					}
				}

				List<Alarm> alarmsCreated = createAlarmsFromStateChange(alarmConfigsToCreate);
				List<Alarm> alarmsDeleted = deleteAlarmsFromStateChange(alarmConfigsToRemove);

				pairAlarmsCreatedAndDeleted = new ImmutablePair<>(alarmsCreated.size(), alarmsDeleted.size());
			}
		}

		return pairAlarmsCreatedAndDeleted;
	}

	private List<Alarm> createAlarmsFromStateChange(Set<AlarmConfig> alarmConfigs) {

		List<Alarm> alarmsCreated = new ArrayList<>();

		if (!CollectionUtils.isEmpty(alarmConfigs)) {

			for (AlarmConfig alarmConfig : alarmConfigs) {
				
				Alarm alarmToUpdated = EntitiesManager.getInstance().removeFromMinTimeMap(alarmConfig, element);

				if (alarmToUpdated != null) {

					alarmToUpdated = elementData.updateAlarm(alarmToUpdated);

					log.debug(String.format("%s => Update Alarm(%d) AlarmConfig %d, %s",
							(new Key(element)).getSimpleValue(), alarmToUpdated.getId(),
							alarmToUpdated.getAlarmConfig().getId(), alarmToUpdated.getAlarmConfig().getAlias()));

				} else {
				
					Alarm alarmCreated = elementData.createAlarm(alarmConfig, element);

					if (alarmCreated != null) {

						log.debug(String.format("%s => Register Alarm(%d) Recognized: %s AlarmConfig %d, %s",
								(new Key(element)).getSimpleValue(), alarmCreated.getId(),
								alarmCreated.getRecognized() != null, alarmCreated.getAlarmConfig().getId(),
								alarmCreated.getAlarmConfig().getAlias()));

						alarmsCreated.add(alarmCreated);
					}
				}
			}
		}

		return alarmsCreated;
	}

	private List<Alarm> deleteAlarmsFromStateChange(Set<AlarmConfig> alarmConfigs) {

		List<Alarm> currentAlarms = element.getAlarms();
		List<Alarm> alarmsDeleted = new ArrayList<>();

		if (!CollectionUtils.isEmpty(alarmConfigs) && !CollectionUtils.isEmpty(currentAlarms)) {

			for (AlarmConfig alarmConfig : alarmConfigs) {

				Alarm currentAlarm = currentAlarms.stream().filter(e -> e.getAlarmConfigId().equals(alarmConfig.getId())
						&& e.getActivation() != null && e.getDeactivation() == null).findFirst().orElse(null);

				if (currentAlarm != null && !EntitiesManager.getInstance().addInMinTimeMap(currentAlarm)) {

					Alarm alarmDeleted = elementData.deleteAlarm(currentAlarm);

					if (alarmDeleted != null) {

						log.debug(String.format("%s => Unregister Alarm(%d) Recognized: %s AlarmConfig %d, %s",
								(new Key(element)).getSimpleValue(), alarmDeleted.getId(),
								alarmDeleted.getRecognized() != null, alarmDeleted.getAlarmConfig().getId(),
								alarmDeleted.getAlarmConfig().getAlias()));

						alarmsDeleted.add(alarmDeleted);
					}
				}
			}
		}

		return alarmsDeleted;
	}
}