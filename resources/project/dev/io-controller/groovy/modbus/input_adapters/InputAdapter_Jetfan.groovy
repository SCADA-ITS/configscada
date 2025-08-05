import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.io.controller.driver.modbustcp.ModbusTcpDriver.ModbusResult;


/**
 * Example of custom input adapter for Jetfan elements. Replace with groovy
 * extension and place in groovy input folder adapters.
 */
class InputAdapter_Jetfan {
	
	static final Long PARAM_FRONT_BEARING_TEMPERATURE = 3L;
	static final Long PARAM_BACK_BEARING_TEMPERATURE = 4L;
	

    org.apache.logging.log4j.Logger log;


    InputAdapter_Jetfan(org.apache.logging.log4j.Logger log) {
        
        this.log = log;
    }

	boolean processParam(final Element element, final ElementTypeParam elementTypeParam,
			final ModbusResult modbusResult, List<ElementValue> elementValues) {
    	
    	log.debug("Processing param " + elementTypeParam + " from modbus " + modbusResult
				+ " with custom adapter for " + (new Key(element)).getSimpleValue() + " ...");
		
		String value = modbusResult.getStrValue();
		
		if (!StringUtils.isEmpty(value) && NumberUtils.isCreatable(value) &&
				ParamType.MEASURE == elementTypeParam.getParamTypeId().longValue() &&
				Set.of(PARAM_FRONT_BEARING_TEMPERATURE, PARAM_BACK_BEARING_TEMPERATURE).contains(elementTypeParam.getId())) {
			
			Integer valueInt = NumberUtils.toInt(value);

			ElementValue elementValue = new ElementValue();
			
			elementValue.setElementTypeId(elementTypeParam.getElementTypeId());
			elementValue.setElementId(element.getId());
			elementValue.setElementTypeParamId(elementTypeParam.getId());
			elementValue.setParamTypeId(elementTypeParam.getParamTypeId());
			elementValue.setValue(String.valueOf(Math.round((valueInt - 32) / 1.8))); // Convert Fahrenheit to Celsius
			
			elementValues.add(elementValue);
		}
		
		return true;
    }

}
