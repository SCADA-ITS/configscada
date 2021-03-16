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
 * The persistent class for the synoptic_layer_element_commands database table.
 * 
 */
@Entity
@Table(name="synoptic_layer_element_commands")
@NamedQuery(name="SynopticLayerElementCommand.findAll", query="SELECT s FROM SynopticLayerElementCommand s")
public class SynopticLayerElementCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@JsonIgnore
	@ManyToOne
	private SynopticLayerElement synopticLayerElement;
	
	@JsonIgnore
	@ManyToOne
	private SynopticCommand synopticCommand;
	

	public SynopticLayerElementCommand() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SynopticLayerElement getSynopticLayerElement() {
		return synopticLayerElement;
	}

	public void setSynopticLayerElement(SynopticLayerElement synopticLayerElement) {
		this.synopticLayerElement = synopticLayerElement;
	}

	public SynopticCommand getSynopticCommand() {
		return synopticCommand;
	}

	public void setSynopticCommand(SynopticCommand synopticCommand) {
		this.synopticCommand = synopticCommand;
	}

}