package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class PlanOrderBO extends GenericBO {

	@Autowired
	GraphqlAdapter graphqlAdapter;

	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_PLAN_ORDERS;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_PLAN_ORDER;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> createPlanOrder(Object input) {

		Map<String, Object> createdPlanOrder = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_CREATE_PLAN_ORDER, (Map<String, Object>) input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CREATE_PLAN_ORDER, createdPlanOrder);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> updatePlanOrder(Object input) {

		Map<String, Object> updatedPlanOrder = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_PLAN_ORDER, (Map<String, Object>) input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_UPDATE_PLAN_ORDER, updatedPlanOrder);
	}

	public Map<String, Object> deletePlanOrder(String id) {

		Map<String, Object> input = new HashMap<>();

		input.put("planOrderId", id);

		Map<String, Object> deletedPlanOrder = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_DELETE_PLAN_ORDER, input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_DELETE_PLAN_ORDER, deletedPlanOrder);
	}

}
