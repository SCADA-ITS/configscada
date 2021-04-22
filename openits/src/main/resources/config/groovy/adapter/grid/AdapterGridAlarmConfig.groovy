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

class AdapterGridAlarmConfig extends AdapterGrid{

	static final String FIELD_ALIAS = "alias";
	static final String FIELD_DESCRIPTION = "description";
	static final String FIELD_LABEL_ALIAS = "labelAlias";
	static final String FIELD_LABEL_DESCRIPTION = "labelDescription";

	static final String FIELD_OBJECT_ALARM_TYPE = "alarmType";
	static final String FIELD_OBJECT_ALARM_LEVEL = "alarmLevel";
	static final String FIELD_OBJECT_ELEMENT_TYPE = "elementType";
	static final String FIELD_OBJECT_ELEMENT = "element";
	
	static final String FIELD_ALARM_TYPE_ALIAS = "alarmTypeAlias";
	static final String FIELD_ALARM_LEVEL_ALIAS = "alarmLevelAlias";
	static final String FIELD_ELEMENT_TYPE_ALIAS = "elementTypeAlias";
	static final String FIELD_ELEMENT_ALIAS = "elementAlias";
	
	static final String FIELD_ALARM_TYPE_DESCRIPTION = "alarmTypeDescription";
	static final String FIELD_ALARM_LEVEL_DESCRIPTION = "alarmLevelDescription";
	static final String FIELD_ELEMENT_TYPE_DESCRIPTION = "elementTypeDescription";
	static final String FIELD_ELEMENT_DESCRIPTION = "elementDescription";
	
	static final String FIELD_ALARM_TYPE_LABEL_ALIAS = "alarmTypeLabelAlias";
	static final String FIELD_ALARM_LEVEL_LABEL_ALIAS = "alarmLevelLabelAlias";
	static final String FIELD_ELEMENT_TYPE_LABEL_ALIAS = "elementTypeLabelAlias";
	
	static final String FIELD_ALARM_TYPE_LABEL_DESCRIPTION = "alarmTypeLabelDescription";
	static final String FIELD_ALARM_LEVEL_LABEL_DESCRIPTION = "alarmLevelLabelDescription";
	static final String FIELD_ELEMENT_TYPE_LABEL_DESCRIPTION = "elementTypeLabelDescription";
	
	public AdapterGridAlarmConfig(CacheManager cacheManager, VO2UIAdapter vo2uiAdapter, org.apache.logging.log4j.Logger log) {
		
		super(cacheManager, vo2uiAdapter, log);
	}
	
	@Override
	public List<Map<String, Object>> toGridData(String fieldId, List<Map<String, Object>> entities) {
		
		List<Map<String, Object>> data = new ArrayList<>();
		
		try {
			
			if (!CollectionUtils.isEmpty(entities)) {
				
				for (Map<String, Object> entity : entities) {
					
					Map<String, Object> dataRow = new HashMap<>();
					
					dataRow.putAll(entity);
					
					flattenAlarmType(dataRow);
					flattenAlarmLevel(dataRow);
					flattenElementType(dataRow);
					
					data.add(vo2uiAdapter.convert(fieldId, dataRow));
				}
			}
			
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return data;
	}
	
	void flattenAlarmType(Map<String, Object> dataRow) {
		
		if (dataRow.containsKey(FIELD_OBJECT_ALARM_TYPE)) {
			
			Object obj = dataRow.get(FIELD_OBJECT_ALARM_TYPE);
			
			if (obj instanceof Map) {
				
				Map<String, Object> map = (Map<String, Object>) obj; 
				
				dataRow.put(FIELD_ALARM_TYPE_ALIAS, map.get(FIELD_ALIAS));
				dataRow.put(FIELD_ALARM_TYPE_DESCRIPTION, map.get(FIELD_DESCRIPTION));
				dataRow.put(FIELD_ALARM_TYPE_LABEL_ALIAS, map.get(FIELD_LABEL_ALIAS));
				dataRow.put(FIELD_ALARM_TYPE_LABEL_DESCRIPTION, map.get(FIELD_LABEL_DESCRIPTION));
			}
			
			dataRow.remove(FIELD_OBJECT_ALARM_TYPE);
		}
	}
	
	void flattenAlarmLevel(Map<String, Object> dataRow) {
		
		if (dataRow.containsKey(FIELD_OBJECT_ALARM_LEVEL)) {
			
			Object obj = dataRow.get(FIELD_OBJECT_ALARM_LEVEL);
			
			if (obj instanceof Map) {
				
				Map<String, Object> map = (Map<String, Object>) obj; 
				
				dataRow.put(FIELD_ALARM_LEVEL_ALIAS, map.get(FIELD_ALIAS));
				dataRow.put(FIELD_ALARM_LEVEL_DESCRIPTION, map.get(FIELD_DESCRIPTION));
				dataRow.put(FIELD_ALARM_LEVEL_LABEL_ALIAS, map.get(FIELD_LABEL_ALIAS));
				dataRow.put(FIELD_ALARM_LEVEL_LABEL_DESCRIPTION, map.get(FIELD_LABEL_DESCRIPTION));
			}
			
			dataRow.remove(FIELD_OBJECT_ALARM_LEVEL);
		}
	}
	
	void flattenElementType(Map<String, Object> dataRow) {
		
		if (dataRow.containsKey(FIELD_OBJECT_ELEMENT_TYPE)) {
			
			Object obj = dataRow.get(FIELD_OBJECT_ELEMENT_TYPE);
			
			if (obj instanceof Map) {
				
				Map<String, Object> map = (Map<String, Object>) obj; 
				
				dataRow.put(FIELD_ELEMENT_TYPE_ALIAS, map.get(FIELD_ALIAS));
				dataRow.put(FIELD_ELEMENT_TYPE_DESCRIPTION, map.get(FIELD_DESCRIPTION));
				dataRow.put(FIELD_ELEMENT_TYPE_LABEL_ALIAS, map.get(FIELD_LABEL_ALIAS));
				dataRow.put(FIELD_ELEMENT_TYPE_LABEL_DESCRIPTION, map.get(FIELD_LABEL_DESCRIPTION));
			}
			
			dataRow.remove(FIELD_OBJECT_ELEMENT_TYPE);
		}
	}
	
	void flattenElement(Map<String, Object> dataRow) {
		
		if (dataRow.containsKey(FIELD_OBJECT_ELEMENT)) {
			
			Object obj = dataRow.get(FIELD_OBJECT_ELEMENT);
			
			if (obj instanceof Map) {
				
				Map<String, Object> map = (Map<String, Object>) obj; 
				
				dataRow.put(FIELD_ELEMENT_ALIAS, map.get(FIELD_ALIAS));
				dataRow.put(FIELD_ELEMENT_DESCRIPTION, map.get(FIELD_DESCRIPTION));
			}
			
			dataRow.remove(FIELD_OBJECT_ELEMENT);
		}
	}
}
