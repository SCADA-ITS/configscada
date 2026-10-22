package com.revenga.rits.back.equipment.manager;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Test;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.command.SetElementStatesChangeCommand;
import com.revenga.rits.back.equipment.manager.data.RootData;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DisconnectSetElementStatesChangeCommandTest {

	private EntitiesManager em;
	
	@Test
	public void main() {
		
		log.debug("[INI] " + this.getClass().getSimpleName());	
		this.em = EntitiesManager.getInstance();
		
		RootData rootData = this.em.getRootData();
		
		Element element = rootData.getElement(9L, 1L);
		
		ElementState elementState = element.getElementState();
		
		elementState.setStateId(element.getElementType().getDisconnectedElementTypeStateId());
		
		sendState(elementState);
		
		log.debug("[END] " + this.getClass().getSimpleName());
	}
	
	private void sendState(ElementState elementState) {
		
		try {
			
			SetElementStatesChangeCommand setElementStatesChangeCommand = new SetElementStatesChangeCommand();
			
			setElementStatesChangeCommand.addElementState(elementState);
			
			EntitiesManager.getInstance().sendCommand(setElementStatesChangeCommand);
		
		} catch (Exception e) {
			
			log.error((new Key(elementState)).getSimpleValue() + " => " + e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
}
