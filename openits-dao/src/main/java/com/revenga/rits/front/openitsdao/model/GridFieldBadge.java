package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the grid_field_badges database table.
 * 
 */
@Entity
@Table(name="grid_field_badges")
@NamedQuery(name="GridFieldBadge.findAll", query="SELECT g FROM GridFieldBadge g")
public class GridFieldBadge implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String color;

	private String value;
	
	private Boolean enabled;
	
	//bi-directional many-to-one association to GridField
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="grid_field_id")
	private GridField gridField;

	public GridFieldBadge() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public GridField getGridField() {
		return this.gridField;
	}

	public void setGridField(GridField gridField) {
		this.gridField = gridField;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
