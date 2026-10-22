package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class PlanSubtypeBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_PLAN_SUBTYPES;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_PLAN_SUBTYPE;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> createPlanSubtype(Object input) {
		
		Map<String, Object> createdPlanSubtype = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CREATE_PLAN_SUBTYPE, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CREATE_PLAN_SUBTYPE, createdPlanSubtype);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updatePlanSubtype(Object input) {
			
		Map<String, Object> updatedPlanSubtype = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_PLAN_SUBTYPE, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_UPDATE_PLAN_SUBTYPE, updatedPlanSubtype);
	}
	
	public Map<String, Object> deletePlanSubtype(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("planSubtypeId", id);
		
		Map<String, Object> deletedPlanSubtype = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_DELETE_PLAN_SUBTYPE, input);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_DELETE_PLAN_SUBTYPE, deletedPlanSubtype);
	}

}
