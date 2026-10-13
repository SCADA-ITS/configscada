package com.revenga.rits.back.virtual.equipment.manager.tunnel.ventilation.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.Location;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@PrimaryKeys(fields = { "id" })
public class VentilationRegime extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8439942373523858297L;

	private String documentUrl;
	private Long fans;
	private String incidentTypeId;
	private String locationId;
	
	private ImsIncidentType incidentType;
	private Location location;
	
	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}
}
