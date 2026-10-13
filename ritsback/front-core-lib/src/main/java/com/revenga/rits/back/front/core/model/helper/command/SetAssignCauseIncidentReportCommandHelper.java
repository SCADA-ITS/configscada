package com.revenga.rits.back.front.core.model.helper.command;

import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeCause;
import com.revenga.rits.back.data.core.model.command.SetAssignCauseIncidentReportCommand;

public class SetAssignCauseIncidentReportCommandHelper {

	private SetAssignCauseIncidentReportCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetAssignCauseIncidentReportCommand get(String incidentReportId, String incidentTypeCauseId) {

		SetAssignCauseIncidentReportCommand command = null;

		if (StringUtils.isNoneBlank(incidentReportId, incidentTypeCauseId)) {

			Key incidentReportKey = new Key(incidentReportId);
			Key causeKey = new Key(incidentTypeCauseId);

			if (incidentReportKey.getClazz() == ImsIncidentReport.class && causeKey.getClazz() == ImsIncidentTypeCause.class) {

				command = new SetAssignCauseIncidentReportCommand();
				command.setIncidentReportId(Long.valueOf(incidentReportKey.getId(1)));
				
				ImsIncidentTypeCause incidentTypeCause = new ImsIncidentTypeCause();
				incidentTypeCause.setPks(causeKey.getIds());
				command.setIncidentTypeCause(incidentTypeCause);
			}
		}

		return command;
	}

}
