package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the grid_field_references database table.
 * 
 */
@Entity
@Table(name="grid_field_references")
@NamedQuery(name="GridFieldReference.findAll", query="SELECT g FROM GridFieldReference g")
public class GridFieldReference implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="em_field")
	private String emField;

	@Column(name="em_field_returned")
	private String emFieldReturned;

	@Column(name="em_id")
	private String emId;

	private Boolean enabled;

	@Column(name="ref_field_id")
	private Integer refFieldId;

	//bi-directional many-to-one association to GridField
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="grid_field_id")
	private GridField gridField;

	public GridFieldReference() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmField() {
		return this.emField;
	}

	public void setEmField(String emField) {
		this.emField = emField;
	}

	public String getEmFieldReturned() {
		return this.emFieldReturned;
	}

	public void setEmFieldReturned(String emFieldReturned) {
		this.emFieldReturned = emFieldReturned;
	}

	public String getEmId() {
		return this.emId;
	}

	public void setEmId(String emId) {
		this.emId = emId;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getRefFieldId() {
		return this.refFieldId;
	}

	public void setRefFieldId(Integer refFieldId) {
		this.refFieldId = refFieldId;
	}

	public GridField getGridField() {
		return this.gridField;
	}

	public void setGridField(GridField gridField) {
		this.gridField = gridField;
	}

}
