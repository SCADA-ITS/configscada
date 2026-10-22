package com.revenga.rits.back.equipment.manager.data.maintenance;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.entities.lib.repository.entity.EntityOnChange;
import com.revenga.rits.back.equipment.manager.data.ElementData;
import com.revenga.rits.back.equipment.manager.executor.coordinator.ElementStateCoordinator;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

public class AlarmConfigOnChange extends EntityOnChange {

	public AlarmConfigOnChange() {
		
		super(AlarmConfig.class);
	}

	@Override
	protected void create() {
		
		EntitiesManager.getInstance().getRootData().fill(basicElement);
	}

	@Override
	protected void update() {

		EntitiesManager.getInstance().getRootData().fill(basicElement);
	}

	@Override
	protected void delete() {

		EntitiesManager.getInstance().getRootData().fill(basicElement);

		if (basicElement instanceof AlarmConfig) {

			AlarmConfig alarmConfig = (AlarmConfig) basicElement;
			
			ElementData elementData = EntitiesManager.getInstance().getRootData()
					.getElementData(alarmConfig.getElementTypeId());
			
			if (elementData != null) {
			
				List<Alarm> currentAlarms = EntitiesManager.getInstance().getRootData().getAlarms();

				if (!CollectionUtils.isEmpty(currentAlarms)) {

					currentAlarms = currentAlarms.stream().filter(e -> e.getAlarmConfigId().equals(alarmConfig.getId()))
							.collect(Collectors.toList());

					if (!CollectionUtils.isEmpty(currentAlarms)) {
						
						currentAlarms.forEach(currentAlarm -> {
							
							Element element = elementData.get(currentAlarm.getTargetElementId());
							
							if (element != null) {
								
								EntitiesManager.getInstance().getRootData().lock(element.getElementTypeId(), element.getId());
							
								if (currentAlarm.getRecognized() == null) {
									
									currentAlarm.setRecognized(Instant.now().toEpochMilli());
									currentAlarm.setRecognizedUserId(User.USER_SYSTEM);
									currentAlarm.setRecognizedComments("LBL_AUTOMATICALLY_TREATED");
								}
								
								elementData.deleteAlarm(currentAlarm);
								
								(new ElementStateCoordinator(element)).calculateState();
								
								EntitiesManager.getInstance().getRootData().unlock(element.getElementTypeId(), element.getId());
							}
						});
					}
				}
			}
		}
	}
		
}
