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

class AdapterGridAlarmFiltered extends AdapterGrid{

	static final String FIELD_ALIAS = "alias";
	static final String FIELD_LABEL_ALIAS = "labelAlias";
    static final String FIELD_OBJECT_ELEMENT = "element";
    static final String FIELD_ELEMENT_ALIAS  = "elementAlias";
    static final String FIELD_OBJECT_ALARM_CONFIG = "alarmConfig";
	static final String FIELD_ALARM_CONFIG_LABEL_ALIAS = "alarmConfigLabelAlias";
	static final String FIELD_OBJECT_USER = "user";
	static final String FIELD_USER_ID = "userId";
	static final String FIELD_ID = "id";
	static final String FIELD_OBJECT_ALARM = "alarm";
	static final String FIELD_ALARM_ID = "alarmId";
	static final String FIELD_ALARM_DISABLED = "alarmDisabled";
	static final String FIELD_ALARM_INHIBIT_LABEL = "alarmInhibitLabel";
	
	static final String LABEL_ALARM_DISABLED = "LBL_ALARM_FILTER_DISABLED_ALARM";
	static final String LABEL_ALARM_MUTED = "LBL_ALARM_FILTER_MUTED";

	public AdapterGridAlarmFiltered(CacheManager cacheManager, VO2UIAdapter vo2uiAdapter, org.apache.logging.log4j.Logger log) {
		
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
					
					assignAlarmInhibitType(dataRow);
					flattenAlarmConfig(dataRow);
					flattenAlarm(dataRow);
					flattenElement(dataRow);
					flattenUser(dataRow);

					data.add(vo2uiAdapter.convert(datasourceId, dataRow));
				}
			}
			
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return data;
	}
	
	void assignAlarmInhibitType(Map<String, Object> dataRow) {
	
		if (dataRow.containsKey(FIELD_ALARM_DISABLED)) {
		
			Object obj = dataRow.get(FIELD_ALARM_DISABLED);
			
			if (obj instanceof Boolean) {
			
				Boolean alarmDisabled = (Boolean) obj;
				
				dataRow.put(FIELD_ALARM_INHIBIT_LABEL, alarmDisabled ? LABEL_ALARM_DISABLED : LABEL_ALARM_MUTED);
				
				dataRow.remove(FIELD_ALARM_DISABLED);
			}
		}
	}
	
	void flattenAlarmConfig(Map<String, Object> dataRow) {
		
		if (dataRow.containsKey(FIELD_OBJECT_ALARM_CONFIG)) {
			
			Object obj = dataRow.get(FIELD_OBJECT_ALARM_CONFIG);
			
			if (obj instanceof Map) {
				
				Map<String, Object> map = (Map<String, Object>) obj; 
				
				dataRow.put(FIELD_ALARM_CONFIG_LABEL_ALIAS, map.get(FIELD_LABEL_ALIAS));
			}
			
			dataRow.remove(FIELD_OBJECT_ALARM_CONFIG);
		}
	}
	
	void flattenAlarm(Map<String, Object> dataRow) {
		
		if (dataRow.containsKey(FIELD_OBJECT_ALARM)) {
			
			Object obj = dataRow.get(FIELD_OBJECT_ALARM);
			
			if (obj instanceof Map) {
				
				Map<String, Object> map = (Map<String, Object>) obj; 
				
				dataRow.put(FIELD_ALARM_ID, map.get(FIELD_ID));
			}
			
			dataRow.remove(FIELD_OBJECT_ALARM);
		}
	}
	
	void flattenElement(Map<String, Object> dataRow) {
		
		if (dataRow.containsKey(FIELD_OBJECT_ELEMENT)) {
			
			Object obj = dataRow.get(FIELD_OBJECT_ELEMENT);
			
			if (obj instanceof Map) {
				
				Map<String, Object> map = (Map<String, Object>) obj; 

				dataRow.put(FIELD_ELEMENT_ALIAS, map.get(FIELD_ALIAS));
			}
			
			dataRow.remove(FIELD_OBJECT_ELEMENT);
		}
	}
	
	void flattenUser(Map<String, Object> dataRow) {
		
		if (dataRow.containsKey(FIELD_OBJECT_USER)) {
			
			Object obj = dataRow.get(FIELD_OBJECT_USER);
			
			if (obj instanceof Map) {
				
				Map<String, Object> map = (Map<String, Object>) obj; 

				dataRow.put(FIELD_USER_ID, map.get(FIELD_ID));
			}
			
			dataRow.remove(FIELD_OBJECT_USER);
		}
	}
}
