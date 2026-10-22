package com.revenga.rits.back.equipment.manager.service.virtual;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.command.virtual.SetAlarmsToIlluminationCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.equipment.manager.service.ThreadSendCommand;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThreadSetAlarmsToIlluminationCommand extends ThreadSendCommand {

	public static final long MAX_COMMANDS_QUEUED = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send_command_825.max_commands_queued"));
	public static final long MAX_MERGE_TIME_MS = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send_command_825.merge_time_ms"));

	public ThreadSetAlarmsToIlluminationCommand() {

		super(MAX_COMMANDS_QUEUED, MAX_MERGE_TIME_MS);
	}

	@Override
	protected void sendPreparedCommands() {

		SetAlarmsToIlluminationCommand commandResult = new SetAlarmsToIlluminationCommand();

		while (!queuePreparedCommands.isEmpty()) {

			SetAlarmsToIlluminationCommand command = (SetAlarmsToIlluminationCommand) queuePreparedCommands.poll();

			if (!CollectionUtils.isEmpty(command.getAlarms())) {

				for (Alarm alarm : command.getAlarms()) {

					commandResult.addAlarm(alarm);
				}
			}

			if (!CollectionUtils.isEmpty(commandResult.getAlarms())) {

				EntitiesManager.getInstance().sendCommand(commandResult);

				log.debug(
						"ThreadSetAlarmsToIlluminationCommand::sendPreparedCommands => SetAlarmsToIlluminationCommand with "
								+ commandResult.getAlarms().size() + " items");
			}
		}
	}
}
