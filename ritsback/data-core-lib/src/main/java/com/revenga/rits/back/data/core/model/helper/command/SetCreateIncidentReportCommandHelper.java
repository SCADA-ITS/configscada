package com.revenga.rits.back.data.core.model.helper.command;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.command.SetCreateIncidentReportCommand;

public class SetCreateIncidentReportCommandHelper {

	private SetCreateIncidentReportCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetCreateIncidentReportCommand get(ImsIncidentType incidentType, Location location,
			List<Alarm> alarms) {

		SetCreateIncidentReportCommand command = null;

		if (incidentType != null && incidentType.getId() != null && location != null && location.getId() != null) {

			command = new SetCreateIncidentReportCommand();
			command.setIncidentTypeId(incidentType.getId());
			command.setLocationId(location.getId());

			if (CollectionUtils.isNotEmpty(alarms)) {

				for (Alarm alarm : alarms) {

					command.addAlarmId(alarm.getId());

				}
			}
		}

		return command;
	}

}
