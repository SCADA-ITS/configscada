package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ElementTypeParamVO extends BasicElementVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 346982501270301312L;
	
	private String dataTypeId;
	@EqualsAndHashCode.Exclude
	private DataTypeVO dataType;
	private String labelUnit;
	private String threshold1;
	private String threshold2;	
	
	private String paramTypeId;
	@EqualsAndHashCode.Exclude
	private ParamTypeVO paramType;


	protected Boolean durable;
	protected String defaultValue;
	
	@EqualsAndHashCode.Exclude
	private List<VmsGraphicVO> graphics;
}
