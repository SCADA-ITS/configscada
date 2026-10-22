package com.revenga.rits.back.front.core.model.helper.command;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.SetAlarmsCommand;
import com.revenga.rits.back.front.core.model.helper.AlarmConfigHelper;
import com.revenga.rits.back.front.core.model.helper.ElementHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;

public class SetAlarmsCommandHelper {

	private SetAlarmsCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetAlarmsCommand get(final List<String> alarmConfigIds, final List<String> targetElementIds, final String creationComments, final String userId) {

		SetAlarmsCommand setAlarmsCommand = null;

		if (!CollectionUtils.isEmpty(alarmConfigIds) && !CollectionUtils.isEmpty(targetElementIds)
				&& alarmConfigIds.size() == targetElementIds.size()) {

			setAlarmsCommand = new SetAlarmsCommand();
			
			setAlarmsCommand.setCreationComments(creationComments);
			
			if(userId != null && !userId.isEmpty()) {
				Key key = new Key(userId);

				if (key.getClazz() == User.class) {
					setAlarmsCommand.setCreationUserId(Long.valueOf(key.getId(1)));
				}
			}			

			for (int i = 0; i < alarmConfigIds.size(); i++) {

				AlarmConfig alarmConfig = null;
				AlarmConfigVO alarmConfigVO;
				Element targetElement = null;
				ElementVO elementVO;

				alarmConfigVO = new AlarmConfigVO();
				alarmConfigVO.setId(alarmConfigIds.get(i));

				alarmConfig = AlarmConfigHelper.get(alarmConfigVO);

				elementVO = new ElementVO();

				elementVO.setId(targetElementIds.get(i));

				targetElement = ElementHelper.get(elementVO);

				setAlarmsCommand.addAlarmConfigElement(alarmConfig, targetElement);
			}
		}

		return setAlarmsCommand;
	}

}
