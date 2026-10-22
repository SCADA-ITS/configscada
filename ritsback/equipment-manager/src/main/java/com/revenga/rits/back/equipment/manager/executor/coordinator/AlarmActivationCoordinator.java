package com.revenga.rits.back.equipment.manager.executor.coordinator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AlarmActivationCoordinator extends EntityCoordinator<List<AlarmConfig>> {

	public AlarmActivationCoordinator(Element element) {
		super(element);
	}

	@Override
	public int update(List<AlarmConfig> alarmConfigs) {

		List<Alarm> alarms = new ArrayList<>();

		try {

			if (!CollectionUtils.isEmpty(alarmConfigs)) {
				
				for (AlarmConfig alarmConfig : alarmConfigs) {
					
					if (CollectionUtils.isEmpty(alarmConfig.getAlarmConfigMeasures())) {
						
						Alarm alarmToUpdated = EntitiesManager.getInstance().removeFromMinTimeMap(alarmConfig, element);

						if (alarmToUpdated != null) {
							
							alarmToUpdated = elementData.updateAlarm(alarmToUpdated);
							
							log.debug(String.format(
									"%s => Update Alarm(%d) AlarmConfig %d, %s",
									(new Key(element)).getSimpleValue(), alarmToUpdated.getId(),
											alarmToUpdated.getAlarmConfig().getId(),
											alarmToUpdated.getAlarmConfig().getAlias()));
						} else {
						
							Alarm alarmCreated = elementData.createAlarm(alarmConfig, element);
							
							if (alarmCreated != null) {
								
								log.debug(String.format("%s => Register Alarm(%d) Recognized: %s Filtered: %s AlarmConfig %d, %s",
										(new Key(element)).getSimpleValue(), alarmCreated.getId(), 
										alarmCreated.getRecognized() != null,
										alarmCreated.getFilteredUntil() != null && alarmCreated.getFilteredUntil() > Instant.now().toEpochMilli(),
										alarmCreated.getAlarmConfig().getId(),
										alarmCreated.getAlarmConfig().getAlias()));
								
								(new ElementStateCoordinator(element)).calculateState();
								
								alarms.add(alarmCreated);
							}
							else {
								
								log.warn(String.format(
										"AlarmConfig %d cannot generate for element %s",
										alarmConfig.getId(), elementKey.getSimpleValue()));
							}
						}
					}
					else {
						
						log.warn(String.format(
								"AlarmConfig %d cannot generate for element %s (measures dependant are automatic)",
								alarmConfig.getId(), elementKey.getSimpleValue()));
					}
				}
			}		

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarms.size();
	}
}