package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ImsIncidentBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> incidentReports = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetIncidents(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetIncidents()).getFieldId(), incidentReports);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> incidentReport = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetIncident(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetIncident()).getFieldId(), incidentReport);
	}
	
	
	public List<Map<String, Object>> getIncidentByType(String id) {
		
		List<Map<String, Object>> incidents = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetIncidentByType(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetIncidentByType()).getFieldId(), incidents);
	}

}
