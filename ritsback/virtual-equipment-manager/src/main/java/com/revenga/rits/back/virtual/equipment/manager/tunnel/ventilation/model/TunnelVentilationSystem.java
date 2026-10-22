package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model;

import lombok.Data;

@Data
public class TunnelVentilationSystem  {

	private Long currentStateId;
	private Long currentWindSpeedRangeId;
	private Long stretchAlarmId;
	private EmergencyAlgorithm emergencyAlgorithm;
	
}
