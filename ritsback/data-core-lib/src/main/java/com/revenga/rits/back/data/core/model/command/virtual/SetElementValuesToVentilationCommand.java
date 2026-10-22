package com.revenga.rits.back.data.core.model.command.virtual;

import java.util.HashSet;
import java.util.Set;

import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.command.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetElementValuesToVentilationCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5124623302805884041L;
	
	private Set<ElementValue> elementValues;
	
	public void addElementValue(ElementValue elementValue) {
		
		if (elementValue != null) {
		
			if (elementValues == null) {
				
				elementValues = new HashSet<>();
			}
			
			elementValues.add(elementValue);
		}
	}
	
	public SetElementValuesToVentilationCommand() {

		super(Command.SET_ELEMENT_VALUES_TO_VENTILATION);
	}
}
