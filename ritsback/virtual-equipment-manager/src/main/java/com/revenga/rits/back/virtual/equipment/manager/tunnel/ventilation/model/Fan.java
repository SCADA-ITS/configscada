package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Fan  {

	private Long lastUpdateFanState;
	private Long delayBeforeStart;
	private Long fanState;
	private Long lastFanState;
	private Long totalOperatingHours;
	private Long operatingOffset;
	private Set<Long> alarmConfigIds; 
}

