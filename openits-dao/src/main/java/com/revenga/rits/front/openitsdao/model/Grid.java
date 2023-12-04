package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the grids database table.
 * 
 */
@Entity
@Table(name="grids")
@NamedQuery(name="Grid.findAll", query="SELECT g FROM Grid g")
public class Grid implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private Boolean enabled;

	private String label;

	private Boolean visible;

	//bi-directional many-to-one association to GridCommand
	@OneToMany(mappedBy="grid", fetch=FetchType.EAGER)
	private Set<GridCommand> gridCommands;

	//bi-directional many-to-one association to GridField
	@OneToMany(mappedBy="grid", fetch=FetchType.EAGER)
	private Set<GridField> gridFields;

	//bi-directional many-to-one association to GridOptionValue
	@OneToMany(mappedBy="grid", fetch=FetchType.EAGER)
	private Set<GridOptionValue> gridOptionValues;

	//uni-directional many-to-one association to Adapter
	@ManyToOne
	private Adapter adapter;

	public Grid() {
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

	public Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Set<GridCommand> getGridCommands() {
		return this.gridCommands;
	}

	public void setGridCommands(Set<GridCommand> gridCommands) {
		this.gridCommands = gridCommands;
	}

	public GridCommand addGridCommand(GridCommand gridCommand) {
		getGridCommands().add(gridCommand);
		gridCommand.setGrid(this);

		return gridCommand;
	}

	public GridCommand removeGridCommand(GridCommand gridCommand) {
		getGridCommands().remove(gridCommand);
		gridCommand.setGrid(null);

		return gridCommand;
	}

	public Set<GridField> getGridFields() {
		return this.gridFields;
	}

	public void setGridFields(Set<GridField> gridFields) {
		this.gridFields = gridFields;
	}

	public GridField addGridField(GridField gridField) {
		getGridFields().add(gridField);
		gridField.setGrid(this);

		return gridField;
	}

	public GridField removeGridField(GridField gridField) {
		getGridFields().remove(gridField);
		gridField.setGrid(null);

		return gridField;
	}

	public Set<GridOptionValue> getGridOptionValues() {
		return this.gridOptionValues;
	}

	public void setGridOptionValues(Set<GridOptionValue> gridOptionValues) {
		this.gridOptionValues = gridOptionValues;
	}

	public GridOptionValue addGridOptionValue(GridOptionValue gridOptionValue) {
		getGridOptionValues().add(gridOptionValue);
		gridOptionValue.setGrid(this);

		return gridOptionValue;
	}

	public GridOptionValue removeGridOptionValue(GridOptionValue gridOptionValue) {
		getGridOptionValues().remove(gridOptionValue);
		gridOptionValue.setGrid(null);

		return gridOptionValue;
	}

	public Adapter getAdapter() {
		return this.adapter;
	}

	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;
	}

}