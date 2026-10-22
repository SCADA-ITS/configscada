package com.revenga.rits.back.data.core.model.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.AlarmFiltered;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetAlarmsFilteredCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -855874937094295231L;
	
	public enum Action {

		FILTER_ON, FILTER_OFF
	}
	
	@Data
	public static class AlarmFilteredAction implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 389153918192920649L;
		
		AlarmFiltered alarmFiltered;
		Action action;
	}
	
	private List<AlarmFilteredAction> alarmFilteredActions;
	
	public void addAlarmFilteredAction(AlarmFiltered alarmFiltered, Action action) {
		
		if (alarmFiltered != null && action != null) {
		
			if (alarmFilteredActions == null) {
				
				alarmFilteredActions = new ArrayList<>();
			}
			
			AlarmFilteredAction alarmFilteredAction = new AlarmFilteredAction();
			
			alarmFilteredAction.setAlarmFiltered(alarmFiltered);
			alarmFilteredAction.setAction(action);
			
			alarmFilteredActions.add(alarmFilteredAction);
		}
	}
	
	public SetAlarmsFilteredCommand() {
	
			super(Command.SET_ALARMS_FILTERED);
	}
}
