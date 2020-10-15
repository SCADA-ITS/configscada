package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ElementTypeBO {
	
	public static final String FIELD_ELEMENT_TYPE_STATES = "elementTypeStates";
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	public List<Map<String, Object>> getElementTypes() {
		
		List<Map<String, Object>> elementTypes = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetElementTypes(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetElementTypes()).getFieldId(), elementTypes);
	}
	
	public Map<String, Object> getElementType(String id) {
		
		Map<String, Object> elementType = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetElementType(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetElementType()).getFieldId(), elementType);
	}
	
	public List<Map<String, Object>> getElementsByElementType(String id) {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(voDataSourceAdapter.getDataSourceGetElementsByElementType(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetElementsByElementType()).getFieldId(), elements);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getElementTypeStatesByElementType(String id) {
		
		Map<String, Object> elementType = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetElementType(), new Object[] { id });
		
		Map<String, Object> elementTypeConverted = vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetElementType()).getFieldId(), elementType);
		
		return (List<Map<String, Object>>) elementTypeConverted.get(FIELD_ELEMENT_TYPE_STATES);	
	}
}
