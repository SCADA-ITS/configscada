package com.revenga.rits.back.equipment.manager.executor.coordinator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmFiltered;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.RequestCommand;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AlarmFilterCoordinator extends EntityCoordinator<Pair<AlarmFiltered, Boolean>> {

	public static final Long PROP_ALARM = 1L;
	public static final Long PROP_USER_ID = 2L;
	public static final Long PROP_USER_COMMENT = 3L;
	
	public AlarmFilterCoordinator(Element element) {
		super(element);
	}

	@Override
	public int update(Pair<AlarmFiltered, Boolean> pair) {

		List<Alarm> alarms = null;

		try {
			
			AlarmFiltered alarmFiltered = pair.getLeft();
			Boolean action = pair.getRight();

			if (action.booleanValue()) {

				alarms = actionFilterOn(alarmFiltered);

			} else {

				alarms = actionFilterOff(alarmFiltered);
			}
			
			if (!CollectionUtils.isEmpty(alarms)) {
				
				(new ElementStateCoordinator(element)).calculateState();
			}
			
			if (!action.booleanValue()) {
				
				RequestCommand requestCommand = new RequestCommand();

				requestCommand.add(element, RequestCommand.REQ_MEASURES);
				requestCommand.add(element, RequestCommand.REQ_ALARMS);

				EntitiesManager.getInstance().sendCommand(requestCommand);
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return !CollectionUtils.isEmpty(alarms) ? alarms.size() : 0; 
	}
		
	private List<Alarm> actionFilterOn(AlarmFiltered alarmFiltered) {

		List<Alarm> alarmsToDeactivate = new ArrayList<>();

		if (alarmFiltered.getFilteredUntil() != null) {
		
			if (alarmFiltered.getFilteredUntil() >= Instant.now().toEpochMilli()) {
				
				if (elementData.createAlarmConfigFiltered(alarmFiltered) != null) {
					
					List<Alarm> alarms = element.getAlarms();
					
					if (!CollectionUtils.isEmpty(alarms)) {
						
						alarms = alarms.stream().filter(e -> e.getAlarmConfigId().equals(alarmFiltered.getAlarmConfigId()))
								.collect(Collectors.toList());
						
						if (!CollectionUtils.isEmpty(alarms)) {
							
							for (Alarm alarm : alarms) {

								elementData.filterAlarm(alarm, alarmFiltered.getFilteredUntil());
								
								alarmsToDeactivate.add(alarm);
								
								if (alarm.getDeactivation() != null) {

									elementData.deleteAlarm(alarm);
								}
							}
						}
					}
				}

			} else {

				log.warn("User cannot filtered alarm with time previos to now. "
						+ Instant.ofEpochMilli(alarmFiltered.getFilteredUntil()).toString() + " < "
						+ Instant.now().toString());
			}
		}
		

		return alarmsToDeactivate;
	}
	
	private List<Alarm> actionFilterOff(AlarmFiltered alarmFiltered) {
		
		List<Alarm> alarmsToActivate = new ArrayList<>();
		List<Alarm> alarms = element.getAlarms();
		
		elementData.removeAlarmFiltered(alarmFiltered);
		
		if (!CollectionUtils.isEmpty(alarms)) {
			
			alarms = alarms.stream().filter(e -> e.getAlarmConfigId().equals(alarmFiltered.getAlarmConfigId()))
					.collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(alarms)) {
				
				for (Alarm alarm : alarms) {

					if (alarm.getFilteredUntil() != null) {

						elementData.unfilterAlarm(alarm);
						
						alarmsToActivate.add(alarm);
					}
				}
			}
		}

		return alarmsToActivate;
	}
}