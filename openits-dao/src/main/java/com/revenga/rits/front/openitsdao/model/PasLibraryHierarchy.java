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
 * The persistent class for the pas_library_hierarchies database table.
 * 
 */
@Entity
@Table(name = "pas_library_hierarchies")
@NamedQuery(name = "PasLibraryHierarchy.findAll", query = "SELECT t FROM PasLibraryHierarchy t")
public class PasLibraryHierarchy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Boolean enabled;

	private Boolean visible;

	@Column(name = "node_parent_id")
	private Integer nodeParentId;

	@Column(name = "node_child_id")
	private Integer nodeChildId;

	// bi-directional many-to-one association to PasLibrary
	@JsonIgnore
	@ManyToOne
	private PasLibrary pasLibrary;

	public PasLibraryHierarchy() {
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

	public PasLibrary getPasLibrary() {
		return this.pasLibrary;
	}

	public void setPasLibrary(PasLibrary pasLibrary) {
		this.pasLibrary = pasLibrary;
	}
	
	public Integer getNodeParentId() {
		return this.nodeParentId;
	}

	public void setNodeParentId(Integer nodeParentId) {
		this.nodeParentId = nodeParentId;
	}
	
	public Integer getNodeChildId() {
		return this.nodeChildId;
	}

	public void setNodeChildId(Integer nodeChildId) {
		this.nodeChildId = nodeChildId;
	}
}