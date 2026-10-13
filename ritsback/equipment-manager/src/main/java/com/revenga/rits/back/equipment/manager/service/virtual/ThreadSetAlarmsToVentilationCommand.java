package com.revenga.rits.back.equipment.manager.service.virtual;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.command.virtual.SetAlarmsToVentilationCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.equipment.manager.service.ThreadSendCommand;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThreadSetAlarmsToVentilationCommand extends ThreadSendCommand {

	public static final long MAX_COMMANDS_QUEUED = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send_command_805.max_commands_queued"));
	public static final long MAX_MERGE_TIME_MS = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send_command_805.merge_time_ms"));

	public ThreadSetAlarmsToVentilationCommand() {

		super(MAX_COMMANDS_QUEUED, MAX_MERGE_TIME_MS);
	}

	@Override
	protected void sendPreparedCommands() {

		SetAlarmsToVentilationCommand commandResult = new SetAlarmsToVentilationCommand();

		while (!queuePreparedCommands.isEmpty()) {

			SetAlarmsToVentilationCommand command = (SetAlarmsToVentilationCommand) queuePreparedCommands
					.poll();

			if (!CollectionUtils.isEmpty(command.getAlarms())) {

				for (Alarm alarms : command.getAlarms()) {

					commandResult.addAlarm(alarms);
				}
			}
		}

		if (!CollectionUtils.isEmpty(commandResult.getAlarms())) {

			EntitiesManager.getInstance().sendCommand(commandResult);

			log.debug("ThreadSetAlarmsToVentilationCommand::sendPreparedCommands => SetAlarmsToVentilationCommand with "
					+ commandResult.getAlarms().size() + " items");
		}
	}
}
