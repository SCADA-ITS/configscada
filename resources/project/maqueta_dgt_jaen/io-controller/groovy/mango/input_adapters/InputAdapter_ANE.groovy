import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import groovy.json.*;

import java.io.FileReader;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.VmsGraphicGraphicGroupValue;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import groovy.json.JsonOutput


/**
 *
 * InputAdapter_ANE.groovy: convert de value of anemometer
 *
 */
class InputAdapter_ANE {
	org.apache.logging.log4j.Logger log;
	
	static final Long ANEM_ELEMENT_TYPE_ID = 18L;
	static final Long TUBE_ELEMENT_TYPE_ID = 2000L;
	static final Long PARAM_MEASURE_WIND_SPEED = 1L;
	static final Long PARAM_MEASURE_AVERAGE_WIND_SPEED_SENSORS = 9L;
	static final Long PARAM_TYPE_MEASURE = 2L;
	
	InputAdapter_ANE(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean wind_speed(Element element, String value, List<ElementValue> elementValues) {
		String result = "";
		if(value == null || value.equals("")) {
			ElementValue elementValue = new ElementValue();
			elementValue.setElementTypeId(element.getElementTypeId());
			elementValue.setElementTypeParamId(PARAM_MEASURE_WIND_SPEED);
			elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
			elementValue.setElementId(element.getId());
			elementValue.setValue("");
			elementValues.add(elementValue);
			return true;
		}

		result = factorConversion(value, element);
		setAverageWindSpeedTube(value, element)

		if (result == null || result.equals("")){
			return false;
		}else{
			ElementValue elementValue = new ElementValue();
			elementValue.setElementTypeId(element.getElementTypeId());
			elementValue.setElementTypeParamId(PARAM_MEASURE_WIND_SPEED);
			elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
			elementValue.setElementId(element.getId());
			elementValue.setValue(result);
			elementValues.add(elementValue);
		
			return true;
		}


	}
	public String factorConversion(String wind_speed, Element element) {

    	try {
        	// Convertir el valor de wind_speed a un número
        	double windSpeedValue = Double.parseDouble(wind_speed);
        
        	// Aplicar la ecuacion de la recta que pasa por dos puntos para sacar la intensidad
        	double entrada_mA = 0.00048834 * windSpeedValue + 4;
        
	        // Aplicar factor de conversion: (20 / 8) * (entrada_mA - 12)
        	double finalResult = (20.0 / 8.0) * (entrada_mA - 12);
        
        	// Devolver el resultado final como String
        	return String.valueOf(finalResult);
        
    	} catch (NumberFormatException e) {
        	log.error("Invalid wind speed format: " + wind_speed, e);
        	return "";  // Retorna un String vacío si el formato no es válido
    	}
	}

	public void setAverageWindSpeedTube(String value, Element element){
		List<Float> values = []
		Long parentElementId = null

		List<ElementHierarchy> parentList = EntitiesManager.getInstance().getsByChild(element.getElementTypeId(), element.getId())

		for (parent in parentList) {

			if (parent.getParentElementTypeId() == TUBE_ELEMENT_TYPE_ID){

				parentElementId = parent.getParentElementId()

				List<ElementHierarchy> childList = EntitiesManager.getInstance().getsByParent(parent.getParentElementTypeId(), parent.getParentElementId())

				for (child in childList) {
					
					if (child.getChildElementTypeId() == ANEM_ELEMENT_TYPE_ID){
						
						Element anem = EntitiesManager.getInstance().getElement(child.getChildElementTypeId(), child.getChildElementId())
						ElementValue windSpeedValue = EntitiesManager.getInstance().getElementValue(anem, PARAM_MEASURE_WIND_SPEED, PARAM_TYPE_MEASURE)
						values.add(Float.parseFloat(windSpeedValue.getValue()))
					}
				}
			}
		}

		Float avg = values.sum() / values.size()

		List<ElementValue> elementValues = EntitiesManager.getInstance().getElementValue(TUBE_ELEMENT_TYPE_ID, PARAM_MEASURE_AVERAGE_WIND_SPEED_SENSORS, PARAM_TYPE_MEASURE)
		ElementValue elementValue = new ElementValue();
		elementValue.setElementTypeId(TUBE_ELEMENT_TYPE_ID);
		elementValue.setElementTypeParamId(PARAM_MEASURE_AVERAGE_WIND_SPEED_SENSORS);
		elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
		elementValue.setElementId(parentElementId);
		elementValue.setValue(avg.toString());
		elementValues.add(elementValue);

		EntitiesManager.getInstance().putElementValues(elementValues);		
	}
}