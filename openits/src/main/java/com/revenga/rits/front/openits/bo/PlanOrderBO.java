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
public class PlanOrderBO {

	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getPlanOrders() {
		
		List<Map<String, Object>> planOrders = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetPlanOrders(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlanOrders()).getFieldId(), planOrders);
	}
				
	public Map<String, Object> getPlanOrder(String id) {
		
		Map<String, Object> planOrder = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetPlanOrder(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlanOrder()).getFieldId(), planOrder);
	}
	
	public Map<String, Object> createPlanOrder(Object input) {
		
		Map<String, Object> createdPlanOrder = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceCreatePlanOrder(), (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceCreatePlanOrder()).getFieldId(), createdPlanOrder);
	}
	
	public Map<String, Object> updatePlanOrder(Object input) {
			
		Map<String, Object> updatedPlanOrder = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceUpdatePlanOrder(), (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceUpdatePlanOrder()).getFieldId(), updatedPlanOrder);
	}
	
	public Map<String, Object> deletePlanOrder(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("planOrderId", id);
		
		Map<String, Object> deletedPlanOrder = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceDeletePlanOrder(), input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceDeletePlanOrder()).getFieldId(), deletedPlanOrder);
	}
	
}
