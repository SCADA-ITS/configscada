package com.revenga.rits.back.equipment.manager.service;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.command.SetElementStatesPublishCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThreadSendSetElementStatesPublishCommand extends ThreadSendCommand {

	public static final long MAX_COMMANDS_QUEUED = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send.setElementStatesPublishCommand.max_commands_queued"));
	public static final long MAX_MERGE_TIME_MS = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send.setElementStatesPublishCommand.merge_time_ms"));
	
	public ThreadSendSetElementStatesPublishCommand() {
		
		super(MAX_COMMANDS_QUEUED, MAX_MERGE_TIME_MS);
	}
	
	protected void sendPreparedCommands() {
		
		SetElementStatesPublishCommand setElementStatesPublishCommandResult = new SetElementStatesPublishCommand();
	
		while (!queuePreparedCommands.isEmpty()) {
			
			SetElementStatesPublishCommand setElementStatesPublishCommand = (SetElementStatesPublishCommand) queuePreparedCommands.poll();
			
			if (!CollectionUtils.isEmpty(setElementStatesPublishCommand.getElementStates())) {
				
				for (ElementState elementState : setElementStatesPublishCommand.getElementStates()) {
					
					setElementStatesPublishCommandResult.addElementState(elementState);
				}
			}
		}
		
		if (!CollectionUtils.isEmpty(setElementStatesPublishCommandResult.getElementStates())) {
		
			EntitiesManager.getInstance().sendCommand(setElementStatesPublishCommandResult);
			
			log.debug("ThreadSendSetElementStatesPublishCommand::sendPreparedCommands => SetElementStatesPublishCommand with "
					+ setElementStatesPublishCommandResult.getElementStates().size() + " items");
		}
	}
}
