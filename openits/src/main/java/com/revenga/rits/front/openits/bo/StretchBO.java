package com.revenga.rits.front.openits.bo;

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
public class StretchBO extends GenericBO {
	
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
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_STRETCHS;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_STRETCH;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getListByIncidentType(String incidentTypeId) {

		List<Map<String, Object>> stretchsFiltered = null;
		List<Map<String, Object>> stretchs = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_STRETCHS, null);
		
		if (!CollectionUtils.isEmpty(stretchs) && incidentTypeId != null) {
			
			List<Map<String, Object>> listIncidentTypeStretchs = imsIncidentTypeStretchBO.getListByIncidenType(incidentTypeId);
			
			if (!CollectionUtils.isEmpty(listIncidentTypeStretchs)) {
				
				List<Map<String, Object>> listIncidentTypeStretchsFiltered = listIncidentTypeStretchs.stream()
						.filter(e -> {
							
							Object incidentType = e.get(FIELD_INCIDENT_TYPE);
							
							if (incidentType instanceof Map) {
								
								return ((Map<String, Object>) incidentType).get(FIELD_INCIDENT_TYPE_ID).equals(incidentTypeId);
							}
							
							return false;
							
						}).collect(Collectors.toList());
						
				
				if (!CollectionUtils.isEmpty(listIncidentTypeStretchsFiltered)) {
				
					stretchsFiltered = stretchs.stream().filter(scretch -> {
						
						return (listIncidentTypeStretchsFiltered.stream()
								.filter(e -> {
									
									Object stretchFilterd = e.get(FIELD_STRETCH);
									
									if (stretchFilterd instanceof Map) {
										
										return ((Map<String, Object>) stretchFilterd).get(FIELD_STRETCH_ID)
												.equals(scretch.get(FIELD_STRETCH_ID));
									}
								
									return false;
									
								}).findFirst().orElse(null)) != null;
						
					}).collect(Collectors.toList());
				}
			}
		}
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_STRETCHS, stretchsFiltered);
	}
	
	public List<Map<String, Object>> getListByLocation(String locationId) {
		
		List<Map<String, Object>> stretchs = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_STRETCH_BY_LOCATION, new Object[] { locationId });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_STRETCH_BY_LOCATION, stretchs);
	}
		
	public List<Map<String, Object>> getStretchByRoad(String id) {
		
		List<Map<String, Object>> stretchs = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_STRETCH_BY_ROAD, new Object[] { id });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_STRETCH_BY_ROAD, stretchs);
	}
}
