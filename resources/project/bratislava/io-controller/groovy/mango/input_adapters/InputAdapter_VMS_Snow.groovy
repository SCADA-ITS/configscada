import java.util.List;
import java.util.ArrayList;
import groovy.json.*;

import org.apache.commons.lang3.exception.ExceptionUtils;

import org.apache.commons.lang3.math.NumberUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.io.controller.service.EntitiesManager;


/**
 *
 * VmsSnowParamsAdapter.groovy: Save the measurement with the current signaling value of the panel
 *
 */
class VmsSnowParamsAdapter {

	// ID Elements Params
	static final Long PARAM_TYPE_MEASURE = 2L;
	static final Long PARAM_MEASURE_PICTO84 = 3L;
	static final Long GRAPHIC_PROTOCOL_TYPE_PARAM_MEM_ADDR = 1L;
	
	static final String APAGADO = "0";


	
	org.apache.logging.log4j.Logger log;
	
	VmsSnowParamsAdapter(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean di_switch_on(Element element, String value, List<ElementValue> elementValues) {

		try {

			if(value == "false"){
			   ElementValue elementValuePICTO84 = new ElementValue();
			   elementValuePICTO84.setElementTypeId(element.getElementTypeId());
			   elementValuePICTO84.setElementTypeParamId(PARAM_MEASURE_PICTO84);
			   elementValuePICTO84.setParamTypeId(PARAM_TYPE_MEASURE);
			   elementValuePICTO84.setElementId(element.getId());
			   elementValuePICTO84.setValue(APAGADO);
			   elementValues.add(elementValuePICTO84);
			   
			   return true;
			}else{

				def json = JsonOutput.toJson([[zone: 1, graphics: [[id: 1, value: "10862"]]]]);
                ElementValue elementValueMessage = new ElementValue();
			    elementValueMessage.setElementTypeId(element.getElementTypeId());
			    elementValueMessage.setElementTypeParamId(PARAM_MEASURE_PICTO84);
			    elementValueMessage.setParamTypeId(PARAM_TYPE_MEASURE);
			    elementValueMessage.setElementId(element.getId());
			    elementValueMessage.setValue(json);
			    elementValues.add(elementValueMessage);
                
				return true;
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
}
