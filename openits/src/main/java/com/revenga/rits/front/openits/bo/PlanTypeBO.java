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
public class PlanTypeBO extends GenericBO {

	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> planTypes = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetPlanTypes(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlanTypes()).getFieldId(), planTypes);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> planType = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetPlanType(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlanType()).getFieldId(), planType);
	}
	
	public Map<String, Object> createPlanType(Object input) {
		
		Map<String, Object> createdPlanType = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceCreatePlanType(), (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceCreatePlanType()).getFieldId(), createdPlanType);
	}
	
	public Map<String, Object> updatePlanType(Object input) {
			
		Map<String, Object> updatedPlanType = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceUpdatePlanType(), (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceUpdatePlanType()).getFieldId(), updatedPlanType);
	}
	
	public Map<String, Object> deletePlanType(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("planTypeId", id);
		
		Map<String, Object> deletedPlanType = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceDeletePlanType(), input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceDeletePlanType()).getFieldId(), deletedPlanType);
	}
}
