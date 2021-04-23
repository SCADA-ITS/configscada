package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the selector_trees database table.
 * 
 */
@Entity
@Table(name = "selector_trees")
@NamedQuery(name = "SelectorTree.findAll", query = "SELECT t FROM SelectorTree t")
public class SelectorTree implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Boolean enabled;

	private String label;

	private Boolean visible;

	// bi-directional many-to-one association to SelectorTreeNode
	@OneToMany(mappedBy = "selectorTree")
	private List<SelectorTreeNode> selectorTreeNodes;

	// bi-directional many-to-one association to SelectorTreeNode
	@OneToMany(mappedBy = "selectorTree")
	private List<SelectorTreeHierarchy> selectorTreeHierarchies;

	@Column(name="default_module_id")
	private Integer defaultModuleId;
	
	public SelectorTree() {
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

	public List<SelectorTreeNode> getTreeNodes() {
		return this.selectorTreeNodes;
	}

	public void setTreeNodes(List<SelectorTreeNode> selectorTreeNodes) {
		this.selectorTreeNodes = selectorTreeNodes;
	}
	
	public Integer getDefaultModuleId() {
		return this.defaultModuleId;
	}

	public void setDefaulModuleId(Integer defaultModuleId) {
		this.defaultModuleId = defaultModuleId;
	}

	public SelectorTreeNode addTreeNode(SelectorTreeNode selectorTreeNode) {
		getTreeNodes().add(selectorTreeNode);
		selectorTreeNode.setSelectorTree(this);

		return selectorTreeNode;
	}

	public SelectorTreeNode removeTreeNode(SelectorTreeNode selectorTreeNode) {
		getTreeNodes().remove(selectorTreeNode);
		selectorTreeNode.setSelectorTree(null);

		return selectorTreeNode;
	}

	public List<SelectorTreeHierarchy> getTreeHierarchies() {
		return this.selectorTreeHierarchies;
	}

	public void setTreeHierarchies(List<SelectorTreeHierarchy> selectorTreeHierarchies) {
		this.selectorTreeHierarchies = selectorTreeHierarchies;
	}

	public SelectorTreeHierarchy addSelectorTreeHierarchy(SelectorTreeHierarchy selectorTreeHierarchy) {
		getTreeHierarchies().add(selectorTreeHierarchy);
		selectorTreeHierarchy.setSelectorTree(this);

		return selectorTreeHierarchy;
	}

	public SelectorTreeHierarchy removeTreeCommand(SelectorTreeHierarchy selectorTreeHierarchy) {
		getTreeHierarchies().remove(selectorTreeHierarchy);
		selectorTreeHierarchy.setSelectorTree(null);

		return selectorTreeHierarchy;
	}

}