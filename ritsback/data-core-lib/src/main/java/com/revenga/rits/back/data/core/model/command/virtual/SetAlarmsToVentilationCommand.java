package com.revenga.rits.back.data.core.model.command.virtual;

import java.util.HashSet;
import java.util.Set;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.command.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetAlarmsToVentilationCommand extends Command {

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
	
	public SetAlarmsToVentilationCommand() {

		super(Command.SET_ALARMS_TO_VENTILATION);
	}
}
