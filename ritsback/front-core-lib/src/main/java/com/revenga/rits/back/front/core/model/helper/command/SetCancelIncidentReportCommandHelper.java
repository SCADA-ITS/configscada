package com.revenga.rits.back.front.core.model.helper.command;

import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.SetCancelIncidentReportCommand;

public class SetCancelIncidentReportCommandHelper {

	private SetCancelIncidentReportCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetCancelIncidentReportCommand get(String incidentReportId, String userId) {

		SetCancelIncidentReportCommand command = null;

		if (StringUtils.isNoneBlank(incidentReportId)) {
			
			Key incidentReportKey = new Key(incidentReportId);
			
			if (incidentReportKey.getClazz() == ImsIncidentReport.class ){
				
				command = new SetCancelIncidentReportCommand();
				command.setIncidentReportId(Long.valueOf(incidentReportKey.getId(1)));
				
				if (userId != null) {
					
					Key userKey = new Key(userId);
					
					if (userKey.getClazz() == User.class) {
						
						command.setUserId(Long.valueOf(userKey.getId(1)));
					}
				}
				
			}	
		}
		return command;
	}

}
