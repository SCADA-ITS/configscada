package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ImsExtIncidentBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;

	public List<Map<String, Object>> getExtIncidents() {
		
		List<Map<String, Object>> extIncidents = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetExtIncidents(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetExtIncidents()).getFieldId(), extIncidents);
	}
				
	public Map<String, Object> getExtIncident(String id) {
		
		Map<String, Object> extIncident = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetExtIncident(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetExtIncident()).getFieldId(), extIncident);
	}
}
