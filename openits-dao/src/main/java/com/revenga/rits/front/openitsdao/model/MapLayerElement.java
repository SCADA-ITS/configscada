package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


/**
 * The persistent class for the map_layer_elements database table.
 * 
 */
@Entity
@Table(name="map_layer_elements")
@NamedQuery(name="MapLayerElement.findAll", query="SELECT m FROM MapLayerElement m")
public class MapLayerElement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="element_id")
	private String elementId;

	@Column(name="horizontal_flip")
	private Boolean horizontalFlip;

	private String label;

	private Float latitude;

	private Float longitude;
	
	@Column(name="latitude_2")
	private Float latitude2;

	@Column(name="longitude_2")
	private Float longitude2;
	
	private Integer rotate;
	
	private Boolean selectable;
	
	@Column(name="show_text")
	private Boolean showText;
	
	@Column(name="text_color")
	private String textColor;
	
	@Column(name="text_pos")
	private Integer textPos;
	
	@Column(name="text_size")
	private Integer textSize;
	
	private Boolean tooltip;
	
	@Column(name="z_index_front")
	private Integer zIndexFront;

	//bi-directional many-to-one association to MapLayerElementZoom
	@OneToMany(mappedBy="mapLayerElement")
	@JsonProperty("layerElementZooms")
	private List<MapLayerElementZoom> mapLayerElementZooms;

	//uni-directional many-to-one association to GraphicIcon
	@ManyToOne
	@JoinColumn(name="graphic_icon_id")
	private GraphicIcon graphicIcon;

	//bi-directional many-to-one association to MapLayer
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="layer_id")
	private MapLayer mapLayer;

	public MapLayerElement() {
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
	
	public Float getLatitude2() {
		return latitude2;
	}

	public void setLatitude2(Float latitude2) {
		this.latitude2 = latitude2;
	}

	public Float getLongitude2() {
		return longitude2;
	}

	public void setLongitude2(Float longitude2) {
		this.longitude2 = longitude2;
	}

	public Integer getRotate() {
		return rotate;
	}

	public void setRotate(Integer rotate) {
		this.rotate = rotate;
	}

	public Boolean getSelectable() {
		return selectable;
	}

	public void setSelectable(Boolean selectable) {
		this.selectable = selectable;
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

	public Integer getTextPos() {
		return textPos;
	}

	public void setTextPos(Integer textPos) {
		this.textPos = textPos;
	}

	public Integer getTextSize() {
		return textSize;
	}

	public void setTextSize(Integer textSize) {
		this.textSize = textSize;
	}

	public Boolean getTooltip() {
		return tooltip;
	}

	public void setTooltip(Boolean tooltip) {
		this.tooltip = tooltip;
	}

	public Integer getzIndexFront() {
		return zIndexFront;
	}

	public void setzIndexFront(Integer zIndexFront) {
		this.zIndexFront = zIndexFront;
	}

	public List<MapLayerElementZoom> getMapLayerElementZooms() {
		return this.mapLayerElementZooms;
	}

	public void setMapLayerElementZooms(List<MapLayerElementZoom> mapLayerElementZooms) {
		this.mapLayerElementZooms = mapLayerElementZooms;
	}

	public MapLayerElementZoom addMapLayerElementZoom(MapLayerElementZoom mapLayerElementZoom) {
		getMapLayerElementZooms().add(mapLayerElementZoom);
		mapLayerElementZoom.setMapLayerElement(this);

		return mapLayerElementZoom;
	}

	public MapLayerElementZoom removeMapLayerElementZoom(MapLayerElementZoom mapLayerElementZoom) {
		getMapLayerElementZooms().remove(mapLayerElementZoom);
		mapLayerElementZoom.setMapLayerElement(null);

		return mapLayerElementZoom;
	}

	public GraphicIcon getGraphicIcon() {
		return this.graphicIcon;
	}

	public void setGraphicIcon(GraphicIcon graphicIcon) {
		this.graphicIcon = graphicIcon;
	}

	public MapLayer getMapLayer() {
		return this.mapLayer;
	}

	public void setMapLayer(MapLayer mapLayer) {
		this.mapLayer = mapLayer;
	}

}