package com.revenga.rits.back.data.core.model.command;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetActivationAlarmsCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7396717887513928815L;
	
	private Map<String, Set<Long>> mapElementAlarmConfigs;
	
	private String activationComments;
	
	public void add(Element element, AlarmConfig alarmConfig) {
	
		if (mapElementAlarmConfigs == null) {
			
			mapElementAlarmConfigs = new HashMap<>();
		}
		
		if (element != null && alarmConfig != null) {
			
			String elementStr = element.getElementTypeId() + ":" + element.getId();
			
			Set<Long> alIds;
			
			if (!mapElementAlarmConfigs.containsKey(elementStr)) {
				
				alIds = new HashSet<>();
				
			}
			else {
			
				alIds = mapElementAlarmConfigs.get(elementStr);
				
				if (alIds == null) {
					
					alIds = new HashSet<>();
				}
			}
			
			alIds.add(alarmConfig.getId());
			
			mapElementAlarmConfigs.put(elementStr, alIds);
		}
	}
	
	
	public SetActivationAlarmsCommand() {

		super(Command.SET_ACTIVATION_ALARMS);
	}
}
