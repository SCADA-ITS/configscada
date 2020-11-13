package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ImsExtIncidentTypeBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getExtIncidentTypes() {
		
		List<Map<String, Object>> extIncidentTypes = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetExtIncidentTypes(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetExtIncidentTypes()).getFieldId(), extIncidentTypes);
	}
				
	public Map<String, Object> getExtIncidentType(String id) {
		
		Map<String, Object> extIncidentType = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetExtIncidentType(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetExtIncidentType()).getFieldId(), extIncidentType);
	}

}
