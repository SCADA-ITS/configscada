package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model;

import java.util.List;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementValue;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StretchTube extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3443889876898294651L;

	private List<String> windSpeedSensorsId;
	private List<ElementValue> windSpeedSensors;
	private List<String> windDirectionSensorsId;
	private List<ElementValue> windDirectionSensors;
	private List<WindSpeedRange> windSpeedRanges;
	private Long defaultRangeId;
	private List<StretchConfig> stretchs;
	
	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}
}
