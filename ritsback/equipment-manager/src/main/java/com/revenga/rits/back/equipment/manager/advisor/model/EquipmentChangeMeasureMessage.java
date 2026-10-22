package com.revenga.rits.back.equipment.manager.advisor.model;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EquipmentChangeMeasureMessage extends EquipmentMessage {

	List<Pair<ElementValue, ElementValue>> elementValues;
	
	public EquipmentChangeMeasureMessage(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {
		super(element);
		this.elementValues = elementValues;
	}	
}
