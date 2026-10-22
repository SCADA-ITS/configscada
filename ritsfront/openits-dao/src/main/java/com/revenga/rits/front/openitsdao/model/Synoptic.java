package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The persistent class for the synoptics database table.
 * 
 */
@Entity
@Table(name="synoptics")
@NamedQuery(name="Synoptic.findAll", query="SELECT s FROM Synoptic s")
public class Synoptic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="base_layer")
	private String baseLayer;

	private String description;

	@Column(name="exclusive_layers")
	private Boolean exclusiveLayers;

	private String icon;

	private String label;

	private String name;

	//bi-directional many-to-one association to SynopticCommand
	@OneToMany(mappedBy="synoptic")
	@JsonProperty("commands")
	private List<SynopticCommand> synopticCommands;

	//bi-directional many-to-one association to SynopticLayerRelationship
	@OneToMany(mappedBy="synoptic")
	@JsonProperty("layerRelationships")
	private List<SynopticLayerRelationship> synopticLayerRelationships;

	//bi-directional many-to-one association to SynopticLayer
	@OneToMany(mappedBy="synoptic")
	@JsonProperty("layers")
	private List<SynopticLayer> synopticLayers;

	public Synoptic() {
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

	public Boolean getExclusiveLayers() {
		return this.exclusiveLayers;
	}

	public void setExclusiveLayers(Boolean exclusiveLayers) {
		this.exclusiveLayers = exclusiveLayers;
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

	public List<SynopticCommand> getSynopticCommands() {
		return this.synopticCommands;
	}

	public void setSynopticCommands(List<SynopticCommand> synopticCommands) {
		this.synopticCommands = synopticCommands;
	}

	public List<SynopticLayerRelationship> getSynopticLayerRelationships() {
		return this.synopticLayerRelationships;
	}

	public void setSynopticLayerRelationships(List<SynopticLayerRelationship> synopticLayerRelationships) {
		this.synopticLayerRelationships = synopticLayerRelationships;
	}
	

	public SynopticLayerRelationship addSynopticLayerRelationship(SynopticLayerRelationship synopticLayerRelationship) {
		getSynopticLayerRelationships().add(synopticLayerRelationship);
		synopticLayerRelationship.setSynoptic(this);

		return synopticLayerRelationship;
	}

	public SynopticLayerRelationship removeSynopticLayerRelationship(SynopticLayerRelationship synopticLayerRelationship) {
		getSynopticLayerRelationships().remove(synopticLayerRelationship);
		synopticLayerRelationship.setSynoptic(null);

		return synopticLayerRelationship;
	}

	public List<SynopticLayer> getSynopticLayers() {
		return this.synopticLayers;
	}

	public void setSynopticLayers(List<SynopticLayer> synopticLayers) {
		this.synopticLayers = synopticLayers;
	}

	public SynopticLayer addSynopticLayer(SynopticLayer synopticLayer) {
		getSynopticLayers().add(synopticLayer);
		synopticLayer.setSynoptic(this);

		return synopticLayer;
	}

	public SynopticLayer removeSynopticLayer(SynopticLayer synopticLayer) {
		getSynopticLayers().remove(synopticLayer);
		synopticLayer.setSynoptic(null);

		return synopticLayer;
	}

}