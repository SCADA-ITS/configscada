package com.revenga.rits.back.data.core.model.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetForwardIncidentReportCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8655017915562530442L;
	/**
	 * 
	 */

	private Long incidentReportId;

	public SetForwardIncidentReportCommand() {

		super(Command.SET_FORWARD_INCIDENT_REPORT);
	}
}
