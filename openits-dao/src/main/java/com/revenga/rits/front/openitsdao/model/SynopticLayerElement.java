package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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

	@Column(name="horizontal_flip")
	private Boolean horizontalFlip;

	private String label;
	
	private Integer rotate;
	
	@Column(name="show_text")
	private Boolean showText;
	
	@Column(name="text_color")
	private String textColor;
	
	private Boolean tooltip;

	private Integer x;

	private Integer y;

	//uni-directional many-to-one association to GraphicIcon
	@ManyToOne
	@JoinColumn(name="graphic_icon_id")
	private GraphicIcon graphicIcon;

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

	public Integer getRotate() {
		return rotate;
	}

	public void setRotate(Integer rotate) {
		this.rotate = rotate;
	}

	public Boolean getShowText() {
		return showText;
	}

	public void setShowText(Boolean showText) {
		this.showText = showText;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public Boolean getTooltip() {
		return tooltip;
	}

	public void setTooltip(Boolean tooltip) {
		this.tooltip = tooltip;
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

	public GraphicIcon getGraphicIcon() {
		return this.graphicIcon;
	}

	public void setGraphicIcon(GraphicIcon graphicIcon) {
		this.graphicIcon = graphicIcon;
	}

	public SynopticLayer getSynopticLayer() {
		return this.synopticLayer;
	}

	public void setSynopticLayer(SynopticLayer synopticLayer) {
		this.synopticLayer = synopticLayer;
	}

}