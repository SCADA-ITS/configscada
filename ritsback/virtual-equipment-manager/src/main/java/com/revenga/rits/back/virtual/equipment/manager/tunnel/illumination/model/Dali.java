package com.revenga.rits.back.virtual.equipment.manager.tunnel.illumination.model;

import java.util.List;

import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.Regime;

import lombok.Data;

@Data
public class Dali {

	private Long currentRegime;
	private List<Regime> regimes;
}
