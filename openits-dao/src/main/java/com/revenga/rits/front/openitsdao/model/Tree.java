package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the trees database table.
 * 
 */
@Entity
@Table(name="trees")
@NamedQuery(name="Tree.findAll", query="SELECT t FROM Tree t")
public class Tree implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private Boolean enabled;

	private String label;

	private Boolean visible;

	//bi-directional many-to-one association to TreeCommand
	@OneToMany(mappedBy="tree")
	private List<TreeCommand> treeCommands;
	
	//bi-directional many-to-one association to TreeOptionValue
	@OneToMany(mappedBy="tree")
	private List<TreeOptionValue> treeOptionValues;
	
	//uni-directional many-to-one association to Adapter
	@ManyToOne
	private Adapter adapter;

	public Tree() {
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

	public List<TreeCommand> getTreeCommands() {
		return this.treeCommands;
	}

	public void setTreeCommands(List<TreeCommand> treeCommands) {
		this.treeCommands = treeCommands;
	}

	public TreeCommand addTreeCommand(TreeCommand treeCommand) {
		getTreeCommands().add(treeCommand);
		treeCommand.setTree(this);

		return treeCommand;
	}

	public TreeCommand removeTreeCommand(TreeCommand treeCommand) {
		getTreeCommands().remove(treeCommand);
		treeCommand.setTree(null);

		return treeCommand;
	}
	
	public List<TreeOptionValue> getTreeOptionValues() {
		return this.treeOptionValues;
	}

	public void setTreeOptionValues(List<TreeOptionValue> treeOptionValues) {
		this.treeOptionValues = treeOptionValues;
	}
	
	public TreeOptionValue addTreeOptionValue(TreeOptionValue treeOptionValue) {
		getTreeOptionValues().add(treeOptionValue);
		treeOptionValue.setTree(this);

		return treeOptionValue;
	}

	public TreeOptionValue removeTreeOptionValue(TreeOptionValue treeOptionValue) {
		getTreeOptionValues().remove(treeOptionValue);
		treeOptionValue.setTree(null);

		return treeOptionValue;
	}
	
	public Adapter getAdapter() {
		return this.adapter;
	}

	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;
	}

}