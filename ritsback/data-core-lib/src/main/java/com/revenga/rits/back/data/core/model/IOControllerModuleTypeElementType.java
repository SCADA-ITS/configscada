package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Defines type of element
 * 
 * @author aborrajo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"moduleTypeId", "elementTypeId"})
public class IOControllerModuleTypeElementType extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7691309909916277872L;
	
	private Long moduleTypeId;
	private Long elementTypeId;
}
