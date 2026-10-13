package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.ImsIncidentReportLog;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetAddLogIncidentReportCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8655017915562530442L;
	/**
	 * 
	 */

	private List<ImsIncidentReportLog> incidentReportLogs;

	public SetAddLogIncidentReportCommand() {

		super(Command.SET_ADD_LOG_INCIDENT_REPORT);
	}

	public void addIncidentReportLog(ImsIncidentReportLog incidentReportLog) {

		if (incidentReportLog != null) {

			if (incidentReportLogs == null) {

				incidentReportLogs = new ArrayList<>();
			}

			incidentReportLogs.add(incidentReportLog);
		}
	}
}
