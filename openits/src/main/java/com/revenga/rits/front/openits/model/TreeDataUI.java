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
	private String value;
	private List<TreeDataUI> data;
	private Map<String, Object> params;
	private Map<String, Object> props;
	
	public TreeDataUI(String id, String value) {
		super();
		
		this.id = id;
		this.value = value;
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
}
