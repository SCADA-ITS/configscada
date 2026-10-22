package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetIncidentReportsPublishCommand extends Command {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7235542783710963196L;
	
	private List<Long> incidentReportIds;
	
	public void addIncident(Long incidentReportId) {
		
		if (incidentReportId != null) {
		
			if (incidentReportIds == null) {
				
				incidentReportIds = new ArrayList<>();
			}
			
			incidentReportIds.add(incidentReportId);
		}
	}
	
	public SetIncidentReportsPublishCommand() {

		super(Command.SET_INCIDENT_REPORTS_PUBLISH);
	}
}
