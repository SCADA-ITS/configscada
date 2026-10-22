package com.revenga.rits.back.test.rits.io.external.manager;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetAlarmsPublishCommand;
import com.revenga.rits.back.entities.lib.repository.CacheManager;

public class ioExternalMagerExecutorTest {

	public ioExternalMagerExecutorTest() {
		
	}
	
	@Test
	public void sendAlarmsInfo() {
		
		SetAlarmsPublishCommand setAlarmsPublishCommand = new SetAlarmsPublishCommand();
		Set<Alarm> alarms = new HashSet<>();
		
		Alarm alarm1 = new Alarm();
		AlarmConfig alarmConfig1 = new AlarmConfig();
		alarmConfig1.setId(10001L);
		alarmConfig1.setAlarmLevelId(6L);
		alarmConfig1.setAlarmTypeId(1L);
		alarm1.setSourceElementTypeId(2L);
		alarm1.setSourceElementId(1L);
		alarm1.setId(1L);
		alarm1.setActivation(2L);
		alarm1.setAlarmConfig(alarmConfig1);
		alarm1.setAlarmConfigId(10001L);
		
		Alarm alarm2 = new Alarm();
		AlarmConfig alarmConfig2 = new AlarmConfig();
		alarmConfig2.setId(10001L);
		alarmConfig2.setAlarmLevelId(1L);
		alarmConfig2.setAlarmTypeId(2L);
		alarm2.setSourceElementTypeId(1L);
		alarm2.setSourceElementId(1L);
		alarm2.setId(2L);
		alarm2.setActivation(2L);
		alarm2.setAlarmConfig(alarmConfig2);
		alarm2.setAlarmConfigId(10001L);
		
		alarms.add(alarm1);
		alarms.add(alarm2);

		setAlarmsPublishCommand.setAlarms(alarms);
		
		sendCommand(setAlarmsPublishCommand);
		
		assertNotNull(setAlarmsPublishCommand);
	}
	
	public Command sendCommand(Command command) {

		return CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME);
	}
	
}
