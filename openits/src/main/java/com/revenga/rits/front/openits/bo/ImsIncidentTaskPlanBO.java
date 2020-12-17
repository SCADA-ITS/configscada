package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ImsIncidentTaskPlanBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;

	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> incidentTaskPlans = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetIncidentTaskPlans(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetIncidentTaskPlans()).getFieldId(), incidentTaskPlans);
	}
	
	public List<Map<String, Object>> getTaskPlansByStretchAndIncidentTypeAndIncidentTypeTask(String stretchId, String incidentTypeId, String incidentTypeTaskId) {
		
		List<Map<String, Object>> incidentTaskPlans = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetTaskPlansByStretchAndIncidentTypeAndIncidentTypeTask(), new Object[] { stretchId, incidentTypeId, incidentTypeTaskId });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetTaskPlansByStretchAndIncidentTypeAndIncidentTypeTask()).getFieldId(), incidentTaskPlans);
	}

	@Override
	public Map<String, Object> getItem(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
