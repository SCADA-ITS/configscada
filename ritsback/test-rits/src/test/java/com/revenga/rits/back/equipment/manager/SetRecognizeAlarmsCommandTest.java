package com.revenga.rits.back.equipment.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Test;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetRecognizeAlarmsCommand;
import com.revenga.rits.back.equipment.manager.data.RootData;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SetRecognizeAlarmsCommandTest {

	private EntitiesManager em;
	
	@Test
	public void main() {
		
		log.debug("[INI] " + this.getClass().getSimpleName());	
		this.em = EntitiesManager.getInstance();
		
		RootData rootData = this.em.getRootData();
		
		Element element = rootData.getElement(9L, 1L);
		
		List<Alarm> alarms = element.getAlarms();
		
		if (!CollectionUtils.isEmpty(alarms)) {
			
			Alarm alarm = alarms.stream().filter(e -> e.getRecognized() == null).findFirst().orElse(null);
			
			if (alarm != null) {
				
				log.debug("Recongnize alarm id = " + alarm.getId() + " (" + alarm.getAlarmConfig().getAlias() + ")" );				
				sendRecognizeAlarm(element, alarm);
			}
		}
		
		log.debug("[END] " + this.getClass().getSimpleName());
	}
	
	private void sendRecognizeAlarm(Element element, Alarm alarm) {
		
		try {
			
			SetRecognizeAlarmsCommand setRecognizeAlarmsCommand = new SetRecognizeAlarmsCommand();

			List<Long> list = new ArrayList<>();
			
			Key key = new Key(alarm);
			
			list.add(Long.valueOf(key.getId(1)));

			setRecognizeAlarmsCommand.setAlarmIds(list);
			setRecognizeAlarmsCommand.setRecognizedUserId(User.USER_SYSTEM);
			
			EntitiesManager.getInstance().sendCommand(setRecognizeAlarmsCommand);
		
		} catch (Exception e) {
			
			log.error((new Key(element)).getSimpleValue() + " => " + e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
}
