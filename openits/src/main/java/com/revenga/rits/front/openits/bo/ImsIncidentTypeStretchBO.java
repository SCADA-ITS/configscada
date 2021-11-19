package com.revenga.rits.front.openits.bo;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ImsIncidentTypeStretchBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPE_STRETCHS;
	}

	@Override
	public Map<String, Object> getItem(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Map<String, Object> getItem(String incidentTypeId, String stretchId) {
		
		Map<String, Object> incidentTypeStretch = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPE_STRETCH, new Object[] { stretchId, incidentTypeId });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPE_STRETCH, incidentTypeStretch);
	}
}
