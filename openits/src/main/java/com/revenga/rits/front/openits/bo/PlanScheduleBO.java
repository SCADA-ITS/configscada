package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class PlanScheduleBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> planSchedules = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_SCHEDULES, null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_SCHEDULES).getFieldId(), planSchedules);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> planSchedule = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_SCHEDULE, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_SCHEDULE).getFieldId(), planSchedule);
	}
	
	public List<Map<String, Object>> getPlanScheduleByPlan(String id) {
		
		List<Map<String, Object>> planScheduleByPlan = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_SCHEDULE_BY_PLAN, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_SCHEDULE_BY_PLAN).getFieldId(), planScheduleByPlan);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> sendPlanScheduleActions(Object input) {
		
		List<Map<String, Object>> sendPlanScheduleActions = voDataSourceAdapter.putList(VODataSourceAdapter.DATA_SOURCE_SEND_PLAN_SCHEDULE_ACTIONS, (Map<String, Object>)input);

		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_SEND_PLAN_SCHEDULE_ACTIONS).getFieldId(), sendPlanScheduleActions);
	}

}
