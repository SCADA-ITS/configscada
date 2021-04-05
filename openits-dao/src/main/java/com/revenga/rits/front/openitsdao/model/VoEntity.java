package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


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

	//uni-directional many-to-one association to VoEntityType
	@ManyToOne
	@JoinColumn(name="vo_entity_type_id")
	private VoEntityType voEntityType;

	//bi-directional many-to-one association to VoEntityValue
	@OneToMany(mappedBy="voEntity", fetch = FetchType.EAGER)
	private List<VoEntityValue> voEntityValues;

	//bi-directional one-to-one association to EquipViewer
	@JsonIgnore
	@OneToOne(mappedBy="voEntity")
	private EquipViewer equipViewer;

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

	public List<VoEntityValue> getVoEntityValues() {
		return this.voEntityValues;
	}

	public void setVoEntityValues(List<VoEntityValue> voEntityValues) {
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
		return this.equipViewer;
	}

	public void setEquipViewer(EquipViewer equipViewer) {
		this.equipViewer = equipViewer;
	}

}