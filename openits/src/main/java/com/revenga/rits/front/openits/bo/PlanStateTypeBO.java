package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class PlanStateTypeBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getPlanStateTypes() {
		
		List<Map<String, Object>> planStateTypes = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetPlanStateTypes(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlanStateTypes()).getFieldId(), planStateTypes);
	}
				
	public List<Map<String, Object>> getPlanOrderStateTypes() {
		
		List<Map<String, Object>> planOrderStateTypes = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetPlanOrderStateTypes(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlanOrderStateTypes()).getFieldId(), planOrderStateTypes);
	}
	
	public List<Map<String, Object>> getPlanScheduleStateTypes() {
		
		List<Map<String, Object>> planScheduleStateTypes = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetPlanScheduleStateTypes(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlanScheduleStateTypes()).getFieldId(), planScheduleStateTypes);
	}

}
