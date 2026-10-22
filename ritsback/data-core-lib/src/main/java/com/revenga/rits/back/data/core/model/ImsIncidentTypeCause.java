package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"incidentTypeId", "id"})
public class ImsIncidentTypeCause extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3168349925411570266L;
	
	private Long incidentTypeId;
}
