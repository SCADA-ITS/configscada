package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the synoptic_commands database table.
 * 
 */
@Entity
@Table(name="synoptic_commands")
@NamedQuery(name="SynopticCommand.findAll", query="SELECT g FROM SynopticCommand g")
public class SynopticCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="default")
	private Boolean default_;

	private String icon;

	private String label;

	private Boolean multiselect;

	private Integer position;
	
	@Column(name="show_text")
	private Boolean showText;
	
	@Column(name="item_required")
	private Boolean itemRequired;

	//bi-directional many-to-one association to Synoptic
	@JsonIgnore
	@ManyToOne
	private Synoptic synoptic;

	//uni-directional many-to-one association to ModuleAction
	@ManyToOne
	private ModuleAction moduleAction;
	
	private String args;
	
	//uni-directional many-to-one association to ViewType
	@ManyToOne
	@JoinColumn(name="view_type_id")
	private ViewType viewType;

	public SynopticCommand() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getDefault_() {
		return this.default_;
	}

	public void setDefault_(Boolean default_) {
		this.default_ = default_;
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

	public Integer getPosition() {
		return this.position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Synoptic getSynoptic() {
		return this.synoptic;
	}

	public void setSynoptic(Synoptic synoptic) {
		this.synoptic = synoptic;
	}

	public ModuleAction getModuleAction() {
		return this.moduleAction;
	}

	public void setModuleAction(ModuleAction moduleAction) {
		this.moduleAction = moduleAction;
	}
	
	public String getArgs() {
		return this.args;
	}

	public void setArgs(String args) {
		this.args = args;
	}
	
	public ViewType getViewType() {
		return this.viewType;
	}

	public void setViewType(ViewType viewType) {
		this.viewType = viewType;
	}

	public Boolean getShowText() {
		return this.showText;
	}

	public void setShowText(Boolean showText) {
		this.showText = showText;
	}

	public Boolean getItemRequired() {
		return this.itemRequired;
	}

	public void setItemRequired(Boolean itemRequired) {
		this.itemRequired = itemRequired;
	}
}
