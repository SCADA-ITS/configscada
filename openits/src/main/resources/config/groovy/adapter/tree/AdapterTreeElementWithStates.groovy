import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.front.openits.model.TreeDataUI;
import com.revenga.rits.front.openitsdao.model.Tree;
import com.revenga.rits.front.openitsdao.model.TreeParam;

public class AdapterTreeElementWithStates {
	
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
	
	static final String TREE_PARAM_STATE_IMAGE = "state_image";

	org.apache.logging.log4j.Logger log;
	
	public AdapterTreeElementWithStates(org.apache.logging.log4j.Logger log) {
		
		this.log = log;
	}
	
	List<TreeDataUI> toTreeData(Tree tree, VO2UIAdapter vo2uiAdapter, String fieldId, List<Map<String, Object>> entities) {
		
		List<TreeDataUI> data = null;
		Map<String, TreeDataUI> elementTypeNodes = new HashMap<>();
		
		try {
			
			if (!CollectionUtils.isEmpty(entities)) {
				
				List<Map<String, Object>> entitiesConverted = vo2uiAdapter.convert(fieldId, entities);
						
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
									(String) entity.get(FIELD_ELEMENT_ALIAS), elementTypeStateImage);
									
							addParameter(tree, item, TREE_PARAM_STATE_IMAGE, elementTypeStateImage);
														
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
	
	TreeDataUI getNode(Object value, Map<String, TreeDataUI> elementTypeNodes) {
		
		TreeDataUI node = null;
		
		if (value instanceof Map) {
			
			Map<String, Object> elementType = (Map<String, Object>) value;
			
			String elementTypeId = (String) elementType.get(FIELD_ELEMENT_TYPE_ID);
			String elementTypeValue = (String) elementType.get(FIELD_ELEMENT_TYPE_LABEL_ALIAS);
			String elementTypeImage = (String) elementType.get(FIELD_ELEMENT_TYPE_TREE_IMAGE);
			
			node = elementTypeNodes.get(elementTypeId);
			
			if (node == null) {
		
				node = new TreeDataUI(elementTypeId, elementTypeValue, elementTypeImage);
				
				node.addProperty(TreeDataUI.PROP_TRANSLATE, true);
				node.addProperty(TreeDataUI.PROP_FOLDER, true);
				
				elementTypeNodes.put(elementTypeId, node);
			}
		}
		
		return node;
	}
	
	void addParameter(final Tree tree, TreeDataUI node, final String treeParamName, final Object value) {

		if (tree != null && treeParamName != null) {
		
			if (!CollectionUtils.isEmpty(tree.getTreeParams())) {
			
				for (TreeParam treeParam : tree.getTreeParams()) {
				
					if (treeParam.getName().equals(treeParamName)) {
					
						node.addParam(treeParamName, value);
						
						break;
					}
				}
			}
		}
		
	}
	
}
