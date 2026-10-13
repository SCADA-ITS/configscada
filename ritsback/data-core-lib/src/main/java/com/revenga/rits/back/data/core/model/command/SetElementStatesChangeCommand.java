package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.ElementState;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetElementStatesChangeCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7363997357041615507L;
	
	private List<ElementState> elementStates;
	
	public void addElementState(ElementState elementState) {
		
		if (elementState != null) {
		
			if (elementStates == null) {
				
				elementStates = new ArrayList<>();
			}
			
			elementStates.add(elementState);
		}
	}
	
	public SetElementStatesChangeCommand() {

		super(Command.SET_ELEMENT_STATES_CHANGE);
	}
}
