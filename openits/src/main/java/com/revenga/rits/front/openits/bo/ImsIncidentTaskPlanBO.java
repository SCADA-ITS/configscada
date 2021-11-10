package com.revenga.rits.front.openits.bo;

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

}
