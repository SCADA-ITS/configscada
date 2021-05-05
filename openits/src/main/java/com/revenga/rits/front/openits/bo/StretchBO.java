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
public class StretchBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;

	@Autowired
	ImsIncidentTypeStretchBO imsIncidentTypeStretchBO;

	private final String FIELD_STRETCH = "stretch";
	private final String FIELD_STRETCH_ID = "id";
	private final String FIELD_INCIDENT_TYPE = "incidentType";
	private final String FIELD_INCIDENT_TYPE_ID = "id";
	
	@Override
	public List<Map<String, Object>> getList() {
	
		List<Map<String, Object>> stretchs = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_STRETCHS, null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_STRETCHS).getFieldId(), stretchs);
	}

	public List<Map<String, Object>> getListByIncidentType(String id) {

		List<Map<String, Object>> scretchsFiltered = null;
		List<Map<String, Object>> stretchs = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_STRETCHS, null);	
						
		if (!CollectionUtils.isEmpty(stretchs) && id != null) {

			List<Map<String, Object>> listIncidentTypeStretchs = imsIncidentTypeStretchBO.getList();
		
			if (!CollectionUtils.isEmpty(listIncidentTypeStretchs)) {
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> listIncidentTypeStretchsFiltered = listIncidentTypeStretchs.stream()
						.filter(e -> {
							
							Object incidentType = e.get(FIELD_INCIDENT_TYPE);
							
							if (incidentType instanceof Map) {
								
								return ((Map<String, Object>) incidentType).get(FIELD_INCIDENT_TYPE_ID).equals(id);
							}
							
							return false;
							
						}).collect(Collectors.toList());
						
				
				if (!CollectionUtils.isEmpty(listIncidentTypeStretchsFiltered)) {
				
					scretchsFiltered = stretchs.stream().filter(scretch -> {
						
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
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_STRETCHS).getFieldId(), scretchsFiltered);
	}
	

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> stretch = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_STRETCH, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_STRETCH).getFieldId(), stretch);
	}
	
	public List<Map<String, Object>> getStretchByRoad(String id) {
		
		List<Map<String, Object>> stretchs = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_STRETCH_BY_ROAD, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_STRETCH_BY_ROAD).getFieldId(), stretchs);
	}

}
