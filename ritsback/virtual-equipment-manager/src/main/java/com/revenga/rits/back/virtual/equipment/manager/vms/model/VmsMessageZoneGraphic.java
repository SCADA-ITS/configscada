package com.revenga.rits.back.virtual.equipment.manager.vms.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class VmsMessageZoneGraphic implements Serializable {

	private static final long serialVersionUID = -6604408426604804213L;
	
	private Integer id;
	private Integer value;
	private Integer alternance;

}
