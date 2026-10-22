package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the vo_entity_enum_types database table.
 * 
 */
@Entity
@Table(name="vo_entity_enum_types")
@NamedQuery(name="VoEntityEnumType.findAll", query="SELECT v FROM VoEntityEnumType v")
public class VoEntityEnumType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	//bi-directional many-to-one association to VoEntity
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="vo_entity_id")
	private VoEntity voEntity;

	//bi-directional many-to-one association to VoEnumType
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="enum_type_id")
	private VoEnumType voEnumType;

	public VoEntityEnumType() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public VoEntity getVoEntity() {
		return this.voEntity;
	}

	public void setVoEntity(VoEntity voEntity) {
		this.voEntity = voEntity;
	}

	public VoEnumType getVoEnumType() {
		return this.voEnumType;
	}

	public void setVoEnumType(VoEnumType voEnumType) {
		this.voEnumType = voEnumType;
	}

}