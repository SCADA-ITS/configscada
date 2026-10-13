package com.revenga.rits.back.virtual.equipment.manager.vms.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class VmsMessageZoneText implements Serializable {

	private static final long serialVersionUID = 849936615493297041L;
	
	private Integer id;
	private String value;
	private String alternance;
	private String color;
}
