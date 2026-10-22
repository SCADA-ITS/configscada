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
import com.revenga.rits.back.data.core.model.command.SetElementValuesPublishCommand;


/**
 *
 * EtdParamsAdapterATC: Save de measures to detectors of ATC
 *
 */
class EtdParamsAdapterATC {

    //BBDD Params
	static final Long TYPE_PARAM_CONFIG = 1L;
	static final Long TYPE_PARAM_MEASURE = 2L;
	static final Long PARAM_CONFIG_ORDER = 2L;
	static final Long ELEMENT_TYPE_ID_DET = 3L;
	
	//Detector Params
	static final Long PARAM_MEASURE_DATE = 1L;
	static final Long PARAM_MEASURE_PERIOD =2L;
	static final Long PARAM_MEASURE_NVEHICLES = 4L;
	static final Long PARAM_MEASURE_OCCUPANCY = 6L;
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
	static final Long PARAM_MEASURE_VEH_PA_10 = 109L;
	static final Long PARAM_MEASURE_VEH_PA_11 = 110L;
	static final Long PARAM_MEASURE_VEH_PA_12 = 111L;
	static final Long PARAM_MEASURE_VEH_PA_13 = 112L;



	
	//State IDs
	
	org.apache.logging.log4j.Logger log;
	
	EtdParamsAdapterATC(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {
	//boolean etd_info(Element element, String value, List<ElementValue> elementValues) {
		boolean res = true;
		int detArrayPos;
		
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
					
					detArrayPos = Integer.parseInt(detOrder.getValue()) - 1;
					
					List<ElementValue> listElements = new ArrayList();
					listElements.add(elementSetValue(PARAM_MEASURE_DATE, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(etd_info.get(detArrayPos).timestamp)));	
					listElements.add(elementSetValue(PARAM_MEASURE_PERIOD, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(etd_info.get(detArrayPos).integration_period)));
					listElements.add(elementSetValue(PARAM_MEASURE_NVEHICLES, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(etd_info.get(detArrayPos).n_vehicles)));	
					listElements.add(elementSetValue(PARAM_MEASURE_OCCUPANCY, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(Math.round(Double.valueOf(etd_info.get(detArrayPos).occupancy)))));	
					listElements.add(elementSetValue(PARAM_MEASURE_SPEED, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(Math.round(Double.valueOf(etd_info.get(detArrayPos).average_speed)))));	
					listElements.add(elementSetValue(PARAM_MEASURE_GAP, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(etd_info.get(detArrayPos).gap)));	
					listElements.add(elementSetValue(PARAM_MEASURE_FAIL_DATA, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(etd_info.get(detArrayPos).fail_data)));	
					listElements.add(elementSetValue(PARAM_MEASURE_LENGTH, TYPE_PARAM_MEASURE, childElement.getId(),String.valueOf(Math.round(Double.valueOf(etd_info.get(detArrayPos).average_length)))));	
					
					String val;
			
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
					
					val = String.valueOf(etd_info.get(detArrayPos).veh_pa_10 == null ? 0 : etd_info.get(detArrayPos).veh_pa_10);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_PA_10, TYPE_PARAM_MEASURE, childElement.getId(),val));
					
					val = String.valueOf(etd_info.get(detArrayPos).veh_pa_11 == null ? 0 : etd_info.get(detArrayPos).veh_pa_11);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_PA_11, TYPE_PARAM_MEASURE, childElement.getId(),val));
					
					val = String.valueOf(etd_info.get(detArrayPos).veh_pa_12 == null ? 0 : etd_info.get(detArrayPos).veh_pa_12);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_PA_12, TYPE_PARAM_MEASURE, childElement.getId(),val));
					
					val = String.valueOf(etd_info.get(detArrayPos).veh_pa_13 == null ? 0 : etd_info.get(detArrayPos).veh_pa_13);
					listElements.add(elementSetValue(PARAM_MEASURE_VEH_PA_13, TYPE_PARAM_MEASURE, childElement.getId(),val));
							
					
					ElementValue[] itemsArray = new ElementValue[listElements.size()];
					itemsArray = listElements.toArray(itemsArray);
					EntitiesManager.getInstance().putElementValues(itemsArray);
					
					SetElementValuesPublishCommand setElementValuesPublishCommand = new SetElementValuesPublishCommand();
					setElementValuesPublishCommand.setElementValues(new HashSet<>(listElements));
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
}