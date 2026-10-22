package com.revenga.rits.back.virtual.equipment.manager.vms.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class VmsMessageZone implements Serializable {

	private static final long serialVersionUID = -7918674496396282207L;
	
	private Integer zone;
	@JsonProperty("alternance_ms")
	private Integer alternanceMs;
	@JsonProperty("flashing_on_ms")
	private Integer flashingOnMs;
	@JsonProperty("flashing_off_ms")
	private Integer flashingOffMs;
	String align;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<VmsMessageZoneText> texts;
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<VmsMessageZoneGraphic> graphics;
}
