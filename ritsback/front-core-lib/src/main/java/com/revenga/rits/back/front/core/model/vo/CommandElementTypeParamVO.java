package com.revenga.rits.back.front.core.model.vo;


import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class CommandElementTypeParamVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = -5808452699934548903L;
	
	private String dataTypeId;
	@EqualsAndHashCode.Exclude
	private DataTypeVO dataType;
	private String labelUnit;
	private String threshold1;
	private String threshold2;
	
	private String elementTypeParamId;
	@EqualsAndHashCode.Exclude
	private ElementTypeParamVO elementTypeParam;
}
