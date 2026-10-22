package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetBlockIncidentReportsPublishCommand extends Command {
	
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
	
	public SetBlockIncidentReportsPublishCommand() {

		super(Command.SET_BLOCK_INCIDENT_REPORTS_PUBLISH);
	}
}
