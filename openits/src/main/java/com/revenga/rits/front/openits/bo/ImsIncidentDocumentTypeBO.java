package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ImsIncidentDocumentTypeBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getIncidentDocumentTypes() {
		
		List<Map<String, Object>> incidentDocumentTypes = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetIncidentDocumentTypes(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetIncidentDocumentTypes()).getFieldId(), incidentDocumentTypes);
	}
				
	public Map<String, Object> getIncidentDocumentType(String id) {
		
		Map<String, Object> incidentDocumentType = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetIncidentDocumentType(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetIncidentDocumentType()).getFieldId(), incidentDocumentType);
	}

}
