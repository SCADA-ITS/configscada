package com.revenga.rits.back.front.core.model.helper.command;

import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.command.SetBackwardIncidentReportCommand;

public class SetBackwardIncidentReportCommandHelper {

	private SetBackwardIncidentReportCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetBackwardIncidentReportCommand get(String incidentReportId) {

		SetBackwardIncidentReportCommand command = null;

		if (StringUtils.isNoneBlank(incidentReportId)) {
			
			Key incidentReportKey = new Key(incidentReportId);
			
			if (incidentReportKey.getClazz() == ImsIncidentReport.class ){
				
				command = new SetBackwardIncidentReportCommand();
				command.setIncidentReportId(Long.valueOf(incidentReportKey.getId(1)));
			}	
		}
		return command;
	}

}
