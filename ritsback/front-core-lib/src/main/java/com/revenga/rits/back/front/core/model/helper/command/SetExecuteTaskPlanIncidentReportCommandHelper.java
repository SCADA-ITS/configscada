package com.revenga.rits.back.front.core.model.helper.command;

import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTaskPlan;
import com.revenga.rits.back.data.core.model.command.SetExecuteTaskPlanIncidentReportCommand;

public class SetExecuteTaskPlanIncidentReportCommandHelper {

	private SetExecuteTaskPlanIncidentReportCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetExecuteTaskPlanIncidentReportCommand get(String incidentReportId, String incidentTaskPlanId) {

		SetExecuteTaskPlanIncidentReportCommand command = null;

		if (StringUtils.isNoneBlank(incidentReportId, incidentTaskPlanId)) {

			Key incidentReportKey = new Key(incidentReportId);
			Key incidentTaskPlanKey = new Key(incidentTaskPlanId);

			if (incidentReportKey.getClazz() == ImsIncidentReport.class
					&& incidentTaskPlanKey.getClazz() == ImsIncidentTaskPlan.class) {

				command = new SetExecuteTaskPlanIncidentReportCommand();
				command.setIncidentReportId(Long.valueOf(incidentReportKey.getId(1)));
				command.setIncidentTaskPlanId(Long.valueOf(incidentTaskPlanKey.getId(1)));
			}
		}
		return command;
	}

}
