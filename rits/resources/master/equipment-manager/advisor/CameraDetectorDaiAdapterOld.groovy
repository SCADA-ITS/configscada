import com.revenga.rits.back.data.core.model.command.SetElementStatesChangeCommand
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;


import groovy.json.JsonSlurper;
import java.time.Instant;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.data.core.model.command.SetElementValuesPublishCommand;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;


/**
 *
 * CameraDetectorDaiAdapter: Save de measures of Camera Detector DAI 
 *
 */

class CameraDetectorDaiAdapter {

 	//BBDD Params
	static final Long TYPE_PARAM_CONFIG = 1L;
	static final Long TYPE_PARAM_MEASURE = 2L;
	static final Long PARAM_CONFIG_ZONE_ID = 5L;
	
	
	// ID Elements Params
	static final Long PARAM_TYPE_MEASURE = 2L;
	static final Long PARAM_MEASURE_PLACE = 4L;
	static final Long PARAM_MEASURE_TYPE_INCIDENT = 5L;
	
	//Alarms
	static final Long ALARM_INCIDENT = 86L;
	static final Long ALARM_VEH_WRONG_DIRECTION = 1301L;
	static final Long ALARM_OBJ_LEFT_ROADSIDE = 1302L;
	static final Long ALARM_OBJ_RIGHT_ROADSIDE = 1303L;
	static final Long ALARM_VEH_SLOW_LEFT_ROADSIDE = 1304L;
	static final Long ALARM_VEH_SLOW_RIGHT_ROADSIDE = 1305L;
	static final Long ALARM_OBJ_LEFT_RAIL = 1306L;
	static final Long ALARM_OBJ_RIGHT_RAIL = 1307L;
	static final Long ALARM_VEH_SLOW_LEFT_RAIL = 1308L;
	static final Long ALARM_VEH_SLOW_RIGHT_RAIL = 1309L;
	static final Long ALARM_PEDESTRIAN = 1310L;
	static final Long ALARM_VEH_STOP_LEFT_ROADSIDE = 1311L;
	static final Long ALARM_VEH_STOP_RIGHT_ROADSIDE = 1312L;
	static final Long ALARM_VEH_STOP_LEFT_RAIL = 1313L;
	static final Long ALARM_VEH_STOP_RIGHT_RAIL = 1314L;
	
	//DAI Type incident
	static final Long DAI_VEH_WRONG_DIRECTION = 1L;
	static final Long DAI_VEH_SLOW = 2L;
	static final Long DAI_PEDESTRIAN = 3L;
	static final Long DAI_OBJECT = 4L;
	static final Long DAI_LOW_VISIBILITY = 5L;
	static final Long DAI_VEH_STOP = 6L;
	
	//DAI Place
	static final Long DAI_LEFT_ROADSIDE = 1L;
	static final Long DAI_LEFT_RAIL = 2L;
	static final Long DAI_RIGHT_RAIL = 3L;
	static final Long DAI_RIGHT_ROADSIDE = 4L;
	

	org.apache.logging.log4j.Logger log;
	
	CameraDetectorDaiAdapter(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {
		int place = 0,type_incident = 0;

		try{
		   
			if (!CollectionUtils.isEmpty(elementValues)) {
				
				for (Pair<ElementValue, ElementValue> pair : elementValues) {
					
					ElementValue elementValue = pair.getRight(); //Actual value
					if(elementValue.getValue() != null && elementValue.getValue() != "null") {
						switch(elementValue.getElementTypeParamId()){
							case PARAM_MEASURE_PLACE:
								place = Integer.parseInt(elementValue.getValue());
								break;
							case PARAM_MEASURE_TYPE_INCIDENT:
								type_incident = Integer.parseInt(elementValue.getValue());
								break;
							default:
							break;
						}
					}
					
				}
			
		
			
					//Obtenemos el ultimo valor
					List<ElementValue> elementValueList;
					elementValueList = EntitiesManager.getInstance().getElementValues(element.getElementTypeId(), element.getId());
											
					for (ElementValue elementValue : elementValueList) {
						if(elementValue.getParamTypeId() == PARAM_TYPE_MEASURE){
							
							if(elementValue.getValue() != null && elementValue.getValue() != "null") {
								switch(elementValue.getElementTypeParamId()){
									case PARAM_MEASURE_TYPE_INCIDENT:
										type_incident = Integer.parseInt(elementValue.getValue());
										break;
									case PARAM_MEASURE_PLACE:
										place = Integer.parseInt(elementValue.getValue());
										break;
									default:
									break;
								}
								
							}
						
							
						}
								
					}
				
				
				SetActivationAlarmsCommand setActivationAlarmsCommandList = new SetActivationAlarmsCommand();
				SetDeactivationAlarmsCommand setDeactivationAlarmsCommandList = new SetDeactivationAlarmsCommand();
				AlarmConfig alarmConfig = new AlarmConfig();
				
				//Llamo al comando de señalizacion
				if (type_incident && place){
					
					switch(type_incident){
						case DAI_VEH_WRONG_DIRECTION:
							alarmConfig.setId(ALARM_VEH_WRONG_DIRECTION);
							setActivationAlarmsCommandList.add(element, alarmConfig);
						break;
						case DAI_VEH_SLOW:
							if (place == DAI_LEFT_ROADSIDE)
							{
								alarmConfig.setId(ALARM_VEH_SLOW_LEFT_ROADSIDE);
								setActivationAlarmsCommandList.add(element, alarmConfig);
							}
							else if (place == DAI_RIGHT_ROADSIDE)
							{
								alarmConfig.setId(ALARM_VEH_SLOW_RIGHT_ROADSIDE);
								setActivationAlarmsCommandList.add(element, alarmConfig);
							}
							else if(place == DAI_LEFT_RAIL)
							{
								alarmConfig.setId(ALARM_VEH_SLOW_LEFT_RAIL);
								setActivationAlarmsCommandList.add(element, alarmConfig);
							}
							else if(place == DAI_RIGHT_RAIL)
							{
								alarmConfig.setId(ALARM_VEH_SLOW_RIGHT_RAIL);
								setActivationAlarmsCommandList.add(element, alarmConfig);
							}
						break;
						case DAI_OBJECT:
						
							if (place == DAI_LEFT_ROADSIDE)
							{
								alarmConfig.setId(ALARM_OBJ_LEFT_ROADSIDE);
								setActivationAlarmsCommandList.add(element, alarmConfig);
							}
							else if (place == DAI_RIGHT_ROADSIDE)
							{
								alarmConfig.setId(ALARM_OBJ_RIGHT_ROADSIDE);
								setActivationAlarmsCommandList.add(element, alarmConfig);
							}
							else if (place == DAI_LEFT_RAIL)
							{
								alarmConfig.setId(ALARM_OBJ_LEFT_RAIL);
								setActivationAlarmsCommandList.add(element, alarmConfig);
							}
							else if (place == DAI_RIGHT_RAIL)
							{
								alarmConfig.setId(ALARM_OBJ_RIGHT_RAIL);
								setActivationAlarmsCommandList.add(element, alarmConfig);
							}
						
						break;
						case DAI_PEDESTRIAN:
							alarmConfig.setId(ALARM_PEDESTRIAN);
							setActivationAlarmsCommandList.add(element, alarmConfig);
						break;
						case DAI_VEH_STOP:
						if (place == DAI_LEFT_ROADSIDE)
						{
							alarmConfig.setId(ALARM_VEH_STOP_LEFT_ROADSIDE);
							setActivationAlarmsCommandList.add(element, alarmConfig);
						}
						else if (place == DAI_RIGHT_ROADSIDE)
						{
							alarmConfig.setId(ALARM_VEH_STOP_RIGHT_ROADSIDE);
							setActivationAlarmsCommandList.add(element, alarmConfig);
						}
						else if(place == DAI_LEFT_RAIL)
						{
							alarmConfig.setId(ALARM_VEH_STOP_LEFT_RAIL);
							setActivationAlarmsCommandList.add(element, alarmConfig);
						}
						else if(place == DAI_RIGHT_RAIL)
						{
							alarmConfig.setId(ALARM_VEH_STOP_RIGHT_RAIL);
							setActivationAlarmsCommandList.add(element, alarmConfig);
						}
					break;
						
					}
				}
				else{
					alarmConfig.setId(ALARM_VEH_WRONG_DIRECTION);
					setDeactivationAlarmsCommandList.add(element, alarmConfig);
					alarmConfig.setId(ALARM_VEH_SLOW_LEFT_ROADSIDE);
					setDeactivationAlarmsCommandList.add(element, alarmConfig);
					alarmConfig.setId(ALARM_VEH_SLOW_RIGHT_ROADSIDE);
					setDeactivationAlarmsCommandList.add(element, alarmConfig);
					alarmConfig.setId(ALARM_VEH_SLOW_LEFT_RAIL);
					setDeactivationAlarmsCommandList.add(element, alarmConfig);
					alarmConfig.setId(ALARM_VEH_SLOW_RIGHT_RAIL);
					setDeactivationAlarmsCommandList.add(element, alarmConfig);
					alarmConfig.setId(ALARM_OBJ_LEFT_ROADSIDE);
					setDeactivationAlarmsCommandList.add(element, alarmConfig);
					alarmConfig.setId(ALARM_OBJ_RIGHT_ROADSIDE);
					setDeactivationAlarmsCommandList.add(element, alarmConfig);
					alarmConfig.setId(ALARM_OBJ_LEFT_RAIL);
					setDeactivationAlarmsCommandList.add(element, alarmConfig);
					alarmConfig.setId(ALARM_OBJ_RIGHT_RAIL);
					setDeactivationAlarmsCommandList.add(element, alarmConfig);
					alarmConfig.setId(ALARM_PEDESTRIAN);
					setDeactivationAlarmsCommandList.add(element, alarmConfig);
					alarmConfig.setId(ALARM_VEH_STOP_LEFT_ROADSIDE);
					setDeactivationAlarmsCommandList.add(element, alarmConfig);
					alarmConfig.setId(ALARM_VEH_STOP_RIGHT_ROADSIDE);
					setDeactivationAlarmsCommandList.add(element, alarmConfig);
					alarmConfig.setId(ALARM_VEH_STOP_LEFT_RAIL);
					setDeactivationAlarmsCommandList.add(element, alarmConfig);
					alarmConfig.setId(ALARM_VEH_STOP_RIGHT_RAIL);
					setDeactivationAlarmsCommandList.add(element, alarmConfig);
					
				}
				
				EntitiesManager.getInstance().sendCommand(setActivationAlarmsCommandList);
				EntitiesManager.getInstance().sendCommand(setDeactivationAlarmsCommandList);
				
			}
			return true;
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.error(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
	
}