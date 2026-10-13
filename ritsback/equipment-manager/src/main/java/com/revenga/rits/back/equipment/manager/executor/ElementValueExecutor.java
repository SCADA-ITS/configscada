package com.revenga.rits.back.equipment.manager.executor;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.SetElementMeasuresChangeCommand;
import com.revenga.rits.back.equipment.manager.executor.service.ElementValueService;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementValueExecutor extends GenericExecutor {
	
	ElementValueService elementValueService;
	
	public ElementValueExecutor(Executor executor, short thread) {

		super(executor, thread);
		
		elementValueService = new ElementValueService(this);
	}

	@Override
	protected Object prepare(Object queueItem) {
		
		try {
			
			if (queueItem instanceof SetElementMeasuresChangeCommand) {
				
				SetElementMeasuresChangeCommand setElementMeasuresChangeCommand = (SetElementMeasuresChangeCommand) queueItem;
				
				if (!CollectionUtils.isEmpty(setElementMeasuresChangeCommand.getElements())) {
					
					for (Element element : setElementMeasuresChangeCommand.getElements()) {
				
						Key keyElement = new Key(element);
						
						EntitiesManager.getInstance().getRootData().lock(element.getElementTypeId(), element.getId());
						
						try {
							
							int changedValues = elementValueService.setChangeMeasure(element);
							
							if (changedValues > 0) {
							
								log.debug(keyElement.getSimpleValue() + " => " + changedValues + " changed values");
							}
							
						} catch (Exception e) {
							
							log.error(e.getMessage());
							log.debug(ExceptionUtils.getStackTrace(e));
						}
						
						EntitiesManager.getInstance().getRootData().unlock(element.getElementTypeId(), element.getId());
					}
				}
			}

		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return null;
	}

	@Override
	protected Object publish() {
			
		return null;
	}

	@Override
	protected void initialize() {
			
		log.debug(this.getClass().getSimpleName() + "[" + threadNum + "] now is running");
	}
	
	@Override
	protected void end() {
		
		// Without implementation
	}

	@Override
	protected void evaluate() {
		
		// Without implementation
	}
}
