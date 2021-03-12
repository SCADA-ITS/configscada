import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

class AdapterGridGeneric {

	org.apache.logging.log4j.Logger log;
	
	AdapterGridGeneric(org.apache.logging.log4j.Logger log) {
		
		this.log = log;
	}
	
	List<Map<String, Object>> toGridData(VO2UIAdapter vo2uiAdapter, String fieldId, List<Map<String, Object>> entities) {
		
		List<Map<String, Object>> data = new ArrayList<>();
		
		try {
			
			if (!CollectionUtils.isEmpty(entities)) {
				
				for (Map<String, Object> entity : entities) {
									
					data.add(vo2uiAdapter.convert(fieldId, getObject(entity, null, fieldId)));
				}
			}
			
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return data;
	}
	
	Map<String, Object> getObject(Map<String, Object> entity, String parentClassName, String fieldId) {
		
		Map<String, Object> dataRow = null;
		
		if (!MapUtils.isEmpty(entity)) {
			
			dataRow = new HashMap<>();
			
			for (Map.Entry<String, Object> entry : entity.entrySet()) {
				
				String key = entry.getKey();
				
				if (!StringUtils.isEmpty(parentClassName)) {
					
					if (parentClassName.contains("[")) {
						
						key = parentClassName + key;
					}
					else {
					
						key = parentClassName + StringUtils.capitalize(key);
					}
				}
				
				Object value = entry.getValue();
				
				boolean primitiveValue = (value instanceof String || value instanceof Boolean || value instanceof Integer || value instanceof Double || value instanceof Float);
				
				if (primitiveValue) {
					
					dataRow.put(key, value);
				}
				else if (value instanceof Map) {
					
					Map<String, Object> objectMapFlattened = getObject((Map<String, Object>) value, entry.getKey(), fieldId);
					
					for (Map.Entry<String, Object> entryObject : objectMapFlattened.entrySet()) {
						
						dataRow.put(entryObject.getKey(), entryObject.getValue());	
					}
				}
				else if (value instanceof List) {
						
					List<?> list = (List<?>) value;
					
					for (Object item : list) {
						
						if (item instanceof Map) {
							
							String fieldIdValue = (String) ((Map<String, Object>) item).get(fieldId);
							
							if (!StringUtils.isEmpty(fieldIdValue)) {
							
								Map<String, Object> objectMapFlattened = getObject((Map<String, Object>) item, entry.getKey() + "[" + fieldIdValue + "].", fieldId);
								
								for (Map.Entry<String, Object> entryObject : objectMapFlattened.entrySet()) {
									
									dataRow.put(entryObject.getKey(), entryObject.getValue());	
								}
							}
						}
					}
				}
			}
		}
		
		return dataRow;
	}
}