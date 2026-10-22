package com.revenga.rits.back.data.core.model.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetExecuteTaskPlanIncidentReportCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8655017915562530442L;
	/**
	 * 
	 */

	private Long incidentReportId;
	private Long incidentTaskPlanId;

	public SetExecuteTaskPlanIncidentReportCommand() {

		super(Command.SET_EXECUTE_TASK_PLAN_INCICENT_REPORT);
	}
}
