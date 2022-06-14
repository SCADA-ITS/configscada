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
	
	public List<Map<String, Object>> getListByIncidenType(String incidentTypeId) {
		
		List<Map<String, Object>> incidentTypeStretchs = voDataSourceAdapter.getList(
				VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPE_STRETCHS_BY_INCIDENT_TYPE,
				new Object[] { incidentTypeId });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPE_STRETCHS_BY_INCIDENT_TYPE,
				incidentTypeStretchs);
	}
	
	public Map<String, Object> getItem(String incidentTypeId, String stretchId) {
		
		Map<String, Object> incidentTypeStretch = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPE_STRETCH, new Object[] { stretchId, incidentTypeId });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPE_STRETCH, incidentTypeStretch);
	}
	
	@SuppressWarnings("unchecked")
	public Object create(Object input) {

		Map<String, Object> createdIncidentTypeStretch = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_CREATE_INCIDENT_TYPE_STRETCH, (Map<String, Object>) input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CREATE_INCIDENT_TYPE_STRETCH, createdIncidentTypeStretch);
	}
	
	@SuppressWarnings("unchecked")
	public Object update(Object input) {

		Map<String, Object> updatedIncidentTypeStretch = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_INCIDENT_TYPE_STRETCH, (Map<String, Object>) input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_UPDATE_INCIDENT_TYPE_STRETCH, updatedIncidentTypeStretch);
	}
	
	public Object delete(String id) {
		
		Map<String, Object> input = new HashMap<>();

		input.put("imsIncidentTypeStretchId", id);

		Map<String, Object> deletedIncidentTypeStretch = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_DELETE_INCIDENT_TYPE_STRETCH, input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_DELETE_INCIDENT_TYPE_STRETCH, deletedIncidentTypeStretch);
	}
}
