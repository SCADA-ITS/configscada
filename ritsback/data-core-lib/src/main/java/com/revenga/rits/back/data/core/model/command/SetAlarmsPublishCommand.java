package com.revenga.rits.back.data.core.model.command;

import java.util.HashSet;
import java.util.Set;

import com.revenga.rits.back.data.core.model.Alarm;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetAlarmsPublishCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8361876357563482759L;
	
	private Set<Alarm> alarms;
	
	public void addAlarm(Alarm alarm) {
		
		if (alarm != null) {
		
			if (alarms == null) {
				
				alarms = new HashSet<>();
			}
			
			alarms.add(alarm);
		}
	}
	
	public SetAlarmsPublishCommand() {

		super(Command.SET_ALARMS_PUBLISH);
	}
}
