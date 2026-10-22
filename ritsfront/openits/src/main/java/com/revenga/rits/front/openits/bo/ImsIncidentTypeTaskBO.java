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
public class ImsIncidentTypeTaskBO extends GenericBO  {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPE_TASKS;
	}

	public List<Map<String, Object>> getIncidentTypeTaskByIncidentTypeAndIncidentState(String incidentTypeId, String incidentStateId) {
		
		List<Map<String, Object>> incidentTypeTasks = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPE_TASK_BY_INCIDENT_TYPE_AND_INCIDENT_STATE, new Object[] {incidentTypeId, incidentStateId });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPE_TASK_BY_INCIDENT_TYPE_AND_INCIDENT_STATE, incidentTypeTasks);
	}

	@Override
	public Map<String, Object> getItem(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public Object create(Object input) {

		Map<String, Object> createdIncidentTypeTask= voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_CREATE_INCIDENT_TYPE_TASK, (Map<String, Object>) input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CREATE_INCIDENT_TYPE_TASK, createdIncidentTypeTask);
	}
	
	@SuppressWarnings("unchecked")
	public Object update(Object input) {

		Map<String, Object> updatedIncidentTypeTask = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_INCIDENT_TYPE_TASK, (Map<String, Object>) input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_UPDATE_INCIDENT_TYPE_TASK, updatedIncidentTypeTask);
	}
	
	public Object delete(String id) {
		
		Map<String, Object> input = new HashMap<>();

		input.put("imsIncidentTypeId", id);

		Map<String, Object> deletedIncidentTypeTask = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_DELETE_INCIDENT_TYPE_TASK, input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_DELETE_INCIDENT_TYPE_TASK, deletedIncidentTypeTask);
	}
}
