package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the synoptic_layers database table.
 * 
 */
@Entity
@Table(name="synoptic_layers")
@NamedQuery(name="SynopticLayer.findAll", query="SELECT s FROM SynopticLayer s")
public class SynopticLayer implements Serializable {
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

	//bi-directional many-to-one association to SynopticLayerElement
	@OneToMany(mappedBy="synopticLayer")
	private List<SynopticLayerElement> synopticLayerElements;

	//bi-directional many-to-one association to Synoptic
	@JsonIgnore
	@ManyToOne
	private Synoptic synoptic;

	public SynopticLayer() {
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

	public String getBaseLayer() {
		return this.baseLayer;
	}

	public void setBaseLayer(String baseLayer) {
		this.baseLayer = baseLayer;
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

	public List<SynopticLayerElement> getSynopticLayerElements() {
		return this.synopticLayerElements;
	}

	public void setSynopticLayerElements(List<SynopticLayerElement> synopticLayerElements) {
		this.synopticLayerElements = synopticLayerElements;
	}

	public SynopticLayerElement addSynopticLayerElement(SynopticLayerElement synopticLayerElement) {
		getSynopticLayerElements().add(synopticLayerElement);
		synopticLayerElement.setSynopticLayer(this);

		return synopticLayerElement;
	}

	public SynopticLayerElement removeSynopticLayerElement(SynopticLayerElement synopticLayerElement) {
		getSynopticLayerElements().remove(synopticLayerElement);
		synopticLayerElement.setSynopticLayer(null);

		return synopticLayerElement;
	}

	public Synoptic getSynoptic() {
		return this.synoptic;
	}

	public void setSynoptic(Synoptic synoptic) {
		this.synoptic = synoptic;
	}

}