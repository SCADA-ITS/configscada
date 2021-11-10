package com.revenga.rits.front.openits.bo;

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

}
