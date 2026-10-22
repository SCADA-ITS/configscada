package com.revenga.rits.back.equipment.manager;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Test;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.equipment.manager.data.RootData;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SetDeactivationAlarmsCommandTest {

	private EntitiesManager em;
	
	@Test
	public void main() {
		
		log.debug("[INI] " + this.getClass().getSimpleName());	
		this.em = EntitiesManager.getInstance();
		
		RootData rootData = this.em.getRootData();
		
		Element element = rootData.getElement(9L, 1L);
		List<AlarmConfig> alarmConfigs = element.getElementType().getAlarmConfigs();
		
		AlarmConfig alarmConfig = alarmConfigs.stream().filter(e -> e.getId().equals(40009L)).findFirst().orElse(null);
		
		sendDeactivationAlarm(element, alarmConfig);
		
		log.debug("[END] " + this.getClass().getSimpleName());
	}
	
	private void sendDeactivationAlarm(Element element, AlarmConfig alarmConfig) {
		
		try {
			
			SetDeactivationAlarmsCommand setDeactivationAlarmsCommand = new SetDeactivationAlarmsCommand();
			
			setDeactivationAlarmsCommand.add(element, alarmConfig);
			
			EntitiesManager.getInstance().sendCommand(setDeactivationAlarmsCommand);
		
		} catch (Exception e) {
			
			log.error((new Key(element)).getSimpleValue() + " => " + e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
}
