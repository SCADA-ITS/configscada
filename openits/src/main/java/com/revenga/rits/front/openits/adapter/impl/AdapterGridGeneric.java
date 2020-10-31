package com.revenga.rits.front.openits.adapter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class AdapterGridGeneric {

	org.apache.logging.log4j.Logger log;
	
	public AdapterGridGeneric(org.apache.logging.log4j.Logger log) {
		
		this.log = log;
	}
	
	public List<Map<String, Object>> toGridData(VO2UIAdapter vo2uiAdapter, String fieldId, List<Map<String, Object>> entities) {
		
		List<Map<String, Object>> data = new ArrayList<>();
		
		try {
			
			if (!CollectionUtils.isEmpty(entities)) {
				
				for (Map<String, Object> entity : entities) {
					
					Map<String, Object> dataRow = new HashMap<>();

					for (Map.Entry<String, Object> entry : entity.entrySet()) {
						
						String key = entry.getKey();
						Object value = entry.getValue();
						
						if (value instanceof List || value instanceof Map) {
					
							continue;
						}
						
						dataRow.put(key, value);
					}
					
					data.add(vo2uiAdapter.convert(fieldId, dataRow));
				}
			}
			
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return data;
	}
}
