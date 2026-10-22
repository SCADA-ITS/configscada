package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.PmCommandElement;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetDeleteCommandsCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7572932161403887950L;

	private List<PmCommandElement> commands;
	private String source;

	public SetDeleteCommandsCommand() {

		super(Command.SET_DELETE_COMMANDS_COMMAND);
	}

	public void addCommand(PmCommandElement command) {

		if (command != null) {

			if (commands == null) {

				commands = new ArrayList<>();
			}

			commands.add(command);
		}
	}
}
