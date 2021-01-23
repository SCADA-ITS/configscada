package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the grid_field_data_types database table.
 * 
 */
@Entity
@Table(name="grid_field_data_types")
@NamedQuery(name="GridFieldDataType.findAll", query="SELECT g FROM GridFieldDataType g")
public class GridFieldDataType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String description;

	private String format;

	private String name;

	public GridFieldDataType() {
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

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}