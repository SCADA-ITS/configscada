package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Defines source of incident
 * 
 * @author aborrajo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class LocationType extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482242987658695912L;
		
}
