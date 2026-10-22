package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.ImsExtIncident;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetExIncidentPublishCommand extends Command {/**
	 * 
	 */
	private static final long serialVersionUID = 4108733430545417080L;
	
	private List<ImsExtIncident> imsExtIncidents;

	public void addImsExtIncident(ImsExtIncident imsExtIncident) {

		if (imsExtIncident != null) {

			if (imsExtIncidents == null) {

				imsExtIncidents = new ArrayList<>();
			}

			imsExtIncidents.add(imsExtIncident);
		}
	}

	public SetExIncidentPublishCommand() {

		super(Command.SET_EX_INCIDENT_PUBLISH);
	}

}
