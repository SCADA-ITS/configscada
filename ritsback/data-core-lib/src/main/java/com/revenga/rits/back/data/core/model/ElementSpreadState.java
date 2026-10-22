package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.IndexKey;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Deprecated
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class ElementSpreadState extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1171297661784343224L;

	@IndexKey
	Long sourceElementTypeId;
	Long sourceElementId;
	Long sourceElementTypeStateId;

	@IndexKey
	Long targetElementTypeId;
	Long targetElementId;
	Long targetElementTypeStateId;
}
