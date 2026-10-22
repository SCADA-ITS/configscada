package com.revenga.rits.back.data.core.model.command.virtual;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.command.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetFireAlarmCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2280654889459367209L;
	
	private Element element;
	private Stretch stretch;
	
	public SetFireAlarmCommand() {

		super(Command.SET_FIRE_ALARM);
	}
}
