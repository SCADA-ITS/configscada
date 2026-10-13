package com.revenga.rits.back.data.core.model.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetBackwardIncidentReportCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8655017915562530442L;
	/**
	 * 
	 */

	private Long incidentReportId;

	public SetBackwardIncidentReportCommand() {

		super(Command.SET_BACKWARD_INCIDENT_REPORT);
	}
}
