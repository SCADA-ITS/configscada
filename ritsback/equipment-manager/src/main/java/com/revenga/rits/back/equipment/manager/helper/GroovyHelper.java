package com.revenga.rits.back.equipment.manager.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetElementValuesPublishCommand;
import com.revenga.rits.back.entities.lib.repository.ElementHierarchiesRepository;
import com.revenga.rits.back.equipment.manager.data.ThreadDataWriter;
import com.revenga.rits.back.equipment.manager.data.ThreadDataWriter.Action;
import com.revenga.rits.back.equipment.manager.executor.coordinator.ElementStateCoordinator;
import com.revenga.rits.back.equipment.manager.executor.service.ElementCommandService;
import com.revenga.rits.back.equipment.manager.executor.service.ElementValueService;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class GroovyHelper {

	private GroovyHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static Command sendElementToHistoriclManager(Element element) {

		return EntitiesManager.getInstance().sendElementToHistoriclManager(element.cloneWithValues());
	}

	public static AlarmConfig getAlarmConfig(Long alarmConfigId) {

		return EntitiesManager.getInstance().getAlarmConfig(alarmConfigId);
	}

	public static Element getElement(Long elementTypeId, Long elementId) {

		return EntitiesManager.getInstance().getElement(elementTypeId, elementId);
	}

	public static void setChangeMeasure(Element element) {

		ElementValueService elementValueService = new ElementValueService(null);
		elementValueService.setChangeMeasure(element);
	}

	public static void setChangeMeasureLock(Element element) {

		EntitiesManager.getInstance().getRootData().lock(element.getElementTypeId(), element.getId());

		ElementValueService elementValueService = new ElementValueService(null);
		elementValueService.setChangeMeasure(element);

		EntitiesManager.getInstance().getRootData().unlock(element.getElementTypeId(), element.getId());
	}

	public static void setChangeState(Element element, ElementState elementState) {

		ElementStateCoordinator elementStateCoordinator = new ElementStateCoordinator(element);

		elementStateCoordinator.update(elementState);
	}

	public static void setChangeStateLock(Element element, ElementState elementState) {

		EntitiesManager.getInstance().getRootData().lock(element.getElementTypeId(), element.getId());

		ElementStateCoordinator elementStateCoordinator = new ElementStateCoordinator(element);

		elementStateCoordinator.update(elementState);

		EntitiesManager.getInstance().getRootData().unlock(element.getElementTypeId(), element.getId());
	}

	public static void activateAlarms(SetActivationAlarmsCommand command) {

		(new ElementCommandService(null)).setActivationAlarmsCommand(command);
	}

	public static void deactivateAlarms(SetDeactivationAlarmsCommand command) {

		(new ElementCommandService(null)).setDeactivationAlarmsCommand(command);
	}

	public static void sendCommand(Command command) {

		if (command instanceof SetElementValuesPublishCommand) {

			SetElementValuesPublishCommand setElementValuesPublishCommand = (SetElementValuesPublishCommand) command;

			if (CollectionUtils.isNotEmpty(setElementValuesPublishCommand.getElementValues())) {

				Set<ElementValue> elementValuesClone = new HashSet<>();

				setElementValuesPublishCommand.getElementValues().forEach(ev -> {

					try {

						elementValuesClone.add((ElementValue) ev.clone());

					} catch (CloneNotSupportedException e) {

						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				});

				setElementValuesPublishCommand.setElementValues(elementValuesClone);
				EntitiesManager.getInstance().sendSetElementValuesPublishCommand(setElementValuesPublishCommand);
			}

		} else {

			EntitiesManager.getInstance().sendCommand(command);
		}
	}

	public static List<ElementHierarchy> getsByParent(Long parentElementTypeId, Long parentElementId,
			Boolean fillObject) {

		List<BasicElement> basicElements = ((ElementHierarchiesRepository) EntitiesManager.getInstance()
				.getRepositoryData().getRepository(ElementHierarchy.class))
				.getsFromCache(new String[] { String.valueOf(parentElementTypeId), String.valueOf(parentElementId),
						null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementHierarchy.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public static boolean putElementValues(ElementValue[] elementValues) {

		boolean res = false;

		try {

			if (!ArrayUtils.isEmpty(elementValues)) {

				ThreadDataWriter threadDataWriter = (ThreadDataWriter) EntitiesManager.getInstance().getRootData()
						.getThread(ThreadDataWriter.class);

				if (threadDataWriter != null) {

					Arrays.asList(elementValues)
							.forEach(elementValue -> threadDataWriter.add(elementValue, Action.PUT));
				}
			}

			res = true;

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return res;
	}

	public static List<ElementHierarchy> getsByChild(Long childElementTypeId, Long childElementId) {

		return ((ElementHierarchiesRepository) EntitiesManager.getInstance().getRepositoryData()
				.getRepository(ElementHierarchy.class)).getsByChild(childElementTypeId, childElementId, false);
	}

	public static Command sendElementStateToIllumination(ElementState elementState) {

		try {

			return EntitiesManager.getInstance().sendElementStateToIllumination((ElementState) elementState.clone());

		} catch (CloneNotSupportedException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		return null;
	}

	public static Command sendElementStateToVentilation(ElementState elementState) {

		try {

			return EntitiesManager.getInstance().sendElementStateToVentilation((ElementState) elementState.clone());

		} catch (CloneNotSupportedException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

		}
		return null;
	}

	public static Command sendElementValuesToIllumination(List<ElementValue> elementValues) {

		List<ElementValue> elementValuesClone = new ArrayList<>();

		if (!CollectionUtils.isEmpty(elementValues)) {
			elementValues.forEach(ev -> {

				try {

					elementValuesClone.add((ElementValue) ev.clone());

				} catch (CloneNotSupportedException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			});
		}

		return EntitiesManager.getInstance().sendElementValuesToIllumination(elementValuesClone);
	}

	public static Command sendElementValuesToVentilation(List<ElementValue> elementValues) {

		List<ElementValue> elementValuesClone = new ArrayList<>();

		if (!CollectionUtils.isEmpty(elementValues)) {
			elementValues.forEach(ev -> {

				try {

					elementValuesClone.add((ElementValue) ev.clone());

				} catch (CloneNotSupportedException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			});
		}
		return EntitiesManager.getInstance().sendElementValuesToVentilation(elementValuesClone);
	}

	public static Command sendAlarmToIllumination(Alarm alarm) {
		try {
			return EntitiesManager.getInstance().sendAlarmToIllumination((Alarm) alarm.clone());

		} catch (CloneNotSupportedException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

		}
		return null;
	}

	public static Command sendAlarmToVentilation(Alarm alarm) {

		try {
			return EntitiesManager.getInstance().sendAlarmToVentilation((Alarm) alarm.clone());

		} catch (CloneNotSupportedException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

		}
		return null;
	}
}
