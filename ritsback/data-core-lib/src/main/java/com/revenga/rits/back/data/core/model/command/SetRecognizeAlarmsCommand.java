package com.revenga.rits.back.data.core.model.command;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetRecognizeAlarmsCommand extends Command {

	/**
	 * 
	 */	
	private static final long serialVersionUID = 5219481364670831327L;
	
	private List<Long> alarmIds;
	private String recognizedComments;
	private Boolean disagree;
	
	/**
	 * User that has recognized the alarm
	 */
	private Long recognizedUserId;			
	
	public SetRecognizeAlarmsCommand() {

		super(Command.SET_RECOGNIZE_ALARMS);
	}
}
