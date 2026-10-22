package com.revenga.rits.back.equipment.manager.advisor.model;

import com.revenga.rits.back.data.core.model.Alarm;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EquipmentCreateAlarmMessage extends EquipmentMessage {

	Alarm alarm;
	
	public EquipmentCreateAlarmMessage(Alarm alarm) {
		
		super(null);
		this.alarm = alarm;
	}
	
}
