package com.revenga.rits.back.front.core.model.helper.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;

public class SetDeactivationAlarmsCommandHelper {

	private SetDeactivationAlarmsCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}
 
	public static SetDeactivationAlarmsCommand get(final List<String> alarmIds, final String deactivationComments, final String userId) {

		SetDeactivationAlarmsCommand setDeactivationAlarmsCommand = null;

		if (!CollectionUtils.isEmpty(alarmIds)) {

			setDeactivationAlarmsCommand = new SetDeactivationAlarmsCommand();

			List<Long> list = new ArrayList<>();

			for (String alarmId : alarmIds) {
				
				Key key = new Key(alarmId);

				if (key.getClazz() == Alarm.class) {

					list.add(Long.valueOf(key.getId(1)));
				}
			}
			
			setDeactivationAlarmsCommand.setAlarmIds(list);
			setDeactivationAlarmsCommand.setDeactivationComments(deactivationComments);
			
			if(userId != null && !userId.isEmpty()) {
				Key key = new Key(userId);

				if (key.getClazz() == Alarm.class) {

					list.add(Long.valueOf(key.getId(1)));
				}
				
				setDeactivationAlarmsCommand.setDeactivationUserId(Long.valueOf(key.getId(1)));
			}
		}

		return setDeactivationAlarmsCommand;
	}
}
