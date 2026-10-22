package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetFinalizeTaskIncidentReportCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8655017915562530442L;
	/**
	 * 
	 */

	private Long incidentReportId;
	private Long incidentTypeTaskId;
	private List<ImsIncidentTypeTaskValue> incidentTypeTaskValues;

	public SetFinalizeTaskIncidentReportCommand() {

		super(Command.SET_FINALIZE_TASK_INCIDENT_REPORT);
	}

	public void addIncidentTypeTaskValue(Long taskTypeId, Long taskTypeParamId, String value) {

		if(taskTypeParamId != null && value != null){
			
			if (incidentTypeTaskValues == null) {
				
				incidentTypeTaskValues = new ArrayList<>();
			}
			
			ImsIncidentTypeTaskValue incidentTypeTaskValue  = new ImsIncidentTypeTaskValue();
			incidentTypeTaskValue.setIncidentTypeTaskId(incidentTypeTaskId);
			incidentTypeTaskValue.setTaskTypeId(taskTypeId);
			incidentTypeTaskValue.setTaskTypeParamId(taskTypeParamId);
			incidentTypeTaskValue.setValue(value);
			
			incidentTypeTaskValues.add(incidentTypeTaskValue);
		}
	}
}
