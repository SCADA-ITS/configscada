package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetSignallingsCommand extends Command {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7572932161403887950L;

	private List<SignallingCommand> commands;
	private Boolean sendWithAnyError;

	public SetSignallingsCommand() {

		super(Command.SET_SIGNALLINGS_COMMAND);
	}
	
	public void addCommand (SignallingCommand command) {

		if (command != null) {

			if (commands == null) {

				commands = new ArrayList<>();
			}

			commands.add(command);
		}
	}
}
