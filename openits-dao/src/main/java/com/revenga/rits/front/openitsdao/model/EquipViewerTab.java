package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the equip_viewer_tabs database table.
 * 
 */
@Entity
@Table(name="equip_viewer_tabs")
@NamedQuery(name="EquipViewerTab.findAll", query="SELECT e FROM EquipViewerTab e")
public class EquipViewerTab implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String description;

	private String icon;

	private String label;

	@Column(name="module_action_id")
	private Integer moduleActionId;

	private String name;

	private Integer position;

	private Boolean visible;

	//bi-directional many-to-one association to EquipViewer
	@ManyToOne
	@JoinColumn(name="equip_viewer_id")
	private EquipViewer equipViewer;

	public EquipViewerTab() {
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

	public Integer getModuleActionId() {
		return this.moduleActionId;
	}

	public void setModuleActionId(Integer moduleActionId) {
		this.moduleActionId = moduleActionId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPosition() {
		return this.position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public EquipViewer getEquipViewer() {
		return this.equipViewer;
	}

	public void setEquipViewer(EquipViewer equipViewer) {
		this.equipViewer = equipViewer;
	}

}