package com.revenga.rits.front.openits.adapter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revenga.rits.front.openits.adapter.GridDataAdapter;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ElementAdapter implements GridDataAdapter<Map<String, Object>> {

	private static final Integer DATA_TYPE_INTEGER = 1;
	private static final Integer DATA_TYPE_ALPHANUMERIC = 2;
	private static final Integer DATA_TYPE_DECIMAL = 3;
	private static final Integer DATA_TYPE_TIMESTAMP = 4;
	private static final Integer DATA_TYPE_BOOLEAN = 5;
	
	private static final String FIELD_ELEMENT_VALUES = "elementValues";
	private static final String FIELD_ELEMENT_TYPE_PARAM = "elementTypeParam";
	private static final String FIELD_ID = "id";
	private static final String FIELD_DATA_TYPE = "dataType";
	private static final String FIELD_STATE = "state";
	private static final String FIELD_VALUE = "value";
	
	@Autowired
	VO2UIAdapter vo2uiAdapter;
	
	@Override
	public List<Map<String, Object>> toGridData(String fieldId, List<Map<String, Object>> entities) throws IllegalAccessException {
		
		List<Map<String, Object>> data = new ArrayList<>();
		
		try {
			
			if (!CollectionUtils.isEmpty(entities)) {
				
				for (Map<String, Object> entity : entities) {
					
					Map<String, Object> dataRow = new HashMap<>();

					dataRow.putAll(entity);
					
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
	
	@SuppressWarnings("unchecked")
	private void flattenElementValues(Map<String, Object> dataRow) {
		
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
										Integer state = (Integer) elementValue.get(FIELD_STATE);

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
										
										if (state != null) {
											
											Object newValue = value;
										
											dataRow.put(fieldParamId, new HashMap<>() {
												/**
												 * 
												 */
												private static final long serialVersionUID = 3628195862041865878L;

												{
													put(FIELD_VALUE, newValue);
													put(FIELD_STATE, state);
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
