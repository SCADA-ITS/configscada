import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;

/**
*
* InputAdapter_RTMS.groovy
*
*/
class InputAdapter_RTMS {
	
	org.apache.logging.log4j.Logger log;
	
	static final Long PARAM_TYPE_MEASURE = 2L;
	
	static final Long ELEMENT_TYPE_PARAM_DATE = 1L;
	static final Long ELEMENT_TYPE_PARAM_PERIOD = 2L;
	static final Long ELEMENT_TYPE_PARAM_FAILDATA = 13L;
	
	static final String PERIOD_VALUE = "1";
	static final String FAILDATA_VALUE = "false";
	
	static final String DATEFORMAT_RTMS = "yyyy-MM-dd'T'HH:mm:ssX";
	
	InputAdapter_RTMS(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	boolean content(Element element, List<List<ElementValue>> listElementValues, List<ElementValue> finalElementValues) {

		boolean res = false;
		Long updatedDate = 0L;
		List<ElementValue> auxFinalElementValues = null;
		ElementValue elementValueDateAux = null;
		
		if (!CollectionUtils.isEmpty(listElementValues) && finalElementValues != null) {
			
			for (List<ElementValue> elementValues : listElementValues) {
				
				if (!CollectionUtils.isEmpty(elementValues)) {
					
					ElementValue elementValueDate = null;
					
					for (int i = 0; elementValueDate == null && i < elementValues.size(); i++) {
					
						ElementValue elementValue = elementValues.get(i);
						
						if (Objects.equals(elementValue.getElementTypeParamId(), ELEMENT_TYPE_PARAM_DATE)) {
							
							elementValueDate = elementValue;
							
							Long date = getEpochMillis(elementValueDate.getValue());
							
							if (date > updatedDate) {
								
								updatedDate = date;
								auxFinalElementValues = elementValues;
								elementValueDateAux = elementValueDate;
							}
						}
					}
				}
			}
			
			if (elementValueDateAux != null && updatedDate > 0) {
				
				elementValueDateAux.setValue(updatedDate.toString());
				finalElementValues.addAll(auxFinalElementValues);
				addExtraValues(element, finalElementValues);
				
				res = true;
			}
		}
        
        return res;
    }
	
	void addExtraValues(Element element, List<ElementValue> finalElementValues) {
		
		if (!CollectionUtils.isEmpty(finalElementValues)) {

			ElementValue period = createElementValue(element, ELEMENT_TYPE_PARAM_PERIOD, PARAM_TYPE_MEASURE, PERIOD_VALUE);
			ElementValue failData = createElementValue(element, ELEMENT_TYPE_PARAM_FAILDATA, PARAM_TYPE_MEASURE, FAILDATA_VALUE);
		
			finalElementValues.addAll(Arrays.asList(period, failData));
		}
	}
	
	ElementValue createElementValue(Element element, long elementTypeParamId, long paramTypeId, String value) {
		
		ElementValue elementValue = new ElementValue();
		
		elementValue.setElementTypeId(element.getElementTypeId());
		elementValue.setElementId(element.getId());
		elementValue.setElementTypeParamId(elementTypeParamId);
		elementValue.setParamTypeId(paramTypeId);
		elementValue.setValue(value);
		
		return elementValue;
	}

	long getEpochMillis(String date) {
		
		long epochMillis = 0L;
		Date dt;
		SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT_RTMS);
		
		try {
	
			dt = sdf.parse(date);
			epochMillis = dt.getTime();
			
		} catch (ParseException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return epochMillis;
	}
}
