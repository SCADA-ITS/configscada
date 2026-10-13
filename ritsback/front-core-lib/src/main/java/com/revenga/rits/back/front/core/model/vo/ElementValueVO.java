package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ElementValueVO extends BasicElementVO {
	/**
	* 
	*/
	private static final long serialVersionUID = -8358617513937641463L;

	protected String value;
	protected String elementTypeParamId;

	@EqualsAndHashCode.Exclude
	protected ElementTypeParamVO elementTypeParam;
	
	private Long state;
	
	@EqualsAndHashCode.Exclude
	protected ElementValueStateVO elementValueState;
}
