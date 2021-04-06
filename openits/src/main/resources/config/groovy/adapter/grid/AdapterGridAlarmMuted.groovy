import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

class AdapterGridElement {

	static final String FIELD_ALIAS = "alias";
	static final String FIELD_LABEL_ALIAS = "labelAlias";
    static final String FIELD_MUTED_UNTIL = "mutedUntil";
    static final String FIELD_OBJECT_ELEMENT = "element";
    static final String FIELD_ELEMENT_ALIAS  = "elementAlias";
    static final String FIELD_OBJECT_ALARM_CONFIG = "alarmConfig";
	static final String FIELD_ALARM_CONFIG_LABEL_ALIAS = "alarmConfigLabelAlias";

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
					
					flattenAlarmConfig(dataRow);
					flattenElement(dataRow);
					
					data.add(vo2uiAdapter.convert(fieldId, dataRow));
				}
			}
			
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return data;
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
}
