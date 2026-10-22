package com.revenga.rits.back.equipment.manager.advisor.model;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EquipmentChangeStateMessage extends EquipmentMessage {

	ElementState elementState;
	
	public EquipmentChangeStateMessage(Element element, ElementState elementState) {
		super(element);
		this.elementState = elementState;
	}	
}
