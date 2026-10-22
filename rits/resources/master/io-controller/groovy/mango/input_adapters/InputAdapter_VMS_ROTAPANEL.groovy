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
class VmsROTAPANELParamsAdapter {

	// ID Elements Params
	static final Long PARAM_TYPE_MEASURE = 2L;
	static final Long PARAM_MEASURE_PICTO1201 = 3L;
	static final Long GRAPHIC_PROTOCOL_TYPE_PARAM_MEM_ADDR = 1L;
	
	static final String APAGADO = "0";


	
	org.apache.logging.log4j.Logger log;
	
	VmsROTAPANELParamsAdapter(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean switch_on_read(Element element, String value, List<ElementValue> elementValues) {

		try {
			def params_parsed

			ElementValue params = EntitiesManager.getInstance().getElementValueConfig(element, 10);
			if (params != null){
				params_parsed = new JsonSlurper().parseText(params.value);			
			}
			if((value == "1.0" || value == "4.0")  && params== null){
			   ElementValue elementValuePICTO1201 = new ElementValue();
			   elementValuePICTO1201.setElementTypeId(element.getElementTypeId());
			   elementValuePICTO1201.setElementTypeParamId(PARAM_MEASURE_PICTO1201);
			   elementValuePICTO1201.setParamTypeId(PARAM_TYPE_MEASURE);
			   elementValuePICTO1201.setElementId(element.getId());
			   elementValuePICTO1201.setValue(APAGADO);
			   elementValues.add(elementValuePICTO1201);
			   
			   return true;
			}else if(value == "2.0"   && params== null){

				def json = JsonOutput.toJson([[zone: 1, graphics: [[id: 1, value: "201"]]]]);
                ElementValue elementValueMessage = new ElementValue();
			    elementValueMessage.setElementTypeId(element.getElementTypeId());
			    elementValueMessage.setElementTypeParamId(PARAM_MEASURE_PICTO1201);
			    elementValueMessage.setParamTypeId(PARAM_TYPE_MEASURE);
			    elementValueMessage.setElementId(element.getId());
			    elementValueMessage.setValue(json);
			    elementValues.add(elementValueMessage);
                log.debug(elementValueMessage)
				return true;

			}else if((value == "1.0" || value == "4.0") && params_parsed == 1){

				def json = JsonOutput.toJson([[zone: 1, graphics: [[id: 1, value: "885"]]]]);
                ElementValue elementValueMessage = new ElementValue();
			    elementValueMessage.setElementTypeId(element.getElementTypeId());
			    elementValueMessage.setElementTypeParamId(PARAM_MEASURE_PICTO1201);
			    elementValueMessage.setParamTypeId(PARAM_TYPE_MEASURE);
			    elementValueMessage.setElementId(element.getId());
			    elementValueMessage.setValue(json);
			    elementValues.add(elementValueMessage);
                log.debug(elementValueMessage)
				return true;

			}else if(value == "2.0" && (params_parsed == 3 || params_parsed == 4 || params_parsed == 1)){

				def json = JsonOutput.toJson([[zone: 1, graphics: [[id: 1, value: "886"]]]]);
                ElementValue elementValueMessage = new ElementValue();
			    elementValueMessage.setElementTypeId(element.getElementTypeId());
			    elementValueMessage.setElementTypeParamId(PARAM_MEASURE_PICTO1201);
			    elementValueMessage.setParamTypeId(PARAM_TYPE_MEASURE);
			    elementValueMessage.setElementId(element.getId());
			    elementValueMessage.setValue(json);
			    elementValues.add(elementValueMessage);
                log.debug(elementValueMessage)
				return true;

			}else if((value == "1.0" || value == "4.0") && params_parsed == 3){

				def json = JsonOutput.toJson([[zone: 1, graphics: [[id: 1, value: "887"]]]]);
                ElementValue elementValueMessage = new ElementValue();
			    elementValueMessage.setElementTypeId(element.getElementTypeId());
			    elementValueMessage.setElementTypeParamId(PARAM_MEASURE_PICTO1201);
			    elementValueMessage.setParamTypeId(PARAM_TYPE_MEASURE);
			    elementValueMessage.setElementId(element.getId());
			    elementValueMessage.setValue(json);
			    elementValues.add(elementValueMessage);
                log.debug(elementValueMessage)
				return true;

			}else if((value == "1.0" || value == "4.0") && params_parsed == 4){

				def json = JsonOutput.toJson([[zone: 1, graphics: [[id: 1, value: "883"]]]]);
                ElementValue elementValueMessage = new ElementValue();
			    elementValueMessage.setElementTypeId(element.getElementTypeId());
			    elementValueMessage.setElementTypeParamId(PARAM_MEASURE_PICTO1201);
			    elementValueMessage.setParamTypeId(PARAM_TYPE_MEASURE);
			    elementValueMessage.setElementId(element.getId());
			    elementValueMessage.setValue(json);
			    elementValues.add(elementValueMessage);
                log.debug(elementValueMessage)
				return true;

			}else if(value == "2.0" && params_parsed == 5){
				def json = JsonOutput.toJson([[zone: 1, graphics: [[id: 1, value: "884"]]]]);
                ElementValue elementValueMessage = new ElementValue();
			    elementValueMessage.setElementTypeId(element.getElementTypeId());
			    elementValueMessage.setElementTypeParamId(PARAM_MEASURE_PICTO1201);
			    elementValueMessage.setParamTypeId(PARAM_TYPE_MEASURE);
			    elementValueMessage.setElementId(element.getId());
			    elementValueMessage.setValue(json);
			    elementValues.add(elementValueMessage);
                log.debug(elementValueMessage)
				return true;


			}else if((value == "1.0" || value == "4.0") && params_parsed == 5){
			   ElementValue elementValuePICTO1201 = new ElementValue();
			   elementValuePICTO1201.setElementTypeId(element.getElementTypeId());
			   elementValuePICTO1201.setElementTypeParamId(PARAM_MEASURE_PICTO1201);
			   elementValuePICTO1201.setParamTypeId(PARAM_TYPE_MEASURE);
			   elementValuePICTO1201.setElementId(element.getId());
			   elementValuePICTO1201.setValue(APAGADO);
			   elementValues.add(elementValuePICTO1201);
			   
			   return true;

			}
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
}
