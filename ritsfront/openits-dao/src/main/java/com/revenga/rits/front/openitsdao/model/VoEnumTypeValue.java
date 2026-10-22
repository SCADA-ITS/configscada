package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the vo_enum_type_values database table.
 * 
 */
@Entity
@Table(name="vo_enum_type_values")
@NamedQuery(name="VoEnumTypeValue.findAll", query="SELECT v FROM VoEnumTypeValue v")
public class VoEnumTypeValue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String key;

	private Boolean translate;

	private String value;
	
	private String image;

	//bi-directional many-to-one association to VoEnumType
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="vo_enum_type_id")
	private VoEnumType voEnumType;

	public VoEnumTypeValue() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Boolean getTranslate() {
		return this.translate;
	}

	public void setTranslate(Boolean translate) {
		this.translate = translate;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public VoEnumType getVoEnumType() {
		return this.voEnumType;
	}

	public void setVoEnumType(VoEnumType voEnumType) {
		this.voEnumType = voEnumType;
	}
	
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}