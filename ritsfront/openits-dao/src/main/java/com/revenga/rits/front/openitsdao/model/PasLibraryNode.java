package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for pas_library_nodes database table.
 * 
 */
@Entity
@Table(name = "pas_library_nodes")
@NamedQuery(name = "PasLibraryNode.findAll", query = "SELECT t FROM PasLibraryNode t")
public class PasLibraryNode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(
		    name = "incrementGenerator",
		    strategy = "org.hibernate.id.IncrementGenerator")
	@GeneratedValue(generator="incrementGenerator")
	private Integer id;
	
	private String name;
	private String description;
	private String label;

	private Boolean enabled;
	private Boolean visible;

	// bi-directional many-to-one association to pasLibrary
	@JsonIgnore
	@ManyToOne
	private PasLibrary pasLibrary;

	// bi-directional many-to-one association to pasMessage
	@ManyToOne
	private PasMessage pasMessage;

	public PasLibraryNode() {
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
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public PasMessage getPasMessage() {
		return this.pasMessage;
	}

	public void setPasMessage(PasMessage pasMessage) {
		this.pasMessage = pasMessage;
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