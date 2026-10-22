package com.revenga.rits.back.data.core.model.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetChangeRoadImpactIncidentReportCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8655017915562530442L;
	/**
	 * 
	 */

	private Long incidentReportId;
	private Long roadImpactId;

	public SetChangeRoadImpactIncidentReportCommand() {

		super(Command.SET_CHANGE_ROAD_IMPACT_INCIDENT_REPORT);
	}
}
