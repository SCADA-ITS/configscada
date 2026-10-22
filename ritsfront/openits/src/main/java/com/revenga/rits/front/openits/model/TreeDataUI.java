package com.revenga.rits.front.openits.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class TreeDataUI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 912965604604466364L;
	
	public static final String PROP_TRANSLATE = "translate";
	public static final String PROP_FOLDER = "folder";
	public static final String PROP_IMAGE = "image";
	public static final String PROP_DISABLED = "disabled";
	public static final String PROP_VALUE_AUX = "valueAux";
	public static final String PROP_VALUE_AUX_TRANSLATE = "valueAuxTranslate";
	
	private String id;
	private String id_;
	private String parentId;
	private String value;
	private List<TreeDataUI> data;
	private Map<String, Object> params;
	private Map<String, Object> props;
	
	public TreeDataUI(TreeDataUI treeDataUI) {
		
		super();
		
		this.id = treeDataUI.getId();
		this.id_ = getId_(id);
		this.value = treeDataUI.getValue();
		this.parentId = treeDataUI.getParentId();
		
		if (treeDataUI.getData() != null) {
			
			for (TreeDataUI item : treeDataUI.getData()) {
				
				this.addItem(new TreeDataUI(item));
			}
		}
		
		if (treeDataUI.getParams() != null) {
		
			this.params = new HashMap<>(treeDataUI.getParams());
		}
		
		if (treeDataUI.getProps() != null) {
			
			this.props = new HashMap<>(treeDataUI.getProps());
		}
	}

	public TreeDataUI(String id, String value) {
		super();
		
		this.id = id;
		this.id_ = getId_(id);
		this.parentId = null;
		this.value = value;
	}
	
	public TreeDataUI(String id, String value, String parentId) {
		super();
		
		this.id = id;
		this.value = value;
		this.parentId = parentId;
	}
	
	public void addItem(TreeDataUI item) {
		
		if (this.data == null) {
			
			this.data = new ArrayList<>();
		}
		
		this.data.add(item);
	}
	
	public void addParam(String name, Object value) {
		
		if (this.params == null) {
			
			this.params = new HashMap<>();
		}
		
		this.params.put(name, value);
	}
	
	public void addProperty(String name, Object value) {
		
		if (this.props == null) {
			
			this.props = new HashMap<>();
		}
		
		this.props.put(name, value);
	}
	
	private String getId_(String id) {
		
		String[] ids = id.split("_");

		return ids.length == 2 ? ids[1] : null;
	}
}
