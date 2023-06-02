package com.revenga.rits.front.openits.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.model.ElementHistoryDTO;
import com.revenga.rits.front.openits.model.EntityChangedUI;
import com.revenga.rits.front.openits.util.ConversionUtil;

@Service
public class ElementBO extends GenericBO {

	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	SessionBO sessionBO;
	
	@Autowired
	ElementTypeBO elementTypeBO;
	
	public static final int ENTITY_TYPE_ELEMENT = getPropertyInt("entityType.element");
	
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
		
		sessionBO.addEntityChanged(ENTITY_TYPE_ELEMENT, (String) createdElement.get("id"), EntityChangedUI.ENTITY_CREATED,
				this.getItem((String) createdElement.get("id")));

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CREATE_ELEMENT, createdElement);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> updateElement(Object input) {

		Map<String, Object> updatedElement = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_ELEMENT,
				(Map<String, Object>) input);
		
		sessionBO.addEntityChanged(ENTITY_TYPE_ELEMENT, (String) updatedElement.get("id"), EntityChangedUI.ENTITY_UPDATED,
				this.getItem((String) updatedElement.get("id")));

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_UPDATE_ELEMENT, updatedElement);
	}

	public Map<String, Object> cloneElement(String id) {

		Map<String, Object> input = new HashMap<>();

		input.put("elementId", id);

		Map<String, Object> clonedElement = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CLONE_ELEMENT,
				input);
		
		sessionBO.addEntityChanged(ENTITY_TYPE_ELEMENT, (String) clonedElement.get("id"), EntityChangedUI.ENTITY_CREATED,
				this.getItem((String) clonedElement.get("id")));

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_CLONE_ELEMENT, clonedElement);
	}

	public Map<String, Object> deleteElement(String id) {

		Map<String, Object> input = new HashMap<>();

		input.put("elementId", id);

		Map<String, Object> deletedElement = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_DELETE_ELEMENT,
				input);
		
		sessionBO.addEntityChanged(ENTITY_TYPE_ELEMENT, (String) deletedElement.get("id"), EntityChangedUI.ENTITY_DELETED,
				this.getItem((String) deletedElement.get("id")));

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
	
	public List<Map<String, Object>> getHistoryElementStates(ElementHistoryDTO elementHistory) {
		
		List<Map<String, Object>> historicalElementStates = null;
		
		historicalElementStates = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_HISTORICAL_ELEMENT_STATES, new Object[] {
						elementHistory.getElementIds(), elementHistory.getDateIni(), elementHistory.getDateEnd() });
		
		return historicalElementStates;
	}
	
	public List<Map<String, Object>> getHistoryElementValues(ElementHistoryDTO elementHistory) {
		
		List<Map<String, Object>> historicalElementValues = null;
				
		List<String> elementIds = elementHistory.getElementIds();
		Long dateIni = elementHistory.getDateIni();
		Long dateEnd = elementHistory.getDateEnd();
		
		if (!CollectionUtils.isEmpty(elementIds) && dateIni < dateEnd) {
			
			List<Map<String, Object>> elements = getElements(elementIds);
			List<String> elementValueIds = new ArrayList<>();
			
			if (!CollectionUtils.isEmpty(elements)) {
				
				for (Map<String, Object> element : elements) {
					
					if (element.get("elementValues") instanceof List) {
						
						List<?> elementValues = (List<?>) element.get("elementValues");
						
						if (!CollectionUtils.isEmpty(elementValues)) {
							
							List<Map<String, Object>> elementValuesMap = elementValues.stream()
									.map(ConversionUtil::objectToMap).collect(Collectors.toList()); 
							
							elementValueIds.addAll(elementValuesMap.stream().filter(e -> {

								Map<String, Object> elementTypeParam = ConversionUtil
										.objectToMap(e.get("elementTypeParam"));

								if (elementTypeParam != null) {

									Map<String, Object> paramType = ConversionUtil
											.objectToMap(elementTypeParam.get("paramType"));

									if (paramType != null) {

										return paramType.get("id").equals("ParamType:2");
									}
								}

								return false;
							}).map(e -> String.valueOf(e.get("id"))).collect(Collectors.toList()));
						}
					}
				}
				
				if (!CollectionUtils.isEmpty(elementValueIds)) {

					historicalElementValues = voDataSourceAdapter.getList(
							VODataSourceAdapter.DATA_SOURCE_GET_HISTORICAL_ELEMENT_VALUES,
							new Object[] { elementValueIds, dateIni, dateEnd });
				}
			}
		}
		
		return historicalElementValues;
	}
}
