package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the tree_filters database table.
 * 
 */
@Entity
@Table(name="tree_filters")
@NamedQuery(name="TreeFilter.findAll", query="SELECT t FROM TreeFilter t")
public class TreeFilter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String description;
	
	private Boolean enabled;
	
	private String name;
	
	@Column(name="label_alias")
	private String labelAlias;
	
	@Column(name="label_description")
	private String labelDescription;
	
	private String value;
	
	private Boolean visible;

	//bi-directional many-to-one association to Tree
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="tree_id")
	private Tree tree;

	public TreeFilter() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabelAlias() {
		return labelAlias;
	}

	public void setLabelAlias(String labelAlias) {
		this.labelAlias = labelAlias;
	}

	public String getLabelDescription() {
		return labelDescription;
	}

	public void setLabelDescription(String labelDescription) {
		this.labelDescription = labelDescription;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

}