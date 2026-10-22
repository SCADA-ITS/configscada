package com.revenga.rits.back.equipment.manager.executor.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.equipment.manager.executor.coordinator.ElementValueCoordinator;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementValueService {

	GenericExecutor genericExecutor;

	public ElementValueService(GenericExecutor genericExecutor) {
		super();

		this.genericExecutor = genericExecutor;
	}

	public int setChangeMeasure(BasicElement basicElement) {

		int changes = -1;
		Element element = null;
		Key elementKey;
		List<ElementValue> newElementValues = null;
		Instant initInstant = Instant.now();

		try {

			if (basicElement instanceof Element) {

				Element inputElement = (Element) basicElement;

				element = EntitiesManager.getInstance().getRootData().getElement(inputElement.getElementTypeId(),
						inputElement.getId());

				newElementValues = inputElement.getElementValues();
			}

			if (element == null) {

				throw new ExecutorServiceException("Element is null");
			}

			elementKey = new Key(element);

			if (CollectionUtils.isEmpty(element.getElementValues())) {

				throw new ExecutorServiceException(elementKey.getSimpleValue() + " without measures");
			}

			if (CollectionUtils.isEmpty(newElementValues)) {

				throw new ExecutorServiceException(elementKey.getSimpleValue() + " received without measures");
			}

			if (element.getElementState().getStateId()
					.equals(element.getElementType().getInitialElementTypeStateId())) {

				throw new ExecutorServiceException(elementKey.getSimpleValue()
						+ " discarting measures in initialized element state. Current state = "
						+ element.getElementState().getStateId());
			}

			if (element.getElementState().getStateId()
					.equals(element.getElementType().getDisconnectedElementTypeStateId())) {

				throw new ExecutorServiceException(elementKey.getSimpleValue()
						+ " discarting measures in disconnected element state. Current state = "
						+ element.getElementState().getStateId());
			}
			
			if (element.getElementState().getStateId()
					.equals(element.getElementType().getMaintenanceElementTypeStateId())) {

				throw new ExecutorServiceException(elementKey.getSimpleValue()
						+ " discarting measures in maintenance element state. Current state = "
						+ element.getElementState().getStateId());
			}

			ElementValueCoordinator elementMeasureCoordinator = new ElementValueCoordinator(element);

			changes = elementMeasureCoordinator.update(newElementValues);

			log.debug(elementKey.getSimpleValue() + " => Process changed measures in "
					+ Duration.between(initInstant, Instant.now()).toMillis() + " ms.");
			
		} catch (ExecutorServiceException e) {

			log.warn(e.getMessage());
			
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return changes;
	}
}