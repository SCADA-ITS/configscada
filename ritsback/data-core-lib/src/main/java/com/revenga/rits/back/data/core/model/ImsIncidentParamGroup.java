package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Defines type of element
 * 
 * @author mamonzon
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class ImsIncidentParamGroup extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 102941597819875918L;
	
}
