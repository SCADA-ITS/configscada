import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.virtual.equipment.manager.une.UneHelper.WeatherDataType;

class ProcessorUneResponse_ElementSubtype_5_1 {

	org.apache.logging.log4j.Logger log;

	static Long ELEMENT_TYPE_PARAM_SURFACE_TEMPERATURE = 1L;
	static Long ELEMENT_TYPE_PARAM_SUB_SURFACE_TEMPERATURE = 2L;
	static Long ELEMENT_TYPE_PARAM_AIR_PRESSURE = 4L;
	static Long ELEMENT_TYPE_PARAM_AIR_TEMPERATURE = 17L;
	static Long PARAM_TYPE_MEASURE = 2L;

	ProcessorUneResponse_ElementSubtype_5_1(org.apache.logging.log4j.Logger log) {
		this.log = log;
	}

	Element processResponse_0x87(Map<WeatherDataType, List<Pair<Integer, String>>> measurementsMap,
			Long elementId, Long elementTypeId) {

		Element element = null;

		if (measurementsMap != null && measurementsMap.size() > 0) {

			element = new Element(elementTypeId, elementId);

			addElementValue(element, ELEMENT_TYPE_PARAM_SURFACE_TEMPERATURE,
					measurementsMap.get(WeatherDataType.SOIL_SURFACE_TEMPERATURE), 0);
			
			addElementValue(element, ELEMENT_TYPE_PARAM_SUB_SURFACE_TEMPERATURE,
					measurementsMap.get(WeatherDataType.SUBSOIL_TEMPERATURE), 0);
			
			addElementValue(element, ELEMENT_TYPE_PARAM_AIR_PRESSURE,
					measurementsMap.get(WeatherDataType.ATMOSPHERIC_PRESSURE), 0);
			
			addElementValue(element, ELEMENT_TYPE_PARAM_AIR_TEMPERATURE,
					measurementsMap.get(WeatherDataType.AIR_TEMPERATURE), 0);
		}

		return element;
	}

	void addElementValue(Element element, Long elementTypeParamId, List<Pair<Integer, String>> measureList,
			int index) {

		if (measureList != null && measureList.size() > index) {

			if (element.getElementValues() == null) {

				List<ElementValue> elementValues = new ArrayList<>();
				element.setElementValues(elementValues);
			}

			ElementValue elementValue = new ElementValue();
			elementValue.setElementId(element.getId());
			elementValue.setElementTypeId(element.getElementTypeId());
			elementValue.setValue(measureList.get(index).getValue());
			elementValue.setElementTypeParamId(elementTypeParamId);
			elementValue.setParamTypeId(PARAM_TYPE_MEASURE);

			element.getElementValues().add(elementValue);
		}

	}

}
