package com.revenga.rits.back.virtual.equipment.manager.vms.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class TravelTime implements Serializable {

	private static final long serialVersionUID = -8450471093618058890L;
	
	private String name;
	private Integer time;
	
	public TravelTime(String name, Integer time) {
		super();
		this.name = name;
		this.time = time;
	}
}
