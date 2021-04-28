package config.groovy.adapter.tree;

import config.groovy.adapter.tree.AdapterTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.front.openits.model.TreeDataUI;
import com.revenga.rits.front.openits.service.CacheManager;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

public class AdapterTreeElementHierarchyIterative extends AdapterTree{

	static final String FIELD_ID = "id";
	static final String FIELD_ELEMENT_TYPE = "elementType";
	static final String FIELD_ELEMENT_TYPE_LABEL_ALIAS = "labelAlias";
	static final String FIELD_ELEMENT_TYPE_PHOTO = "elementTypePhoto";
	static final String FIELD_ALIAS = "alias";
	static final String FIELD_PARENT = "parent";
	static final String FIELD_CHILD = "child";
	
	static final int PARENT_ID_IDX = 0;
	static final int CHILD_ID_IDX = 1; 

	public AdapterTreeElementHierarchyIterative(CacheManager cacheManager, VO2UIAdapter vo2uiAdapter, org.apache.logging.log4j.Logger log) {

		super(cacheManager, vo2uiAdapter, log);
	}

	@Override
	public List<TreeDataUI> toTreeData(String fieldId, List<Map<String, Object>> entities) {

		List<TreeDataUI> data = new ArrayList<>();
		Map<String, TreeDataUI> mapNodes = null;
		List<String> childElements = null;

		try {

			if (!CollectionUtils.isEmpty(entities)) {

				List<Map<String, Object>> hierarchiesConverted = vo2uiAdapter.convert(fieldId, entities);

				mapNodes = getNodes();
				childElements = fillNodes(mapNodes, hierarchiesConverted);
				
				for(String childElementId : childElements){
					
					mapNodes.remove(childElementId);
				}
				
				for (Map.Entry<String, TreeDataUI> entry : mapNodes.entrySet()) {
					
					data.add(entry.getValue());
				}
			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return data;
	}

	@SuppressWarnings("unchecked")
	private Map<String, TreeDataUI> getNodes() {

		Map<String, TreeDataUI> mapNodes = new HashMap<>();
		List<Map<String, Object>> elements = null;

		elements = (List<Map<String, Object>>) cacheManager.getEntities(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENTS);

		for (Map<String, Object> element : elements) {

			String elementId = (String) element.get(FIELD_ID);
			String elementAlias = (String) element.get(FIELD_ALIAS);

			TreeDataUI node = new TreeDataUI(elementId + "_" + elementId, elementAlias);

			Map<String, Object> elementType = getElementTypeFromElement(element);

			node.addProperty(TreeDataUI.PROP_TRANSLATE, false);
			node.addProperty(TreeDataUI.PROP_VALUE_AUX_TRANSLATE, true);
			node.addProperty(TreeDataUI.PROP_VALUE_AUX, elementType != null ? elementType.get(FIELD_ELEMENT_TYPE_LABEL_ALIAS): null);
			node.addProperty(TreeDataUI.PROP_IMAGE, elementType != null ? elementType.get(FIELD_ELEMENT_TYPE_PHOTO): null);
			node.addProperty(TreeDataUI.PROP_FOLDER, false);

			mapNodes.put(elementId, node);
		}
		return mapNodes;
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
	
	@SuppressWarnings("unchecked")
	private List<String> fillNodes(Map<String, TreeDataUI> mapNodes, List<Map<String, Object>> hierarchies) {
		
		List<String> childElements = new ArrayList<>();
		
		for (Map<String, Object> hierarchy : hierarchies) {
			
			Map<String, Object> parentElement = (Map<String, Object>) hierarchy.get(FIELD_PARENT);
			Map<String, Object> childElement = (Map<String, Object>) hierarchy.get(FIELD_CHILD);
			
			if(parentElement != null && childElement != null){
				
				String parentElementId =  (String) parentElement.get(FIELD_ID);
				String childElementId =  (String) childElement.get(FIELD_ID);
				
				if(parentElementId != null && childElementId != null){
					
					TreeDataUI parentTreeData = mapNodes.get(parentElementId);
					TreeDataUI childTreeData = mapNodes.get(childElementId);
					
					if(parentTreeData != null && childTreeData != null) {
						
						parentTreeData.addProperty(TreeDataUI.PROP_FOLDER, true);
						
						TreeDataUI newChild = new TreeDataUI(childTreeData);
						
						newChild.setId(parentTreeData.getId().split("_")[CHILD_ID_IDX] + "_" + childTreeData.getId().split("_")[CHILD_ID_IDX]);									
						parentTreeData.addItem(newChild);
											
						mapNodes.put(childElementId, newChild)
						childElements.add(childElementId);
					}
				}
			}
		}
		
		return childElements;
	}
}
