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
public class PlanOrderBO extends GenericBO {

	@Autowired
	GraphqlAdapter graphqlAdapter;

	@Autowired
	VO2UIAdapter vo2uiAdapter;

	@Autowired
	VODataSourceAdapter voDataSourceAdapter;

	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> planOrders = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_ORDERS, null);

		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_ORDERS).getFieldId(), planOrders);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> planOrder = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_ORDER,
				new Object[] { id });

		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_ORDER).getFieldId(), planOrder);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> createPlanOrder(Object input) {

		Map<String, Object> createdPlanOrder = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_CREATE_PLAN_ORDER, (Map<String, Object>) input);

		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_CREATE_PLAN_ORDER).getFieldId(), createdPlanOrder);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> updatePlanOrder(Object input) {

		Map<String, Object> updatedPlanOrder = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_PLAN_ORDER, (Map<String, Object>) input);

		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_UPDATE_PLAN_ORDER).getFieldId(), updatedPlanOrder);
	}

	public Map<String, Object> deletePlanOrder(String id) {

		Map<String, Object> input = new HashMap<>();

		input.put("planOrderId", id);

		Map<String, Object> deletedPlanOrder = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_DELETE_PLAN_ORDER, input);

		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_DELETE_PLAN_ORDER).getFieldId(), deletedPlanOrder);
	}

}
