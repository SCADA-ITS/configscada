package com.revenga.rits.front.openits.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.model.LocationCoodinateUI;
import com.revenga.rits.front.openitsdao.model.VoEntity;
import com.revenga.rits.front.openitsdao.model.VoEntityHierarchy;
import com.revenga.rits.front.openitsdao.model.VoEntityType;
import com.revenga.rits.front.openitsdao.model.VoEntityValue;
import com.revenga.rits.front.openitsdao.service.VoEntityHierarchyService;
import com.revenga.rits.front.openitsdao.service.VoEntityService;
import com.revenga.rits.front.openitsdao.service.VoEntityTypeService;

@Service
public class LocationBO extends GenericBO {

	private static final String ENTITY_TYPE_LOCATION = "LocationUI";
	private static final String PARAM_ID = "id";
	private static final String PARAM_LATTITUDE = "lat";
	private static final String PARAM_LONGITUDE = "lon";
	private static final String PARAM_COORDINATES = "coordinates";
	private static final Integer PARAM_LATTITUDE_ID = 5;
	private static final Integer PARAM_LONGITUDE_ID = 6;

	@Autowired
	VoEntityService voEntityService;
	
	@Autowired
	VoEntityTypeService voEntityTypeService;
	
	@Autowired
	VoEntityHierarchyService voEntityHierarchyService;
	
	private List<VoEntity> voEntityLocations;
	private List<VoEntityHierarchy> voEntityHierarchies;
	
	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> locations = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_LOCATIONS, null);
		
		return assignCoordinates(vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_LOCATIONS, locations));
	}
	
	public List<Map<String, Object>> getListSummary() {
		
		List<Map<String, Object>> locations = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_LOCATIONS_SUMMARY, null);
		
		return assignCoordinates(vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_LOCATIONS_SUMMARY, locations));
	}

	@Override
	public Map<String, Object> getItem(String id) {
		
		Map<String, Object> location = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_LOCATION, new Object[] { id });
		
		return assignCoordinates(vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_LOCATION, location));
	}
	
	public List<Map<String, Object>> getLocationByStretch(String stretchId) {
		
		List<Map<String, Object>> locations = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_LOCATION_BY_STRETCH, new Object[] { stretchId });
		
		return assignCoordinates(vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_LOCATION_BY_STRETCH, locations));
	}
	
	private List<Map<String, Object>> assignCoordinates(List<Map<String, Object>> locations) {
		
		if (voEntityLocations == null) {
			
			VoEntityType voEntityTypeLocation = voEntityTypeService.getById(ENTITY_TYPE_LOCATION);
			
			if (voEntityTypeLocation != null) {
				
				voEntityLocations = voEntityService.getByVoEntityType(voEntityTypeLocation);
			}
		}
		
		if (voEntityHierarchies == null) {
			
			voEntityHierarchies = voEntityHierarchyService.getAll();
		}
		
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
			
			VoEntity voEntityParent = voEntityLocations.stream()
					.filter(e -> e.getId().equals(location.get(PARAM_ID).toString())).findFirst().orElse(null);
			
			if (voEntityParent != null) {
				
				List<VoEntityHierarchy> voEntityHierarchiesByParent = voEntityHierarchies.stream()
						.filter(e -> e.getVoEntityParent().getId().equals(voEntityParent.getId())).collect(Collectors.toList());
				
				if (!CollectionUtils.isEmpty(voEntityHierarchiesByParent)) {
					
					Collections.sort(voEntityHierarchiesByParent, new Comparator<VoEntityHierarchy>() {
						@Override
						public int compare(VoEntityHierarchy o1, VoEntityHierarchy o2) {
							return o1.getPosition().compareTo(o2.getPosition());
						}
					});
				
					for (VoEntityHierarchy voEntityHierarchy : voEntityHierarchiesByParent) {
						
						List<VoEntityValue> voEntityValues =  List.copyOf(voEntityHierarchy.getVoEntityChild().getVoEntityValues());
						
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
