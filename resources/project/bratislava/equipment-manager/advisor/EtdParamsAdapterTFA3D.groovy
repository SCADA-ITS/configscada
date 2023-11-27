import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import groovy.json.JsonSlurper;
import java.time.Instant;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.equipment.manager.helper.GroovyHelper;
import com.revenga.rits.back.data.core.model.command.SetElementValuesPublishCommand;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;


/**
 *
 * EtdParamsAdapterTFA: Save de measures to detectors of ETD
 *
 */
class EtdParamsAdapterTFA3D {

    //BBDD Params
	static final Long TYPE_PARAM_CONFIG = 1L;
	static final Long TYPE_PARAM_MEASURE = 2L;
	static final Long PARAM_CONFIG_ORDER = 2L;
	static final Long ELEMENT_TYPE_ID_DET = 3L;
	
	//Detector Params
	static final Long PARAM_MEASURE_DATE = 1L;
	static final Long PARAM_MEASURE_PERIOD =2L;
	static final Long PARAM_MEASURE_CURRENT_DIRECTION = 3L;
	static final Long PARAM_MEASURE_NVEHICLES = 4L;
	static final Long PARAM_MEASURE_OCCUPANCY = 6L;
	static final Long PARAM_MEASURE_CONGESTION = 7L;
	static final Long PARAM_MEASURE_KAMIKAZE = 8L;
	static final Long PARAM_MEASURE_SPEED = 9L;
	static final Long PARAM_MEASURE_GAP = 10L;
	static final Long PARAM_MEASURE_FAIL_DATA = 13L;
	static final Long PARAM_MEASURE_LENGTH = 17L;
	
	static final Long PARAM_MEASURE_VEH_PA_1 = 100L;
	static final Long PARAM_MEASURE_VEH_PA_2 = 101L;
	static final Long PARAM_MEASURE_VEH_PA_3 = 102L;
	static final Long PARAM_MEASURE_VEH_PA_4 = 103L;
	static final Long PARAM_MEASURE_VEH_PA_5 = 104L;
	static final Long PARAM_MEASURE_VEH_PA_6 = 105L;
	static final Long PARAM_MEASURE_VEH_PA_7 = 106L;
	static final Long PARAM_MEASURE_VEH_PA_8 = 107L;
	static final Long PARAM_MEASURE_VEH_PA_9 = 108L;

	static final Long PARAM_MEASURE_VEH_SPEED_1 = 40L;
	static final Long PARAM_MEASURE_VEH_SPEED_2 = 41L;
	static final Long PARAM_MEASURE_VEH_SPEED_3 = 42L;
	static final Long PARAM_MEASURE_VEH_SPEED_4 = 43L;
	static final Long PARAM_MEASURE_VEH_SPEED_5 = 44L;
	static final Long PARAM_MEASURE_VEH_SPEED_6 = 45L;
	static final Long PARAM_MEASURE_VEH_SPEED_7 = 46L;
	static final Long PARAM_MEASURE_VEH_SPEED_8 = 47L;
	static final Long PARAM_MEASURE_VEH_SPEED_9 = 48L;		

	//Alarms
	static final Long ALARM_DETECTOR_FAIL = 139L;
	static final Long ALARM_DETECTOR_CONGESTION = 142L;
	
	org.apache.logging.log4j.Logger log;
	
	EtdParamsAdapterTFA3D(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {
		boolean res = true;
		int detArrayPos;					
		String val;
		List<Alarm> listAlarmsCreate;
		List<Alarm> listAlarmsRemove;
		List<ElementValue> listElements;
		
		log.debug("Ejecutando EtdParamsAdapterTFA3D Element" + element.getElementTypeId() + ":" + element.getId());
		try {

			if (elementValues != null) {
				// Search detector childs of ETDs
				List<ElementHierarchy> childs;
				childs = EntitiesManager.getInstance().getsByParent(element.getElementTypeId(), element.getId(), false);
				
				// Parse the json value
				def etd_info = new JsonSlurper().parseText(elementValues.get(0).getRight().getValue());	

				for (ElementHierarchy child : childs){
					Element childElement = EntitiesManager.getInstance().getElement(child.getChildElementTypeId(), child.getChildElementId());					
					ElementValue detOrder = EntitiesManager.getInstance().getElementValueConfig(childElement, PARAM_CONFIG_ORDER);	
					log.debug("child" + child);
					SetActivationAlarmsCommand setActivationAlarmsCommandList = new SetActivationAlarmsCommand();
					SetDeactivationAlarmsCommand setDeactivationAlarmsCommandList = new SetDeactivationAlarmsCommand();
									
					detArrayPos = Integer.parseInt(detOrder.getValue()) - 1;
					log.debug("detARRAYPOS-> " + detArrayPos);
					listAlarmsCreate = new ArrayList();
					listAlarmsRemove = new ArrayList();
					listElements = new ArrayList();
					listElements.add(elementSetValue(PARAM_MEASURE_DATE, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(etd_info.get(detArrayPos).timestamp)));	
					listElements.add(elementSetValue(PARAM_MEASURE_PERIOD, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(etd_info.get(detArrayPos).integration_period)));	
					listElements.add(elementSetValue(PARAM_MEASURE_CURRENT_DIRECTION, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(etd_info.get(detArrayPos).current_direction)));	
					listElements.add(elementSetValue(PARAM_MEASURE_NVEHICLES, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(etd_info.get(detArrayPos).n_vehicles)));	
					listElements.add(elementSetValue(PARAM_MEASURE_OCCUPANCY, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(Math.round(Double.valueOf(etd_info.get(detArrayPos).occupancy)))));	
					listElements.add(elementSetValue(PARAM_MEASURE_KAMIKAZE, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(etd_info.get(detArrayPos).kamikaze)));	
					listElements.add(elementSetValue(PARAM_MEASURE_SPEED, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(Math.round(Double.valueOf(etd_info.get(detArrayPos).average_speed)))));	
					listElements.add(elementSetValue(PARAM_MEASURE_GAP, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(etd_info.get(detArrayPos).gap)));	
					listElements.add(elementSetValue(PARAM_MEASURE_LENGTH, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(Math.round(Double.valueOf(etd_info.get(detArrayPos).average_length)))));	
					listElements.add(elementSetValue(PARAM_MEASURE_CONGESTION, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(etd_info.get(detArrayPos).congestion)));

					if(etd_info.get(detArrayPos).congestion){
						setActivationAlarmsCommandList.add(childElement, EntitiesManager.getInstance().getAlarmConfig(ALARM_DETECTOR_CONGESTION));
					}else{
						setDeactivationAlarmsCommandList.add(childElement, EntitiesManager.getInstance().getAlarmConfig(ALARM_DETECTOR_CONGESTION));
					}
					
					listElements.add(elementSetValue(PARAM_MEASURE_FAIL_DATA, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(etd_info.get(detArrayPos).fail_data)));	
					if(etd_info.get(detArrayPos).fail_data){
						setActivationAlarmsCommandList.add(childElement, EntitiesManager.getInstance().getAlarmConfig(ALARM_DETECTOR_FAIL));
					}else{
						setDeactivationAlarmsCommandList.add(childElement, EntitiesManager.getInstance().getAlarmConfig(ALARM_DETECTOR_FAIL));
					}
					
					val = String.valueOf(etd_info.get(detArrayPos).veh_pa_1 == null ? 0 : etd_info.get(detArrayPos).veh_pa_1);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_PA_1, TYPE_PARAM_MEASURE, childElement.getId(),val));
					
					val = String.valueOf(etd_info.get(detArrayPos).veh_pa_2 == null ? 0 : etd_info.get(detArrayPos).veh_pa_2);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_PA_2, TYPE_PARAM_MEASURE, childElement.getId(),val));

					val = String.valueOf(etd_info.get(detArrayPos).veh_pa_3 == null ? 0 : etd_info.get(detArrayPos).veh_pa_3);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_PA_3, TYPE_PARAM_MEASURE, childElement.getId(),val));

					val = String.valueOf(etd_info.get(detArrayPos).veh_pa_4 == null ? 0 : etd_info.get(detArrayPos).veh_pa_4);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_PA_4, TYPE_PARAM_MEASURE, childElement.getId(),val));

					val = String.valueOf(etd_info.get(detArrayPos).veh_pa_5 == null ? 0 : etd_info.get(detArrayPos).veh_pa_5);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_PA_5, TYPE_PARAM_MEASURE, childElement.getId(),val));

					val = String.valueOf(etd_info.get(detArrayPos).veh_pa_6 == null ? 0 : etd_info.get(detArrayPos).veh_pa_6);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_PA_6, TYPE_PARAM_MEASURE, childElement.getId(),val));

					val = String.valueOf(etd_info.get(detArrayPos).veh_pa_7 == null ? 0 : etd_info.get(detArrayPos).veh_pa_7);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_PA_7, TYPE_PARAM_MEASURE, childElement.getId(),val));

					val = String.valueOf(etd_info.get(detArrayPos).veh_pa_8 == null ? 0 : etd_info.get(detArrayPos).veh_pa_8);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_PA_8, TYPE_PARAM_MEASURE, childElement.getId(),val));

					val = String.valueOf(etd_info.get(detArrayPos).veh_pa_9 == null ? 0 : etd_info.get(detArrayPos).veh_pa_9);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_PA_9, TYPE_PARAM_MEASURE, childElement.getId(),val));

					val = String.valueOf(etd_info.get(detArrayPos).veh_speed_1 == null ? 0 : etd_info.get(detArrayPos).veh_speed_1);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_SPEED_1, TYPE_PARAM_MEASURE, childElement.getId(),val));
					
					val = String.valueOf(etd_info.get(detArrayPos).veh_speed_2 == null ? 0 : etd_info.get(detArrayPos).veh_speed_2);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_SPEED_2, TYPE_PARAM_MEASURE, childElement.getId(),val));
					
					val = String.valueOf(etd_info.get(detArrayPos).veh_speed_3 == null ? 0 : etd_info.get(detArrayPos).veh_speed_3);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_SPEED_3, TYPE_PARAM_MEASURE, childElement.getId(),val));

					val = String.valueOf(etd_info.get(detArrayPos).veh_speed_4 == null ? 0 : etd_info.get(detArrayPos).veh_speed_4);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_SPEED_4, TYPE_PARAM_MEASURE, childElement.getId(),val));

					val = String.valueOf(etd_info.get(detArrayPos).veh_speed_5 == null ? 0 : etd_info.get(detArrayPos).veh_speed_5);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_SPEED_5, TYPE_PARAM_MEASURE, childElement.getId(),val));

					val = String.valueOf(etd_info.get(detArrayPos).veh_speed_6 == null ? 0 : etd_info.get(detArrayPos).veh_speed_6);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_SPEED_6, TYPE_PARAM_MEASURE, childElement.getId(),val));

					val = String.valueOf(etd_info.get(detArrayPos).veh_speed_7 == null ? 0 : etd_info.get(detArrayPos).veh_speed_7);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_SPEED_7, TYPE_PARAM_MEASURE, childElement.getId(),val));

					val = String.valueOf(etd_info.get(detArrayPos).veh_speed_8 == null ? 0 : etd_info.get(detArrayPos).veh_speed_8);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_SPEED_8, TYPE_PARAM_MEASURE, childElement.getId(),val));

					val = String.valueOf(etd_info.get(detArrayPos).veh_speed_9 == null ? 0 : etd_info.get(detArrayPos).veh_speed_9);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_SPEED_9, TYPE_PARAM_MEASURE, childElement.getId(),val));
					
					ElementValue[] itemsArray = new ElementValue[listElements.size()];
					itemsArray = listElements.toArray(itemsArray);
					EntitiesManager.getInstance().putElementValues(itemsArray);
					SetElementValuesPublishCommand setElementValuesPublishCommand = new SetElementValuesPublishCommand();
					setElementValuesPublishCommand.setElementValues(new HashSet<>(listElements));										
					
					EntitiesManager.getInstance().sendCommand(setActivationAlarmsCommandList);
					EntitiesManager.getInstance().sendCommand(setDeactivationAlarmsCommandList);
					EntitiesManager.getInstance().sendCommand(setElementValuesPublishCommand);
				}
			}	
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return res;
	}
	
	
	ElementValue elementSetValue(Long elementTypeParamId, Long paramTypeId, Long elementId, String value){
		ElementValue element = new ElementValue();
	    element.setElementTypeId(ELEMENT_TYPE_ID_DET);
	    element.setElementTypeParamId(elementTypeParamId);
	    element.setParamTypeId(paramTypeId);
	    element.setElementId(elementId);
	    element.setValue(value);
	    
	    return element;
	}
	
	Alarm createAlarm(AlarmConfig alarmConfig, Element element) {
		
		Alarm alarmMine = null;
		
		if (alarmConfig.getMine().booleanValue()) {
			
			alarmMine = new Alarm();
			
			alarmMine.setAlarmConfigId(alarmConfig.getId());
			
			if (element != null) {
			
				alarmMine.setSourceElementId(element.getId());
				alarmMine.setSourceElementTypeId(element.getElementTypeId());
				alarmMine.setTargetElementId(element.getId());
				alarmMine.setTargetElementTypeId(element.getElementTypeId());
			}
			
			if (!alarmConfig.getTreatment().booleanValue()) {
				
				alarmMine.setRecognized(Instant.now().toEpochMilli());
				alarmMine.setRecognizedComments("Automatically treated by system");
			}
			
			alarmMine.generateId();
		}
		
		return alarmMine;
	}
	
	public List<Alarm> unregister(AlarmConfig alarmConfig, Element element, List<Alarm> currentAlarms) {
		
		List<Alarm> alarmsResult = new ArrayList<>();
		
		List<Alarm> alarmsMine = removeAlarm(alarmConfig, element, currentAlarms);
		
		if (!CollectionUtils.isEmpty(alarmsMine)) {
			
			alarmsResult.addAll(alarmsMine);
		}
		
		List<Alarm> alarmsYours = removeAlarmYours(alarmConfig, element, currentAlarms);
		
		if (!CollectionUtils.isEmpty(alarmsYours)) {
			
			alarmsResult.addAll(alarmsYours);
		}
			
		return alarmsResult;
	}
}
