package com.revenga.rits.back.equipment.manager.service;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.command.SetElementValuesPublishCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThreadSendSetElementValuesPublishCommand extends ThreadSendCommand {
	
	public static final long MAX_COMMANDS_QUEUED = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send.setElementValuesPublishCommand.max_commands_queued"));
	public static final long MAX_MERGE_TIME_MS = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send.setElementValuesPublishCommand.merge_time_ms"));
	
	public ThreadSendSetElementValuesPublishCommand() {
		
		super(MAX_COMMANDS_QUEUED, MAX_MERGE_TIME_MS);
	}
	
	protected void sendPreparedCommands() {
		
		SetElementValuesPublishCommand setElementValuesPublishCommandResult = new SetElementValuesPublishCommand();
	
		while (!queuePreparedCommands.isEmpty()) {
			
			SetElementValuesPublishCommand setElementValuesPublishCommand = (SetElementValuesPublishCommand) queuePreparedCommands.poll();
			
			if (!CollectionUtils.isEmpty(setElementValuesPublishCommand.getElementValues())) {
				
				for (ElementValue elementValue : setElementValuesPublishCommand.getElementValues()) {
					
					setElementValuesPublishCommandResult.addElementValue(elementValue);
				}
			}
		}
		
		if (!CollectionUtils.isEmpty(setElementValuesPublishCommandResult.getElementValues())) {
		
			EntitiesManager.getInstance().sendCommand(setElementValuesPublishCommandResult);
			
			log.debug("ThreadSendSetElementValuesPublishCommand::sendPreparedCommands => SetElementValuesPublishCommand with "
					+ setElementValuesPublishCommandResult.getElementValues().size() + " items");
		}
	}
}
