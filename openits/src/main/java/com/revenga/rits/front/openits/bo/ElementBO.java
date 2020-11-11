package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ElementBO {
		
	
	
	@Autowired
	LocationBO locationBO;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getElements() {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(voDataSourceAdapter.getDataSourceGetElements(), null);
		
		return locationBO.replaceLocations(vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetElements()).getFieldId(), elements));
	}
	
	public List<Map<String, Object>> getElements(List<String> ids) {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(voDataSourceAdapter.getDataSourceGetElementsArray(), new Object[] { ids });
		
		return locationBO.replaceLocations(vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetElementsArray()).getFieldId(), elements));
	}
	
	public Map<String, Object> getElement(String id) {
		
		Map<String, Object> element = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetElement(), new Object[] { id });
		
		return locationBO.replaceLocation(vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetElement()).getFieldId(), element));
	}
	
}
