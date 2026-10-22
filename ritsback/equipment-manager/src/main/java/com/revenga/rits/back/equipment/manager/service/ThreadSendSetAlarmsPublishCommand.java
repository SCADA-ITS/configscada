package com.revenga.rits.back.equipment.manager.service;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.SetAlarmsPublishCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThreadSendSetAlarmsPublishCommand extends ThreadSendCommand {

	public static final long MAX_COMMANDS_QUEUED = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send.setAlarmsPublishCommand.max_commands_queued"));
	public static final long MAX_MERGE_TIME_MS = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "thread.send.setAlarmsPublishCommand.merge_time_ms"));
	
	public ThreadSendSetAlarmsPublishCommand() {
		
		super(MAX_COMMANDS_QUEUED, MAX_MERGE_TIME_MS);
	}
	
	protected void sendPreparedCommands() {
		
		SetAlarmsPublishCommand setAlarmsPublishCommandResult = new SetAlarmsPublishCommand();
	
		while (!queuePreparedCommands.isEmpty()) {
			
			SetAlarmsPublishCommand setAlarmsPublishCommand = (SetAlarmsPublishCommand) queuePreparedCommands.poll();
			
			if (!CollectionUtils.isEmpty(setAlarmsPublishCommand.getAlarms())) {
				
				for (Alarm alarm : setAlarmsPublishCommand.getAlarms()) {
					
					Alarm existAlarm = null;
					
					Element element = alarm.getTargetElement();
					
					if (!CollectionUtils.isEmpty(setAlarmsPublishCommandResult.getAlarms())) {
										
						existAlarm = setAlarmsPublishCommandResult.getAlarms().stream().filter(e -> e.getId().equals(alarm.getId())).findFirst().orElse(null);
						
						if (existAlarm != null) {
							
							setAlarmsPublishCommandResult.getAlarms().remove(existAlarm);
							
							log.warn(String.format(
									"%s => Alarm(%d) is duplicated. Discarting to publish. Recognized: %s Deactivation: %s AlarmConfig %d, %s",
									element != null ? (new Key(element)).getSimpleValue() : "",
									existAlarm.getId(), 
									existAlarm.getRecognized() != null,
									existAlarm.getDeactivation() != null, 
									existAlarm.getAlarmConfig().getId(),
									existAlarm.getAlarmConfig().getAlias()));
						}
					}
					
					if (existAlarm != null) {
						
						log.warn(String.format(
								"%s => Alarm(%d) is published instead. Recognized: %s Deactivation: %s AlarmConfig %d, %s",
								element != null ? (new Key(element)).getSimpleValue() : "",
								alarm.getId(), 
								alarm.getRecognized() != null,
								alarm.getDeactivation() != null, 
								alarm.getAlarmConfig().getId(),
								alarm.getAlarmConfig().getAlias()));
					}
									
					setAlarmsPublishCommandResult.addAlarm(alarm);
				}
			}
		}
		
		if (!CollectionUtils.isEmpty(setAlarmsPublishCommandResult.getAlarms())) {
		
			EntitiesManager.getInstance().sendCommand(setAlarmsPublishCommandResult);
			
			log.debug("ThreadSendSetAlarmsPublishCommand::sendPreparedCommands => SetAlarmsPublishCommand with "
					+ setAlarmsPublishCommandResult.getAlarms().size() + " items");
		}
	}
}
