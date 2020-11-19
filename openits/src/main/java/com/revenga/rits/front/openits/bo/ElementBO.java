package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ElementBO extends GenericBO {
			
	@Autowired
	LocationBO locationBO;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(voDataSourceAdapter.getDataSourceGetElements(), null);
		
		return locationBO.replaceLocations(vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetElements()).getFieldId(), elements));
	}

	@Override
	public Map<String, Object> getItem(String id) {
		
		Map<String, Object> element = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetElement(), new Object[] { id });
		
		return locationBO.replaceLocation(vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetElement()).getFieldId(), element));
	}
	
	public List<Map<String, Object>> getElements(List<String> ids) {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(voDataSourceAdapter.getDataSourceGetElementsArray(), new Object[] { ids });
		
		return locationBO.replaceLocations(vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetElementsArray()).getFieldId(), elements));
	}
	
	public Map<String, Object> createElement(Object input) {
		
		Map<String, Object> createdElement = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceCreateElement(), (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceCreateElement()).getFieldId(), createdElement);
	}
	
	public Map<String, Object> updateElement(Object input) {
			
		Map<String, Object> updatedElement = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceUpdateElement(), (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceUpdateElement()).getFieldId(), updatedElement);
	}
	
	public Map<String, Object> cloneElement(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("elementId", id);
		
		Map<String, Object> clonedElement = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceCloneElement(), input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceCloneElement()).getFieldId(), clonedElement);
	}
	
	public Map<String, Object> deleteElement(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("elementId", id);
		
		Map<String, Object> deletedElement = voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceDeleteElement(), input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceDeleteElement()).getFieldId(), deletedElement);
	}
	
}
