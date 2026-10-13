package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "commandElementTypeId", "elementTypeId", "elementId", "preconditionId", "commandElementTypeParamId" })
public class CommandElementValuePrecondition extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9043133146459873700L;
	/**
	 * 
	 */

	private Long commandElementTypeId;
	private Long elementTypeId;
	private Long elementId;

	private Long preconditionId;

	private Long commandElementTypeParamId;

	private Long thresholdOperationId;
	private String thresholdVal1;
	private String thresholdVal2;

}
