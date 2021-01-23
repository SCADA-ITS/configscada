package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the synoptic_layer_relationships database table.
 * 
 */
@Entity
@Table(name="synoptic_layer_relationships")
@NamedQuery(name="SynopticLayerRelationship.findAll", query="SELECT s FROM SynopticLayerRelationship s")
public class SynopticLayerRelationship implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="parent_layer_id")
	private Integer parent_layer_id;

	//@Column(name="child_layer_id")
	private Integer child_layer_id;

	//bi-directional many-to-one association to Synoptic
	@JsonIgnore
	@ManyToOne
	private Synoptic synoptic;

	public SynopticLayerRelationship() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParent_layer_id() {
		return parent_layer_id;
	}

	public void setParent_layer_id(Integer parent_layer_id) {
		this.parent_layer_id = parent_layer_id;
	}

	public Integer getChild_layer_id() {
		return child_layer_id;
	}

	public void setChild_layer_id(Integer child_layer_id) {
		this.child_layer_id = child_layer_id;
	}

	public Synoptic getSynoptic() {
		return this.synoptic;
	}

	public void setSynoptic(Synoptic synoptic) {
		this.synoptic = synoptic;
	}

}