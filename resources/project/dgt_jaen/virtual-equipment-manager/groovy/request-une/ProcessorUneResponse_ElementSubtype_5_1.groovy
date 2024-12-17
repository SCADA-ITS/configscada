import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.MutablePair
import org.apache.commons.lang3.tuple.ImmutablePair
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.virtual.equipment.manager.une.UneHelper.WeatherDataType;

class ProcessorUneResponse_ElementSubtype_5_1 {

	org.apache.logging.log4j.Logger log;


	static Long ELEMENT_TYPE_PARAM_AIR_PRESSURE = 4L;
	static Long ELEMENT_TYPE_PARAM_RELATIVE_HUMIDITY = 10L;
	static Long ELEMENT_TYPE_PARAM_AIR_TEMPERATURE = 17L;
	static Long ELEMENT_TYPE_PARAM_DEW_POINT_TEMPERATURE = 18L;
	static Long ELEMENT_TYPE_PARAM_VISIBILITY = 20L;
	static Long ELEMENT_TYPE_PARAM_WIND_SPEED = 26L;
	static Long ELEMENT_TYPE_PARAM_WIND_DIRECTION = 27L;
	static Long ELEMENT_TYPE_PARAM_WIND_TYPE = 28L;
	static Long ELEMENT_TYPE_PARAM_DATE = 29L;
	static Long ELEMENT_TYPE_PARAM_PERIOD = 30L;

	static Long PARAM_TYPE_MEASURE = 2L;

	ProcessorUneResponse_ElementSubtype_5_1(org.apache.logging.log4j.Logger log) {
		this.log = log;
	}

	Element processResponse_0x87(Map<WeatherDataType, List<Pair<Integer, String>>> measurementsMap,
                             Long elementId, Long elementTypeId) {
    Element element = null;
    
    if (measurementsMap != null && measurementsMap.size() > 0) {
        element = new Element(elementTypeId, elementId);

        // Otros valores de medidas
        addElementValue(element, ELEMENT_TYPE_PARAM_AIR_PRESSURE,
                         measurementsMap.get(WeatherDataType.ATMOSPHERIC_PRESSURE), 0);
        addElementValue(element, ELEMENT_TYPE_PARAM_RELATIVE_HUMIDITY,
                         measurementsMap.get(WeatherDataType.RELATIVE_HUMIDITY), 0);
        addElementValue(element, ELEMENT_TYPE_PARAM_DEW_POINT_TEMPERATURE,
                         measurementsMap.get(WeatherDataType.DEW_POINT_TEMPERATURE), 0);
        addElementValue(element, ELEMENT_TYPE_PARAM_AIR_TEMPERATURE,
                         measurementsMap.get(WeatherDataType.AIR_TEMPERATURE), 0);
        addElementValue(element, ELEMENT_TYPE_PARAM_VISIBILITY,
                         measurementsMap.get(WeatherDataType.VISIBILITY), 0);
        addElementValue(element, ELEMENT_TYPE_PARAM_WIND_SPEED,
                         measurementsMap.get(WeatherDataType.WIND_SPEED), 0);
        addElementValue(element, ELEMENT_TYPE_PARAM_WIND_DIRECTION,
                         measurementsMap.get(WeatherDataType.WIND_DIRECTION), 0);
        addElementValue(element, ELEMENT_TYPE_PARAM_WIND_TYPE,
                         measurementsMap.get(WeatherDataType.WIND_TYPE), 0);

        // Separar fecha y período de DATE_PEIOD usando la nueva función
        List<ImmutablePair<Integer, String>> dateList = new ArrayList<>();
        List<ImmutablePair<Integer, String>> periodList = new ArrayList<>();
        separateDateAndPeriod(measurementsMap.get(WeatherDataType.DATE_PEIOD), dateList, periodList);

        // Convertir los períodos de segundos a minutos
        convertPeriodToMinutes(periodList);

        // Pasar las listas separadas a addElementValue
        addElementValue(element, ELEMENT_TYPE_PARAM_DATE, dateList, 0);
        addElementValue(element, ELEMENT_TYPE_PARAM_PERIOD, periodList, 0);
    }
    return element;
}

	// Función que separa las fechas y los períodos
	private void separateDateAndPeriod(List<Pair<Integer, String>> datePeriodPairs,
										List<ImmutablePair<Integer, String>> dateList,
										List<ImmutablePair<Integer, String>> periodList) {
		if (datePeriodPairs != null && !datePeriodPairs.isEmpty()) {
			for (Pair<Integer, String> pair : datePeriodPairs) {
				String combinedData = pair.getRight(); // La cadena combinada "2024-12-16 / 5"
				
				// Separar en fecha y período
				String[] parts = combinedData.split(" / ");
				if (parts.length == 2) {
					String date = parts[0];   // "2024-12-16"
					String period = parts[1]; // "5"
					
					// Agregar los valores a las listas correspondientes
					dateList.add(new ImmutablePair<>(pair.getLeft(), date));
					periodList.add(new ImmutablePair<>(pair.getLeft(), period));
				} else {
					log.error("El formato del dato no es válido: " + combinedData);
				}
			}
		}
	}

	// Función que convierte los períodos de segundos a minutos
	private void convertPeriodToMinutes(List<ImmutablePair<Integer, String>> periodList) {
		if (periodList != null && !periodList.isEmpty()) {
			for (int i = 0; i < periodList.size(); i++) {
				ImmutablePair<Integer, String> entry = periodList.get(i);
				try {
					int periodInSeconds = Integer.parseInt(entry.getRight()); // Convertir el valor a int
					int periodInMinutes = periodInSeconds / 60; // Convertir a minutos
					periodList.set(i, new ImmutablePair<>(entry.getLeft(), String.valueOf(periodInMinutes))); // Crear una nueva instancia de ImmutablePair con el nuevo valor
				} catch (NumberFormatException e) {
					log.error("Error al convertir el período a minutos: " + entry.getRight(), e);
				}
			}
		}
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
