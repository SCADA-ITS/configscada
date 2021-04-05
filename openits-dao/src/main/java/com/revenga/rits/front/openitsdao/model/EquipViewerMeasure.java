package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the equip_viewer_measures database table.
 * 
 */
@Entity
@Table(name="equip_viewer_measures")
@NamedQuery(name="EquipViewerMeasure.findAll", query="SELECT e FROM EquipViewerMeasure e")
public class EquipViewerMeasure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private Integer position;

	private Boolean visible;

	//bi-directional many-to-one association to EquipViewerGrpMeasure
	@ManyToOne
	@JoinColumn(name="equip_viewer_grp_measure_id")
	private EquipViewerGrpMeasure equipViewerGrpMeasure;

	//uni-directional many-to-one association to VoEntity
	@ManyToOne
	@JoinColumn(name="vo_entities_id")
	private VoEntity voEntity;

	public EquipViewerMeasure() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public EquipViewerGrpMeasure getEquipViewerGrpMeasure() {
		return this.equipViewerGrpMeasure;
	}

	public void setEquipViewerGrpMeasure(EquipViewerGrpMeasure equipViewerGrpMeasure) {
		this.equipViewerGrpMeasure = equipViewerGrpMeasure;
	}

	public VoEntity getVoEntity() {
		return this.voEntity;
	}

	public void setVoEntity(VoEntity voEntity) {
		this.voEntity = voEntity;
	}

}