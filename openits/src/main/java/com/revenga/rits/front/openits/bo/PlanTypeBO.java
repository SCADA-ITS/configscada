package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class PlanTypeBO extends GenericBO {

	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_PLAN_TYPES;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_PLAN_TYPE;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> createPlanType(Object input) {
		
		Map<String, Object> createdPlanType = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CREATE_PLAN_TYPE, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CREATE_PLAN_TYPE, createdPlanType);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updatePlanType(Object input) {
			
		Map<String, Object> updatedPlanType = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_PLAN_TYPE, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_UPDATE_PLAN_TYPE, updatedPlanType);
	}
	
	public Map<String, Object> deletePlanType(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("planTypeId", id);
		
		Map<String, Object> deletedPlanType = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_DELETE_PLAN_TYPE, input);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_DELETE_PLAN_TYPE, deletedPlanType);
	}
}
