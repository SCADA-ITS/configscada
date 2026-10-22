package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model;

import java.util.List;

import lombok.Data;

@Data
public class EmergencyAlgorithm {

	private Integer maxNfansStart;
	private List<StretchTube> configs;
}
