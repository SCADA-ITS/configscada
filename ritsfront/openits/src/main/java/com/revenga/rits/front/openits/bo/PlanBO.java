package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class PlanBO extends GenericBO {

	@Autowired
	GraphqlAdapter graphqlAdapter;

	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_PLANS;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_PLAN;
	}

	public List<Map<String, Object>> getSummaryList() {

		List<Map<String, Object>> plans = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_PLANS_SUMMARY,
				null);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_PLANS_SUMMARY, plans);
	}
	
	public List<Map<String, Object>> getPlansPlanLibrary() {

		List<Map<String, Object>> plans = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_PLANS_PLAN_LIBRARY,
				null);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_PLANS_PLAN_LIBRARY, plans);
	}
	
	public Map<String, Object> getPlanPlanLibrary(String id) {

		Map<String, Object> plan = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_PLAN_LIBRARY,
				new Object[] { id });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_PLAN_LIBRARY, plan);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> createPlan(Object input) {

		Map<String, Object> createdPlan = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CREATE_PLAN,
				(Map<String, Object>) input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CREATE_PLAN, createdPlan);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> updatePlan(Object input) {

		Map<String, Object> updatedPlan = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_PLAN,
				(Map<String, Object>) input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_UPDATE_PLAN, updatedPlan);
	}

	public Map<String, Object> deletePlan(String id) {

		Map<String, Object> input = new HashMap<>();

		input.put("planId", id);

		Map<String, Object> deletedPlan = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_DELETE_PLAN, input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_DELETE_PLAN, deletedPlan);
	}

	@SuppressWarnings("unchecked")
	public Boolean sendPlayPlan(Object input) {

		Boolean res = false;

		Map<String, Object> sendPlayPlan = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_PLAY_PLAN,
				(Map<String, Object>) input);

		res = checkMapIsBoolean(sendPlayPlan);

		return res;

	}

	@SuppressWarnings("unchecked")
	public Boolean sendExecutePlan(Object input) {

		Boolean res = false;

		
		Map<String, Object> sendExecutePlan = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_EXECUTE_PLAN,
				(Map<String, Object>) input);

		res = checkMapIsBoolean(sendExecutePlan);

		return res;

	}

	public List<Map<String, Object>> getPlanHierarchies() {
		
		List<Map<String, Object>> planHierarchies = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_HIERARCHIES, null);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_HIERARCHIES, planHierarchies);
	}
	
	public List<Map<String, Object>> getPlanNodes() {
		
		List<Map<String, Object>> planNodes = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_NODES, null);
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_PLAN_NODES, planNodes);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> createPlanNode(Object input) {

		Map<String, Object> createdPlan = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CREATE_PLAN_NODE,
				(Map<String, Object>) input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CREATE_PLAN_NODE, createdPlan);
	}

	public Map<String, Object> deletePlanNode(String id) {

		Map<String, Object> input = new HashMap<>();

		input.put("planNodeId", id);

		Map<String, Object> deletedPlanNode = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_DELETE_PLAN_NODE, input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_DELETE_PLAN_NODE, deletedPlanNode);
	}
}
