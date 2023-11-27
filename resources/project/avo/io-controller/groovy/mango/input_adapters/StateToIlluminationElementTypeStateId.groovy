import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

/**
 *
 * StateToIlluminationElementTypeStateId.groovy: Save the measurement with the current signaling value of the tube
 *
 */
class StateToIlluminationElementTypeStateId {
	
	org.apache.logging.log4j.Logger log;
	
	static final Long ELEMENT_TYPE_PARAM_ILLUMINATION_STATE = 8L;
	static final Long PARAM_TYPE_MEASURE = 2L;
	static final Long ELEMENT_TYPE_PARAM_ILLUMINATION_REGIME = 4L;
	static final String ILLUMINATION_REGIME_VALUE = "0.0";
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
			
			elementValue.setValue(ELEMENT_TYPE_STATE_UP);

			elementValues.add(elementValue);
			
			return true;
			
		} else {

			return false;
		}
	}

	boolean illumination_regime(Element element, String value, List<ElementValue> elementValues) {

		if(value != null && elementValues != null ) {

			ElementValue elementValue = new ElementValue();

			elementValue.setElementTypeId(element.getElementTypeId());
			elementValue.setElementTypeParamId(ELEMENT_TYPE_PARAM_ILLUMINATION_REGIME);
			elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
			elementValue.setElementId(element.getId());

			List<ElementHierarchy> elementHierarchies = EntitiesManager.getInstance().getsByChild(element.getElementTypeId(), element.getId())

			if (elementHierarchies.get(0).getParentElementTypeId() == 2002 && elementHierarchies.get(0).getParentElementId() == 2 && (value.equals("1.0") || value.equals("2.0") || value.equals("3.0"))) {

				elementValue.setValue(ILLUMINATION_REGIME_VALUE);
			}else {
				elementValue.setValue(value)
			}
			elementValues.add(elementValue);

			return true;
		}else {
			return false;
		}
	}
}