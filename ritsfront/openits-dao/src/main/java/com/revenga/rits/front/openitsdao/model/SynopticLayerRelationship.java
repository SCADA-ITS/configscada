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
	private Integer parentLayerId;

	@Column(name="child_layer_id")
	private Integer childLayerId;

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

	public Integer getParentLayerId() {
		return parentLayerId;
	}

	public void setParentLayerId(Integer parentLayerId) {
		this.parentLayerId = parentLayerId;
	}

	public Integer getChildLayerId() {
		return childLayerId;
	}

	public void setChildLayerId(Integer childLayerId) {
		this.childLayerId = childLayerId;
	}

	public Synoptic getSynoptic() {
		return this.synoptic;
	}

	public void setSynoptic(Synoptic synoptic) {
		this.synoptic = synoptic;
	}

}