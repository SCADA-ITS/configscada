package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetDeactivationAlarmsCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -823853632348337710L;
	
	private List<Long> alarmIds;
	private Map<String, Set<Long>> mapElementAlarmConfigs;
	
	private String deactivationComments;
	
	/*
	 * userId that deactivate the alarm.
	 */
	private Long deactivationUserId;
	
	
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
	
	public void add(Alarm alarm) {
		
		if (alarmIds == null) {
			
			alarmIds = new ArrayList<>();
		}
		
		if (alarm != null) {
		
			alarmIds.add(alarm.getId());
		}
	}
	
	public SetDeactivationAlarmsCommand() {

		super(Command.SET_DEACTIVATION_ALARMS);
	}
}
