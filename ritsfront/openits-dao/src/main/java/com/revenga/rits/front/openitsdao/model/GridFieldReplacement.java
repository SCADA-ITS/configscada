package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the grid_field_replacements database table.
 * 
 */
@Entity
@Table(name="grid_field_replacements")
@NamedQuery(name="GridFieldReplacement.findAll", query="SELECT g FROM GridFieldReplacement g")
public class GridFieldReplacement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private Boolean enabled;

	private String label;

	private String value;

	//bi-directional many-to-one association to GridField
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="grid_field_id")
	private GridField gridField;

	public GridFieldReplacement() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public GridField getGridField() {
		return this.gridField;
	}

	public void setGridField(GridField gridField) {
		this.gridField = gridField;
	}

}
