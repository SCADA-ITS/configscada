package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ElementBO extends GenericBO {

	@Autowired
	LocationBO locationBO;

	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT;
	}

	public List<Map<String, Object>> getElements(List<String> ids) {

		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY, new Object[] { ids });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY, elements);
	}
	
	public Map<String, Object> getElementWithoutChilds(String id) {
		
		Map<String, Object> element = voDataSourceAdapter
				.get(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_WITHOUT_CHILDS, new Object[] { id });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_WITHOUT_CHILDS, element);
	}

	public List<Map<String, Object>> getHierarchies() {

		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_HIERARCHIES, null);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_HIERARCHIES, elements);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> createElement(Object input) {

		Map<String, Object> createdElement = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CREATE_ELEMENT,
				(Map<String, Object>) input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CREATE_ELEMENT, createdElement);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> updateElement(Object input) {

		Map<String, Object> updatedElement = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_ELEMENT,
				(Map<String, Object>) input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_UPDATE_ELEMENT, updatedElement);
	}

	public Map<String, Object> cloneElement(String id) {

		Map<String, Object> input = new HashMap<>();

		input.put("elementId", id);

		Map<String, Object> clonedElement = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CLONE_ELEMENT,
				input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CLONE_ELEMENT, clonedElement);
	}

	public Map<String, Object> deleteElement(String id) {

		Map<String, Object> input = new HashMap<>();

		input.put("elementId", id);

		Map<String, Object> deletedElement = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_DELETE_ELEMENT,
				input);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_DELETE_ELEMENT, deletedElement);
	}

	@SuppressWarnings("unchecked")
	public Boolean sendCommandElement(Object input) {

		Boolean res = false;

		Map<String, Object> sendCommandElement = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_SEND_COMMAND_ELEMENT, (Map<String, Object>) input);

		res = checkMapIsBoolean(sendCommandElement);

		return res;

	}

}
