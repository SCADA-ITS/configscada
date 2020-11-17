package com.revenga.rits.front.openits.bo;

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
	
}
