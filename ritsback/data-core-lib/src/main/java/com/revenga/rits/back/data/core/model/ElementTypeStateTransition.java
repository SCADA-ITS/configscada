package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.IndexKey;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"elementTypeId", "currentElementTypeStateId", "nextElementTypeStateId"})
public class ElementTypeStateTransition extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4628441741092572501L;
	
	Long elementTypeId;
	
	Long currentElementTypeStateId;
	Long nextElementTypeStateId;
}
