package com.revenga.rits.front.openits.bo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.model.SynopticUI;
import com.revenga.rits.front.openitsdao.model.Synoptic;
import com.revenga.rits.front.openitsdao.model.SynopticLayer;
import com.revenga.rits.front.openitsdao.model.SynopticLayerElement;
import com.revenga.rits.front.openitsdao.service.SynopticService;

@Service
public class SynopticBO {
	
	@Autowired
	SynopticService synopticService;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
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
	
	private List<Map<String, Object>> getData(Synoptic synoptic) {
		 
		Set<String> elementsWithStates = new HashSet<>();
		Set<String> elementsWithStatesMeasuresAlarms = new HashSet<>();
		List<Map<String, Object>> elements = new ArrayList<>();

		getElementIds(synoptic, elementsWithStates, elementsWithStatesMeasuresAlarms);
		
		if (!CollectionUtils.isEmpty(elementsWithStates)) {
			
			elements.addAll(getElementsStates(List.copyOf(elementsWithStates)));
		}
		
		if (!CollectionUtils.isEmpty(elementsWithStatesMeasuresAlarms)) {
			
			elements.addAll(getElementsStatesMeasuresAlarms(List.copyOf(elementsWithStatesMeasuresAlarms)));
		}
		
		return elements;
	}
	
	private List<Map<String, Object>> getElementsStates(List<String> ids) {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(voDataSourceAdapter.getDataSourceGetElementsArrayStatesSynoptic(), new Object[] { ids });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetElementsArrayStatesSynoptic()).getFieldId(), elements);
	}
	
	private List<Map<String, Object>> getElementsStatesMeasuresAlarms(List<String> ids) {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(voDataSourceAdapter.getDataSourceGetElementsArrayStatesMeasuresAlarmsSynoptic(), new Object[] { ids });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetElementsArrayStatesMeasuresAlarmsSynoptic()).getFieldId(), elements);
	}
	
	private void getElementIds(Synoptic synoptic, Set<String> elementsWithStates, Set<String> elementsWithStatesMeasuresAlarms) {
		
		if (synoptic != null && !CollectionUtils.isEmpty(synoptic.getSynopticLayers())) {
				
			for (SynopticLayer synopticLayer : synoptic.getSynopticLayers()) {
				
				if (!CollectionUtils.isEmpty(synopticLayer.getSynopticLayerElements())) {
					
					for (SynopticLayerElement synopticLayerElement : synopticLayer.getSynopticLayerElements()) {
						
						if (synopticLayerElement.getCustomGraphicEquipment() != null) {
						
							elementsWithStatesMeasuresAlarms.add(synopticLayerElement.getElementId());
						}
						else {
							
							elementsWithStates.add(synopticLayerElement.getElementId());
						}
					}
				}
			}
		}
	}
}
