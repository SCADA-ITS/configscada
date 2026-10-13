package com.revenga.rits.back.equipment.manager.advisor;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.util.GroovyManager;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.equipment.manager.advisor.model.EquipmentChangeAlarmMessage;
import com.revenga.rits.back.equipment.manager.advisor.model.EquipmentChangeMeasureMessage;
import com.revenga.rits.back.equipment.manager.advisor.model.EquipmentChangeStateMessage;
import com.revenga.rits.back.equipment.manager.advisor.model.EquipmentMessage;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

import groovy.lang.GroovyObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EquipmentAdvisorProcessor {

	private static final String PATH_GROOVY_EQUIPMENT_MANAGER_ADVISOR = "equipment-manager/advisor/";
	private static final String METHOD_ON_MEASURE_CHANGE = "process";
	private static final String METHOD_ON_STATE_CHANGE = "process";
	private static final String METHOD_ON_ALARM_CHANGE = "process";

	public boolean process(EquipmentMessage equipmentMessage) {
		
		Boolean res = false;
				
		try {
			
			if (equipmentMessage instanceof EquipmentChangeMeasureMessage) {
				
				res = processOnMeasureChange((EquipmentChangeMeasureMessage) equipmentMessage);
			}
			else if (equipmentMessage instanceof EquipmentChangeStateMessage) {
				
				res = processOnStateChange((EquipmentChangeStateMessage) equipmentMessage);
			}
			else if (equipmentMessage instanceof EquipmentChangeAlarmMessage) {
				
				res = processOnAlarmChange((EquipmentChangeAlarmMessage) equipmentMessage);				
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return res;
	}
	
	private boolean processOnMeasureChange(EquipmentChangeMeasureMessage equipmentChangeMeasureMessage) {
		
		GroovyObject groovyObj;
		String groovyFile;
		
		if (equipmentChangeMeasureMessage.getElement() != null) {
		
			groovyFile = PATH_GROOVY_EQUIPMENT_MANAGER_ADVISOR + equipmentChangeMeasureMessage.getElement().getOnChangeMeasure();
			
			if (ResourcesUtil.getFile(groovyFile) != null) {
				
				groovyObj = GroovyManager.getInstance().getGroovyObject(groovyFile);
				
				if (groovyObj != null) {
			
					return (boolean) groovyObj.invokeMethod(METHOD_ON_MEASURE_CHANGE, new Object[] {
							equipmentChangeMeasureMessage.getElement(), equipmentChangeMeasureMessage.getElementValues() });
				}
			}			
		}
		
		return false;
	}
	
	private boolean processOnStateChange(EquipmentChangeStateMessage equipmentChangeStateMessage) {
		
		GroovyObject groovyObj;
		String groovyFile;
		
		if (equipmentChangeStateMessage.getElement() != null
				&& !StringUtils.isEmpty(equipmentChangeStateMessage.getElement().getOnChangeState())) {
		
			groovyFile = PATH_GROOVY_EQUIPMENT_MANAGER_ADVISOR + equipmentChangeStateMessage.getElement().getOnChangeState();
			
			if (ResourcesUtil.getFile(groovyFile) != null) {
				
				groovyObj = GroovyManager.getInstance().getGroovyObject(groovyFile);
				
				if (groovyObj != null) {
			
					return (boolean) groovyObj.invokeMethod(METHOD_ON_STATE_CHANGE, new Object[] {
							equipmentChangeStateMessage.getElement(), equipmentChangeStateMessage.getElementState() });
				}
			}
		}
		
		return false;
	}
	
	private boolean processOnAlarmChange(EquipmentChangeAlarmMessage equipmentChangeAlarmMessage) {
		
		GroovyObject groovyObj;
		String groovyFile;
		boolean res = true;
		
		if (!CollectionUtils.isEmpty(equipmentChangeAlarmMessage.getAlarms())) {
			
			for (Alarm alarm : equipmentChangeAlarmMessage.getAlarms()) {
				
				Element element = EntitiesManager.getInstance().getElement(alarm.getTargetElementTypeId(), alarm.getTargetElementId());
				
				if (element != null && !StringUtils.isEmpty(element.getOnChangeAlarm())) {
						
					groovyFile = PATH_GROOVY_EQUIPMENT_MANAGER_ADVISOR + element.getOnChangeAlarm();
					
					if (ResourcesUtil.getFile(groovyFile) != null) {
					
						groovyObj = GroovyManager.getInstance().getGroovyObject(groovyFile);
						
						if (groovyObj != null) {
						
							res &= (boolean) groovyObj.invokeMethod(METHOD_ON_ALARM_CHANGE, new Object[] { element, alarm });
						}
					}
				}
			}
		}
		
		return res;
	}
}
