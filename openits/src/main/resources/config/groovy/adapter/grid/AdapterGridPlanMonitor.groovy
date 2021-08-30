package config.groovy.adapter.grid;

import config.groovy.adapter.grid.AdapterGridGeneric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revenga.rits.front.openits.service.CacheManager;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

class AdapterGridPlanMonitor extends AdapterGridGeneric{

	static final String FIELD_PLAN_STATE_TYPE_ID = "planStateTypeId";
	static final List<String> PLAN_STATE_VALUES = new ArrayList<String>() {{
        add("PlanStateType:2");
        add("PlanStateType:4");
        add("PlanStateType:5");
    }};
	
	public AdapterGridPlanMonitor(CacheManager cacheManager, VO2UIAdapter vo2uiAdapter, org.apache.logging.log4j.Logger log) {
		
		super(cacheManager, vo2uiAdapter, log);
	}
	
	@Override
	public List<Map<String, Object>> toGridData(String fieldId, List<Map<String, Object>> entities) {
		
		List<Map<String, Object>> gridDataFiltered = new ArrayList<>();
		
		try {
		
			List<Map<String, Object>> gridData = super.toGridData(fieldId, entities);
			
			if (!CollectionUtils.isEmpty(gridData)) {
				
				for (Map<String, Object> entity : gridData) {

					String planStateTypeId = entity.get(FIELD_PLAN_STATE_TYPE_ID);
					
					if (PLAN_STATE_VALUES.contains(planStateTypeId)) {
					
						gridDataFiltered.add(entity);
					}
				}
			}
			
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return gridDataFiltered;
	}
}