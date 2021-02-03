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
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS, null);
		
		return locationBO.replaceLocations(vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS).getFieldId(), elements));
	}

	@Override
	public Map<String, Object> getItem(String id) {
		
		Map<String, Object> element = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT, new Object[] { id });
		
		return locationBO.replaceLocation(vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT).getFieldId(), element));
	}
	
	public List<Map<String, Object>> getElements(List<String> ids) {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY, new Object[] { ids });
		
		return locationBO.replaceLocations(vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY).getFieldId(), elements));
	}
	
	public Map<String, Object> createElement(Object input) {
		
		Map<String, Object> createdElement = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CREATE_ELEMENT, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_CREATE_ELEMENT).getFieldId(), createdElement);
	}
	
	public Map<String, Object> updateElement(Object input) {
			
		Map<String, Object> updatedElement = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_ELEMENT, (Map<String, Object>) input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_UPDATE_ELEMENT).getFieldId(), updatedElement);
	}
	
	public Map<String, Object> cloneElement(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("elementId", id);
		
		Map<String, Object> clonedElement = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CLONE_ELEMENT, input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_CLONE_ELEMENT).getFieldId(), clonedElement);
	}
	
	public Map<String, Object> deleteElement(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("elementId", id);
		
		Map<String, Object> deletedElement = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_DELETE_ELEMENT, input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_DELETE_ELEMENT).getFieldId(), deletedElement);
	}
	
	public Boolean sendCommandElement(Object input) {
	    
	    Boolean res = false;
	    
	    Map<String, Object> sendCommandElement = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_COMMAND_ELEMENT, (Map<String, Object>) input);
	    
	    res = checkMapIsBoolean(sendCommandElement);

	    return res;
	    
	  }
	
}
