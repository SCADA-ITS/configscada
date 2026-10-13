package com.revenga.rits.back.virtual.equipment.manager.tunnel.model;

import java.util.List;

import lombok.Data;

@Data
public class TubeRegimes {

	private Integer measuresValidityPeriod_s;
	private Integer periodTime_s;
	private List<Regime> regimes;
	private List<SmConfig> smConfigs;
}
