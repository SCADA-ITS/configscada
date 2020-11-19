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
public class PlanSubtypeBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;

	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> planSubtypes = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetPlanSubtypes(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlanSubtypes()).getFieldId(), planSubtypes);
	}

	@Override
	public Map<String, Object> getItem(String id) {
		
		Map<String, Object> planSubtype = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetPlanSubtype(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlanSubtype()).getFieldId(), planSubtype);
	}

	
	public Map<String, Object> createPlanSubtype(Object input) {
		
		Map<String, Object> createdPlanSubtype = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceCreatePlanSubtype(), (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceCreatePlanSubtype()).getFieldId(), createdPlanSubtype);
	}
	
	public Map<String, Object> updatePlanSubtype(Object input) {
			
		Map<String, Object> updatedPlanSubtype = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceUpdatePlanSubtype(), (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceUpdatePlanSubtype()).getFieldId(), updatedPlanSubtype);
	}
	
	public Map<String, Object> deletePlanSubtype(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("planSubtypeId", id);
		
		Map<String, Object> deletedPlanSubtype = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceDeletePlanSubtype(), input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceDeletePlanSubtype()).getFieldId(), deletedPlanSubtype);
	}

}
