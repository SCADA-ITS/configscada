package com.revenga.rits.back.data.core.dao.impl;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JacksonXmlRootElement(localName = "DML")
public class MapperDML implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -264922448096159775L;

	public static final String ENABLED = "true";
	
	@JacksonXmlProperty(isAttribute = true)
	String enabled;
	
	@JacksonXmlProperty(isAttribute = true)
	String model;
	
	@JsonProperty("SELECT")
	String select;
	@JsonProperty("INSERT")
	String insert;
	@JsonProperty("UPDATE")
	String update;
	@JsonProperty("DELETE")
	String delete;

	public MapperDML(MapperDML dml) {
		this.enabled = dml.enabled;
		this.model = dml.model;
		this.select = dml.select;
		this.insert = dml.insert;
		this.update = dml.update;
		this.delete = dml.delete;
	}
}