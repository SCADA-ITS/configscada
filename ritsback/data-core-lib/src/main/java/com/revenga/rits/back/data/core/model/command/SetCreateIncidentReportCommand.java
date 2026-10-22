package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetCreateIncidentReportCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8655017915562530442L;
	/**
	 * 
	 */

	private Long incidentTypeId;
	private Long locationId;
	private Long userId;
	private List<Long> alarmIds;
	private List<Long> affectionStretchIds;
	private Long roadImpactId;
	
	public void addAlarmId(Long alarmId) {

		if (alarmId != null) {

			if (alarmIds == null) {

				alarmIds = new ArrayList<>();
			}
			alarmIds.add(alarmId);
		}
	}
	
	public void addAffectionStretchId(Long stretchId) {

		if (stretchId != null) {

			if (affectionStretchIds == null) {

				affectionStretchIds = new ArrayList<>();
			}
			affectionStretchIds.add(stretchId);
		}
	}

	public SetCreateIncidentReportCommand() {

		super(Command.SET_CREATE_INCIDENT_REPORT);
	}
}
