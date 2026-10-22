package com.revenga.rits.back.virtual.equipment.manager.tunnel.model;

import com.revenga.rits.back.data.core.util.SmProcessor;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model.TubeVentilationRegimes;

import lombok.Data;

@Data
public class Tube {

	private Integer currentVentilationMode;
	private Long targetFans;
	private Boolean checkVentilationRegime;
	private SmProcessor smVentilationProcessor;
	private TubeVentilationRegimes tubeVentilationRegimes;
	
	private Integer currentIlluminationMode;
	private Long currentIlluminationRegime;
	private SmProcessor smIlluminationProcessor;
	private TubeRegimes tubeIlluminationRegimes;
}
