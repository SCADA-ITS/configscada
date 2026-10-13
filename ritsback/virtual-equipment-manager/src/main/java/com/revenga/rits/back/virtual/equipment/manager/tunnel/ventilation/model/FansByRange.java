package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class FansByRange implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1327329860403486338L;
	private Long rangeId;
	private Integer fans;
	private Integer fansOtherTubes;
}
