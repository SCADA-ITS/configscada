package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the vo_enum_types database table.
 * 
 */
@Entity
@Table(name="vo_enum_types")
@NamedQuery(name="VoEnumType.findAll", query="SELECT v FROM VoEnumType v")
public class VoEnumType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String alias;

	private String description;

	private Boolean enabled;

	private Boolean visible;

	//bi-directional many-to-one association to VoEntityEnumType
	@JsonIgnore
	@OneToMany(mappedBy="voEnumType")
	private List<VoEntityEnumType> voEntityEnumTypes;

	//bi-directional many-to-one association to VoEnumTypeValue
	@OneToMany(mappedBy="voEnumType")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<VoEnumTypeValue> voEnumTypeValues;

	public VoEnumType() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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

	public List<VoEntityEnumType> getVoEntityEnumTypes() {
		return this.voEntityEnumTypes;
	}

	public void setVoEntityEnumTypes(List<VoEntityEnumType> voEntityEnumTypes) {
		this.voEntityEnumTypes = voEntityEnumTypes;
	}

	public VoEntityEnumType addVoEntityEnumType(VoEntityEnumType voEntityEnumType) {
		getVoEntityEnumTypes().add(voEntityEnumType);
		voEntityEnumType.setVoEnumType(this);

		return voEntityEnumType;
	}

	public VoEntityEnumType removeVoEntityEnumType(VoEntityEnumType voEntityEnumType) {
		getVoEntityEnumTypes().remove(voEntityEnumType);
		voEntityEnumType.setVoEnumType(null);

		return voEntityEnumType;
	}

	public Set<VoEnumTypeValue> getVoEnumTypeValues() {
		return this.voEnumTypeValues;
	}

	public void setVoEnumTypeValues(Set<VoEnumTypeValue> voEnumTypeValues) {
		this.voEnumTypeValues = voEnumTypeValues;
	}

	public VoEnumTypeValue addVoEnumTypeValue(VoEnumTypeValue voEnumTypeValue) {
		getVoEnumTypeValues().add(voEnumTypeValue);
		voEnumTypeValue.setVoEnumType(this);

		return voEnumTypeValue;
	}

	public VoEnumTypeValue removeVoEnumTypeValue(VoEnumTypeValue voEnumTypeValue) {
		getVoEnumTypeValues().remove(voEnumTypeValue);
		voEnumTypeValue.setVoEnumType(null);

		return voEnumTypeValue;
	}

}