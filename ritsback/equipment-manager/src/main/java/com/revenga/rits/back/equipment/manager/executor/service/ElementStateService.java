package com.revenga.rits.back.equipment.manager.executor.service;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.command.SetElementStatesChangeCommand;
import com.revenga.rits.back.equipment.manager.executor.coordinator.ElementStateCoordinator;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementStateService {

	GenericExecutor genericExecutor;
	
	public ElementStateService(GenericExecutor genericExecutor) {
		super();

		this.genericExecutor = genericExecutor;
	}
	
	public void setElementStatesChangeCommand(SetElementStatesChangeCommand setElementStatesChangeCommand) {

		try {

			if (setElementStatesChangeCommand  == null) {
				
				throw new ExecutorServiceException("setElementStatesChangeCommand is null");
			}
			
			if (CollectionUtils.isEmpty(setElementStatesChangeCommand.getElementStates())) {
				
				throw new ExecutorServiceException("setElementStatesChangeCommand.getElementStates() is empty");
			}

			log.debug("setElementStatesChangeCommand -> init");

			Instant initInstant = Instant.now();

			for (ElementState newElementState : setElementStatesChangeCommand.getElementStates()) {

				Element element = EntitiesManager.getInstance().getRootData()
						.getElement(newElementState.getElementTypeId(), newElementState.getElementId());

				if (element != null) {
					
					EntitiesManager.getInstance().getRootData().lock(element.getElementTypeId(), element.getId());

					try {
						
						if (element.getLastUpdatedState() < setElementStatesChangeCommand.getCreatedAt()) {
							
							element.setLastUpdatedState(setElementStatesChangeCommand.getCreatedAt());
							
							ElementStateCoordinator elementStateCoordinator = new ElementStateCoordinator(element);

							elementStateCoordinator.update(newElementState);
						}
						else {
							
							log.warn(String.format("%s => New state %d is outdated %d ms. Current state is %d",
									(new Key(element)).getSimpleValue(), 
									newElementState.getStateId(),
									element.getLastUpdatedState() - setElementStatesChangeCommand.getCreatedAt(),
									element.getElementState() != null ? element.getElementState().getStateId() : -1));
						}					
						
					} catch (Exception e) {
						
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
					
					EntitiesManager.getInstance().getRootData().unlock(element.getElementTypeId(), element.getId());
				}
			}

			log.debug("setElementStatesChangeCommand -> Process "
					+ setElementStatesChangeCommand.getElementStates().size() + " states in "
					+ Duration.between(initInstant, Instant.now()).toMillis() + " ms.");

		} catch (ExecutorServiceException e) {

			log.warn(e.getMessage());
			
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

	}
}