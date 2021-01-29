package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the grid_option_values database table.
 * 
 */
@Entity
@Table(name="grid_option_values")
@NamedQuery(name="GridOptionValue.findAll", query="SELECT g FROM GridOptionValue g")
public class GridOptionValue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String value;

	//uni-directional many-to-one association to GridOption
	@ManyToOne
	@JoinColumn(name="grid_option_id")
	private GridOption gridOption;

	//bi-directional many-to-one association to Grid
	@JsonIgnore
	@ManyToOne
	private Grid grid;

	public GridOptionValue() {
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

	public GridOption getGridOption() {
		return this.gridOption;
	}

	public void setGridOption(GridOption gridOption) {
		this.gridOption = gridOption;
	}

	public Grid getGrid() {
		return this.grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

}