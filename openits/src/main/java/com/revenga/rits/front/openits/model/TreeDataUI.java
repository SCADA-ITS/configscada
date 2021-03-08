package com.revenga.rits.front.openits.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class TreeDataUI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 912965604604466364L;
	
	private String id;
	private String image;
	private String value;
	private Boolean translate;
	private List<TreeDataUI> data;
	
	public TreeDataUI(String id, String value, Boolean translate, String image) {
		super();
		
		this.id = id;
		this.value = value;
		this.translate = translate;
		this.image = image;
	}
	
	public void addItem(TreeDataUI item) {
		
		if (this.data == null) {
			
			this.data = new ArrayList<>();
		}
		
		this.data.add(item);
	}
	
}
