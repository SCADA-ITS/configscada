package com.revenga.rits.back.equipment.manager.advisor.model;

import java.io.Serializable;

import com.revenga.rits.back.data.core.model.Element;

import lombok.Data;

@Data
public abstract class EquipmentMessage implements Serializable {

	private static final long serialVersionUID = 471243593262951793L;
	
	Element element;
	
	public EquipmentMessage(Element element) {
		super();
		this.element = element;
	}
}
