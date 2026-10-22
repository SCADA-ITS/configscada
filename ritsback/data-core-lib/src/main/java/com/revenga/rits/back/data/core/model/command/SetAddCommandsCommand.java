package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.PmCommandElement;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetAddCommandsCommand extends Command {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7572932161403887950L;

	private List<PmCommandElement> commands;
	private Boolean sendWithAnyError;

	public SetAddCommandsCommand() {

		super(Command.SET_ADD_COMMANDS_COMMAND);
	}
	
	public void addCommand (PmCommandElement command) {

		if (command != null) {

			if (commands == null) {

				commands = new ArrayList<>();
			}

			commands.add(command);
		}
	}
}
