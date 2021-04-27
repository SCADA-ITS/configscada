package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the maps database table.
 * 
 */
@Entity
@Table(name="maps")
@NamedQuery(name="Map.findAll", query="SELECT m FROM Map m")
public class Map implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String description;

	private String icon;

	private String label;

	private String name;

	private Float latitude;

	private Float longitude;

	private Integer zoom;

	//bi-directional many-to-one association to MapCommand
	@OneToMany(mappedBy="map")
	private List<MapCommand> mapCommands;

	//bi-directional many-to-one association to MapLayerRelationship
	@OneToMany(mappedBy="map")
	private List<MapLayerRelationship> mapLayerRelationships;

	//bi-directional many-to-one association to MapLayer
	@OneToMany(mappedBy="map")
	private List<MapLayer> mapLayers;

	public Map() {
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

	public List<MapCommand> getMapCommands() {
		return mapCommands;
	}

	public void setMapCommands(List<MapCommand> mapCommands) {
		this.mapCommands = mapCommands;
	}

	public List<MapLayerRelationship> getMapLayerRelationships() {
		return this.mapLayerRelationships;
	}

	public void setMapLayerRelationships(List<MapLayerRelationship> mapLayerRelationships) {
		this.mapLayerRelationships = mapLayerRelationships;
	}

	public MapLayerRelationship addMapLayerRelationship(MapLayerRelationship mapLayerRelationship) {
		getMapLayerRelationships().add(mapLayerRelationship);
		mapLayerRelationship.setMap(this);

		return mapLayerRelationship;
	}

	public MapLayerRelationship removeMapLayerRelationship(MapLayerRelationship mapLayerRelationship) {
		getMapLayerRelationships().remove(mapLayerRelationship);
		mapLayerRelationship.setMap(null);

		return mapLayerRelationship;
	}

	public List<MapLayer> getMapLayers() {
		return this.mapLayers;
	}

	public void setMapLayers(List<MapLayer> mapLayers) {
		this.mapLayers = mapLayers;
	}

	public MapLayer addMapLayer(MapLayer mapLayer) {
		getMapLayers().add(mapLayer);
		mapLayer.setMap(this);

		return mapLayer;
	}

	public MapLayer removeMapLayer(MapLayer mapLayer) {
		getMapLayers().remove(mapLayer);
		mapLayer.setMap(null);

		return mapLayer;
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

	public Integer getZoom() {
		return zoom;
	}

	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}

}