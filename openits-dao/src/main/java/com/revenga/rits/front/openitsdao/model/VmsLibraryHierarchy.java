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
 * The persistent class for the vms_library_hierarchies database table.
 * 
 */
@Entity
@Table(name = "vms_library_hierarchies")
@NamedQuery(name = "VmsLibraryHierarchy.findAll", query = "SELECT t FROM VmsLibraryHierarchy t")
public class VmsLibraryHierarchy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Boolean enabled;

	private Boolean visible;

	@Column(name = "node_parent_id")
	private Integer nodeParentId;

	@Column(name = "node_child_id")
	private Integer nodeChildId;

	// bi-directional many-to-one association to Tree
	@JsonIgnore
	@ManyToOne
	private VmsLibrary vmsLibrary;

	public VmsLibraryHierarchy() {
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

	public Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public VmsLibrary getVmsLibrary() {
		return this.vmsLibrary;
	}

	public void setVmsLibrary(VmsLibrary vmsLibrary) {
		this.vmsLibrary = vmsLibrary;
	}
	
	public Integer getNodeParentId() {
		return this.nodeParentId;
	}

	public void setNodeParenIdt(Integer nodeParentId) {
		this.nodeParentId = nodeParentId;
	}
	
	public Integer getNodeChildId() {
		return this.nodeChildId;
	}

	public void setNodeChildId(Integer nodeChildId) {
		this.nodeChildId = nodeChildId;
	}
}