package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the synoptic_layer_elements database table.
 * 
 */
@Entity
@Table(name="synoptic_layer_elements")
@NamedQuery(name="SynopticLayerElement.findAll", query="SELECT s FROM SynopticLayerElement s")
public class SynopticLayerElement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="element_id")
	private String elementId;

	private Integer height;

	@Column(name="horizontal_flip")
	private Boolean horizontalFlip;

	private String label;

	private Integer width;

	private Integer x;

	private Integer y;

	//uni-directional many-to-one association to CustomGraphicEquipment
	@ManyToOne
	@JoinColumn(name="custom_graphic_equipment_id")
	private CustomGraphicEquipment customGraphicEquipment;

	//bi-directional many-to-one association to SynopticLayer
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="layer_id")
	private SynopticLayer synopticLayer;

	public SynopticLayerElement() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getElementId() {
		return this.elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Boolean getHorizontalFlip() {
		return this.horizontalFlip;
	}

	public void setHorizontalFlip(Boolean horizontalFlip) {
		this.horizontalFlip = horizontalFlip;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getX() {
		return this.x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return this.y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public CustomGraphicEquipment getCustomGraphicEquipment() {
		return this.customGraphicEquipment;
	}

	public void setCustomGraphicEquipment(CustomGraphicEquipment customGraphicEquipment) {
		this.customGraphicEquipment = customGraphicEquipment;
	}

	public SynopticLayer getSynopticLayer() {
		return this.synopticLayer;
	}

	public void setSynopticLayer(SynopticLayer synopticLayer) {
		this.synopticLayer = synopticLayer;
	}

}