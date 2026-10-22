package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ElementBO extends GenericBO {

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
	
	public List<Map<String, Object>> getElementsWithState(List<String> ids) {

		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_SYNOPTIC, new Object[] { ids });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_SYNOPTIC, elements);
	}
	
	public List<Map<String, Object>> getElementsByStretchs(List<String> stretchIds, String elementTypeId) {

		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_BY_STRETCHS, new Object[] { stretchIds, elementTypeId });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_BY_STRETCHS, elements);
	}
	
	
	public List<Map<String, Object>> getElementsCommands(List<String> ids) {

		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_COMMANDS, new Object[] { ids });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS_ARRAY_COMMANDS, elements);
	}
	
	public Map<String, Object> getElementWithoutChilds(String id) {
		
		Map<String, Object> element = voDataSourceAdapter
				.get(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_WITHOUT_CHILDS, new Object[] { id });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_WITHOUT_CHILDS, element);
	}
	
	public Map<String, Object> getElementWithParents(String id) {
		
		Map<String, Object> element = voDataSourceAdapter
				.get(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_WITH_PARENTS, new Object[] { id });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_WITH_PARENTS, element);
	}

	public List<Map<String, Object>> getHierarchies() {

		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_HIERARCHIES, null);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_HIERARCHIES, elements);
	}
	
	public List<Map<String, Object>> getElementTrees(String userId) {

		List<Map<String, Object>> elements = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_TREES_BY_USER, new Object[] { userId });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_TREES_BY_USER, elements);
	}
	
	public Map<String, Object> getElementTree(String id) {

		Map<String, Object> element = voDataSourceAdapter
				.get(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_TREE, new Object[] { id });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_TREE, element);
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
	public List<Map<String, Object>> sendCommandElement(Object input) {

		return voDataSourceAdapter
				.putList(VODataSourceAdapter.DATA_SOURCE_SEND_COMMAND_ELEMENT, (Map<String, Object>) input);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> sendCommandElementSync(Object input) {

		return voDataSourceAdapter
				.putList(VODataSourceAdapter.DATA_SOURCE_SEND_COMMAND_ELEMENT_SYNC, (Map<String, Object>) input);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> addCommandElement(Object input) {

		return voDataSourceAdapter
				.putList(VODataSourceAdapter.DATA_SOURCE_ADD_COMMAND_ELEMENT, (Map<String, Object>) input);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> deleteCommandElement(Object input) {

		return voDataSourceAdapter
				.putList(VODataSourceAdapter.DATA_SOURCE_DELETE_COMMAND_ELEMENT, (Map<String, Object>) input);
	}

	public List<Map<String, Object>> enablePriority(String elementId, String priorityId) {
		
		Map<String, Object> input = new HashMap<>();

		input.put("elementId", elementId);
		input.put("priorityId", priorityId);

		return voDataSourceAdapter
				.putList(VODataSourceAdapter.DATA_SOURCE_ENABLE_PRIORITY, input);
	}
	
	public List<Map<String, Object>> disablePriority(String elementId, String priorityId) {

		Map<String, Object> input = new HashMap<>();

		input.put("elementId", elementId);
		input.put("priorityId", priorityId);

		return voDataSourceAdapter
				.putList(VODataSourceAdapter.DATA_SOURCE_DISABLE_PRIORITY, input);
	}
}
