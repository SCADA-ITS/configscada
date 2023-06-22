package config.groovy.adapter.grid;

import config.groovy.adapter.grid.AdapterGrid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revenga.rits.front.openits.service.CacheManager;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

class AdapterGridElement extends AdapterGrid{

	static final String DATA_TYPE_INTEGER = "DataType:1";
	static final String DATA_TYPE_ALPHANUMERIC = "DataType:2";
	static final String DATA_TYPE_DECIMAL = "DataType:3";
	static final String DATA_TYPE_TIMESTAMP = "DataType:4";
	static final String DATA_TYPE_BOOLEAN = "DataType:5";
	
	static final String FIELD_ELEMENT_VALUES = "elementValues";
	static final String FIELD_ELEMENT_TYPE_PARAM = "elementTypeParam";
	static final String FIELD_ID = "id";
	static final String FIELD_LABEL_ALIAS = "labelAlias";
	static final String FIELD_DATA_TYPE = "dataType";
	static final String FIELD_ELEMENT_VALUE_STATE = "elementValueState";
	static final String FIELD_STATE = "state";
	static final String FIELD_VALUE = "value";
	static final String FIELD_ELEMENT_TYPE_STATE = "elementTypeState";
	static final String FIELD_ELEMENT_STATE = "elementState";
	static final String FIELD_ELEMENT_STATE_CURRENT= "currentElementTypeState";
	static final String FIELD_ELEMENT_STATE_MAX_ALARM_LEVEL= "maxAlarmLevel";
	static final String ALARM_STATE_ID = "3"
	
	static final String FIELD_ELEMENT_STATE_STATE = "state";
	static final String FIELD_ELEMENT_STATE_STATE_LABEL = "stateLabel";
	static final String FIELD_LOCATION = "location";
	static final String FIELD_ALIAS = "alias";
	static final String FIELD_DESCRIPTION = "description";
	static final String FIELD_LOCATION_ID = "locationId";
	static final String FIELD_LOCATION_ALIAS = "locationAlias";
	static final String FIELD_LOCATION_DESCRIPTION = "locationDescription";
	
	public AdapterGridElement(CacheManager cacheManager, VO2UIAdapter vo2uiAdapter, org.apache.logging.log4j.Logger log) {
		
		super(cacheManager, vo2uiAdapter, log);
	}
	
	@Override
	public List<Map<String, Object>> toGridData(Integer datasourceId, List<Map<String, Object>> entities) {
		
		List<Map<String, Object>> data = new ArrayList<>();
		
		try {
			
			if (!CollectionUtils.isEmpty(entities)) {
				
				for (Map<String, Object> entity : entities) {
					
					Map<String, Object> dataRow = new HashMap<>();
					
					dataRow.putAll(entity);
					
					flattenElementTypeState(dataRow);
					flattenElementValues(dataRow);
					flattenLocation(dataRow);
					
					data.add(vo2uiAdapter.convert(datasourceId, dataRow));
				}
			}
			
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return data;
	}
	
	void flattenElementTypeState(Map<String, Object> dataRow) {
		
		if (dataRow.containsKey(FIELD_ELEMENT_STATE)) {
			
			Object objElementState = dataRow.get(FIELD_ELEMENT_STATE);
			
			if (objElementState instanceof Map) {
				
				Map<String, Object> elementState = (Map<String, Object>) objElementState; 
				
				
				if (elementState.containsKey(FIELD_ELEMENT_STATE_CURRENT)) {
					Object objCurrentElementTypeState = elementState.get(FIELD_ELEMENT_STATE_CURRENT);
					
					if (objCurrentElementTypeState instanceof Map) {
						
						Map<String, Object> currentElementTypeState = (Map<String, Object>) objCurrentElementTypeState;
						String labelAlias = currentElementTypeState.get(FIELD_LABEL_ALIAS);
						String id = currentElementTypeState.get(FIELD_ID);
						String[] parts = id.split(":");
						
						
						if (parts.length == 3 && parts[2].equals(ALARM_STATE_ID) && elementState.containsKey(FIELD_ELEMENT_STATE_MAX_ALARM_LEVEL)){
							
							Object oblMaxAlarmLevel = elementState.get(FIELD_ELEMENT_STATE_MAX_ALARM_LEVEL);
							
							if (oblMaxAlarmLevel instanceof Map) {
						
								Map<String, Object> maxAlarmLevel = (Map<String, Object>) oblMaxAlarmLevel;
								
								if(maxAlarmLevel.containsKey(FIELD_LABEL_ALIAS)){
									
									labelAlias = labelAlias + " - " + maxAlarmLevel.get(FIELD_LABEL_ALIAS);
								}
							}
							
						}
						
						dataRow.put(FIELD_ELEMENT_STATE_STATE, id);
						dataRow.put(FIELD_ELEMENT_STATE_STATE_LABEL, labelAlias);
					}
				}
					
			}
			
			dataRow.remove(FIELD_ELEMENT_STATE);
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
										
										Object objDataType = elementTypeParam.get(FIELD_DATA_TYPE);
									
										if (objDataType instanceof Map) {
										
											Map<String, Object> dataType = (Map<String, Object>) objDataType;
										
											String dataTypeId = (String) dataType.get(FIELD_ID);
											
											Object elementValueStateObj = elementValue.get(FIELD_ELEMENT_VALUE_STATE);

											String valueStr = (String) elementValue.get(FIELD_VALUE);
											
											Object value = valueStr;
											
											try {
											
												if (dataTypeId.equals(DATA_TYPE_INTEGER)) {
												
													value = Integer.parseInt(valueStr);
												}
												else if (dataTypeId.equals(DATA_TYPE_ALPHANUMERIC)) {
													
													value = valueStr;
												}
												else if (dataTypeId.equals(DATA_TYPE_DECIMAL)) {
													
													value = Float.parseFloat(valueStr);
												}
												else if (dataTypeId.equals(DATA_TYPE_TIMESTAMP)) {
													
													value = Long.parseLong(valueStr);
												}
												else if (dataTypeId.equals(DATA_TYPE_BOOLEAN)) {
													
													value = Boolean.parseBoolean(valueStr);
												}
											}
											catch (Exception e) {
											
												log.error(e.getMessage());
												log.debug(ExceptionUtils.getStackTrace(e));
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
				}
				
				dataRow.remove(FIELD_ELEMENT_VALUES);
			}
		}
	}
	
	void flattenLocation(Map<String, Object> dataRow) {
		
		if (dataRow.containsKey(FIELD_LOCATION)) {
			
			Object objLocation = dataRow.get(FIELD_LOCATION);
			
			if (objLocation instanceof Map) {
				
				Map<String, Object> location = (Map<String, Object>) objLocation; 
				
				dataRow.put(FIELD_LOCATION_ID, location.get(FIELD_ID));
				dataRow.put(FIELD_LOCATION_ALIAS, location.get(FIELD_ALIAS));
				dataRow.put(FIELD_LOCATION_DESCRIPTION, location.get(FIELD_DESCRIPTION));
			}
			
			dataRow.remove(FIELD_LOCATION);
		}
	}
}
