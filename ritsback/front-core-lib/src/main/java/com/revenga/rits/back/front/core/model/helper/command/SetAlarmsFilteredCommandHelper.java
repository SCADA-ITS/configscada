package com.revenga.rits.back.front.core.model.helper.command;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.AlarmFiltered;
import com.revenga.rits.back.data.core.model.command.SetAlarmsFilteredCommand;
import com.revenga.rits.back.data.core.model.command.SetAlarmsFilteredCommand.Action;
import com.revenga.rits.back.front.core.model.helper.AlarmFilteredHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmFilteredVO;

public class SetAlarmsFilteredCommandHelper {

	private SetAlarmsFilteredCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetAlarmsFilteredCommand get(final List<AlarmFilteredVO> alarmsFiltered) {

		SetAlarmsFilteredCommand setAlarmsFilteredCommand = null;

		if (!CollectionUtils.isEmpty(alarmsFiltered)) {

			setAlarmsFilteredCommand = new SetAlarmsFilteredCommand();

			for (AlarmFilteredVO alarmFilteredVO : alarmsFiltered) {

				AlarmFiltered alarmFiltered = AlarmFilteredHelper.get(alarmFilteredVO);
				if (alarmFiltered != null && alarmFiltered.getEnabled() != null) {

					if (alarmFiltered.getEnabled().booleanValue()) {

						setAlarmsFilteredCommand.addAlarmFilteredAction(alarmFiltered, Action.FILTER_ON);

					} else {

						setAlarmsFilteredCommand.addAlarmFilteredAction(alarmFiltered, Action.FILTER_OFF);
					}
				}
			}
		}

		return setAlarmsFilteredCommand;
	}

}
