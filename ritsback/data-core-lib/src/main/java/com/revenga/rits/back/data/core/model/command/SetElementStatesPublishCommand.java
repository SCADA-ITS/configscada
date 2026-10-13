package com.revenga.rits.back.data.core.model.command;

import java.util.HashSet;
import java.util.Set;

import com.revenga.rits.back.data.core.model.ElementState;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetElementStatesPublishCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2280654889459367209L;
	
	private Set<ElementState> elementStates;
	
	public void addElementState(ElementState elementState) {
		
		if (elementState != null) {
		
			if (elementStates == null) {
				
				elementStates = new HashSet<>();
			}
			else {
				
				if (elementStates.contains(elementState)) {
					
					elementStates.remove(elementState);
				}
			}
			
			elementStates.add(elementState);
		}
	}
	
	public SetElementStatesPublishCommand() {

		super(Command.SET_ELEMENT_STATES_PUBLISH);
	}
}
