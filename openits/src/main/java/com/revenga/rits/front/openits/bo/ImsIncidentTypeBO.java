package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ImsIncidentTypeBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired
	ImsIncidentTypeStretchBO imsIncidentTypeStretchBO;
	
	private static final String FIELD_STRETCH = "stretch";
	private static final String FIELD_STRETCH_ID = "id";
	private static final String FIELD_INCIDENT_TYPE = "incidentType";
	private static final String FIELD_INCIDENT_TYPE_ID = "id";
	
	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPES;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPE;
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
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPES, incidentsFiltered);
	}
	
	@SuppressWarnings("unchecked")
	public Object create(Object input) {

		Map<String, Object> createdIncidentType = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_CREATE_INCIDENT_TYPE, (Map<String, Object>) input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CREATE_INCIDENT_TYPE, createdIncidentType);
	}
	
	@SuppressWarnings("unchecked")
	public Object update(Object input) {

		Map<String, Object> updatedIncidentType = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_INCIDENT_TYPE, (Map<String, Object>) input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_UPDATE_INCIDENT_TYPE, updatedIncidentType);
	}
	
	public Object delete(String id) {
		
		Map<String, Object> input = new HashMap<>();

		input.put("imsIncidentTypeId", id);

		Map<String, Object> deletedIncidentType = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_DELETE_INCIDENT_TYPE, input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_DELETE_INCIDENT_TYPE, deletedIncidentType);
	}
}
