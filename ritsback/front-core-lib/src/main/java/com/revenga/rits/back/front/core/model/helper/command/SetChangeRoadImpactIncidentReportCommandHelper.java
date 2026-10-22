package com.revenga.rits.back.front.core.model.helper.command;

import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.RoadImpact;
import com.revenga.rits.back.data.core.model.command.SetChangeRoadImpactIncidentReportCommand;

public class SetChangeRoadImpactIncidentReportCommandHelper {

	private SetChangeRoadImpactIncidentReportCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetChangeRoadImpactIncidentReportCommand get(String incidentReportId, String roadImpactId) {

		SetChangeRoadImpactIncidentReportCommand command = null;

		if (StringUtils.isNoneBlank(incidentReportId)) {

			Key incidentKey = new Key(incidentReportId);

			if (incidentKey.getClazz() == ImsIncidentReport.class) {

				command = new SetChangeRoadImpactIncidentReportCommand();
				command.setIncidentReportId(Long.valueOf(incidentKey.getId(1)));

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
