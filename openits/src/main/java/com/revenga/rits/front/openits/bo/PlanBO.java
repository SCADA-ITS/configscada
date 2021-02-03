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

		List<Map<String, Object>> plans = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_PLANS, null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_PLANS).getFieldId(), plans);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> plan = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_PLAN, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_PLAN).getFieldId(), plan);
	}
	
	public Map<String, Object> createPlan(Object input) {
		
		Map<String, Object> createdPlan = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CREATE_PLAN, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_CREATE_PLAN).getFieldId(), createdPlan);
	}
	
	public Map<String, Object> updatePlan(Object input) {
			
		Map<String, Object> updatedPlan = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_PLAN, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_UPDATE_PLAN).getFieldId(), updatedPlan);
	}
	
	public Map<String, Object> deletePlan(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("planId", id);
		
		Map<String, Object> deletedPlan = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_DELETE_PLAN, input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_DELETE_PLAN).getFieldId(), deletedPlan);
	}
	
	public Boolean sendPlayPlan(Object input) {
	    
	    Boolean res = false;
	    
	    Map<String, Object> sendPlayPlan = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_PLAY_PLAN, (Map<String, Object>) input);
	    
	    res = checkMapIsBoolean(sendPlayPlan);

	    return res;
	    
	  }

}
