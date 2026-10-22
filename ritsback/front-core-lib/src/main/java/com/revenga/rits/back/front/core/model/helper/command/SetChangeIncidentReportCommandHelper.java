package com.revenga.rits.back.front.core.model.helper.command;

import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.RoadImpact;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.command.SetChangeIncidentReportCommand;

public class SetChangeIncidentReportCommandHelper {

	private SetChangeIncidentReportCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetChangeIncidentReportCommand get(String incidentReportId, String locationId,
			String affectionStretchId, String incindetTypeId, String roadImpactId) {

		SetChangeIncidentReportCommand command = null;

		if (StringUtils.isNoneBlank(incidentReportId, locationId, affectionStretchId, incindetTypeId)) {

			Key incidentKey = new Key(incidentReportId);
			Key locationKey = new Key(locationId);
			Key affectionStretchKey = new Key(affectionStretchId);
			Key incindetTypeKey = new Key(incindetTypeId);

			if (incidentKey.getClazz() == ImsIncidentReport.class && locationKey.getClazz() == Location.class
					&& affectionStretchKey.getClazz() == Stretch.class
					&& incindetTypeKey.getClazz() == ImsIncidentType.class) {

				command = new SetChangeIncidentReportCommand();
				command.setIncidentReportId(Long.valueOf(incidentKey.getId(1)));
				command.setLocationId(Long.valueOf(locationKey.getId(1)));
				command.setAffectionStretchId(Long.valueOf(affectionStretchKey.getId(1)));
				command.setIncidentTypeId(Long.valueOf(incindetTypeKey.getId(1)));

				if (StringUtils.isNoneBlank(roadImpactId)) {

					Key roadImpactKey = new Key(roadImpactId);

					if (roadImpactKey.getClazz() == RoadImpact.class) {
						
						command.setRoadImpactId(Long.valueOf(roadImpactKey.getId(1)));
					}
				}
			}
		}

		return command;
	}

}
