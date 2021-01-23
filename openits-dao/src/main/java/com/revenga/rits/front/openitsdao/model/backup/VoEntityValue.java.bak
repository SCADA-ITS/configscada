package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the vo_entity_values database table.
 * 
 */
@Entity
@Table(name="vo_entity_values")
@NamedQuery(name="VoEntityValue.findAll", query="SELECT v FROM VoEntityValue v")
public class VoEntityValue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String value;

	//bi-directional many-to-one association to VoEntity
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="vo_entity_id")
	private VoEntity voEntity;

	//uni-directional many-to-one association to VoEntityField
	@ManyToOne
	@JoinColumn(name="vo_entity_field_id")
	private VoEntityField voEntityField;

	public VoEntityValue() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public VoEntity getVoEntity() {
		return this.voEntity;
	}

	public void setVoEntity(VoEntity voEntity) {
		this.voEntity = voEntity;
	}

	public VoEntityField getVoEntityField() {
		return this.voEntityField;
	}

	public void setVoEntityField(VoEntityField voEntityField) {
		this.voEntityField = voEntityField;
	}

}
