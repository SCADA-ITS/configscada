package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the vo_entity_types database table.
 * 
 */
@Entity
@Table(name="vo_entity_types")
@NamedQuery(name="VoEntityType.findAll", query="SELECT v FROM VoEntityType v")
public class VoEntityType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private String alias;

	private String description;

	private Boolean enabled;

	private Boolean visible;

	//bi-directional many-to-one association to VoEntity
	@JsonIgnore
	@OneToMany(mappedBy="voEntityType")
	private List<VoEntity> voEntities;

	//bi-directional many-to-one association to VoEntityField
	@OneToMany(mappedBy="voEntityType")
	private List<VoEntityField> voEntityFields;

	public VoEntityType() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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

	public Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public List<VoEntity> getVoEntities() {
		return this.voEntities;
	}

	public void setVoEntities(List<VoEntity> voEntities) {
		this.voEntities = voEntities;
	}

	public VoEntity addVoEntity(VoEntity voEntity) {
		getVoEntities().add(voEntity);
		voEntity.setVoEntityType(this);

		return voEntity;
	}

	public VoEntity removeVoEntity(VoEntity voEntity) {
		getVoEntities().remove(voEntity);
		voEntity.setVoEntityType(null);

		return voEntity;
	}

	public List<VoEntityField> getVoEntityFields() {
		return this.voEntityFields;
	}

	public void setVoEntityFields(List<VoEntityField> voEntityFields) {
		this.voEntityFields = voEntityFields;
	}

	public VoEntityField addVoEntityField(VoEntityField voEntityField) {
		getVoEntityFields().add(voEntityField);
		voEntityField.setVoEntityType(this);

		return voEntityField;
	}

	public VoEntityField removeVoEntityField(VoEntityField voEntityField) {
		getVoEntityFields().remove(voEntityField);
		voEntityField.setVoEntityType(null);

		return voEntityField;
	}

}