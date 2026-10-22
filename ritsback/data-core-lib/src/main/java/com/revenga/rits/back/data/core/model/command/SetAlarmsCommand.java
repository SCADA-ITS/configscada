package com.revenga.rits.back.data.core.model.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetAlarmsCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8655017915562530442L;
	
	/**
	 * User that has created the alarm
	 */
	private Long creationUserId;	
	
	private String creationComments;
	
	@Data
	public static class AlarmConfigElement implements Serializable {		
		/**
		 * 
		 */
		private static final long serialVersionUID = -2903338184393630165L;
		
		AlarmConfig alarmConfig;
		Element element;
	}
	
	private List<AlarmConfigElement> alarmConfigElements;
	
	public void addAlarmConfigElement(AlarmConfig alarmConfig, Element element) {
		
		if (alarmConfig != null && element != null) {
		
			if (alarmConfigElements == null) {
				
				alarmConfigElements = new ArrayList<>();
			}
			
			AlarmConfigElement alarmConfigElement = new AlarmConfigElement();
			
			alarmConfigElement.setAlarmConfig(alarmConfig);
			alarmConfigElement.setElement(element);
			
			alarmConfigElements.add(alarmConfigElement);
		}
	}
	
	public SetAlarmsCommand() {

		super(Command.SET_ALARMS);
	}
}
