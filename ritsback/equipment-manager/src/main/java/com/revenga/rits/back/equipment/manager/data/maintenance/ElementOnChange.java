package com.revenga.rits.back.equipment.manager.data.maintenance;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.RequestCommand;
import com.revenga.rits.back.entities.lib.repository.entity.EntityOnChange;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementOnChange extends EntityOnChange {

	public ElementOnChange() {
		
		super(Element.class);
	}

	@Override
	protected void create() {
		
		if (basicElement instanceof Element) {
			
			Element element = (Element) basicElement;
			
			EntitiesManager.getInstance().getRootData().loadElement(element);
			
			//Request for the real current state
			initialRequest();
		}
	}

	@Override
	protected void update() {
		
		if (basicElement instanceof Element) {
			
			Element element = (Element) basicElement;
			
			EntitiesManager.getInstance().getRootData().updateElement(element);
			
			//Request for the real current state
			initialRequest();
		}
	}

	@Override
	protected void delete() {
		
		if (basicElement instanceof Element) {
			
			EntitiesManager.getInstance().getRootData().deleteElement((Element) basicElement);
		}
	}
	
	private void initialRequest() {
		
		Element element = (Element) basicElement;
		
		new Thread("ElementOnChangeInitialRequest_" + (new Key(element)).getSimpleValue()) {
			
			@Override
			public void run() {
				
				try {
					
					Thread.sleep(5000);
					
					RequestCommand requestCommandState = new RequestCommand();
					
					requestCommandState.add((Element) basicElement, RequestCommand.REQ_STATE);
					
					EntitiesManager.getInstance().sendCommand(requestCommandState);
					
				} catch (InterruptedException e) {
					
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
					Thread.currentThread().interrupt();
				}
			}
		}.start();
	}
}
