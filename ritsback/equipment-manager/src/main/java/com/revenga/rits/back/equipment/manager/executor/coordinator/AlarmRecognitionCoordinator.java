package com.revenga.rits.back.equipment.manager.executor.coordinator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.Element;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AlarmRecognitionCoordinator extends EntityCoordinator<Map<Long, Object>> {

	public static final Long PROP_ALARM = 1L;
	public static final Long PROP_USER_ID = 2L;
	public static final Long PROP_USER_COMMENT = 3L;
	public static final Long PROP_DISAGREE= 4L;
	
	public AlarmRecognitionCoordinator(Element element) {
		super(element);
	}

		@Override
	public int update(Map<Long, Object> mapProps) {

		List<Alarm> alarms = new ArrayList<>();

		try {
			
			Alarm alarm = (Alarm) mapProps.get(PROP_ALARM);
			Long userId = (Long) mapProps.get(PROP_USER_ID);
			String userComment = (String) mapProps.get(PROP_USER_COMMENT);
			Boolean disagree = (Boolean) mapProps.get(PROP_DISAGREE);

			List<Alarm> alarmsElement = element.getAlarms();
			
			if (!CollectionUtils.isEmpty(alarmsElement)) {
				
				Alarm alarmToRecognize = alarmsElement.stream().filter(e -> e.getId().equals(alarm.getId())).findFirst()
						.orElse(null);
				
				if (alarmToRecognize != null && alarmToRecognize.getRecognized() == null) {
					
					elementData.recognizeAlarm(alarmToRecognize, userId, userComment, disagree);
					
				}
				else {
				
					log.warn(String.format("Alarm %d has already been recognized", alarm.getId()));
				}
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarms.size();
	}
}