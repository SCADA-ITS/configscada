package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Defines param of ext Incident
 * 
 * @author agomez
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"extIncidentTypeId",  "id"})
public class ImsExtIncidentTypeParam extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5859644687524343563L;

	private Long extIncidentTypeId;
	
}
