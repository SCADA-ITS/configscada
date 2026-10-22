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

class AdapterGridUser extends AdapterGrid{

	static final String FIELD_OBJECT_ROLE = "role";
	static final String FIELD_OBJECT_ROLE_ALIAS = "alias";
	
	static final String FIELD_ROLE_ALIAS = "roleAlias";
	
	public AdapterGridUser(CacheManager cacheManager, VO2UIAdapter vo2uiAdapter, org.apache.logging.log4j.Logger log) {
		
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
					
					flattenRole(dataRow);
					
					data.add(vo2uiAdapter.convert(datasourceId, dataRow));
				}
			}
			
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return data;
	}
	
	void flattenRole(Map<String, Object> dataRow) {
		
		if (dataRow.containsKey(FIELD_OBJECT_ROLE)) {
			
			Object objRole = dataRow.get(FIELD_OBJECT_ROLE);
			
			if (objRole instanceof Map) {
				
				Map<String, Object> role = (Map<String, Object>) objRole; 
				
				dataRow.put(FIELD_ROLE_ALIAS, role.get(FIELD_OBJECT_ROLE_ALIAS));
			}
			
			dataRow.remove(FIELD_OBJECT_ROLE);
		}
	}
}
