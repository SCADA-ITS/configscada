package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the vo_entities database table.
 * 
 */
@Entity
@Table(name="vo_entities")
@NamedQuery(name="VoEntity.findAll", query="SELECT v FROM VoEntity v")
public class VoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private Boolean enabled;

	private Boolean visible;

	//bi-directional many-to-one association to VoEntityType
	@ManyToOne
	@JoinColumn(name="vo_entity_type_id")
	private VoEntityType voEntityType;

	//bi-directional many-to-one association to VoEntityEnumType
	@OneToMany(mappedBy="voEntity")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<VoEntityEnumType> voEntityEnumTypes;

	//bi-directional one-to-one association to EquipViewer
	@JsonIgnore
	@OneToOne(mappedBy="voEntity")
	private EquipViewer equipViewer;
	
	//bi-directional many-to-one association to VoEntityValue
	@OneToMany(mappedBy="voEntity")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<VoEntityValue> voEntityValues;

	public VoEntity() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public VoEntityType getVoEntityType() {
		return this.voEntityType;
	}

	public void setVoEntityType(VoEntityType voEntityType) {
		this.voEntityType = voEntityType;
	}

	public Set<VoEntityEnumType> getVoEntityEnumTypes() {
		return this.voEntityEnumTypes;
	}

	public void setVoEntityEnumTypes(Set<VoEntityEnumType> voEntityEnumTypes) {
		this.voEntityEnumTypes = voEntityEnumTypes;
	}

	public VoEntityEnumType addVoEntityEnumType(VoEntityEnumType voEntityEnumType) {
		getVoEntityEnumTypes().add(voEntityEnumType);
		voEntityEnumType.setVoEntity(this);

		return voEntityEnumType;
	}

	public VoEntityEnumType removeVoEntityEnumType(VoEntityEnumType voEntityEnumType) {
		getVoEntityEnumTypes().remove(voEntityEnumType);
		voEntityEnumType.setVoEntity(null);

		return voEntityEnumType;
	}

	public Set<VoEntityValue> getVoEntityValues() {
		return this.voEntityValues;
	}

	public void setVoEntityValues(Set<VoEntityValue> voEntityValues) {
		this.voEntityValues = voEntityValues;
	}

	public VoEntityValue addVoEntityValue(VoEntityValue voEntityValue) {
		getVoEntityValues().add(voEntityValue);
		voEntityValue.setVoEntity(this);

		return voEntityValue;
	}

	public VoEntityValue removeVoEntityValue(VoEntityValue voEntityValue) {
		getVoEntityValues().remove(voEntityValue);
		voEntityValue.setVoEntity(null);

		return voEntityValue;
	}

	public EquipViewer getEquipViewer() {
		return equipViewer;
	}

	public void setEquipViewer(EquipViewer equipViewer) {
		this.equipViewer = equipViewer;
	}

}