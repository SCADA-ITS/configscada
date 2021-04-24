package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the map_layers database table.
 * 
 */
@Entity
@Table(name="map_layers")
@NamedQuery(name="MapLayer.findAll", query="SELECT m FROM MapLayer m")
public class MapLayer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="base_layer")
	private String baseLayer;

	private String description;

	private String icon;

	private String label;

	private String name;

	private Boolean visible;

	//bi-directional many-to-one association to MapLayerElement
	@OneToMany(mappedBy="mapLayer")
	private List<MapLayerElement> mapLayerElements;

	//bi-directional many-to-one association to Map
	@JsonIgnore
	@ManyToOne
	private Map map;

	public MapLayer() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBaseLayer() {
		return this.baseLayer;
	}

	public void setBaseLayer(String baseLayer) {
		this.baseLayer = baseLayer;
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

	public Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public List<MapLayerElement> getMapLayerElements() {
		return this.mapLayerElements;
	}

	public void setMapLayerElements(List<MapLayerElement> mapLayerElements) {
		this.mapLayerElements = mapLayerElements;
	}

	public MapLayerElement addMapLayerElement(MapLayerElement mapLayerElement) {
		getMapLayerElements().add(mapLayerElement);
		mapLayerElement.setMapLayer(this);

		return mapLayerElement;
	}

	public MapLayerElement removeMapLayerElement(MapLayerElement mapLayerElement) {
		getMapLayerElements().remove(mapLayerElement);
		mapLayerElement.setMapLayer(null);

		return mapLayerElement;
	}

	public Map getMap() {
		return this.map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

}