package com.revenga.rits.front.openits.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.model.LocationCoodinateUI;
import com.revenga.rits.front.openitsdao.model.VoEntity;
import com.revenga.rits.front.openitsdao.model.VoEntityHierarchy;
import com.revenga.rits.front.openitsdao.model.VoEntityValue;
import com.revenga.rits.front.openitsdao.service.VoEntityHierarchyService;
import com.revenga.rits.front.openitsdao.service.VoEntityService;

@Service
public class LocationBO extends GenericBO {

	private static final String FIELD_LOCATION = "location";
	private static final String FIELD_LOCATION_ID = "id";
	private static final String PARAM_ID = "id";
	private static final String PARAM_LATTITUDE = "lat";
	private static final String PARAM_LONGITUDE = "lon";
	private static final String PARAM_COORDINATES = "coordinates";
	private static final Integer PARAM_LATTITUDE_ID = 5;
	private static final Integer PARAM_LONGITUDE_ID = 6;

	@Autowired
	VoEntityService voEntityService;
	
	@Autowired
	VoEntityHierarchyService voEntityHierarchyService;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> locations = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_LOCATIONS, null);
		
		return assignCoordinates(vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_LOCATIONS).getFieldId(), locations));
	}

	@Override
	public Map<String, Object> getItem(String id) {
		
		Map<String, Object> location = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_LOCATION, new Object[] { id });
		
		return assignCoordinates(vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_LOCATION).getFieldId(), location));
	}
	
	public List<Map<String, Object>> getLocationByStretch(String alarmTypeId) {
		
		List<Map<String, Object>> locations = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_LOCATION_BY_STRETCH, new Object[] { alarmTypeId });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_LOCATION_BY_STRETCH).getFieldId(), locations);
	}
	
	public List<Map<String, Object>> replaceLocations(List<Map<String, Object>> elements) {
		
		List<Map<String, Object>> elementsRes = new ArrayList<>();
		
		if (!CollectionUtils.isEmpty(elements)) {
			
			for (Map<String, Object> element : elements) {
				
				elementsRes.add(replaceLocation(element));
			}
		}
		
		return elementsRes;
	}
	
	public Map<String, Object> replaceLocation(Map<String, Object> element) {
		
		// TODO: replaceLocation
		if (element.containsKey(FIELD_LOCATION)) {
			
			if (element.get(FIELD_LOCATION) instanceof Map) {
				
				Map<String, Object> locationElement = (Map<String, Object>) element.get(FIELD_LOCATION);
				
				if (locationElement != null) {
				
					Map<String, Object> location = getItem(locationElement.get(FIELD_LOCATION_ID).toString());
					
					if (location != null) {
						
						element.put(FIELD_LOCATION, location);
					}
				}
			}
		}
		
		return element;
	}
	
	private List<Map<String, Object>> assignCoordinates(List<Map<String, Object>> locations) {
		
		List<Map<String, Object>> locationsRes = new ArrayList<>();
		
		if (!CollectionUtils.isEmpty(locations)) {
			
			for (Map<String, Object> location : locations) {
				
				locationsRes.add(assignCoordinates(location));
			}
		}
		
		return locationsRes;
	}
	
	private Map<String, Object> assignCoordinates(Map<String, Object> location) {
		
		return assignCoordinates(location, null);
	}
	
	private Map<String, Object> assignCoordinates(Map<String, Object> location, List<LocationCoodinateUI> locationCoordinates) {
		
		if (locationCoordinates == null) {
		
			locationCoordinates = new ArrayList<>();
		}
		
		if (!location.containsKey(PARAM_LATTITUDE) && !location.containsKey(PARAM_LONGITUDE)) {
			
			//List<VoEntityHierarchy> voEntityHierarchies = voEntityHierarchyService.getByParentId(location.get(PARAM_ID).toString());
			
			VoEntity voEntityParent = voEntityService.getById(location.get(PARAM_ID).toString());
			
			List<VoEntityHierarchy> voEntityHierarchies = voEntityHierarchyService
					.getByVoEntityParent(voEntityParent);
			
			Collections.sort(voEntityHierarchies, new Comparator<VoEntityHierarchy>() {
				@Override
				public int compare(VoEntityHierarchy o1, VoEntityHierarchy o2) {
					return o1.getPosition().compareTo(o2.getPosition());
				}
			});
			
			if (!CollectionUtils.isEmpty(voEntityHierarchies)) {
			
				for (VoEntityHierarchy voEntityHierarchy : voEntityHierarchies) {
					
					List<VoEntityValue> voEntityValues = voEntityHierarchy.getVoEntityChild().getVoEntityValues();
					
					if (!CollectionUtils.isEmpty(voEntityValues)) {
						
						VoEntityValue latEntityValue = voEntityValues.stream()
								.filter(e -> e.getVoEntityField().getId().equals(PARAM_LATTITUDE_ID)).findFirst().orElse(null);
						VoEntityValue lonEntityValue = voEntityValues.stream()
								.filter(e -> e.getVoEntityField().getId().equals(PARAM_LONGITUDE_ID)).findFirst().orElse(null);
						
						if (latEntityValue != null && lonEntityValue != null) {
						
							locationCoordinates.add(new LocationCoodinateUI(
									Double.parseDouble(latEntityValue.getValue()),
									Double.parseDouble(lonEntityValue.getValue()), voEntityHierarchy.getPosition()));
						}
						else {
							
							Map<String, Object> newLocation = new HashMap<>();
							newLocation.put(PARAM_ID, locationCoordinates);
							
							assignCoordinates(newLocation, locationCoordinates);
						}
					}
				}
			}
		}
		else {
			
			locationCoordinates
					.add(new LocationCoodinateUI(Double.parseDouble(location.get(PARAM_LATTITUDE).toString()),
							Double.parseDouble(location.get(PARAM_LONGITUDE).toString())));
		}
		
		location.put(PARAM_COORDINATES, locationCoordinates);
		location.remove(PARAM_LATTITUDE);
		location.remove(PARAM_LONGITUDE);
		
		return location;
	}

}
