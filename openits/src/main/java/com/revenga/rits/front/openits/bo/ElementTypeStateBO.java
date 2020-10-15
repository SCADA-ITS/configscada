package com.revenga.rits.front.openits.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ElementTypeStateBO {
	
	private static final String FIELD_ELEMENT_TYPE_STATE_ID = "id";
	
	@Autowired
	ElementTypeBO elementTypeBO;
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getElementTypeStates() {
		
		List<Map<String, Object>> elementTypeStates = new ArrayList<>();
		List<Map<String, Object>> elementTypes = elementTypeBO.getElementTypes();
		
		if (!CollectionUtils.isEmpty(elementTypes)) {
			
			for (Map<String, Object> elementType : elementTypes) {
				
				Object elementTypeStateObj = elementType.get(ElementTypeBO.FIELD_ELEMENT_TYPE_STATES);
				
				if (elementTypeStateObj instanceof List) {
				
					elementTypeStates.addAll((List<Map<String, Object>>) elementTypeStateObj);
				}
			}
		}
		
		return elementTypeStates;
	}
				
	public Map<String, Object> getElementTypeState(String id) {
		
		List<Map<String, Object>> elementTypeStates = getElementTypeStates();
				
		if (!CollectionUtils.isEmpty(elementTypeStates)) {
			
			for (Map<String, Object> elementTypeState : elementTypeStates) {
				
				if (elementTypeState.get(FIELD_ELEMENT_TYPE_STATE_ID).equals(id)) {
					
					return elementTypeState;
				}
			}
		}
		
		return null;
	}
}
