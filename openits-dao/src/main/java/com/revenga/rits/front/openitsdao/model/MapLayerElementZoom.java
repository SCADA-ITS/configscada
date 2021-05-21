package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the map_layer_element_zooms database table.
 * 
 */
@Entity
@Table(name="map_layer_element_zooms")
@NamedQuery(name="MapLayerElementZoom.findAll", query="SELECT m FROM MapLayerElementZoom m")
public class MapLayerElementZoom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="horizontal_flip")
	private Boolean horizontalFlip;

	@Column(name="zoom_lower")
	private Integer zoomLower;

	@Column(name="zoom_upper")
	private Integer zoomUpper;

	//bi-directional many-to-one association to GraphicIcon
	@ManyToOne
	@JoinColumn(name="graphic_icon_id")
	private GraphicIcon graphicIcon;

	private Float latitude;

	private Float longitude;
	
	private Integer rotate;
	
	@Column(name="show_text")
	private Boolean showText;
	
	@Column(name="text_color")
	private String textColor;

	//bi-directional many-to-one association to MapLayerElement
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="map_layer_element_id")
	private MapLayerElement mapLayerElement;

	public MapLayerElementZoom() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getHorizontalFlip() {
		return this.horizontalFlip;
	}

	public void setHorizontalFlip(Boolean horizontalFlip) {
		this.horizontalFlip = horizontalFlip;
	}

	public Integer getZoomLower() {
		return this.zoomLower;
	}

	public void setZoomLower(Integer zoomLower) {
		this.zoomLower = zoomLower;
	}

	public Integer getZoomUpper() {
		return this.zoomUpper;
	}

	public void setZoomUpper(Integer zoomUpper) {
		this.zoomUpper = zoomUpper;
	}

	public GraphicIcon getGraphicIcon() {
		return this.graphicIcon;
	}

	public void setGraphicIcon(GraphicIcon graphicIcon) {
		this.graphicIcon = graphicIcon;
	}
	
	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
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

	public MapLayerElement getMapLayerElement() {
		return this.mapLayerElement;
	}

	public void setMapLayerElement(MapLayerElement mapLayerElement) {
		this.mapLayerElement = mapLayerElement;
	}

}