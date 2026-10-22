package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.model.BasicElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@PrimaryKeys(fields = { "id" })
public class WindSpeedRange  extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6485416118011581735L;
	private Double min;
	private Double max;
	
	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}
}
