package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the vo_entity_fields database table.
 * 
 */
@Entity
@Table(name="vo_entity_fields")
@NamedQuery(name="VoEntityField.findAll", query="SELECT v FROM VoEntityField v")
public class VoEntityField implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String description;

	private Boolean enabled;

	private String field;

	@Column(name="label_description")
	private String labelDescription;

	@Column(name="label_field")
	private String labelField;

	private Boolean visible;

	//bi-directional many-to-one association to VoEntityType
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="vo_entity_type_id")
	private VoEntityType voEntityType;

	//bi-directional many-to-one association to VoEntityValue
	@JsonIgnore
	@OneToMany(mappedBy="voEntityField")
	private List<VoEntityValue> voEntityValues;

	public VoEntityField() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getLabelDescription() {
		return this.labelDescription;
	}

	public void setLabelDescription(String labelDescription) {
		this.labelDescription = labelDescription;
	}

	public String getLabelField() {
		return this.labelField;
	}

	public void setLabelField(String labelField) {
		this.labelField = labelField;
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
		voEntityValue.setVoEntityField(this);

		return voEntityValue;
	}

	public VoEntityValue removeVoEntityValue(VoEntityValue voEntityValue) {
		getVoEntityValues().remove(voEntityValue);
		voEntityValue.setVoEntityField(null);

		return voEntityValue;
	}

}