import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;


/**
 *
 * StateToIlluminationElementTypeStateId.groovy: Save the measurement with the current signaling value of the tube
 *
 */
class StateToIlluminationElementTypeStateId {
	
	org.apache.logging.log4j.Logger log;

	static final Long ELEMENT_TYPE_PARAM_ILLUMINATION_STATE = 8L;
	static final Long PARAM_TYPE_MEASURE = 2L;
	static final String ELEMENT_TYPE_STATE_UNKNOWN = "ElementTypeState:2002:0";
	static final String ELEMENT_TYPE_STATE_UP = "ElementTypeState:2002:1";
	static final String ELEMENT_TYPE_STATE_DOWN = "ElementTypeState:2002:2";
	
	StateToIlluminationElementTypeStateId(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	boolean status(Element element, String value, List<ElementValue> elementValues) {

		if(value != null && elementValues != null ) {

			ElementValue elementValue = new ElementValue();

			elementValue.setElementTypeId(element.getElementTypeId());
			elementValue.setElementTypeParamId(ELEMENT_TYPE_PARAM_ILLUMINATION_STATE);
			elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
			elementValue.setElementId(element.getId());

			if(value.equals("0.0")) {
				
				elementValue.setValue(ELEMENT_TYPE_STATE_DOWN);
			}
			else if(value.equals("1.0")) {
				
				elementValue.setValue(ELEMENT_TYPE_STATE_UP);
				
			}else {
				
				elementValue.setValue(ELEMENT_TYPE_STATE_UNKNOWN);
			}

			elementValues.add(elementValue);
			return true;
			
		} else {

			return false;
		}
	}
}