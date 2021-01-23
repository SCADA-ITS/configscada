package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the vw_equip_grp_measures database table.
 * 
 */
@Entity
@Table(name="vw_equip_grp_measures")
@NamedQuery(name="VwEquipGrpMeasure.findAll", query="SELECT v FROM VwEquipGrpMeasure v")
public class VwEquipGrpMeasure implements Serializable {
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

	//bi-directional many-to-one association to VwEquip
	@ManyToOne
	@JoinColumn(name="vw_equip_id")
	private VwEquip vwEquip;

	//bi-directional many-to-one association to VwEquipMeasure
	@OneToMany(mappedBy="vwEquipGrpMeasure")
	private List<VwEquipMeasure> vwEquipMeasures;

	public VwEquipGrpMeasure() {
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

	public VwEquip getVwEquip() {
		return this.vwEquip;
	}

	public void setVwEquip(VwEquip vwEquip) {
		this.vwEquip = vwEquip;
	}

	public List<VwEquipMeasure> getVwEquipMeasures() {
		return this.vwEquipMeasures;
	}

	public void setVwEquipMeasures(List<VwEquipMeasure> vwEquipMeasures) {
		this.vwEquipMeasures = vwEquipMeasures;
	}

	public VwEquipMeasure addVwEquipMeasure(VwEquipMeasure vwEquipMeasure) {
		getVwEquipMeasures().add(vwEquipMeasure);
		vwEquipMeasure.setVwEquipGrpMeasure(this);

		return vwEquipMeasure;
	}

	public VwEquipMeasure removeVwEquipMeasure(VwEquipMeasure vwEquipMeasure) {
		getVwEquipMeasures().remove(vwEquipMeasure);
		vwEquipMeasure.setVwEquipGrpMeasure(null);

		return vwEquipMeasure;
	}

}