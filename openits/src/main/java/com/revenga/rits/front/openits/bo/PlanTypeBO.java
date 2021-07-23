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

		List<Map<String, Object>> planTypes = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_TYPES, null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_TYPES).getFieldId(), planTypes);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> planType = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_TYPE, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_TYPE).getFieldId(), planType);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> createPlanType(Object input) {
		
		Map<String, Object> createdPlanType = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CREATE_PLAN_TYPE, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_CREATE_PLAN_TYPE).getFieldId(), createdPlanType);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updatePlanType(Object input) {
			
		Map<String, Object> updatedPlanType = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_PLAN_TYPE, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_UPDATE_PLAN_TYPE).getFieldId(), updatedPlanType);
	}
	
	public Map<String, Object> deletePlanType(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("planTypeId", id);
		
		Map<String, Object> deletedPlanType = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_DELETE_PLAN_TYPE, input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_DELETE_PLAN_TYPE).getFieldId(), deletedPlanType);
	}
}
