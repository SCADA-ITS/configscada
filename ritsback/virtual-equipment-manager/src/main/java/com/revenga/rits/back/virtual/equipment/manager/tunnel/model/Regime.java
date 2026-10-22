package com.revenga.rits.back.virtual.equipment.manager.tunnel.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.model.BasicElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@PrimaryKeys(fields = { "id" })
public class Regime extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8439942373523858297L;

	private List<RegimeConfig> configs;
	private Long operationalMinutes;
	private Long currentRegimeConfigId;
	private String documentUrl;
	
	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}
}
