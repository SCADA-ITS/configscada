package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class PlanStateTypeBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
		
	public List<Map<String, Object>> getPlanStateTypes() {
		
		List<Map<String, Object>> planStateTypes = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_STATE_TYPES, null);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_STATE_TYPES, planStateTypes);
	}
				
	public List<Map<String, Object>> getPlanOrderStateTypes() {
		
		List<Map<String, Object>> planOrderStateTypes = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_ORDER_STATE_TYPES, null);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_ORDER_STATE_TYPES, planOrderStateTypes);
	}
	
	public List<Map<String, Object>> getPlanScheduleStateTypes() {
		
		List<Map<String, Object>> planScheduleStateTypes = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_SCHEDULE_STATE_TYPES, null);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_SCHEDULE_STATE_TYPES, planScheduleStateTypes);
	}

	@Override
	public List<Map<String, Object>> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getItem(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
