import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import groovy.json.JsonSlurper;
import java.util.Date;
import java.util.Calendar;
import org.apache.commons.lang3.tuple.Pair;
import java.util.Random;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.data.core.model.command.SetElementValuesPublishCommand;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;


/**
 *
 * ChangeMeasureETD: Save de measures to detectors of ETD
 *
 */
class ChangeMeasureETD {

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
	
	static final Long PARAM_MEASURE_VEH_LENGTH_1 = 20L;
	static final Long PARAM_MEASURE_VEH_LENGTH_2 = 21L;

	static final Long PARAM_MEASURE_VEH_SPEED_1 = 40L;
	static final Long PARAM_MEASURE_VEH_SPEED_2 = 41L;
	static final Long PARAM_MEASURE_VEH_SPEED_3 = 42L;	

	//Alarms
	static final Long ALARM_DETECTOR_FAIL = 34L;
	static final Long ALARM_DETECTOR_CONGESTION = 36L;
	
	org.apache.logging.log4j.Logger log;
	
	ChangeMeasureETD(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {
		boolean res = true;
		int detArrayPos;	
		List<ElementValue> listElements;
		Random random = new Random();
		int numVeh, restante, clas;
		
		log.debug("Ejecutando ChangeMeasureETD Element" + element.getElementTypeId() + ":" + element.getId());
		try {

			if (elementValues != null) {
				// Search detector childs of ETDs
				List<ElementHierarchy> childs;
				childs = EntitiesManager.getInstance().getsByParent(element.getElementTypeId(), element.getId(), false);
				
				for (ElementHierarchy child : childs){
					Element childElement = EntitiesManager.getInstance().getElement(child.getChildElementTypeId(), child.getChildElementId());					
					ElementValue detOrder = EntitiesManager.getInstance().getElementValueConfig(childElement, PARAM_CONFIG_ORDER);	
					
					detArrayPos = Integer.parseInt(detOrder.getValue()) - 1;
					listElements = new ArrayList();
					
					Date date = new Date();
					listElements.add(elementSetValue(PARAM_MEASURE_DATE, TYPE_PARAM_MEASURE, childElement.getId(), String.valueOf(obtenerFecha())));	
					listElements.add(elementSetValue(PARAM_MEASURE_PERIOD, TYPE_PARAM_MEASURE, childElement.getId(), "1"));	
					listElements.add(elementSetValue(PARAM_MEASURE_CURRENT_DIRECTION, TYPE_PARAM_MEASURE, childElement.getId(), "true"));	
					
					numVeh = random.nextInt(15) + 1;
					listElements.add(elementSetValue(PARAM_MEASURE_NVEHICLES, TYPE_PARAM_MEASURE, childElement.getId(), String.valueOf(numVeh)));	
					listElements.add(elementSetValue(PARAM_MEASURE_OCCUPANCY, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(random.nextInt(40) + 1)));	
					listElements.add(elementSetValue(PARAM_MEASURE_KAMIKAZE, TYPE_PARAM_MEASURE, childElement.getId(),"0"));	
					listElements.add(elementSetValue(PARAM_MEASURE_SPEED, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(random.nextInt(51) + 90)));	
					listElements.add(elementSetValue(PARAM_MEASURE_GAP, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(random.nextInt(71) + 30)));	
					listElements.add(elementSetValue(PARAM_MEASURE_LENGTH, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(random.nextInt(81) + 40)));									
					listElements.add(elementSetValue(PARAM_MEASURE_CONGESTION, TYPE_PARAM_MEASURE, childElement.getId(),"0"));	
					listElements.add(elementSetValue(PARAM_MEASURE_FAIL_DATA, TYPE_PARAM_MEASURE, childElement.getId(), "0"));	
					
					clas = random.nextInt(numVeh)
					restante = numVeh - clas;
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_LENGTH_1, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(clas)));
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_LENGTH_2, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(restante)));
					
					clas = random.nextInt(numVeh)
					restante = numVeh - clas;
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_SPEED_1, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(clas)));
					
					clas = random.nextInt(restante)
					restante = restante - clas;
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_SPEED_2, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(clas)));
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_SPEED_3, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(restante)));
					
					
					ElementValue[] itemsArray = new ElementValue[listElements.size()];
					itemsArray = listElements.toArray(itemsArray);
					EntitiesManager.getInstance().putElementValues(itemsArray);
					
					SetElementValuesPublishCommand setElementValuesPublishCommand = new SetElementValuesPublishCommand();
					setElementValuesPublishCommand.setElementValues(new HashSet<>(listElements));
				}
			}
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return res;
	}
	
	long obtenerFecha() {
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.SECOND, 0);

        long milliseconds = calendar.getTimeInMillis();

        return milliseconds;
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
}
