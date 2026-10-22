package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the vms_library_hierarchies database table.
 * 
 */
@Entity
@Table(name = "vms_library_element_subtypes")
@NamedQuery(name = "VmsLibraryElementSubtype.findAll", query = "SELECT t FROM VmsLibraryElementSubtype t")
public class VmsLibraryElementSubtype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String elementSubtypeId;

	private Boolean enabled;

	private Boolean visible;

	// bi-directional many-to-one association to Tree
	@ManyToOne
	private VmsLibrary vmsLibrary;

	public VmsLibraryElementSubtype() {
	}

	public String getElementSubtypeId() {
		return this.elementSubtypeId;
	}

	public void setId(String elementSubtypeId) {
		this.elementSubtypeId = elementSubtypeId;
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
	
}