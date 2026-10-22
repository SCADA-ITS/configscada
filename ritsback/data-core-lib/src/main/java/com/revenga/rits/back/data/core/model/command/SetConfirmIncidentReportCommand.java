package com.revenga.rits.back.data.core.model.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetConfirmIncidentReportCommand extends Command {/**
	 * 
	 */
	private static final long serialVersionUID = 8363145333141483674L;

	
	private Long incidentReportId;
	private Long userId;

	public SetConfirmIncidentReportCommand() {

		super(Command.SET_CONFIRM_INCIDENT_REPORT);
	}
}
