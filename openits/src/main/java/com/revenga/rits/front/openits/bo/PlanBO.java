package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class PlanBO extends GenericBO {

	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	

	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> plans = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetPlans(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlans()).getFieldId(), plans);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> plan = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetPlan(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlan()).getFieldId(), plan);
	}
	
	public Map<String, Object> createPlan(Object input) {
		
		Map<String, Object> createdPlan = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceCreatePlan(), (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceCreatePlan()).getFieldId(), createdPlan);
	}
	
	public Map<String, Object> updatePlan(Object input) {
			
		Map<String, Object> updatedPlan = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceUpdatePlan(), (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceUpdatePlan()).getFieldId(), updatedPlan);
	}
	
	public Map<String, Object> deletePlan(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("planId", id);
		
		Map<String, Object> deletedPlan = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceDeletePlan(), input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceDeletePlan()).getFieldId(), deletedPlan);
	}

}
