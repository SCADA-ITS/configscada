package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;

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
@Table(name = "vms_library_nodes")
@NamedQuery(name = "VmsLibraryNode.findAll", query = "SELECT t FROM VmsLibraryNode t")
public class VmsLibraryNode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	private String description;
	private String signaling;
	private Boolean enabled;
	private Boolean visible;

	// bi-directional many-to-one association to Tree
	@JsonIgnore
	@ManyToOne
	private VmsLibrary vmsLibrary;

	public VmsLibraryNode() {
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

	public String getSignaling() {
		return this.signaling;
	}

	public void setSignaling(String signaling) {
		this.signaling = signaling;
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

}