package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the vo_entity_hierarchies database table.
 * 
 */
@Entity
@Table(name="vo_entity_hierarchies")
@NamedQuery(name="VoEntityHierarchy.findAll", query="SELECT v FROM VoEntityHierarchy v")
public class VoEntityHierarchy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private Integer position;

	//bi-directional many-to-one association to VoEntity
	@ManyToOne
	@JoinColumn(name="child_id")
	private VoEntity voEntityChild;

	//bi-directional many-to-one association to VoEntity
	@ManyToOne
	@JoinColumn(name="parent_id")
	private VoEntity voEntityParent;

	public VoEntityHierarchy() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPosition() {
		return this.position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public VoEntity getVoEntityChild() {
		return this.voEntityChild;
	}

	public void setVoEntityChild(VoEntity voEntityChild) {
		this.voEntityChild = voEntityChild;
	}

	public VoEntity getVoEntityParent() {
		return this.voEntityParent;
	}

	public void setVoEntityParent(VoEntity voEntityParent) {
		this.voEntityParent = voEntityParent;
	}

}