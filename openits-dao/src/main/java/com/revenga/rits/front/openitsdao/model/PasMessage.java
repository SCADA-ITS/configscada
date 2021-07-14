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
 * The persistent class for pas_messages database table.
 * 
 */
@Entity
@Table(name = "pas_messages")
@NamedQuery(name = "PasMessage.findAll", query = "SELECT t FROM PasMessage t")
public class PasMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String alias;
	private String description;
	private String message;
	private String audio;
	private Boolean enabled;

	private Boolean visible;

	// bi-directional many-to-one association to PasLibrayNode
	@OneToMany(mappedBy = "pasMessage")
	private List<PasLibraryNode> pasLibraryNodes;

	public PasMessage() {
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public List<PasLibraryNode> getPasLibraryNodes() {
		return this.pasLibraryNodes;
	}

	public void setPasLibraryNode(List<PasLibraryNode> pasLibraryNodes) {
		this.pasLibraryNodes = pasLibraryNodes;
	}

	public PasLibraryNode addPasLibraryNode(PasLibraryNode pasLibraryNode) {
		getPasLibraryNodes().add(pasLibraryNode);
		pasLibraryNode.setPasMessage(this);

		return pasLibraryNode;
	}

	public PasLibraryNode removePasLibraryNode(PasLibraryNode pasLibraryNode) {
		getPasLibraryNodes().remove(pasLibraryNode);
		pasLibraryNode.setPasMessage(null);

		return pasLibraryNode;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

}