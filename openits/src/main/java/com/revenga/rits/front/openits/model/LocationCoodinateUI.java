package com.revenga.rits.front.openits.model;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class LocationCoodinateUI implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -303594213430553680L;
	
	Double lat;
	Double lon;
	Integer position;
	
	public LocationCoodinateUI(Double lat, Double lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}
	
	public LocationCoodinateUI(Double lat, Double lon, Integer position) {
		super();
		this.lat = lat;
		this.lon = lon;
		this.position = position;
	}
}
