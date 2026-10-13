package com.revenga.rits.back.equipment.manager.advisor.model;

import java.util.List;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.Element;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EquipmentChangeAlarmMessage extends EquipmentMessage {

	List<Alarm> alarms;
	
	public EquipmentChangeAlarmMessage(List<Alarm> alarms) {
		
		super(null);
		this.alarms = alarms;
	}
	
	public EquipmentChangeAlarmMessage(Element element, List<Alarm> alarms) {
		super(element);
		this.alarms = alarms;
	}
}
