import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;


/**
 *
 * StateToVentilationElementTypeStateId.groovy: Save the measurement with the current signaling value of the tube
 *
 */
class StateToVentilationElementTypeStateId {
	org.apache.logging.log4j.Logger log;


	static final Long ELEMENT_TYPE_PARAM_VENTILATION_STATE = 7L;
	static final Long PARAM_TYPE_MEASURE = 2L;
	static final String ELEMENT_TYPE_STATE_UNKNOWN = "ElementTypeState:2001:0";
	static final String ELEMENT_TYPE_STATE_UP = "ElementTypeState:2001:1";
	static final String ELEMENT_TYPE_STATE_DOWN = "ElementTypeState:2001:2";
	
	
	StateToVentilationElementTypeStateId(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	boolean status(Element element, String value, List<ElementValue> elementValues) {
		
		if(value != null && elementValues != null ) {

			ElementValue elementValue = new ElementValue();

			elementValue.setElementTypeId(element.getElementTypeId());
			elementValue.setElementTypeParamId(ELEMENT_TYPE_PARAM_VENTILATION_STATE);
			elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
			elementValue.setElementId(element.getId());

			if(value.equals("0.0")) {
				
				elementValue.setValue(ELEMENT_TYPE_STATE_DOWN);
				
			} else if(value.equals("1.0")) {
				
				elementValue.setValue(ELEMENT_TYPE_STATE_UP);
				
			} else {
				
				elementValue.setValue(ELEMENT_TYPE_STATE_UNKNOWN);
			}
			elementValues.add(elementValue);
			return true;
			
		} else {

			return false;
		}
	}
}