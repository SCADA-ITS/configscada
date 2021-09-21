package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The persistent class for the map_commands database table.
 * 
 */
@Entity
@Table(name="map_commands")
@NamedQuery(name="MapCommand.findAll", query="SELECT g FROM MapCommand g")
public class MapCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="default")
	private Boolean default_;

	@ManyToOne
	private MapCommandGroup commandGroup;

	private Boolean context;

	private String icon;

	private String label;

	private Boolean multiselect;

	private Integer position;
	
	@Column(name="show_text")
	private Boolean showText;
	
	@Column(name="item_required")
	private Boolean itemRequired;

	//bi-directional many-to-one association to Map
	@JsonIgnore
	@ManyToOne
	private Map map;

	//uni-directional many-to-one association to ModuleAction
	@ManyToOne
	private ModuleAction moduleAction;
	
	private String args;
	
	//uni-directional many-to-one association to ViewType
	@ManyToOne
	@JoinColumn(name="view_type_id")
	private ViewType viewType;

	//bi-directional many-to-one association to MapElementTypeCommand
	@OneToMany(mappedBy="mapCommand")
	@JsonProperty("elementTypeCommand")
	private List<MapElementTypeCommand> mapElementTypeCommand;


	public MapCommand() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MapCommandGroup getCommandGroup() {
		return commandGroup;
	}

	public void setCommandGroup(MapCommandGroup commandGroup) {
		this.commandGroup = commandGroup;
	}

	public Boolean getContext() {
		return this.context;
	}

	public void setContext(Boolean context) {
		this.context = context;
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

	public Map getMap() {
		return this.map;
	}

	public void setMap(Map map) {
		this.map = map;
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

	public List<MapElementTypeCommand> getMapElementTypeCommand() {
		return mapElementTypeCommand;
	}

	public void setMapElementTypeCommand(List<MapElementTypeCommand> mapElementTypeCommand) {
		this.mapElementTypeCommand = mapElementTypeCommand;
	}
}
