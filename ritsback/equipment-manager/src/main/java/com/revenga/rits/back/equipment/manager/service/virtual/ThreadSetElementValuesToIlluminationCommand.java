package com.revenga.rits.back.equipment.manager.service.virtual;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementValuesToIlluminationCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.equipment.manager.service.ThreadSendCommand;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThreadSetElementValuesToIlluminationCommand extends ThreadSendCommand {

	public static final long MAX_COMMANDS_QUEUED = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send_command_826.max_commands_queued"));
	public static final long MAX_MERGE_TIME_MS = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send_command_826.merge_time_ms"));

	public ThreadSetElementValuesToIlluminationCommand() {
		
		super(MAX_COMMANDS_QUEUED, MAX_MERGE_TIME_MS);
	}

	@Override
	protected void sendPreparedCommands() {

		SetElementValuesToIlluminationCommand commandResult = new SetElementValuesToIlluminationCommand();

		while (!queuePreparedCommands.isEmpty()) {

			SetElementValuesToIlluminationCommand command = (SetElementValuesToIlluminationCommand) queuePreparedCommands
					.poll();

			if (!CollectionUtils.isEmpty(command.getElementValues())) {

				for (ElementValue elementValue : command.getElementValues()) {

					commandResult.addElementValue(elementValue);
				}
			}
		}

		if (!CollectionUtils.isEmpty(commandResult.getElementValues())) {

			EntitiesManager.getInstance().sendCommand(commandResult);

			log.debug(
					"ThreadSetElementValuesToIlluminationCommand::sendPreparedCommands => SetElementValuesToIlluminationCommand with "
							+ commandResult.getElementValues().size() + " items");
		}
	}
}
