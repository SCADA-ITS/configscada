package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ImsIncidentTypeBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getIncidentTypes() {
		
		List<Map<String, Object>> incidents = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetIncidentTypes(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetIncidentTypes()).getFieldId(), incidents);
	}
				
	public Map<String, Object> getIncidentType(String id) {
		
		Map<String, Object> incident = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetIncidentType(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetIncidentType()).getFieldId(), incident);
	}

}
