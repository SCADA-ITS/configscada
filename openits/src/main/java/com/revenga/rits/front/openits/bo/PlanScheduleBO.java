package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class PlanScheduleBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getPlanSchedules() {
		
		List<Map<String, Object>> planSchedules = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetPlanSchedules(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlanSchedules()).getFieldId(), planSchedules);
	}
				
	public Map<String, Object> getPlanSchedule(String id) {
		
		Map<String, Object> planSchedule = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetPlanSchedule(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlanSchedule()).getFieldId(), planSchedule);
	}
	
	public List<Map<String, Object>> getPlanScheduleByPlan(String id) {
		
		List<Map<String, Object>> planScheduleByPlan = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetPlanScheduleByPlan(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlanScheduleByPlan()).getFieldId(), planScheduleByPlan);
	}

}
