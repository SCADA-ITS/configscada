import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import groovy.json.JsonSlurper;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;

/**
 *
 * Vms_METEO_TRAFICO.groovy: Save the measurement with the current signaling value of the panel
 *
 */
class Vms_METEO_TRAFICO {

	// ID Elements Params
	static final Long PARAM_TYPE_MEASURE = 2L;
	static final Long PARAM_MEASURE_RATE = 8L;
	static final Long PARAM_MEASURE_SERVICE = 12L;
	static final Long PARAM_MEASURE_ENABLED = 7L;
	static final Long PARAM_COMMAND_SIGNALLING = 2L;
	static final int DATA_TYPE_ALPHANUMERIC = 1;
	static final Long ELEMENT_TYPE_VMS_TEMPERATURE = 71L;
	static final int PARAM_ID_1 = 1;
	
	
	org.apache.logging.log4j.Logger log;
	
	Vms_METEO_TRAFICO(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {

		List<ElementValue> listElements;

		List<ElementHierarchy> childs;
		ElementValue elementValues_aux = elementValues.get(0).getRight()
		int element_param_id = elementValues_aux.elementTypeParamId

		childs = EntitiesManager.getInstance().getsByParent(element.getElementTypeId(), element.getId(), false);

		Integer air_temperature;
		Integer sub_temperature;
		try{
			if (elementValues != null && element_param_id == 8){
				for (ElementHierarchy child : childs){
					listElements = new ArrayList();
					Element childElement = EntitiesManager.getInstance().getElement(child.getChildElementTypeId(), child.getChildElementId());					

					ElementValue service = EntitiesManager.getInstance().getElementValueMeasure(childElement, PARAM_MEASURE_SERVICE);

					listElements.add(service.getValue())

					def rate = new JsonSlurper().parseText(elementValues.get(0).getRight().getValue());

					if (rate.size() == 1){
						air_temperature = rate.get(0).air_temperature
						sub_temperature = rate.get(0).surface_temperature
					}else{
						air_temperature = rate.get(1).air_temperature
						sub_temperature = rate.get(1).surface_temperature
					}
					
					
					String serviceValue = service.getValue()
	
					if (serviceValue == null){
						serviceValue = "0";
					}
					EntitiesManager.getInstance().sendCommand(rellenaSignallingCommand(element, serviceValue + "#" + Integer.toString(sub_temperature) + "#" + Integer.toString(air_temperature)));

						
				}
			}
			return true;
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.error(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}

	SignallingCommand rellenaSignallingCommand(Element element, String paramValue){
		SignallingCommand signallingCommand = new SignallingCommand();
		
		signallingCommand.setSignallingCommandId(PARAM_COMMAND_SIGNALLING);
		signallingCommand.setElementTypeId(element.getElementTypeId());
		signallingCommand.setElementId(element.getId());

		signallingCommand.setSignallingParams(rellenaSignallingParam(paramValue));

		return signallingCommand;
	}
	
	List<SignallingParam> rellenaSignallingParam(String paramValue){
		List<SignallingParam> signallingParams = new ArrayList<>();
		
		SignallingParam signallingParam = new SignallingParam();

		signallingParam.setId(PARAM_ID_1);
		signallingParam.setType(DATA_TYPE_ALPHANUMERIC);
		signallingParam.setValue(paramValue);

		signallingParams.add(signallingParam);

		return signallingParams;
	}
}
