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

		List<Map<String, Object>> planOrderTypes = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_ORDER_TYPES, null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_ORDER_TYPES).getFieldId(), planOrderTypes);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> planOrderType = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_ORDER_TYPE, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_ORDER_TYPE).getFieldId(), planOrderType);
	}

}
