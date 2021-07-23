package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ImsIncidentTypeBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Autowired
	ImsIncidentTypeStretchBO imsIncidentTypeStretchBO;
	
	private static final String FIELD_STRETCH = "stretch";
	private static final String FIELD_STRETCH_ID = "id";
	private static final String FIELD_INCIDENT_TYPE = "incidentType";
	private static final String FIELD_INCIDENT_TYPE_ID = "id";

	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> incidents = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPES, null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPES).getFieldId(), incidents);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getListByStretch(String id) {

		List<Map<String, Object>> incidentsFiltered = null;
		List<Map<String, Object>> incidents = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPES, null);
		
		if (!CollectionUtils.isEmpty(incidents)) {
			
			List<Map<String, Object>> listIncidentTypeStretchs = imsIncidentTypeStretchBO.getList();
			
			if (!CollectionUtils.isEmpty(listIncidentTypeStretchs)) {
				
				List<Map<String, Object>> listIncidentTypeStretchsFiltered = listIncidentTypeStretchs.stream()
						.filter(e -> {
							
							Object stretch = e.get(FIELD_STRETCH);
							
							if (stretch instanceof Map) {
								
								return ((Map<String, Object>) stretch).get(FIELD_STRETCH_ID).equals(id);
							}
							
							return false;
							
						}).collect(Collectors.toList());
						
				
				if (!CollectionUtils.isEmpty(listIncidentTypeStretchsFiltered)) {
				
					incidentsFiltered = incidents.stream().filter(incident -> {
						
						return (listIncidentTypeStretchsFiltered.stream()
								.filter(e -> {
									
									Object incidentType = e.get(FIELD_INCIDENT_TYPE);
									
									if (incidentType instanceof Map) {
										
										return ((Map<String, Object>) incidentType).get(FIELD_INCIDENT_TYPE_ID)
												.equals(incident.get(FIELD_INCIDENT_TYPE_ID));
									}
								
									return false;
									
								}).findFirst().orElse(null)) != null;
						
					}).collect(Collectors.toList());
				}
			}
		}
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPES).getFieldId(), incidentsFiltered);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> incident = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPE, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPE).getFieldId(), incident);
	}

}
