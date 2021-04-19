package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the equip_viewer_grp_measures database table.
 * 
 */
@Entity
@Table(name="equip_viewer_configs")
@NamedQuery(name="EquipViewerConfig.findAll", query="SELECT e FROM EquipViewerConfig e")
public class EquipViewerConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private Integer position;

	private Boolean visible;

	//uni-directional many-to-one association to EquipViewer
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="equip_viewer_id")
	private EquipViewer equipViewer;
		
	//uni-directional many-to-one association to VoEntity
	@ManyToOne
	@JoinColumn(name="vo_entities_id")
	private VoEntity voEntity;

	public EquipViewerConfig() {
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

	public EquipViewer getEquipViewer() {
		return equipViewer;
	}

	public void setEquipViewer(EquipViewer equipViewer) {
		this.equipViewer = equipViewer;
	}

	public VoEntity getVoEntity() {
		return this.voEntity;
	}

	public void setVoEntity(VoEntity voEntity) {
		this.voEntity = voEntity;
	}

}