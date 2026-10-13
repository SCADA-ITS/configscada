package com.revenga.rits.back.equipment.manager.executor.service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.AlarmFiltered;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetAlarmsFilteredCommand;
import com.revenga.rits.back.data.core.model.command.SetAlarmsFilteredCommand.Action;
import com.revenga.rits.back.data.core.model.command.SetAlarmsFilteredCommand.AlarmFilteredAction;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetRecognizeAlarmsCommand;
import com.revenga.rits.back.equipment.manager.executor.coordinator.AlarmActivationCoordinator;
import com.revenga.rits.back.equipment.manager.executor.coordinator.AlarmDeactivationCoordinator;
import com.revenga.rits.back.equipment.manager.executor.coordinator.AlarmFilterCoordinator;
import com.revenga.rits.back.equipment.manager.executor.coordinator.AlarmRecognitionCoordinator;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementCommandService {

	GenericExecutor genericExecutor;

	public ElementCommandService(GenericExecutor genericExecutor) {
		super();

		this.genericExecutor = genericExecutor;
	}

	public void setActivationAlarmsCommand(SetActivationAlarmsCommand setActivationAlarmsCommand) {

		try {

			Instant initInstant = Instant.now();

			int items = 0;

			if (!MapUtils.isEmpty(setActivationAlarmsCommand.getMapElementAlarmConfigs())) {

				items = setActivationAlarmsCommand.getMapElementAlarmConfigs().size();

				log.debug("setActivationAlarmsCommand. processing " + items + " items");

				setActivationAlarmsByAlarmConfigIds(setActivationAlarmsCommand);
			}

			log.debug("setActivationAlarmsCommand -> Processed " + items + " items in "
					+ Duration.between(initInstant, Instant.now()).toMillis() + " ms.");

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public void setRecognizeAlarmsCommand(SetRecognizeAlarmsCommand setRecognizeAlarmsCommand) {

		try {

			Instant initInstant = Instant.now();

			int items = 0;

			if (!CollectionUtils.isEmpty(setRecognizeAlarmsCommand.getAlarmIds())) {

				items = setRecognizeAlarmsCommand.getAlarmIds().size();

				log.debug("setRecognizeAlarmsCommand. processing " + items + " items");

				setRecognizeAlarmsByAlarmIds(setRecognizeAlarmsCommand);
			}

			log.debug("setRecognizeAlarmsCommand -> Processed " + items + " items in "
					+ Duration.between(initInstant, Instant.now()).toMillis() + " ms.");

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public void setDeactivationAlarmsCommand(SetDeactivationAlarmsCommand setDeactivationAlarmsCommand) {

		try {

			Instant initInstant = Instant.now();

			List<Long> alarmIds = setDeactivationAlarmsCommand.getAlarmIds();

			int items = 0;

			if (!CollectionUtils.isEmpty(alarmIds)) {

				items = alarmIds.size();

				log.debug("setDeactivationAlarmsCommand. processing " + items + " items");
				setDeactivationAlarmsByAlarmsIds(setDeactivationAlarmsCommand);
			} else if (!MapUtils.isEmpty(setDeactivationAlarmsCommand.getMapElementAlarmConfigs())) {

				items = setDeactivationAlarmsCommand.getMapElementAlarmConfigs().size();

				log.debug("setDeactivationAlarmsCommand. processing " + items + " items");
				setDeactivationAlarmsByAlarmConfigIds(setDeactivationAlarmsCommand);
			}

			log.debug("setDeactivationAlarmsCommand -> Processed " + items + " items in "
					+ Duration.between(initInstant, Instant.now()).toMillis() + " ms.");

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public void setAlarmsFilteredCommand(SetAlarmsFilteredCommand setAlarmsFilteredCommand) {

		if (!CollectionUtils.isEmpty(setAlarmsFilteredCommand.getAlarmFilteredActions())) {

			List<AlarmFilteredAction> alarmFilteredActions = setAlarmsFilteredCommand.getAlarmFilteredActions();

			for (AlarmFilteredAction alarmFilteredAction : alarmFilteredActions) {

				AlarmFiltered alarmFiltered = alarmFilteredAction.getAlarmFiltered();
				Action action = alarmFilteredAction.getAction();

				if (alarmFiltered != null && action != null) {
					
					Element element = EntitiesManager.getInstance().getRootData()
							.getElement(alarmFiltered.getElementTypeId(), alarmFiltered.getElementId());
					
					if (element != null) {
						
						EntitiesManager.getInstance().getRootData().lock(element.getElementTypeId(), element.getId());
						
						try {
							
							alarmFiltered.setElement(element);
							
							List<AlarmConfig> alarmConfigs = element.getElementType().getAlarmConfigs();
							
							if (!CollectionUtils.isEmpty(alarmConfigs)) {
								
								AlarmConfig alarmConfig = alarmConfigs.stream()
										.filter(e -> e.getId().equals(alarmFiltered.getAlarmConfigId())).findFirst()
										.orElse(null);
								
								if (alarmConfig != null) {
									
									alarmFiltered.setAlarmConfig(alarmConfig);
									
									AlarmFilterCoordinator alarmFilterCoordinator = new AlarmFilterCoordinator(element);
									
									alarmFilterCoordinator.update(
											new ImmutablePair<>(alarmFiltered, action == Action.FILTER_ON));
								}
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

	private void setRecognizeAlarmsByAlarmIds(SetRecognizeAlarmsCommand setRecognizeAlarmsCommand) {

		List<Alarm> currentAlarms = EntitiesManager.getInstance().getRootData().getAlarms();
		
		if (!CollectionUtils.isEmpty(currentAlarms)) {
			
			List<Long> alarmIds = setRecognizeAlarmsCommand.getAlarmIds();

			if (!CollectionUtils.isEmpty(alarmIds)) {
				
				List<Alarm> alarms = currentAlarms.stream()
						.filter(currentAlarm -> (alarmIds.stream()
								.filter(alarmId -> currentAlarm.getId().equals(alarmId)).findFirst()
								.orElse(null)) != null)
						.collect(Collectors.toList());
				
				if (!CollectionUtils.isEmpty(alarms)) {
					
					for (Alarm alarm : alarms) {
					
						Element element = alarm.getTargetElement();
						
						if (element != null) {
							
							EntitiesManager.getInstance().getRootData().lock(element.getElementTypeId(), element.getId());
						
							try {
								
								AlarmRecognitionCoordinator alarmRecognitionCoordinator = new AlarmRecognitionCoordinator(alarm.getTargetElement());
								
								Map<Long, Object> mapProps = new HashMap<>();
								
								mapProps.put(AlarmRecognitionCoordinator.PROP_ALARM, alarm);
								mapProps.put(AlarmRecognitionCoordinator.PROP_USER_ID, setRecognizeAlarmsCommand.getRecognizedUserId());
								mapProps.put(AlarmRecognitionCoordinator.PROP_USER_COMMENT, setRecognizeAlarmsCommand.getRecognizedComments());
								mapProps.put(AlarmRecognitionCoordinator.PROP_DISAGREE, setRecognizeAlarmsCommand.getDisagree());
								alarmRecognitionCoordinator.update(mapProps);
								
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
	}
	
	private void setActivationAlarmsByAlarmConfigIds(SetActivationAlarmsCommand setActivationAlarmsCommand) {

		Map<String, Set<Long>> mapElementAlarmConfigs = null;

		mapElementAlarmConfigs = setActivationAlarmsCommand.getMapElementAlarmConfigs();

		if (!MapUtils.isEmpty(mapElementAlarmConfigs)) {

			int entryItem = 0;

			for (Map.Entry<String, Set<Long>> entry : mapElementAlarmConfigs.entrySet()) {

				Element element;
				String[] elementStrArr = entry.getKey().split(":");

				if (elementStrArr.length == 2) {

					element = EntitiesManager.getInstance().getRootData().getElement(Long.parseLong(elementStrArr[0]),
							Long.parseLong(elementStrArr[1]));
					
					if (element != null) {
						
						EntitiesManager.getInstance().getRootData().lock(element.getElementTypeId(), element.getId());
						
						try {
							
							log.debug("setActivationAlarmsByAlarmConfigIds [" + (new Key(element)).getSimpleValue() + "] ("
									+ ++entryItem + "/" + mapElementAlarmConfigs.size() + ")");
							
							setActivationAlarmsElement(element, entry.getValue());
							
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
	
	private void setActivationAlarmsElement(Element element, Set<Long> alarmConfigIds) {
		
		try {
			
			if (element != null && !CollectionUtils.isEmpty(alarmConfigIds)) {
			
				Key elementKey = new Key(element);

				if (element.getElementState().getStateId()
						.equals(element.getElementType().getInitialElementTypeStateId())) {

					throw new ExecutorServiceException(elementKey.getSimpleValue()
							+ " discarting activation alarms in initialized element state. Current state = "
							+ element.getElementState().getStateId());
				}

				if (element.getElementState().getStateId()
						.equals(element.getElementType().getDisconnectedElementTypeStateId())) {

					throw new ExecutorServiceException(elementKey.getSimpleValue()
							+ " discarting activation alarms in disconnected element state. Current state = "
							+ element.getElementState().getStateId());
				}

				AlarmActivationCoordinator alarmActivationCoordinator = new AlarmActivationCoordinator(element);
				
				if (!CollectionUtils.isEmpty(element.getElementType().getAlarmConfigs())) {
				
					List<AlarmConfig> alarmConfigs = element.getElementType().getAlarmConfigs().stream()
							.filter(alarmConfig -> (alarmConfigIds.stream()
									.filter(e -> alarmConfig.getId().equals(e)).findFirst().orElse(null)) != null)
							.collect(Collectors.toList());
					
					if (!CollectionUtils.isEmpty(alarmConfigs)) {
						
						alarmActivationCoordinator.update(alarmConfigs);
					}
				}
			}
			
		} catch (ExecutorServiceException e) {

			log.warn(e.getMessage());
			
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void setDeactivationAlarmsByAlarmConfigIds(SetDeactivationAlarmsCommand setDeactivationAlarmsCommand) {

		Map<String, Set<Long>> mapElementAlarmConfigs = null;

		mapElementAlarmConfigs = setDeactivationAlarmsCommand.getMapElementAlarmConfigs();

		if (!MapUtils.isEmpty(mapElementAlarmConfigs)) {

			int entryItem = 0;

			for (Map.Entry<String, Set<Long>> entry : mapElementAlarmConfigs.entrySet()) {

				Element element;
				String[] elementStrArr = entry.getKey().split(":");

				if (elementStrArr.length == 2) {

					element = EntitiesManager.getInstance().getRootData().getElement(Long.parseLong(elementStrArr[0]),
							Long.parseLong(elementStrArr[1]));

					if (element != null) {
						
						EntitiesManager.getInstance().getRootData().lock(element.getElementTypeId(), element.getId());

						try {
							
							log.debug("setDeactivationAlarmsByAlarmConfigIds [" + (new Key(element)).getSimpleValue() + "] ("
									+ ++entryItem + "/" + mapElementAlarmConfigs.size() + ")");

							setDeactivationAlarmsByAlarmConfigIdsElement(setDeactivationAlarmsCommand, element, entry.getValue());
							
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
	
	private void setDeactivationAlarmsByAlarmConfigIdsElement(SetDeactivationAlarmsCommand setDeactivationAlarmsCommand, Element element, Set<Long> alarmConfigIds) {
		
		try {
			
			if (setDeactivationAlarmsCommand != null && element != null && !CollectionUtils.isEmpty(alarmConfigIds)) {
				
				Key elementKey = new Key(element);

				if (element.getElementState().getStateId()
						.equals(element.getElementType().getInitialElementTypeStateId())) {

					throw new ExecutorServiceException(elementKey.getSimpleValue()
							+ " discarting deactivation alarms in initialized element state. Current state = "
							+ element.getElementState().getStateId());
				}

				if (element.getElementState().getStateId()
						.equals(element.getElementType().getDisconnectedElementTypeStateId())) {

					throw new ExecutorServiceException(elementKey.getSimpleValue()
							+ " discarting deactivation alarms in disconnected element state. Current state = "
							+ element.getElementState().getStateId());
				}
				
				Map<Long, Object> mapProps = new HashMap<>();
				
				AlarmDeactivationCoordinator alarmDeactivationCoordinator = new AlarmDeactivationCoordinator(element);
				
				for (Long alarmConfigId : alarmConfigIds) {
					
					List<AlarmConfig> alarmConfigs = element.getElementType().getAlarmConfigs();
					
					if (!CollectionUtils.isEmpty(alarmConfigs)) {
						
						AlarmConfig alarmConfig = alarmConfigs.stream().filter(e -> e.getId().equals(alarmConfigId)).findFirst().orElse(null);
						
						if (alarmConfig != null) {
							
							mapProps.put(AlarmDeactivationCoordinator.PROP_ALARM_CONFIG, alarmConfig);
							mapProps.put(AlarmDeactivationCoordinator.PROP_USER_ID, setDeactivationAlarmsCommand.getUserid());
							mapProps.put(AlarmDeactivationCoordinator.PROP_USER_COMMENT, setDeactivationAlarmsCommand.getDeactivationComments());
							
							if (!MapUtils.isEmpty(mapProps)) {
								
								alarmDeactivationCoordinator.update(mapProps);
							}
						}
					}
				}
			}
		
		} catch (ExecutorServiceException e) {

			log.warn(e.getMessage());
			
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	private void setDeactivationAlarmsByAlarmsIds(SetDeactivationAlarmsCommand setDeactivationAlarmsCommand) {

		List<Alarm> currentAlarms = EntitiesManager.getInstance().getRootData().getAlarms();
		
		if (!CollectionUtils.isEmpty(currentAlarms)) {
			
			List<Long> alarmIds = setDeactivationAlarmsCommand.getAlarmIds();

			if (!CollectionUtils.isEmpty(alarmIds)) {
				
				List<Alarm> alarms = currentAlarms.stream()
						.filter(currentAlarm -> (alarmIds.stream()
								.filter(alarmId -> currentAlarm.getId().equals(alarmId)).findFirst()
								.orElse(null)) != null)
						.collect(Collectors.toList());
				
				if (!CollectionUtils.isEmpty(alarms)) {
					
					for (Alarm alarm : alarms) {
					
						Element element = alarm.getTargetElement();
						
						if (element != null) {
							
							EntitiesManager.getInstance().getRootData().lock(element.getElementTypeId(), element.getId());
							
							try {
								
								setDeactivationAlarmsByAlarmsIdsElement(setDeactivationAlarmsCommand, element, alarm);
								
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
	}
	
	private void setDeactivationAlarmsByAlarmsIdsElement(SetDeactivationAlarmsCommand setDeactivationAlarmsCommand, Element element, Alarm alarm) {
		
		try {
			
			if (setDeactivationAlarmsCommand != null && element != null && alarm != null) {
				
				Key elementKey = new Key(element);

				if (element.getElementState().getStateId()
						.equals(element.getElementType().getInitialElementTypeStateId())) {

					throw new ExecutorServiceException(elementKey.getSimpleValue()
							+ " discarting deactivation alarms in initialized element state. Current state = "
							+ element.getElementState().getStateId());
				}

				if (element.getElementState().getStateId()
						.equals(element.getElementType().getDisconnectedElementTypeStateId())) {

					throw new ExecutorServiceException(elementKey.getSimpleValue()
							+ " discarting deactivation alarms in disconnected element state. Current state = "
							+ element.getElementState().getStateId());
				}
				
				AlarmDeactivationCoordinator alarmDeactivationCoordinator = new AlarmDeactivationCoordinator(alarm.getTargetElement());
				
				Map<Long, Object> mapProps = new HashMap<>();
				
				mapProps.put(AlarmDeactivationCoordinator.PROP_ALARM, alarm);
				mapProps.put(AlarmDeactivationCoordinator.PROP_USER_ID, setDeactivationAlarmsCommand.getUserid());
				mapProps.put(AlarmDeactivationCoordinator.PROP_USER_COMMENT, setDeactivationAlarmsCommand.getDeactivationComments());
				
				alarmDeactivationCoordinator.update(mapProps);
			}
		
		} catch (ExecutorServiceException e) {

			log.warn(e.getMessage());
			
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
}