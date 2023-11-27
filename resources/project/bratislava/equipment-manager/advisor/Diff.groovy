import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;


import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;

/**
 *
 * Diff: 
 *
 */
class Diff {

	// ID Elements Params
	static final Long PARAM_MEASURE_AVG_SPEED = 9L;
	static final Long PARAM_MEASURE_INTENSITY = 4L;
	static final Long ELEMENT_TYPE_SECTION = 4L;
	static final Long MAX_DIFF_SPEED = 30L;
	static final Long SPEED_TH1 = 70L;
	static final Long SPEED_TH2 = 50L;
	static final Long ALARM_CONFIGID_TH1 = 4100004L;
	static final Long ALARM_CONFIGID_TH2 = 4200004L;

	org.apache.logging.log4j.Logger log;
	
	Diff(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {
		boolean res = true;
		Element childElement;
		ElementValue avgSpeedChild, avgSpeedParent, intensityChild, intensityParent;
		
		SetActivationAlarmsCommand setActivationAlarmsCommandList = new SetActivationAlarmsCommand();
		SetDeactivationAlarmsCommand setDeactivationAlarmsCommandList = new SetDeactivationAlarmsCommand();
		AlarmConfig alarmConfig = new AlarmConfig();
	
		try {
			List<ElementHierarchy> childs = EntitiesManager.getInstance().getsByParent(element.getElementTypeId(), element.getId(), false);
		
			if (!CollectionUtils.isEmpty(childs)) {
				for (ElementHierarchy child : childs){

					if(child.getChildElementTypeId() == ELEMENT_TYPE_SECTION){
						childElement = EntitiesManager.getInstance().getElement(child.getChildElementTypeId(), child.getChildElementId());					
		
						if (childElement != null){
							avgSpeedChild = EntitiesManager.getInstance().getElementValueMeasure(childElement, PARAM_MEASURE_AVG_SPEED);
							avgSpeedParent = EntitiesManager.getInstance().getElementValueMeasure(element, PARAM_MEASURE_AVG_SPEED);
							intensityChild = EntitiesManager.getInstance().getElementValueMeasure(childElement, PARAM_MEASURE_INTENSITY);
							intensityParent = EntitiesManager.getInstance().getElementValueMeasure(element, PARAM_MEASURE_INTENSITY);
							
							if(intensityChild.getValue() != null && intensityParent != null && Integer.parseInt(intensityChild.getValue()) > 1 && Integer.parseInt(intensityParent.getValue()) > 1){
								if(avgSpeedChild.getValue() != null && avgSpeedParent.getValue() != null){
									
									int diffSpeed = Integer.parseInt(avgSpeedChild.getValue()) - Integer.parseInt(avgSpeedParent.getValue());
								
									if(diffSpeed > MAX_DIFF_SPEED && Integer.parseInt(avgSpeedParent.getValue()) < SPEED_TH2){
										
										log.error("Se cumple criterio > maxspeed y < speed_th2"); 
										log.error("El valor de la velocidad del padre Element:4:" + element.getId() + " --> " +  avgSpeedParent); 
										log.error("El valor de la velocidad del hijo Element:4:" + childElement.getId() + " --> " +  intensityChild); 
										log.error("El valor de la resta hijo - padre: " + diffSpeed); 
										
										alarmConfig.setId(ALARM_CONFIGID_TH2);
										setActivationAlarmsCommandList.add(element, alarmConfig);
										
										alarmConfig.setId(ALARM_CONFIGID_TH1);
										setDeactivationAlarmsCommandList.add(element, alarmConfig);
									}else if(diffSpeed > MAX_DIFF_SPEED && Integer.parseInt(avgSpeedParent.getValue()) < SPEED_TH1){
									
										log.error("Se cumple criterio > maxspeed y < speed_th1"); 
										log.error("El valor de la velocidad del padre Element:4:" + element.getId() + " --> " +  avgSpeedParent); 
										log.error("El valor de la velocidad del hijo Element:4:" + childElement.getId() + " --> " +  intensityChild); 
										log.error("El valor de la resta hijo - padre: " + diffSpeed); 
										
										alarmConfig.setId(ALARM_CONFIGID_TH1);
										setActivationAlarmsCommandList.add(element, alarmConfig);
										
										alarmConfig.setId(ALARM_CONFIGID_TH2);
										setDeactivationAlarmsCommandList.add(element, alarmConfig);									
									}else{
									
										alarmConfig.setId(ALARM_CONFIGID_TH1);
										setDeactivationAlarmsCommandList.add(element, alarmConfig);
										
										alarmConfig.setId(ALARM_CONFIGID_TH2);
										setDeactivationAlarmsCommandList.add(element, alarmConfig);										
									}
									
									EntitiesManager.getInstance().sendCommand(setActivationAlarmsCommandList);
									EntitiesManager.getInstance().sendCommand(setDeactivationAlarmsCommandList);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return res;
	}
}
