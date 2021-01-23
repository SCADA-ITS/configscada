package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;


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

	private String value;

	@Column(name="vo_enum_type_id")
	private String voEnumTypeId;

	public VoEnumTypeValue() {
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

	public String getVoEnumTypeId() {
		return this.voEnumTypeId;
	}

	public void setVoEnumTypeId(String voEnumTypeId) {
		this.voEnumTypeId = voEnumTypeId;
	}

}