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

		List<Map<String, Object>> planSchedules = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetPlanSchedules(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlanSchedules()).getFieldId(), planSchedules);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> planSchedule = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetPlanSchedule(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlanSchedule()).getFieldId(), planSchedule);
	}
	
	public List<Map<String, Object>> getPlanScheduleByPlan(String id) {
		
		List<Map<String, Object>> planScheduleByPlan = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetPlanScheduleByPlan(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlanScheduleByPlan()).getFieldId(), planScheduleByPlan);
	}
	
	public List<Map<String, Object>> sendPlanScheduleActions(Object input) {
		
		List<Map<String, Object>> sendPlanScheduleActions = voDataSourceAdapter.putList(voDataSourceAdapter.getDataSourceSendPlanScheduleActions(), (Map<String, Object>)input);

		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceCreatePlanSubtype()).getFieldId(), sendPlanScheduleActions);
	}

}
