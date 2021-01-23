package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the vw_equips database table.
 * 
 */
@Entity
@Table(name="vw_equips")
@NamedQuery(name="VwEquip.findAll", query="SELECT v FROM VwEquip v")
public class VwEquip implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String description;

	private String icon;

	private String label;

	private String name;

	@Column(name="show_childs")
	private Boolean showChilds;

	@Column(name="show_main_tab")
	private Boolean showMainTab;

	//bi-directional many-to-one association to VwEquipGrpMeasure
	@OneToMany(mappedBy="vwEquip")
	private List<VwEquipGrpMeasure> vwEquipGrpMeasures;

	//bi-directional many-to-one association to VwEquipTab
	@OneToMany(mappedBy="vwEquip")
	private List<VwEquipTab> vwEquipTabs;

	//bi-directional many-to-one association to VoEntity
	@ManyToOne
	@JoinColumn(name="vo_entity_id")
	private VoEntity voEntity;

	public VwEquip() {
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

	public Boolean getShowChilds() {
		return this.showChilds;
	}

	public void setShowChilds(Boolean showChilds) {
		this.showChilds = showChilds;
	}

	public Boolean getShowMainTab() {
		return this.showMainTab;
	}

	public void setShowMainTab(Boolean showMainTab) {
		this.showMainTab = showMainTab;
	}

	public List<VwEquipGrpMeasure> getVwEquipGrpMeasures() {
		return this.vwEquipGrpMeasures;
	}

	public void setVwEquipGrpMeasures(List<VwEquipGrpMeasure> vwEquipGrpMeasures) {
		this.vwEquipGrpMeasures = vwEquipGrpMeasures;
	}

	public VwEquipGrpMeasure addVwEquipGrpMeasure(VwEquipGrpMeasure vwEquipGrpMeasure) {
		getVwEquipGrpMeasures().add(vwEquipGrpMeasure);
		vwEquipGrpMeasure.setVwEquip(this);

		return vwEquipGrpMeasure;
	}

	public VwEquipGrpMeasure removeVwEquipGrpMeasure(VwEquipGrpMeasure vwEquipGrpMeasure) {
		getVwEquipGrpMeasures().remove(vwEquipGrpMeasure);
		vwEquipGrpMeasure.setVwEquip(null);

		return vwEquipGrpMeasure;
	}

	public List<VwEquipTab> getVwEquipTabs() {
		return this.vwEquipTabs;
	}

	public void setVwEquipTabs(List<VwEquipTab> vwEquipTabs) {
		this.vwEquipTabs = vwEquipTabs;
	}

	public VwEquipTab addVwEquipTab(VwEquipTab vwEquipTab) {
		getVwEquipTabs().add(vwEquipTab);
		vwEquipTab.setVwEquip(this);

		return vwEquipTab;
	}

	public VwEquipTab removeVwEquipTab(VwEquipTab vwEquipTab) {
		getVwEquipTabs().remove(vwEquipTab);
		vwEquipTab.setVwEquip(null);

		return vwEquipTab;
	}

	public VoEntity getVoEntity() {
		return this.voEntity;
	}

	public void setVoEntity(VoEntity voEntity) {
		this.voEntity = voEntity;
	}

}