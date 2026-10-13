package com.revenga.rits.back.data.core.model.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetCancelIncidentReportCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8655017915562530442L;
	/**
	 * 
	 */

	private Long incidentReportId;
	private Long userId;

	public SetCancelIncidentReportCommand() {

		super(Command.SET_CANCEL_INCIDENT_REPORT);
	}
}
