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
import com.revenga.rits.back.data.core.model.ElementTypeParam;


import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

class InputAdapter_camera_sanctionating{
	org.apache.logging.log4j.Logger log;
	
	static final Long PARAM_CONFIG_JSONCONFIG = 4L;
	static final Long PARAM_MEASURE_PLATE = 6L;
	static final Long PARAM_MEASURE_DATEHOUR = 7L;
	static final Long PARAM_MEASURE_SPEED = 8L;
	static final Long PARAM_TYPE_MEASURE = 2L;
	
	InputAdapter_camera_sanctionating(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	boolean jsonMedidas(Element element, String value, List<ElementValue> elementValues) {

       
	    def jsonObject = new JsonSlurper().parseText(value);

		String plate =  jsonObject[0].plate;
		String datehour =  jsonObject[0].datehour;
		String speed =  jsonObject[0].speed;

		ElementValue elementValuePlate = new ElementValue();
		elementValuePlate.setElementTypeId(element.getElementTypeId());
		elementValuePlate.setElementId(element.getId());
		elementValuePlate.setParamTypeId(PARAM_TYPE_MEASURE);
		elementValuePlate.setElementTypeParamId(PARAM_MEASURE_PLATE);
		elementValuePlate.setValue(plate);
		elementValues.add(elementValuePlate);

		

		
		ElementValue elementValueDatehour = new ElementValue();
		elementValueDatehour.setElementTypeId(element.getElementTypeId());
		elementValueDatehour.setElementId(element.getId());
		elementValueDatehour.setParamTypeId(PARAM_TYPE_MEASURE);
		elementValueDatehour.setElementTypeParamId(PARAM_MEASURE_DATEHOUR);
		elementValueDatehour.setValue(datehour);
		elementValues.add(elementValueDatehour);

		
		ElementValue elementValueSpeed = new ElementValue();
		elementValueSpeed.setElementTypeId(element.getElementTypeId());
		elementValueSpeed.setElementId(element.getId());
		elementValueSpeed.setParamTypeId(PARAM_TYPE_MEASURE);
		elementValueSpeed.setElementTypeParamId(PARAM_MEASURE_SPEED);
		elementValueSpeed.setValue(speed);
		elementValues.add(elementValueSpeed);

		log.debug("elementValues  --> " + elementValues);

		return true;
	}

}