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
 * The persistent class for the pas_library_elements database table.
 * 
 */
@Entity
@Table(name = "pas_library_elements")
@NamedQuery(name = "PasLibraryElement.findAll", query = "SELECT t FROM PasLibraryElement t")
public class PasLibraryElement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String elementId;

	private Boolean enabled;

	private Boolean visible;

	// bi-directional many-to-one association to PasLibrary
	@ManyToOne
	private PasLibrary pasLibrary;

	public PasLibraryElement() {
	}

	public String getElementId() {
		return this.elementId;
	}

	public void setId(String elementId) {
		this.elementId = elementId;
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
	
}