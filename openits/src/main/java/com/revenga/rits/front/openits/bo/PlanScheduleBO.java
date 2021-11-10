package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class PlanScheduleBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_PLAN_SCHEDULES;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_PLAN_SCHEDULE;
	}
	
	public List<Map<String, Object>> getPlanScheduleByPlan(String id) {
		
		List<Map<String, Object>> planScheduleByPlan = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_SCHEDULE_BY_PLAN, new Object[] { id });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_SCHEDULE_BY_PLAN, planScheduleByPlan);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> sendPlanScheduleActions(Object input) {
		
		List<Map<String, Object>> sendPlanScheduleActions = voDataSourceAdapter.putList(VODataSourceAdapter.DATA_SOURCE_SEND_PLAN_SCHEDULE_ACTIONS, (Map<String, Object>)input);

		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_SEND_PLAN_SCHEDULE_ACTIONS, sendPlanScheduleActions);
	}

}
