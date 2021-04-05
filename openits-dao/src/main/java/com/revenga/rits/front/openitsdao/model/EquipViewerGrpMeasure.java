package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the equip_viewer_grp_measures database table.
 * 
 */
@Entity
@Table(name="equip_viewer_grp_measures")
@NamedQuery(name="EquipViewerGrpMeasure.findAll", query="SELECT e FROM EquipViewerGrpMeasure e")
public class EquipViewerGrpMeasure implements Serializable {
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

	//bi-directional many-to-one association to EquipViewer
	@ManyToOne
	@JoinColumn(name="equip_viewer_id")
	private EquipViewer equipViewer;

	//bi-directional many-to-one association to EquipViewerMeasure
	@OneToMany(mappedBy="equipViewerGrpMeasure")
	private List<EquipViewerMeasure> equipViewerMeasures;

	public EquipViewerGrpMeasure() {
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

	public EquipViewer getEquipViewer() {
		return this.equipViewer;
	}

	public void setEquipViewer(EquipViewer equipViewer) {
		this.equipViewer = equipViewer;
	}

	public List<EquipViewerMeasure> getEquipViewerMeasures() {
		return this.equipViewerMeasures;
	}

	public void setEquipViewerMeasures(List<EquipViewerMeasure> equipViewerMeasures) {
		this.equipViewerMeasures = equipViewerMeasures;
	}

	public EquipViewerMeasure addEquipViewerMeasure(EquipViewerMeasure equipViewerMeasure) {
		getEquipViewerMeasures().add(equipViewerMeasure);
		equipViewerMeasure.setEquipViewerGrpMeasure(this);

		return equipViewerMeasure;
	}

	public EquipViewerMeasure removeEquipViewerMeasure(EquipViewerMeasure equipViewerMeasure) {
		getEquipViewerMeasures().remove(equipViewerMeasure);
		equipViewerMeasure.setEquipViewerGrpMeasure(null);

		return equipViewerMeasure;
	}

}