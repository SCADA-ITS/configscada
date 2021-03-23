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
 * The persistent class for the module_actions database table.
 * 
 */
@Entity
@Table(name="module_actions")
@NamedQuery(name="ModuleAction.findAll", query="SELECT m FROM ModuleAction m")
public class ModuleAction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String description;

	private Boolean enabled;

	//bi-directional many-to-one association to Module
	@JsonIgnore
	@ManyToOne
	private Module module;

	private String name;

	private Boolean visible;

	private String systemPermissionId;
	
	public ModuleAction() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
	public Module getModule() {
		return this.module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getSystemPermissionId() {
		return this.systemPermissionId;
	}

	public void setSystemPermissionId(String systemPermissionId) {
		this.systemPermissionId = systemPermissionId;
	}
}