package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity

public class CommandElementValuePreconditionVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5742165439103820764L;
	/**
	 * 
	 */

	@EqualsAndHashCode.Exclude	
	private CommandElementTypeParamVO commandElementTypeParam;
	@EqualsAndHashCode.Exclude	
	private ThresholdOperationVO thresholdOperation;
	
	private String thresholdVal1;
	private String thresholdVal2;
}
