package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ImsIncidentSourceBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getIncidentsSource() {
		
		List<Map<String, Object>> incidentsSource = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetIncidentsSource(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetIncidentsSource()).getFieldId(), incidentsSource);
	}
				
	public Map<String, Object> getIncidentSource(String id) {
		
		Map<String, Object> incidentSource = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetIncidentSource(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetIncidentSource()).getFieldId(), incidentSource);
	}

}
