import java.util.List;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;


/**
 *
 * InputAdapter_FAN.groovy
 *
 */
class InputAdapter_FAN {
	org.apache.logging.log4j.Logger log;

    final Double MIN_VALUE_MODBUS = 0.0;
    final Double MAX_VALUE_MODBUS = 32760.0;    
    final Double MIN_VALUE_VIBRATION = 0.0;
    final Double MAX_VALUE_VIBRATION = 25.0;


    final Long PARAM_TYPE_MEASURE = 2
    final Long PARAM_MEASURE_FAN_VIBRATION = 2

    InputAdapter_FAN(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean fan_vibration(Element element, String value, List<ElementValue> elementValues) {

        double result = calcular_entrada_ma(Float.parseFloat(value), element)

        ElementValue elementValue = new ElementValue();
        elementValue.setElementTypeId(element.getElementTypeId());
        elementValue.setElementTypeParamId(PARAM_MEASURE_FAN_VIBRATION);
        elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
        elementValue.setElementId(element.getId());
        elementValue.setValue(result.toString());
        elementValues.add(elementValue);

        return true

    }

    double calcular_entrada_ma(double value, Element element){
        double x_value = 0.0

	double pendiente = (MAX_VALUE_VIBRATION - MIN_VALUE_VIBRATION)/(MAX_VALUE_MODBUS - MIN_VALUE_MODBUS);
        x_value = value * pendiente;          	
        x_value = Math.round(x_value * 100) 

        return x_value
    }

}