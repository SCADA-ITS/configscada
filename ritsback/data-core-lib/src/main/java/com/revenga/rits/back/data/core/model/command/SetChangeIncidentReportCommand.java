package com.revenga.rits.back.data.core.model.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetChangeIncidentReportCommand extends Command {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4276365256755292521L;
	private Long incidentReportId;
	private Long locationId;
	private Long affectionStretchId;
	private Long incidentTypeId;
	private Long roadImpactId;


	public SetChangeIncidentReportCommand() {

		super(Command.SET_CHANGE_INCIDENT_REPORT);
	}
}
