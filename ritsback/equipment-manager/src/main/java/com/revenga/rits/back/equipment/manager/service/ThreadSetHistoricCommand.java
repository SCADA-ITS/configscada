package com.revenga.rits.back.equipment.manager.service;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.SetHistoricElementCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThreadSetHistoricCommand extends ThreadSendCommand {

	public static final long MAX_COMMANDS_QUEUED = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send_command_200.max_commands_queued"));
	public static final long MAX_MERGE_TIME_MS = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send_command_200.merge_time_ms"));

	public ThreadSetHistoricCommand() {

		super(MAX_COMMANDS_QUEUED, MAX_MERGE_TIME_MS);
	}

	@Override
	protected void sendPreparedCommands() {

		SetHistoricElementCommand commandResult = new SetHistoricElementCommand();

		while (!queuePreparedCommands.isEmpty()) {

			SetHistoricElementCommand command = (SetHistoricElementCommand) queuePreparedCommands.poll();

			if (!CollectionUtils.isEmpty(command.getElements())) {

				for (Element element : command.getElements()) {

					if (CollectionUtils.isNotEmpty(element.getElementValues())) {

						Element auxElement = commandResult.getElements().stream().filter(be -> be.equals(element))
								.findFirst().orElse(null);

						if (auxElement == null) {

							commandResult.addElement(element);

						} else {

							element.getElementValues().forEach(be -> auxElement.getElementValues().add(be));
						}

					}

				}
			}
		}

		if (!CollectionUtils.isEmpty(commandResult.getElements())) {

			EntitiesManager.getInstance().sendCommand(commandResult);

			log.debug("ThreadSetHistoricCommand::sendPreparedCommands => SetHistoricElementCommand with "
					+ commandResult.getElements().size() + " items");
		}
	}
}
