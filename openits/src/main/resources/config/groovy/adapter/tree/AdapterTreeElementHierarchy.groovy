package config.groovy.adapter.tree;

import config.groovy.adapter.tree.AdapterTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.front.openits.model.TreeDataUI;
import com.revenga.rits.front.openits.service.CacheManager;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

public class AdapterTreeElementHierarchy extends AdapterTree{
	
	static final String FIELD_ID = "id";
	static final String FIELD_ELEMENT_TYPE = "elementType";
	static final String FIELD_ELEMENT_TYPE_LABEL_ALIAS = "labelAlias";
	static final String FIELD_ELEMENT_TYPE_PHOTO = "elementTypePhoto";
	static final String FIELD_ALIAS = "alias";
	static final String FIELD_PARENT = "parent";
	static final String FIELD_CHILD = "child";

	public AdapterTreeElementHierarchy(CacheManager cacheManager, VO2UIAdapter vo2uiAdapter, org.apache.logging.log4j.Logger log) {
		
		super(cacheManager, vo2uiAdapter, log);
	}
	
	@Override
	public List<TreeDataUI> toTreeData(Integer datasourceId, List<Map<String, Object>> entities) {

		List<TreeDataUI> data = null;
		
		try {
			
			if (!CollectionUtils.isEmpty(entities)) {
				
				List<Map<String, Object>> hierarchiesConverted = vo2uiAdapter.convert(datasourceId, entities);
				
				data = getRootNodes(hierarchiesConverted);
			}
			
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return data;
	}
	
	@SuppressWarnings("unchecked")
	private List<TreeDataUI> getRootNodes(List<Map<String, Object>> hierarchies) {
		
		List<TreeDataUI> data = new ArrayList<>();
		List<Map<String, Object>> elements = null;
		
		elements = (List<Map<String, Object>>) cacheManager.getEntities(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS);
	
		for (Map<String, Object> element : elements) {
			
			boolean isElementRoot = true;
			
			String elementId = (String) element.get(FIELD_ID);
			String elementAlias = (String) element.get(FIELD_ALIAS);
		
			for (int i = 0; isElementRoot && i < hierarchies.size(); i++) {
				
				Map<String, Object> hierarchy = hierarchies.get(i);
				
				Map<String, Object> childElement = (Map<String, Object>) hierarchy.get(FIELD_CHILD); 
				
				isElementRoot = !(childElement != null && childElement.get(FIELD_ID).equals(elementId));
			}
			
			if (isElementRoot) {
				
				TreeDataUI rootNode = new TreeDataUI(elementId + "_" + elementId, elementAlias);
				
				data.add(rootNode);
				
				fillNode(rootNode, element, hierarchies);
			}
		}
		
		return data;
	}

	@SuppressWarnings("unchecked")
	private void fillNode(TreeDataUI node, Map<String, Object> element, List<Map<String, Object>> hierarchies) {
		
		for (Map<String, Object> hierarchy : hierarchies) {
			
			Map<String, Object> parentElement = (Map<String, Object>) hierarchy.get(FIELD_PARENT);
			Map<String, Object> childElement = (Map<String, Object>) hierarchy.get(FIELD_CHILD);
			
			String parentElementId = parentElement != null ? (String) parentElement.get(FIELD_ID) : null;
			String childElementId = childElement != null ? (String) childElement.get(FIELD_ID) : null;
			String childElementAlias = childElement != null ? (String) childElement.get(FIELD_ALIAS) : null;
			
			
			Map<String, Object> elementType = getElementTypeFromElement(element);
			
			node.addProperty(TreeDataUI.PROP_TRANSLATE, false);
			node.addProperty(TreeDataUI.PROP_VALUE_AUX_TRANSLATE, true);
			node.addProperty(TreeDataUI.PROP_VALUE_AUX, elementType != null ? elementType.get(FIELD_ELEMENT_TYPE_LABEL_ALIAS): null);
			node.addProperty(TreeDataUI.PROP_IMAGE, elementType != null ? elementType.get(FIELD_ELEMENT_TYPE_PHOTO): null);
			
			if (!StringUtils.isEmpty(parentElementId) && !StringUtils.isEmpty(childElementId) && node.getId().split("_")[1].equals(parentElementId)) {
				
				TreeDataUI childNode = new TreeDataUI(parentElementId + "_" + childElementId, childElementAlias, parentElementId);
				
				node.addItem(childNode);
				
				node.addProperty(TreeDataUI.PROP_FOLDER, true);
				
				fillNode(childNode, childElement, hierarchies);
			}
			else {
				
				node.addProperty(TreeDataUI.PROP_FOLDER, false);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> getElementTypeFromElement(Map<String, Object> element) {
		
		Map<String, Object> elementType = null;
		
		Object elementTypeObj = element.get(FIELD_ELEMENT_TYPE);
		
		if (elementTypeObj != null && elementTypeObj instanceof Map) {
			
			String elementTypeId = (String) ((Map<String, Object>) elementTypeObj).get(FIELD_ID);
			
			if (!StringUtils.isEmpty(elementTypeId)) {
				
				elementType =  (Map<String, Object>) cacheManager.getEntity(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_TYPES, FIELD_ID, elementTypeId);
			}
		}
		
		return elementType;
	}
}
