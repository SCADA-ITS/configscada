package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the vw_equip_tabs database table.
 * 
 */
@Entity
@Table(name="vw_equip_tabs")
@NamedQuery(name="VwEquipTab.findAll", query="SELECT v FROM VwEquipTab v")
public class VwEquipTab implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String description;

	private String icon;

	private String label;

	private String name;

	private Integer position;

	private Boolean visible;

	//bi-directional many-to-one association to ModuleAction
	@ManyToOne
	@JoinColumn(name="module_action_id")
	private ModuleAction moduleAction;

	//bi-directional many-to-one association to VwEquip
	@ManyToOne
	@JoinColumn(name="vw_equip_id")
	private VwEquip vwEquip;

	public VwEquipTab() {
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

	public ModuleAction getModuleAction() {
		return this.moduleAction;
	}

	public void setModuleAction(ModuleAction moduleAction) {
		this.moduleAction = moduleAction;
	}

	public VwEquip getVwEquip() {
		return this.vwEquip;
	}

	public void setVwEquip(VwEquip vwEquip) {
		this.vwEquip = vwEquip;
	}

}