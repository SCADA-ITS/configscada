package com.revenga.rits.back.front.core.model.helper.command;

import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReportLog;
import com.revenga.rits.back.data.core.model.command.SetDeleteLogIncidentReportCommand;

public class SetDeleteLogIncidentReportCommandHelper {

	private SetDeleteLogIncidentReportCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetDeleteLogIncidentReportCommand get(String incidentReportLogId) {

		SetDeleteLogIncidentReportCommand command = null;

		if (StringUtils.isNoneBlank(incidentReportLogId)) {

			Key incidentReportLogKey = new Key(incidentReportLogId);

			if (incidentReportLogKey.getClazz() == ImsIncidentReportLog.class) {

				command = new SetDeleteLogIncidentReportCommand();
				command.setIncidentReportLogId(Long.valueOf(incidentReportLogKey.getId(1)));
			}
		}
		return command;
	}

}
