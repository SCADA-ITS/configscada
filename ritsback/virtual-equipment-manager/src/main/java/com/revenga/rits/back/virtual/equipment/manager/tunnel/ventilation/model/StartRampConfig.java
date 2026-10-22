package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model;

import java.util.Set;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.model.BasicElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@PrimaryKeys(fields = { "id" })
public class StartRampConfig extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 233453919929495862L;
	
	private Integer startRampSeconds;
	private Set<Integer> startRampNFans;

	
	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}
}
