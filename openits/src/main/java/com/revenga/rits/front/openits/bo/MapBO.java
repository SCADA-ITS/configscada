package com.revenga.rits.front.openits.bo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.model.MapUI;
import com.revenga.rits.front.openits.model.SynopticUI;
import com.revenga.rits.front.openitsdao.model.MapLayer;
import com.revenga.rits.front.openitsdao.model.MapLayerElement;
import com.revenga.rits.front.openitsdao.model.Synoptic;
import com.revenga.rits.front.openitsdao.model.SynopticLayerElement;
import com.revenga.rits.front.openitsdao.service.MapLayerElementService;
import com.revenga.rits.front.openitsdao.service.MapService;

@Service
public class MapBO extends GenericBO {
		
	@Autowired
	MapService mapService;
	
	@Autowired
	MapLayerElementService mapLayerElementService;
	
	@Autowired
	LocationBO locationBO;
	
	public List<MapUI> getMaps() {
		
		List<com.revenga.rits.front.openitsdao.model.Map> maps = mapService.getAll();
		List<MapUI> mapUIs = null; 
		
		if (!CollectionUtils.isEmpty(maps)) {
			
			for (com.revenga.rits.front.openitsdao.model.Map map : maps) {
				
				if (mapUIs == null) {
					
					mapUIs = new ArrayList<>();
				}
				
				MapUI mapUI = new MapUI();
				
				mapUI.setMap(map);
				mapUI.setData(getData(map));
				
				mapUIs.add(mapUI);
			}
		}
		
		return mapUIs;
	}
	
	public MapUI getMap(Integer id) {
		
		MapUI mapUI = null;
		com.revenga.rits.front.openitsdao.model.Map map = mapService.getById(id);
		
		if (map != null) {
			
			mapUI = new MapUI();
			
			mapUI.setMap(map);
			mapUI.setData(getData(map));
		}
		
		return mapUI;
	}
	
	public List<MapUI> getMapsByElement(String elementId) {
		
		List<MapUI> mapUIs = new ArrayList<>();
		List<MapLayerElement> mapLayerElements = mapLayerElementService.getAll();
		List<com.revenga.rits.front.openitsdao.model.Map> mapList = mapService.getAll();
		
		
		if (!CollectionUtils.isEmpty(mapLayerElements) && !CollectionUtils.isEmpty(mapList)) {
			
			Set<Integer> mapIds = new HashSet<>();
			
			List<MapLayerElement> mapLayerElementsFiltered = mapLayerElements.stream()
					.filter(layer -> StringUtils.equals(layer.getElementId(), elementId)).collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(mapLayerElementsFiltered)) {
				
				mapLayerElementsFiltered.forEach(mapLayerElement -> {
					
					if (mapLayerElement.getMapLayer() != null && mapLayerElement.getMapLayer().getMap() != null) {
						
						mapIds.add(mapLayerElement.getMapLayer().getMap().getId());
					}
				});
			}
			
			mapIds.forEach(mapId -> {
				
				com.revenga.rits.front.openitsdao.model.Map map = mapList.stream().filter(e -> e.getId().equals(mapId)).findFirst().orElse(null);
				
				if (map != null) {
					
					MapUI mapUI = new MapUI();
					
					mapUI.setMap(map);
					
					mapUIs.add(mapUI);
				}
			});
		}
		
		return mapUIs;
	}
	
	private List<Map<String, Object>> getData(com.revenga.rits.front.openitsdao.model.Map map) {

		Set<String> elementsWithStates = new HashSet<>();
		Set<String> elementsWithStatesMeasures = new HashSet<>();
		Set<String> elementsWithStatesAlarms = new HashSet<>();
		Set<String> elementsWithStatesMeasuresAlarms = new HashSet<>();
		List<Map<String, Object>> elements = new ArrayList<>();

		getElementIds(map, elementsWithStates, elementsWithStatesMeasures, elementsWithStatesAlarms, elementsWithStatesMeasuresAlarms);
		
		if (!CollectionUtils.isEmpty(elementsWithStates)) {
			
			elements.addAll(getElementsStates(List.copyOf(elementsWithStates)));
		}
		
		if (!CollectionUtils.isEmpty(elementsWithStatesMeasures)) {
			
			elements.addAll(getElementsStatesMeasures(List.copyOf(elementsWithStatesMeasures)));
		}

		if (!CollectionUtils.isEmpty(elementsWithStatesAlarms)) {
			
			elements.addAll(getElementsStatesAlarms(List.copyOf(elementsWithStatesAlarms)));
		}

		if (!CollectionUtils.isEmpty(elementsWithStatesMeasuresAlarms)) {
			
			elements.addAll(getElementsStatesMeasuresAlarms(List.copyOf(elementsWithStatesMeasuresAlarms)));
		}
		
		return elements;
		
	}

	private List<Map<String, Object>> getElementsStates(List<String> ids) {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MAP, new Object[] { ids });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MAP, elements);
	}
	
	private List<Map<String, Object>> getElementsStatesMeasures(List<String> ids) {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MEASURES_MAP, new Object[] { ids });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MEASURES_MAP, elements);
	}

	private List<Map<String, Object>> getElementsStatesAlarms(List<String> ids) {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_ALARMS_MAP, new Object[] { ids });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_ALARMS_MAP, elements);
	}

	private List<Map<String, Object>> getElementsStatesMeasuresAlarms(List<String> ids) {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MEASURES_ALARMS_MAP, new Object[] { ids });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MEASURES_ALARMS_MAP, elements);
	}
	
	private void getElementIds(com.revenga.rits.front.openitsdao.model.Map map, Set<String> elementsWithStates, Set<String> elementsWithStatesMeasures, Set<String> elementsWithStatesAlarms, Set<String> elementsWithStatesMeasuresAlarms) {
		
		if (map != null && !CollectionUtils.isEmpty(map.getMapLayers())) {
				
			for (MapLayer mapLayer : map.getMapLayers()) {
				
				if (!CollectionUtils.isEmpty(mapLayer.getMapLayerElements())) {
					
					for (MapLayerElement mapLayerElement : mapLayer.getMapLayerElements()) {
						
						if (mapLayerElement.getGraphicIcon() !=null && mapLayerElement.getGraphicIcon().getMeasures() && mapLayerElement.getGraphicIcon().getAlarms()) {
						
							elementsWithStatesMeasuresAlarms.add(mapLayerElement.getElementId());
						}
						else if(mapLayerElement.getGraphicIcon() !=null && mapLayerElement.getGraphicIcon().getMeasures()){
							
							elementsWithStatesMeasures.add(mapLayerElement.getElementId());
						}
						else if(mapLayerElement.getGraphicIcon() !=null && mapLayerElement.getGraphicIcon().getAlarms()){
							
							elementsWithStatesAlarms.add(mapLayerElement.getElementId());
						}
						else{
							
							elementsWithStates.add(mapLayerElement.getElementId());
						}
					}
				}
			}
		}
	}

	@Override
	public List<Map<String, Object>> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getItem(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
