package com.revenga.rits.back.equipment.manager.service.virtual;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementStatesToIlluminationCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.equipment.manager.service.ThreadSendCommand;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThreadSetElementStatesToIlluminationCommand extends ThreadSendCommand {

	public static final long MAX_COMMANDS_QUEUED = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send_command_827.max_commands_queued"));
	public static final long MAX_MERGE_TIME_MS = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send_command_827.merge_time_ms"));

	public ThreadSetElementStatesToIlluminationCommand() {

		super(MAX_COMMANDS_QUEUED, MAX_MERGE_TIME_MS);
	}

	@Override
	protected void sendPreparedCommands() {

		SetElementStatesToIlluminationCommand commandResult = new SetElementStatesToIlluminationCommand();

		while (!queuePreparedCommands.isEmpty()) {

			SetElementStatesToIlluminationCommand command = (SetElementStatesToIlluminationCommand) queuePreparedCommands
					.poll();

			if (!CollectionUtils.isEmpty(command.getElementStates())) {

				for (ElementState elementState : command.getElementStates()) {

					commandResult.addElementState(elementState);
				}
			}

			if (!CollectionUtils.isEmpty(commandResult.getElementStates())) {

				EntitiesManager.getInstance().sendCommand(commandResult);

				log.debug(
						"ThreadSetElementValuesToIlluminationCommand::sendPreparedCommands => SetElementStateToIlluminationCommand with "
								+ commandResult.getElementStates().size() + " items");
			}
		}
	}
}
