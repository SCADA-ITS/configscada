package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Defines element_subtype_active_command table
 * 
 * @author F. Olivares
 */

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "elementSubtypeId", "commandElementTypeId", "elementTypeId" })
public class ElementSubtypeActiveCommand extends BasicElement {


	private static final long serialVersionUID = 5906198999556828029L;
	
	private Long elementSubtypeId;
	private Long commandElementTypeId;
	private Long elementTypeId;
}