package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model;

import java.util.List;

import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.SmConfig;

import lombok.Data;

@Data
public class TubeVentilationRegimes {

	private String windDirectionId;
	private ElementTypeParam windDirection;
	private Boolean currentWindDirection;
	
	private Integer measuresValidityPeriod_s;
	private Integer periodTime_s;
	private List<VentilationRegime> ventilationRegimes;
	private List<SmConfig> smConfigs;
}
