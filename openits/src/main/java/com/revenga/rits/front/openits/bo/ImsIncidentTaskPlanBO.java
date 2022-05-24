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
public class ImsIncidentTaskPlanBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TASK_PLANS;
	}
	
	public List<Map<String, Object>> getTaskPlansByStretchAndIncidentTypeAndIncidentTypeTask(String stretchId, String incidentTypeId, String incidentTypeTaskId) {
		
		List<Map<String, Object>> incidentTaskPlans = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_TASK_PLANS_BY_STRETCH_AND_INCIDENT_TYPE_AND_INCIDENT_TYPE_TASK, new Object[] { stretchId, incidentTypeId, incidentTypeTaskId });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_TASK_PLANS_BY_STRETCH_AND_INCIDENT_TYPE_AND_INCIDENT_TYPE_TASK, incidentTaskPlans);
	}

	@Override
	public Map<String, Object> getItem(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public Object create(Object input) {

		Map<String, Object> createdIncidentTaskPlan = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_CREATE_INCIDENT_TASK_PLAN, (Map<String, Object>) input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CREATE_INCIDENT_TASK_PLAN, createdIncidentTaskPlan);
	}
	
	@SuppressWarnings("unchecked")
	public Object update(Object input) {

		Map<String, Object> updatedIncidentTaskPlan = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_INCIDENT_TASK_PLAN, (Map<String, Object>) input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_UPDATE_INCIDENT_TASK_PLAN, updatedIncidentTaskPlan);
	}
	
	public Object delete(String id) {
		
		Map<String, Object> input = new HashMap<>();

		input.put("imsIncidentTaskPlanId", id);

		Map<String, Object> deletedIncidentTaskPlan = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_DELETE_INCIDENT_TASK_PLAN, input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_DELETE_INCIDENT_TASK_PLAN, deletedIncidentTaskPlan);
	}
}
