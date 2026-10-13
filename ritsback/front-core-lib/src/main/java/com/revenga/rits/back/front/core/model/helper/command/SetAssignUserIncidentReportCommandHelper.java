package com.revenga.rits.back.front.core.model.helper.command;

import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.SetAssignUserIncidentReportCommand;

public class SetAssignUserIncidentReportCommandHelper {

	private SetAssignUserIncidentReportCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetAssignUserIncidentReportCommand get(String incidentReportId, String userId) {

		SetAssignUserIncidentReportCommand command = null;

		if (StringUtils.isNoneBlank(incidentReportId, userId)) {

			Key incidentReportKey = new Key(incidentReportId);
			Key userKey = new Key(userId);

			if (incidentReportKey.getClazz() == ImsIncidentReport.class && userKey.getClazz() == User.class) {

				command = new SetAssignUserIncidentReportCommand();
				command.setIncidentReportId(Long.valueOf(incidentReportKey.getId(1)));
				command.setUserId(Long.valueOf(userKey.getId(1)));
			}
		}

		return command;
	}

}
