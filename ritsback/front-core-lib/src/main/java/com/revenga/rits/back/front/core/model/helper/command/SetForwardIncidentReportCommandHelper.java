package com.revenga.rits.back.front.core.model.helper.command;

import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.command.SetForwardIncidentReportCommand;

public class SetForwardIncidentReportCommandHelper {

	private SetForwardIncidentReportCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetForwardIncidentReportCommand get(String incidentReportId) {

		SetForwardIncidentReportCommand command = null;

		if (StringUtils.isNoneBlank(incidentReportId)) {
			
			Key incidentReportKey = new Key(incidentReportId);
			
			if (incidentReportKey.getClazz() == ImsIncidentReport.class ){
				
				command = new SetForwardIncidentReportCommand();
				command.setIncidentReportId(Long.valueOf(incidentReportKey.getId(1)));
			}	
		}
		return command;
	}

}
