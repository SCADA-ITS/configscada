package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"moduleTypeId", "id"})
public class IOControllerModuleTypeParam extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7162803914075513288L;
	
	private Long moduleTypeId;
	private Long dataTypeId;

}
