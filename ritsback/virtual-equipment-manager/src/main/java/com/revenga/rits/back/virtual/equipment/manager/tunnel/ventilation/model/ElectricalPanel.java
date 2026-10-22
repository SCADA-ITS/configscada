package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model;

import java.util.List;

import lombok.Data;

@Data
public class ElectricalPanel  {

	private Integer startRampSeconds;
	private Integer startRampNFan;
	private Integer maxNfansStart;
	private List<StartRampConfig> startRampConfigs;
}
