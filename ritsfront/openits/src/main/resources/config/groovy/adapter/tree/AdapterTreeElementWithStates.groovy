package config.groovy.adapter.tree;

import config.groovy.adapter.tree.AdapterTree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.revenga.rits.front.openits.service.CacheManager;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.front.openits.model.TreeDataUI;

public class AdapterTreeElementWithStates extends AdapterTree{
	
	static final String FIELD_ELEMENT_TYPE = "elementType";
	static final String FIELD_ELEMENT_TYPE_ID = "id";
	static final String FIELD_ELEMENT_TYPE_LABEL_ALIAS = "labelAlias";
	static final String FIELD_ELEMENT_TYPE_TREE_IMAGE = "treeImage";
	
	static final String FIELD_ELEMENT_TYPE_STATE = "elementTypeState";
	static final String FIELD_ELEMENT_TYPE_STATE_ID = "id";
	static final String FIELD_ELEMENT_TYPE_STATE_TREE_IMAGE = "treeImage";
	
	static final String FIELD_ELEMENT_ID = "id";
	static final String FIELD_ELEMENT_ALIAS = "alias";
	
	static final String TREE_FIELD_ID = "id";
	static final String TREE_FIELD_VALUE = "value";
	static final String TREE_FIELD_IMAGE = "image";
	static final String TREE_FIELD_DATA = "data";

	public AdapterTreeElementWithStates(CacheManager cacheManager, VO2UIAdapter vo2uiAdapter, org.apache.logging.log4j.Logger log) {
		
		super(cacheManager, vo2uiAdapter, log);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TreeDataUI> toTreeData(Integer datasourceId, List<Map<String, Object>> entities) {
		
		List<TreeDataUI> data = null;
		Map<String, TreeDataUI> elementTypeNodes = new HashMap<>();
		
		try {
			
			if (!CollectionUtils.isEmpty(entities)) {
				
				List<Map<String, Object>> entitiesConverted = vo2uiAdapter.convert(datasourceId, entities);
						
				for (Map<String, Object> entity : entitiesConverted) {
									
					Object elementTypeObject = entity.get(FIELD_ELEMENT_TYPE);
					
					if (elementTypeObject != null) {
					
						TreeDataUI node = getNode(elementTypeObject, elementTypeNodes);
											
						if (node != null) {
							
							Object elementTypeStateObj = entity.get(FIELD_ELEMENT_TYPE_STATE);
							String elementTypeStateImage = null;							
							
							if (elementTypeStateObj instanceof Map) {
								
								Map<String, Object> elementTypeState = (Map<String, Object>) elementTypeStateObj;
								
								elementTypeStateImage = (String) elementTypeState.get(FIELD_ELEMENT_TYPE_STATE_TREE_IMAGE);
							}
													
							TreeDataUI item = new TreeDataUI((String) entity.get(FIELD_ELEMENT_ID),
									(String) entity.get(FIELD_ELEMENT_ALIAS));
							
							item.addProperty(TreeDataUI.PROP_IMAGE, elementTypeStateImage);
														
							node.addItem(item);
							 
						}
					}			
				}
				
				data = elementTypeNodes.values().stream().collect(Collectors.toList());
			}
			
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return data;
	}
	
	@SuppressWarnings("unchecked")
	TreeDataUI getNode(Object value, Map<String, TreeDataUI> elementTypeNodes) {
		
		TreeDataUI node = null;
		
		if (value instanceof Map) {
			
			Map<String, Object> elementType = (Map<String, Object>) value;
			
			String elementTypeId = (String) elementType.get(FIELD_ELEMENT_TYPE_ID);
			String elementTypeValue = null;
			
			if (!StringUtils.isEmpty(elementTypeId)) {
			
				Object entityTypeObj = cacheManager.getEntity(VODataSourceAdapter.DATA_SOURCE_GET_ELEMENT_TYPES, FIELD_ELEMENT_TYPE_ID, elementTypeId);
				
				if (entityTypeObj != null && entityTypeObj instanceof Map) {
				
					Map<String, Object> entityType = (Map<String, Object>) entityTypeObj;
					
					elementTypeValue = (String) entityType.get(FIELD_ELEMENT_TYPE_LABEL_ALIAS);
				}
			}
			
			String elementTypeImage = (String) elementType.get(FIELD_ELEMENT_TYPE_TREE_IMAGE);
			
			node = elementTypeNodes.get(elementTypeId);
			
			if (node == null) {
		
				node = new TreeDataUI(elementTypeId, elementTypeValue);
				
				node.addProperty(TreeDataUI.PROP_TRANSLATE, true);
				node.addProperty(TreeDataUI.PROP_IMAGE, elementTypeImage);
				node.addProperty(TreeDataUI.PROP_FOLDER, true);
				
				elementTypeNodes.put(elementTypeId, node);
			}
		}
		
		return node;
	}
}
