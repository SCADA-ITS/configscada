package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ElementTypeBO extends GenericBO {
	
	public static final String FIELD_ELEMENT_TYPE_STATES = "elementTypeStates";
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> elementTypes = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_TYPES, null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_TYPES).getFieldId(), elementTypes);
	}

	@Override
	public Map<String, Object> getItem(String id) {
		
		Map<String, Object> elementType = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_TYPE, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_TYPE).getFieldId(), elementType);
	}
	
	public List<Map<String, Object>> getElementsByElementType(String id) {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_BY_ELEMENT_TYPE, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_BY_ELEMENT_TYPE).getFieldId(), elements);
	}

	public List<Map<String, Object>> getElementsWithChildsByElementType(String id) {
		
		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_WITH_CHILDS_BY_ELEMENT_TYPE, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_WITH_CHILDS_BY_ELEMENT_TYPE).getFieldId(), elements);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getElementTypeStatesByElementType(String id) {
		
		Map<String, Object> elementType = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_TYPE, new Object[] { id });
		
		Map<String, Object> elementTypeConverted = vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_TYPE).getFieldId(), elementType);
		
		return (List<Map<String, Object>>) elementTypeConverted.get(FIELD_ELEMENT_TYPE_STATES);	
	}
}
