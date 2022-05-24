package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ImsIncidentTypeStateTransitionBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@PostConstruct
	private void initialize() {
		
	}

	@SuppressWarnings("unchecked")
	public Object create(Object input) {

		Map<String, Object> createdIncidentTypeStateTransition = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_CREATE_INCIDENT_TYPE_STATE_TRANSITION, (Map<String, Object>) input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CREATE_INCIDENT_TYPE_STATE_TRANSITION, createdIncidentTypeStateTransition);
	}
	
	@SuppressWarnings("unchecked")
	public Object update(Object input) {

		Map<String, Object> updatedIncidentTypeStateTransition = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_INCIDENT_TYPE_STATE_TRANSITION, (Map<String, Object>) input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_UPDATE_INCIDENT_TYPE_STATE_TRANSITION, updatedIncidentTypeStateTransition);
	}
	
	public Object delete(String id) {
		
		Map<String, Object> input = new HashMap<>();

		input.put("imsIncidentTypeStateTransitionId", id);

		Map<String, Object> deletedIncidentTypeStateTransition = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_DELETE_INCIDENT_TYPE_STATE_TRANSITION, input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_DELETE_INCIDENT_TYPE_STATE_TRANSITION, deletedIncidentTypeStateTransition);
	}
}
