package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class PlanOrderTypeBO extends GenericBO {

	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> planOrderTypes = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetPlanOrderTypes(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlanOrderTypes()).getFieldId(), planOrderTypes);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> planOrderType = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetPlanOrderType(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetPlanOrderType()).getFieldId(), planOrderType);
	}

}
