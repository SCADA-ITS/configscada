package com.revenga.rits.back.front.core.model.helper.command;

import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.SetConfirmIncidentReportCommand;

public class SetConfirmIncidentReportCommandHelper {
	
	private SetConfirmIncidentReportCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetConfirmIncidentReportCommand get(String incidentReportId, String userId) {

		SetConfirmIncidentReportCommand command = null;

		if (StringUtils.isNoneBlank(incidentReportId)) {
			
			Key incidentReportKey = new Key(incidentReportId);
			
			if (incidentReportKey.getClazz() == ImsIncidentReport.class ){
				
				command = new SetConfirmIncidentReportCommand();
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
