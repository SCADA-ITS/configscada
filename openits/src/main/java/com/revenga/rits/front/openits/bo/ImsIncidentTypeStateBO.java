package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ImsIncidentTypeStateBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getIncidentTypeStates() {
		
		List<Map<String, Object>> incidentTypeStates = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetIncidentTypeStates(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetIncidentTypeStates()).getFieldId(), incidentTypeStates);
	}
				
	public List<Map<String, Object>> getNextState(String id) {
		
		List<Map<String, Object>> incidentNextState = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetNextState(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetNextState()).getFieldId(), incidentNextState);
	}

}
