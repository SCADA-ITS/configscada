package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The persistent class for the maps database table.
 * 
 */
@Entity
@Table(name = "maps")
@NamedQuery(name = "Map.findAll", query = "SELECT m FROM Map m")
public class Map implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="custom_provider_url")
	private String customProviderUrl;
	
	@Column(name="default_zoom_search")
	private Integer defaultZoomSearch;

	private String description;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
	private MapIncident mapIncident;
	
	private String icon;

	private String label;

	private String name;

	private Float latitude;
	
	@Column(name="limit_end_lon")
	private Float limitEndLon;
	
	@Column(name="limit_end_lat")
	private Float limitEndLat;
	
	@Column(name="limit_init_lon")
	private Float limitInitLon;
	
	@Column(name="limit_init_lat")
	private Float limitInitLat;

	private Float longitude;
	
	@Column(name="dark_mode")
	private Boolean darkMode;

	private Integer zoom;
	
	@Column(name="zoom_max")
	private Integer zoomMax;
	
	@Column(name="zoom_min")
	private Integer zoomMin;

	// bi-directional many-to-one association to MapCommand
	@OneToMany(mappedBy = "map")
	@JsonProperty("commands")
	private List<MapCommand> mapCommands;

	// bi-directional many-to-one association to MapLayerRelationship
	@OneToMany(mappedBy = "map")
	@JsonProperty("layerRelationships")
	private List<MapLayerRelationship> mapLayerRelationships;

	// bi-directional many-to-one association to MapLayer
	@OneToMany(mappedBy = "map")
	@JsonProperty("layers")
	private List<MapLayer> mapLayers;

	// bi-directional many-to-one association to MapZone
	@OneToMany(mappedBy = "map")
	private List<MapZone> mapZones;

	public Map() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomProviderUrl() {
		return customProviderUrl;
	}

	public void setCustomProviderUrl(String customProviderUrl) {
		this.customProviderUrl = customProviderUrl;
	}

	public Integer getDefaultZoomSearch() {
		return defaultZoomSearch;
	}

	public void setDefaultZoomSearch(Integer defaultZoomSearch) {
		this.defaultZoomSearch = defaultZoomSearch;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MapIncident getMapIncident() {
		return mapIncident;
	}

	public void setMapIncident(MapIncident mapIncident) {
		this.mapIncident = mapIncident;
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

	public Float getLimitEndLon() {
		return limitEndLon;
	}

	public void setLimitEndLon(Float limitEndLon) {
		this.limitEndLon = limitEndLon;
	}

	public Float getLimitEndLat() {
		return limitEndLat;
	}

	public void setLimitEndLat(Float limitEndLat) {
		this.limitEndLat = limitEndLat;
	}

	public Float getLimitInitLon() {
		return limitInitLon;
	}

	public void setLimitInitLon(Float limitInitLon) {
		this.limitInitLon = limitInitLon;
	}

	public Float getLimitInitLat() {
		return limitInitLat;
	}

	public void setLimitInitLat(Float limitInitLat) {
		this.limitInitLat = limitInitLat;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Boolean getDarkMode() {
		return darkMode;
	}

	public void setDarkMode(Boolean darkMode) {
		this.darkMode = darkMode;
	}

	public Integer getZoom() {
		return zoom;
	}

	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}
	
	public Integer getZoomMax() {
		return zoomMax;
	}

	public void setZoomMax(Integer zoomMax) {
		this.zoomMax = zoomMax;
	}

	public Integer getZoomMin() {
		return zoomMin;
	}

	public void setZoomMin(Integer zoomMin) {
		this.zoomMin = zoomMin;
	}

	public List<MapZone> getMapZones() {
		return this.mapZones;
	}

	public void setMapZones(List<MapZone> mapZones) {
		this.mapZones = mapZones;
	}

	public MapZone addMapLayer(MapZone mapZone) {
		getMapZones().add(mapZone);
		mapZone.setMap(this);

		return mapZone;
	}

	public MapZone removeMapLayer(MapZone mapZone) {
		getMapZones().remove(mapZone);
		mapZone.setMap(null);

		return mapZone;
	}

}