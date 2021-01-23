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

	@Column(name="adapter_id")
	private Integer adapterId;

	@Column(name="cmd_buttons")
	private Boolean cmdButtons;

	private Boolean enabled;

	private String icon;

	private String label;

	private Boolean multiselect;

	private Boolean search;

	private Boolean sorted;

	private Boolean visible;

	//bi-directional many-to-one association to TreeCommand
	@OneToMany(mappedBy="tree")
	private List<TreeCommand> treeCommands;

	public Tree() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAdapterId() {
		return this.adapterId;
	}

	public void setAdapterId(Integer adapterId) {
		this.adapterId = adapterId;
	}

	public Boolean getCmdButtons() {
		return this.cmdButtons;
	}

	public void setCmdButtons(Boolean cmdButtons) {
		this.cmdButtons = cmdButtons;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getMultiselect() {
		return this.multiselect;
	}

	public void setMultiselect(Boolean multiselect) {
		this.multiselect = multiselect;
	}

	public Boolean getSearch() {
		return this.search;
	}

	public void setSearch(Boolean search) {
		this.search = search;
	}

	public Boolean getSorted() {
		return this.sorted;
	}

	public void setSorted(Boolean sorted) {
		this.sorted = sorted;
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

}