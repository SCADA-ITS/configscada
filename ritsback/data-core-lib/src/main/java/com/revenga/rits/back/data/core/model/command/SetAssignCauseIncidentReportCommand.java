package com.revenga.rits.back.data.core.model.command;

import com.revenga.rits.back.data.core.model.ImsIncidentTypeCause;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetAssignCauseIncidentReportCommand extends Command {


	/**
	 * 
	 */
	private static final long serialVersionUID = 36868418296906145L;
	private Long incidentReportId;
	private ImsIncidentTypeCause incidentTypeCause;

	public SetAssignCauseIncidentReportCommand() {

		super(Command.SET_ASSIGN_USER_INCIDENT_REPORT);
	}
}
