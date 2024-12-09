import java.util.List;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;


/**
 *
 * InputAdapter_VANE.groovy
 *
 */
class InputAdapter_VANE {
	org.apache.logging.log4j.Logger log;

    final int MIN_VALUE_MODBUS = 0;
    final int MAX_VALUE_MODBUS = 32760;
    final int MAX_VALUE_MA = 20;
    final int MIN_VALUE_MA = 4;

    final Long PARAM_TYPE_MEASURE = 2
    
    final Long PARAM_MEASURE_WIND_DIRECTION = 1
    final Long PARAM_MEASURE_WIND_SPEED = 2

    InputAdapter_VANE(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean wind_direction(Element element, String value, List<ElementValue> elementValues) {

        double valor = calcular_entrada_ma(Float.parseFloat(value))
        double result = factor_conversion_wind_direction(valor)

        ElementValue elementValue = new ElementValue();
        elementValue.setElementTypeId(element.getElementTypeId());
        elementValue.setElementTypeParamId(PARAM_MEASURE_WIND_DIRECTION);
        elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
        elementValue.setElementId(element.getId());
        elementValue.setValue(result.toString());
        elementValues.add(elementValue);

        return true

    }

	boolean wind_speed(Element element, String value, List<ElementValue> elementValues) {

        double valor = calcular_entrada_ma(Float.parseFloat(value))
        double result = factor_conversion_wind_speed(valor)

        ElementValue elementValue = new ElementValue();
        elementValue.setElementTypeId(element.getElementTypeId());
        elementValue.setElementTypeParamId(PARAM_MEASURE_WIND_SPEED);
        elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
        elementValue.setElementId(element.getId());
        elementValue.setValue(result.toString());
        elementValues.add(elementValue);

        return true

    }

    double calcular_entrada_ma(double value){
        double x_value = 0.0

        x_value = (((value - MIN_VALUE_MODBUS) * (MAX_VALUE_MA - MIN_VALUE_MA)) / MAX_VALUE_MODBUS - MIN_VALUE_MODBUS) + 4
        x_value = Math.round(x_value * 100) / 100.0f 

        return x_value
    }

    double factor_conversion_wind_direction(double value){
        double result = (360/16) * (value - 4)
        result = Math.round(result * 100) / 100.0f

        return result
    }

    double factor_conversion_wind_speed(double value){
        double result = (50/16) * (value - 4)
        result = Math.round(result * 100) / 100.0f

        return result
    }

}