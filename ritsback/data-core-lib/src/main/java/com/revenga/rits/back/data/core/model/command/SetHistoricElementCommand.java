package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.Element;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetHistoricElementCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4473447244473207054L;
	
	private List<Element> elements;
	
	public void addElement(Element element) {
		
		if (element != null) {
		
			if (elements == null) {
				
				elements = new ArrayList<>();
			}
			
			elements.add(element);
		}
	}
	
	public SetHistoricElementCommand() {

		super(Command.SET_HISTORIC_ELEMENT);
	}
}
