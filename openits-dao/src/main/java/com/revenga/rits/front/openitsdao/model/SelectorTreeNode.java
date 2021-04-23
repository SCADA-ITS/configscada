package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the trees selector_tree_nodes database table.
 * 
 */
@Entity
@Table(name = "selector_tree_nodes")
@NamedQuery(name = "SelectorTreeNodes.findAll", query = "SELECT t FROM SelectorTreeNode t")
public class SelectorTreeNode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Boolean enabled;

	private String label;

	private Boolean visible;

	// bi-directional many-to-one association to Tree
	@JsonIgnore
	@ManyToOne
	private SelectorTree selectorTree;

	@Column(name="module_id")
	private Integer moduleId;

	public SelectorTreeNode() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public SelectorTree getSelectorTree() {
		return this.selectorTree;
	}

	public void setSelectorTree(SelectorTree selectorTree) {
		this.selectorTree = selectorTree;
	}

	public Integer getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

}