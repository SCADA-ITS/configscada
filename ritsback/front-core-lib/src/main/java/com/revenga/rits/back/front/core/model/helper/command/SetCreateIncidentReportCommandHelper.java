package com.revenga.rits.back.front.core.model.helper.command;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.RoadImpact;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.SetCreateIncidentReportCommand;

public class SetCreateIncidentReportCommandHelper {

	private SetCreateIncidentReportCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @param incidentTypeId
	 * @param locationId
	 * @param incidentTypeCauseId
	 * @param alarmIds
	 * @return
	 */
	public static SetCreateIncidentReportCommand get(String incidentTypeId, String locationId, List<String> alarmIds,
			List<String> affectionStretchIds, String userId, String roadImpactId) {

		SetCreateIncidentReportCommand command = null;

		if (StringUtils.isNoneBlank(incidentTypeId, locationId)) {

			Key incidentTypeKey = new Key(incidentTypeId);
			Key locationKey = new Key(locationId);

			if (incidentTypeKey.getClazz() == ImsIncidentType.class && locationKey.getClazz() == Location.class) {

				command = new SetCreateIncidentReportCommand();
				command.setIncidentTypeId(Long.valueOf(incidentTypeKey.getId(1)));
				command.setLocationId(Long.valueOf(locationKey.getId(1)));

				if (CollectionUtils.isNotEmpty(alarmIds)) {

					for (String alarmId : alarmIds) {
						Key alarmKey = new Key(alarmId);

						if (alarmKey.getClazz() == Alarm.class) {

							command.addAlarmId(Long.valueOf(alarmKey.getId(1)));
						}
					}
				}
				
				if (CollectionUtils.isNotEmpty(affectionStretchIds)) {

					for (String affectionStretchId : affectionStretchIds) {
						Key affectionStretchKey = new Key(affectionStretchId);

						if (affectionStretchKey.getClazz() == Stretch.class) {

							command.addAffectionStretchId(Long.valueOf(affectionStretchKey.getId(1)));
						}
					}
				}
				
				if (userId != null) {
					
					Key userKey = new Key(userId);
					
					if (userKey.getClazz() == User.class) {
						
						command.setUserId(Long.valueOf(userKey.getId(1)));
					}
				}
				
				if (roadImpactId != null) {
					
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
