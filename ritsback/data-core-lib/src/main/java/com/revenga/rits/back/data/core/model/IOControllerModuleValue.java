package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"moduleTypeId", "moduleId", "moduleTypeParamId"})
public class IOControllerModuleValue extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1033910652452366586L;
	
	private Long moduleTypeId;
	private Long moduleId;
	private Long moduleTypeParamId;
	private String value;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"moduleTypeId=moduleTypeId", "moduleTypeParamId=id"})
	private IOControllerModuleTypeParam moduleTypeParam;
}
