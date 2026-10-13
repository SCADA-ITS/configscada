package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Defines tIms Incident Groups
 * 
 * @author F. Olivares
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class ImsIncidentGroup extends BasicElement {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4565537665796694982L;

}

