package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import java.util.List;

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
@Table(name = "vms_libraries")
@NamedQuery(name = "VmsLibrary.findAll", query = "SELECT t FROM VmsLibrary t")
public class VmsLibrary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String label;
	private String name;
	private String description;
	private Boolean enabled;

	private Boolean visible;

	// bi-directional many-to-one association to VmsLibrayNode
	@OneToMany(mappedBy = "vmsLibrary")
	private List<VmsLibraryNode> vmsLibraryNodes;

	// bi-directional many-to-one association to VmsLibrayNode
	@OneToMany(mappedBy = "vmsLibrary")
	private List<VmsLibraryHierarchy> vmsLibraryHierarchies;

	public VmsLibrary() {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<VmsLibraryNode> getVmsLibraryNodes() {
		return this.vmsLibraryNodes;
	}

	public void setVmsLibraryNode(List<VmsLibraryNode> vmsLibraryNodes) {
		this.vmsLibraryNodes = vmsLibraryNodes;
	}

	public VmsLibraryNode addVmsLibraryNode(VmsLibraryNode vmsLibraryNode) {
		getVmsLibraryNodes().add(vmsLibraryNode);
		vmsLibraryNode.setVmsLibrary(this);

		return vmsLibraryNode;
	}

	public VmsLibraryNode removeVmsLibraryNode(VmsLibraryNode vmsLibraryNode) {
		getVmsLibraryNodes().remove(vmsLibraryNode);
		vmsLibraryNode.setVmsLibrary(null);

		return vmsLibraryNode;
	}

	public List<VmsLibraryHierarchy> getVmsLibraryHierarchies() {
		return this.vmsLibraryHierarchies;
	}

	public void setVmsLibraryHierarchies(List<VmsLibraryHierarchy> vmsLibraryHierarchies) {
		this.vmsLibraryHierarchies = vmsLibraryHierarchies;
	}

	public VmsLibraryHierarchy addVmsLibrayHierarchy(VmsLibraryHierarchy vmsLibraryHierarchy) {
		getVmsLibraryHierarchies().add(vmsLibraryHierarchy);
		vmsLibraryHierarchy.setVmsLibrary(this);

		return vmsLibraryHierarchy;
	}

	public VmsLibraryHierarchy removeVmsLibraryCommand(VmsLibraryHierarchy vmsLibraryHierarchy) {
		getVmsLibraryHierarchies().remove(vmsLibraryHierarchy);
		vmsLibraryHierarchy.setVmsLibrary(null);

		return vmsLibraryHierarchy;
	}

}