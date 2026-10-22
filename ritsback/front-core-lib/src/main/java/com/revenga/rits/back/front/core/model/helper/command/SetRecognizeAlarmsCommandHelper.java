package com.revenga.rits.back.front.core.model.helper.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.SetRecognizeAlarmsCommand;

public class SetRecognizeAlarmsCommandHelper {

	private SetRecognizeAlarmsCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}
 
	public static SetRecognizeAlarmsCommand get(final List<String> alarmIds, final String recognizedComments, final String userId, final Boolean disagree) {

		SetRecognizeAlarmsCommand setRecognizeAlarmsCommand = null;

		if (!CollectionUtils.isEmpty(alarmIds)) {

			setRecognizeAlarmsCommand = new SetRecognizeAlarmsCommand();

			List<Long> list = new ArrayList<>();

			for (String alarmId : alarmIds) {
				
				Key key = new Key(alarmId);

				if (key.getClazz() == Alarm.class) {

					list.add(Long.valueOf(key.getId(1)));
				}
			}
							
			setRecognizeAlarmsCommand.setAlarmIds(list);
			setRecognizeAlarmsCommand.setRecognizedComments(recognizedComments);
			setRecognizeAlarmsCommand.setDisagree(disagree);	
			if(userId != null && !userId.isEmpty()) {
				Key key = new Key(userId);

				if (key.getClazz() == User.class) {
					setRecognizeAlarmsCommand.setRecognizedUserId(Long.valueOf(key.getId(1)));
				}
			}			
		}

		return setRecognizeAlarmsCommand;
	}
}
