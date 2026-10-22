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
 * The persistent class for pas_libraries database table.
 * 
 */
@Entity
@Table(name = "pas_libraries")
@NamedQuery(name = "PasLibrary.findAll", query = "SELECT t FROM PasLibrary t")
public class PasLibrary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String label;
	private String name;
	private String description;
	private Boolean enabled;

	private Boolean visible;

	// bi-directional many-to-one association to PasLibrayNode
	@OneToMany(mappedBy = "pasLibrary")
	private List<PasLibraryNode> pasLibraryNodes;

	// bi-directional many-to-one association to PasLibrayHierarchy
	@OneToMany(mappedBy = "pasLibrary")
	private List<PasLibraryHierarchy> pasLibraryHierarchies;

	public PasLibrary() {
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

	public List<PasLibraryNode> getPasLibraryNodes() {
		return this.pasLibraryNodes;
	}

	public void setPasLibraryNode(List<PasLibraryNode> pasLibraryNodes) {
		this.pasLibraryNodes = pasLibraryNodes;
	}

	public PasLibraryNode addPasLibraryNode(PasLibraryNode pasLibraryNode) {
		getPasLibraryNodes().add(pasLibraryNode);
		pasLibraryNode.setPasLibrary(this);

		return pasLibraryNode;
	}

	public PasLibraryNode removePasLibraryNode(PasLibraryNode pasLibraryNode) {
		getPasLibraryNodes().remove(pasLibraryNode);
		pasLibraryNode.setPasLibrary(null);

		return pasLibraryNode;
	}

	public List<PasLibraryHierarchy> getPasLibraryHierarchies() {
		return this.pasLibraryHierarchies;
	}

	public void setPasLibraryHierarchies(List<PasLibraryHierarchy> pasLibraryHierarchies) {
		this.pasLibraryHierarchies = pasLibraryHierarchies;
	}

	public PasLibraryHierarchy addPasLibrayHierarchy(PasLibraryHierarchy pasLibraryHierarchy) {
		getPasLibraryHierarchies().add(pasLibraryHierarchy);
		pasLibraryHierarchy.setPasLibrary(this);

		return pasLibraryHierarchy;
	}

	public PasLibraryHierarchy removePasLibraryCommand(PasLibraryHierarchy pasLibraryHierarchy) {
		getPasLibraryHierarchies().remove(pasLibraryHierarchy);
		pasLibraryHierarchy.setPasLibrary(null);

		return pasLibraryHierarchy;
	}

}