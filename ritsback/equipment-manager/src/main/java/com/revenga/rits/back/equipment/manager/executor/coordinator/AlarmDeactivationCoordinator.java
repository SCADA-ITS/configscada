package com.revenga.rits.back.equipment.manager.executor.coordinator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AlarmDeactivationCoordinator extends EntityCoordinator<Map<Long, Object>> {
	
	public static final Long PROP_ALARM = 1L;
	public static final Long PROP_ALARM_CONFIG = 2L;
	public static final Long PROP_USER_ID = 3L;
	public static final Long PROP_USER_COMMENT = 4L;

	public AlarmDeactivationCoordinator(Element element) {
		super(element);
	}

	@Override
	public int update(Map<Long, Object> mapProps) {

		List<Alarm> alarms = new ArrayList<>();
		
		try {
			
			Alarm alarm = (Alarm) mapProps.get(PROP_ALARM);
			AlarmConfig alarmConfig = (AlarmConfig) mapProps.get(PROP_ALARM_CONFIG);
			Long userId = (Long) mapProps.get(PROP_USER_ID);
			String userComment = (String) mapProps.get(PROP_USER_COMMENT);
			
			if (alarm != null) {
				
				Alarm alarmDeleted = deactivateByAlarm(alarm, userId, userComment);
				
				if (alarmDeleted != null) {
					
					alarms.add(alarmDeleted);
				}
			}
			else if (alarmConfig != null) {
				
				Alarm alarmDeleted = deactivateByAlarmConfig(alarmConfig, userId, userComment);
				
				if (alarmDeleted != null) {
					
					alarms.add(alarmDeleted);
				}
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarms.size();
	}
	
	private Alarm deactivateByAlarm(Alarm alarm, Long userId, String userComment) {
		
		Alarm alarmDeleted = null;
		
		if (!CollectionUtils.isEmpty(element.getAlarms())) {
			
			Alarm alarmToDelete = element.getAlarms().stream().filter(
					e -> e.getId().equals(alarm.getId()) && e.getActivation() != null && e.getDeactivation() == null)
					.findFirst().orElse(null);	
			
			if (alarmToDelete != null) {
				
				if (alarmToDelete.getActivation() != null && alarmToDelete.getDeactivation() == null) {
					
					alarmDeleted = elementData.deleteAlarm(alarmToDelete, userId, userComment);
					
					if (alarmDeleted != null) {
						
						log.debug(String.format("%s => Unregister Alarm(%d) Recognized: %s AlarmConfig %d, %s",
								(new Key(element)).getSimpleValue(), alarmDeleted.getId(), 
								alarmDeleted.getRecognized() != null,
								alarmDeleted.getAlarmConfig().getId(),
								alarmDeleted.getAlarmConfig().getAlias()));
						
						(new ElementStateCoordinator(element)).calculateState();
					}
				}
				else {
					
					log.warn(String.format("Alarm %d has already been deleted", alarm.getId()));			
				}
			}
		}	
		
		return alarmDeleted;
	}
	
	private Alarm deactivateByAlarmConfig(AlarmConfig alarmConfig, Long userId, String userComment) {
		
		Alarm alarmDeleted = null;
		
		if (!CollectionUtils.isEmpty(element.getAlarms())) {
			
			Alarm alarmToDelete = element.getAlarms().stream().filter(e -> e.getAlarmConfigId().equals(alarmConfig.getId())
					&& e.getActivation() != null && e.getDeactivation() == null).findFirst().orElse(null);
			
			if (alarmToDelete != null) {
				
				alarmDeleted = elementData.deleteAlarm(alarmToDelete, userId, userComment);
				
				if (alarmDeleted != null) {
				
					log.debug(String.format("%s => Unregister Alarm(%d) Recognized: %s AlarmConfig %d, %s",
							(new Key(element)).getSimpleValue(), alarmDeleted.getId(), 
							alarmDeleted.getRecognized() != null,
							alarmDeleted.getAlarmConfig().getId(),
							alarmDeleted.getAlarmConfig().getAlias()));
					
					(new ElementStateCoordinator(element)).calculateState();
				}
			}
		}
		
		return alarmDeleted;
	}
}