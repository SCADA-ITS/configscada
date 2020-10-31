import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

class AdapterGridElement {

	static final Integer DATA_TYPE_INTEGER = 1;
	static final Integer DATA_TYPE_ALPHANUMERIC = 2;
	static final Integer DATA_TYPE_DECIMAL = 3;
	static final Integer DATA_TYPE_TIMESTAMP = 4;
	static final Integer DATA_TYPE_BOOLEAN = 5;
	
	static final String FIELD_ELEMENT_VALUES = "elementValues";
	static final String FIELD_ELEMENT_TYPE_PARAM = "elementTypeParam";
	static final String FIELD_ID = "id";
	static final String FIELD_LABEL_ALIAS = "labelAlias";
	static final String FIELD_DATA_TYPE = "dataType";
	static final String FIELD_ELEMENT_VALUE_STATE = "elementValueState";
	static final String FIELD_STATE = "state";
	static final String FIELD_VALUE = "value";
	static final String FIELD_ELEMENT_TYPE_STATE = "elementTypeState";
	static final String FIELD_ELEMENT_TYPE_STATE_STATE = "state";
	static final String FIELD_ELEMENT_TYPE_STATE_STATE_LABEL = "stateLabel";
	
	org.apache.logging.log4j.Logger log;
	
	public AdapterGridElement(org.apache.logging.log4j.Logger log) {
		
		this.log = log;
	}
	
	List<Map<String, Object>> toGridData(VO2UIAdapter vo2uiAdapter, String fieldId, List<Map<String, Object>> entities) {
		
		List<Map<String, Object>> data = new ArrayList<>();
		
		try {
			
			if (!CollectionUtils.isEmpty(entities)) {
				
				for (Map<String, Object> entity : entities) {
					
					Map<String, Object> dataRow = new HashMap<>();
					
					dataRow.putAll(entity);
					
					flattenElementTypeState(dataRow);
					flattenElementValues(dataRow);
					
					data.add(vo2uiAdapter.convert(fieldId, dataRow));
				}
			}
			
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return data;
	}
	
	void flattenElementTypeState(Map<String, Object> dataRow) {
		
		if (dataRow.containsKey(FIELD_ELEMENT_TYPE_STATE)) {
			
			Object objElementTypeState = dataRow.get(FIELD_ELEMENT_TYPE_STATE);
			
			if (objElementTypeState instanceof Map) {
				
				Map<String, Object> elementTypeState = (Map<String, Object>) objElementTypeState; 
				
				dataRow.put(FIELD_ELEMENT_TYPE_STATE_STATE, elementTypeState.get(FIELD_ID));
				dataRow.put(FIELD_ELEMENT_TYPE_STATE_STATE_LABEL, elementTypeState.get(FIELD_LABEL_ALIAS));
			}
			
			dataRow.remove(FIELD_ELEMENT_TYPE_STATE);
		}
	}
	
	void flattenElementValues(Map<String, Object> dataRow) {
		
		if (dataRow.containsKey(FIELD_ELEMENT_VALUES)) {
			
			Object objElementValues = dataRow.get(FIELD_ELEMENT_VALUES);
			
			if (objElementValues instanceof List) {
				
				List<?> elementValues = (List<?>) objElementValues;
				
				if (!CollectionUtils.isEmpty(elementValues)) {
					
					for (Object objElementValue : elementValues) {
						
						if (objElementValue instanceof Map) {
							
							Map<String, Object> elementValue = (Map<String, Object>) objElementValue;
							
							if (elementValue.get(FIELD_VALUE) != null) {
								
								if (elementValue.containsKey(FIELD_ELEMENT_TYPE_PARAM)) {
						
									Object objElementTypeParam = elementValue.get(FIELD_ELEMENT_TYPE_PARAM);
									
									if (objElementTypeParam instanceof Map) {
										
										Map<String, Object> elementTypeParam = (Map<String, Object>) objElementTypeParam;
										
										String fieldParamId = (String) elementTypeParam.get(FIELD_ID);
										Integer dataType = (Integer) elementTypeParam.get(FIELD_DATA_TYPE);
										Object elementValueStateObj = elementValue.get(FIELD_ELEMENT_VALUE_STATE);

										String valueStr = (String) elementValue.get(FIELD_VALUE);
										
										Object value = null;
										
										if (dataType.equals(DATA_TYPE_INTEGER)) {
											
											value = Integer.parseInt(valueStr);
										}
										else if (dataType.equals(DATA_TYPE_ALPHANUMERIC)) {
											
											value = valueStr;
										}
										else if (dataType.equals(DATA_TYPE_DECIMAL)) {
											
											value = Float.parseFloat(valueStr);
										}
										else if (dataType.equals(DATA_TYPE_TIMESTAMP)) {
											
											value = Long.parseLong(valueStr);
										}
										else if (dataType.equals(DATA_TYPE_BOOLEAN)) {
											
											value = Boolean.parseBoolean(valueStr);
										}
										
										if (elementValueStateObj instanceof Map) {
											
											Object newValue = value;
											
											Map<String, Object> elementValueState = (Map<String, Object>) elementValueStateObj;
										
											dataRow.put(fieldParamId, new HashMap<String, Object>() {
												{
													put(FIELD_VALUE, newValue);
													put(FIELD_STATE, elementValueState.get(FIELD_ID));
												}
											});
										}
										else {
											
											dataRow.put(fieldParamId, value);
										}
									}
								}
							}
						}
					}
				}
				
				dataRow.remove(FIELD_ELEMENT_VALUES);
			}
		}
	}
}
