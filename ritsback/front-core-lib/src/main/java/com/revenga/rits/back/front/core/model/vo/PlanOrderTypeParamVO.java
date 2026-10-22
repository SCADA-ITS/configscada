package com.revenga.rits.back.front.core.model.vo;


import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class PlanOrderTypeParamVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = -1247364292345275391L;/**
	 * 
	 */
	
	private String dataTypeId;
	@EqualsAndHashCode.Exclude
	private DataTypeVO dataType;
	private String labelUnit;
	private String threshold1;
	private String threshold2;
}
