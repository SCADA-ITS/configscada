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
 * The persistent class for the synoptic_element_type_commands database table.
 * 
 */
@Entity
@Table(name="synoptic_element_type_commands")
@NamedQuery(name="SynopticElementTypeCommand.findAll", query="SELECT s FROM SynopticElementTypeCommand s")
public class SynopticElementTypeCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="element_type")
	private String elementType;
	
	//uni-directional many-to-one association to SynopticCommand
	@JsonIgnore
	@ManyToOne
	private SynopticCommand synopticCommand;

	//uni-directional many-to-one association to ModuleAction
	@ManyToOne
	private ModuleAction moduleAction;
	

	public SynopticElementTypeCommand() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public SynopticCommand getSynopticCommand() {
		return synopticCommand;
	}

	public void setSynopticCommand(SynopticCommand synopticCommand) {
		this.synopticCommand = synopticCommand;
	}

	public ModuleAction getModuleAction() {
		return moduleAction;
	}

	public void setModuleAction(ModuleAction moduleAction) {
		this.moduleAction = moduleAction;
	}

}