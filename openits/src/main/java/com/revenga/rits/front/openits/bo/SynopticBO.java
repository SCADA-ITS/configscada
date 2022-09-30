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
import com.revenga.rits.front.openits.model.SynopticUI;
import com.revenga.rits.front.openitsdao.model.Synoptic;
import com.revenga.rits.front.openitsdao.model.SynopticLayer;
import com.revenga.rits.front.openitsdao.model.SynopticLayerElement;
import com.revenga.rits.front.openitsdao.service.SynopticLayerElementService;
import com.revenga.rits.front.openitsdao.service.SynopticService;

@Service
public class SynopticBO extends GenericBO {
	
	@Autowired
	SynopticService synopticService;
	
	@Autowired
	SynopticLayerElementService synopticLayerElementService;
	
	public List<SynopticUI> getSynoptics() {
		
		List<Synoptic> synoptics = synopticService.getAll();
		List<SynopticUI> synopticUIs = null;
		
		if (!CollectionUtils.isEmpty(synoptics)) {
			
			for (Synoptic synoptic : synoptics) {
				
				if (synopticUIs == null) {
					
					synopticUIs = new ArrayList<>();
				}
				
				SynopticUI synopticUI = new SynopticUI();
				
				synopticUI.setSynoptic(synoptic);
				synopticUI.setData(getData(synoptic));
				
				synopticUIs.add(synopticUI);
			}
		}
		
		return synopticUIs;
	}
	
	public SynopticUI getSynoptic(Integer id) {
		
		SynopticUI synopticUI = null;
		Synoptic synoptic = synopticService.getById(id);
		
		if (synoptic != null) {
			
			synopticUI = new SynopticUI();
			
			synopticUI.setSynoptic(synoptic);
			synopticUI.setData(getData(synoptic));
		}
		
		return synopticUI;
	}
	
	public List<SynopticUI> getSynopticsByElement(String elementId) {
		
		List<SynopticUI> synopticUIs = new ArrayList<>();
		List<SynopticLayerElement> synopticLayerElements = synopticLayerElementService.getAll();
		List<Synoptic> synopticList = synopticService.getAll();
		
		
		if (!CollectionUtils.isEmpty(synopticLayerElements) && !CollectionUtils.isEmpty(synopticList)) {
			
			Set<Integer> synopticIds = new HashSet<>();
			
			List<SynopticLayerElement> synopticLayerElementsFiltered = synopticLayerElements.stream()
					.filter(layer -> StringUtils.equals(layer.getElementId(), elementId)).collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(synopticLayerElementsFiltered)) {
				
				synopticLayerElementsFiltered.forEach(synopticLayerElement -> {
					
					if (synopticLayerElement.getSynopticLayer() != null && synopticLayerElement.getSynopticLayer().getSynoptic() != null) {
						
						synopticIds.add(synopticLayerElement.getSynopticLayer().getSynoptic().getId());
					}
				});
			}
			
			synopticIds.forEach(synopticId -> {
				
				Synoptic synoptic = synopticList.stream().filter(e -> e.getId().equals(synopticId)).findFirst().orElse(null);
				
				if (synoptic != null) {
					
					SynopticUI synopticUI = new SynopticUI();
					
					synopticUI.setSynoptic(synoptic);
					
					synopticUIs.add(synopticUI);
				}
			});
		}
		
		return synopticUIs;
	}
	
	private List<Map<String, Object>> getData(Synoptic synoptic) {
		 
		Set<String> elementsWithStates = new HashSet<>();
		Set<String> elementsWithStatesMeasures = new HashSet<>();
		Set<String> elementsWithStatesAlarms = new HashSet<>();
		Set<String> elementsWithStatesMeasuresAlarms = new HashSet<>();
		List<Map<String, Object>> elements = new ArrayList<>();

		getElementIds(synoptic, elementsWithStates, elementsWithStatesMeasures, elementsWithStatesAlarms, elementsWithStatesMeasuresAlarms);
		
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
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_SYNOPTIC, new Object[] { ids });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_SYNOPTIC, elements);
	}
	
	private List<Map<String, Object>> getElementsStatesMeasures(List<String> ids) {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MEASURES_SYNOPTIC, new Object[] { ids });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MEASURES_SYNOPTIC, elements);
	}

	private List<Map<String, Object>> getElementsStatesAlarms(List<String> ids) {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_ALARMS_SYNOPTIC, new Object[] { ids });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_ALARMS_SYNOPTIC, elements);
	}

	private List<Map<String, Object>> getElementsStatesMeasuresAlarms(List<String> ids) {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MEASURES_ALARMS_SYNOPTIC, new Object[] { ids });
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MEASURES_ALARMS_SYNOPTIC, elements);
	}
	
	private void getElementIds(Synoptic synoptic, Set<String> elementsWithStates, Set<String> elementsWithStatesMeasures, Set<String> elementsWithStatesAlarms, Set<String> elementsWithStatesMeasuresAlarms) {
		
		if (synoptic != null && !CollectionUtils.isEmpty(synoptic.getSynopticLayers())) {
				
			for (SynopticLayer synopticLayer : synoptic.getSynopticLayers()) {
				
				if (!CollectionUtils.isEmpty(synopticLayer.getSynopticLayerElements())) {
					
					for (SynopticLayerElement synopticLayerElement : synopticLayer.getSynopticLayerElements()) {
						
						if (synopticLayerElement.getGraphicIcon() !=null && synopticLayerElement.getGraphicIcon().getMeasures() && synopticLayerElement.getGraphicIcon().getAlarms()) {
						
							elementsWithStatesMeasuresAlarms.add(synopticLayerElement.getElementId());
						}
						else if(synopticLayerElement.getGraphicIcon() !=null && synopticLayerElement.getGraphicIcon().getMeasures()){
							
							elementsWithStatesMeasures.add(synopticLayerElement.getElementId());
						}
						else if(synopticLayerElement.getGraphicIcon() !=null && synopticLayerElement.getGraphicIcon().getAlarms()){
							
							elementsWithStatesAlarms.add(synopticLayerElement.getElementId());
						}
						else{
							
							elementsWithStates.add(synopticLayerElement.getElementId());
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
