package config.groovy.adapter.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.groovy.adapter.grid.AdapterGrid;

import com.revenga.rits.front.openits.service.CacheManager;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

class AdapterGridPlanMonitor extends AdapterGrid {

	static final String FIELD_PLAN_STATE_TYPE = "planStateType";
	static final String FIELD_PLAN_STATE_TYPE_LABEL_ALIAS = "planStateTypeLabelAlias";
	static final String FIELD_LABEL_ALIAS = "labelAlias";
	static final String FIELD_FILTER_PLAN_STATE = "filterPlanState";
	static final String FIELD_CURRENT_PLAN_ORDER = "currentPlanOrder";
	static final String FIELD_ID = "id";

	static final String FIELD_CURRENT_PLAN_ORDER_ALIAS = "currentPlanOrderAlias";
	static final String FIELD_CURRENT_PLAN_ORDER_ORDER = "currentPlanOrderOrder";

	static final List<String> PLAN_STATE_VALUES = new ArrayList<String>() {
		{
			add("PlanStateType:2");
			add("PlanStateType:4");
			add("PlanStateType:5");
		}
	};

	public AdapterGridPlanMonitor(CacheManager cacheManager, VO2UIAdapter vo2uiAdapter,
			org.apache.logging.log4j.Logger log) {

		super(cacheManager, vo2uiAdapter, log);
	}

	@Override
	public List<Map<String, Object>> toGridData(String fieldId, List<Map<String, Object>> entities) {

		List<Map<String, Object>> data = new ArrayList<>();

		try {

			for (Map<String, Object> entity : entities) {

				Map<String, Object> dataRow = new HashMap<>();

				dataRow.putAll(entity);
				filterPlanState(dataRow);
				currentPlanOrder(dataRow);
				flattenPlanStateType(dataRow);
				
				data.add(vo2uiAdapter.convert(fieldId, dataRow));
			}

		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return data;
	}

	void filterPlanState(Map<String, Object> dataRow) {

		if (dataRow.containsKey(FIELD_PLAN_STATE_TYPE)) {

			Object obj = dataRow.get(FIELD_PLAN_STATE_TYPE);

			if (obj instanceof Map) {

				Map<String, Object> map = (Map<String, Object>) obj;

				if (PLAN_STATE_VALUES.contains(map.get(FIELD_ID))) {

					dataRow.put(FIELD_FILTER_PLAN_STATE, "0");

				} else {

					dataRow.put(FIELD_FILTER_PLAN_STATE, "-1");
				}
			}
		}
	}

	void currentPlanOrder(Map<String, Object> dataRow) {

		if (dataRow.containsKey(FIELD_CURRENT_PLAN_ORDER)) {

			Object obj = dataRow.get(FIELD_CURRENT_PLAN_ORDER);

			if (obj instanceof Map) {

				Map<String, Object> map = (Map<String, Object>) obj;
				dataRow.put(FIELD_CURRENT_PLAN_ORDER_ALIAS, map.get(FIELD_ID));
				dataRow.put(FIELD_CURRENT_PLAN_ORDER_ORDER, map.get(FIELD_ID));
			}
			dataRow.remove(FIELD_CURRENT_PLAN_ORDER);
		}
	}

	void flattenPlanStateType(Map<String, Object> dataRow) {

		if (dataRow.containsKey(FIELD_PLAN_STATE_TYPE)) {

			Object obj = dataRow.get(FIELD_PLAN_STATE_TYPE);

			if (obj instanceof Map) {

				Map<String, Object> map = (Map<String, Object>) obj;

				dataRow.put(FIELD_PLAN_STATE_TYPE_LABEL_ALIAS, map.get(FIELD_LABEL_ALIAS));
			}

			dataRow.remove(FIELD_PLAN_STATE_TYPE);
		}
	}
}