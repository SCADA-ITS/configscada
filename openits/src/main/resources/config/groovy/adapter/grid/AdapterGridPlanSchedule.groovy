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

class AdapterGridPlanSchedule extends AdapterGrid{

	static final String FIELD_ALIAS = "alias";
	static final String FIELD_LABEL_ALIAS = "labelAlias";
	static final String FIELD_DESCRIPTION = "description";
	static final String FIELD_CRON_EXPRESSION = "cronExpression";

	static final String FIELD_OBJECT_PLAN = "plan";
	static final String FIELD_OBJECT_PLAN_STATE_TYPE = "planStateType";
	static final String FIELD_OBJECT_PLAN_SCHEDULE_STATE_TYPE = "planScheduleStateType";

	static final String FIELD_PLAN_ID = "planId";
	static final String FIELD_PLAN_ALIAS = "planAlias";
	static final String FIELD_PLAN_DESCRIPTION = "planDescription";

	static final String FIELD_PLAN_STATE_TYPE_ID = "planStateTypeId";
	static final String FIELD_PLAN_STATE_TYPE_ALIAS = "planStateTypeAlias";
	static final String FIELD_PLAN_STATE_TYPE_DESCRIPTION = "planStateTypeDescription";
	static final String FIELD_PLAN_STATE_TYPE_LABEL_ALIAS = "planStateTypeLabelAlias";

	static final String FIELD_PLAN_SCHEDULE_STATE_TYPE_ALIAS = "planScheduleStateTypeAlias";
	static final String FIELD_PLAN_SCHEDULE_STATE_TYPE_DESCRIPTION = "planScheduleStateTypeDescription";
	static final String FIELD_PLAN_SCHEDULE_STATE_TYPE_LABEL_ALIAS = "planScheduleStateTypeLabelAlias";
	
	public AdapterGridPlanSchedule(CacheManager cacheManager, VO2UIAdapter vo2uiAdapter, org.apache.logging.log4j.Logger log) {
		
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
					
					flattenPlan(dataRow);
					flattenPlanScheduleStateType(dataRow);
					
					data.add(vo2uiAdapter.convert(datasourceId, dataRow));
				}
			}
			
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return data;
	}
	
	void flattenPlan(Map<String, Object> dataRow) {
		
		if (dataRow.containsKey(FIELD_OBJECT_PLAN)) {
			
			Object obj = dataRow.get(FIELD_OBJECT_PLAN);

			if (obj instanceof Map) {
				
				Map<String, Object> map = (Map<String, Object>) obj; 
				dataRow.put(FIELD_PLAN_ALIAS, map.get(FIELD_ALIAS));
				dataRow.put(FIELD_PLAN_DESCRIPTION, map.get(FIELD_DESCRIPTION));

				if (obj.containsKey(FIELD_OBJECT_PLAN_STATE_TYPE)) {
					
					Object obj2 = obj.get(FIELD_OBJECT_PLAN_STATE_TYPE);

					if (obj2 instanceof Map) {

						Map<String, Object> map2 = (Map<String, Object>) obj2;
						dataRow.put(FIELD_PLAN_STATE_TYPE_ALIAS, map2.get(FIELD_ALIAS));
						dataRow.put(FIELD_PLAN_STATE_TYPE_DESCRIPTION, map2.get(FIELD_DESCRIPTION));
						dataRow.put(FIELD_PLAN_STATE_TYPE_LABEL_ALIAS, map2.get(FIELD_LABEL_ALIAS));
					}

					obj.remove(FIELD_OBJECT_PLAN_STATE_TYPE);
				}

			}
			
			dataRow.remove(FIELD_OBJECT_PLAN);
		}
	}

	void flattenPlanScheduleStateType(Map<String, Object> dataRow) {

		if (dataRow.containsKey(FIELD_OBJECT_PLAN_SCHEDULE_STATE_TYPE)) {

			Object obj = dataRow.get(FIELD_OBJECT_PLAN_SCHEDULE_STATE_TYPE);

			if (obj instanceof Map) {

				Map<String, Object> map = (Map<String, Object>) obj;

				dataRow.put(FIELD_PLAN_SCHEDULE_STATE_TYPE_LABEL_ALIAS, map.get(FIELD_LABEL_ALIAS));
			}
			else {
			
				dataRow.put(FIELD_PLAN_SCHEDULE_STATE_TYPE_LABEL_ALIAS, "LBL_UNKNOWN");
			}
			dataRow.remove(FIELD_OBJECT_PLAN_SCHEDULE_STATE_TYPE);
		}
		else {
		
			dataRow.put(FIELD_PLAN_SCHEDULE_STATE_TYPE_LABEL_ALIAS, "LBL_UNKNOWN");
		}
	}

}
