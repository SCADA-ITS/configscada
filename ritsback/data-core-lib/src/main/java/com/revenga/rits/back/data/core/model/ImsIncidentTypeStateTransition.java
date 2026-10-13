package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Defines 
 * 
 * @author agomez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"incidentTypeId", "iniIncidentStateId"})
public class ImsIncidentTypeStateTransition extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482242987658695912L;
	
	private Long incidentTypeId;
	private Long iniIncidentStateId;
	private Long endIncidentStateId;
	private Boolean auto;
}
