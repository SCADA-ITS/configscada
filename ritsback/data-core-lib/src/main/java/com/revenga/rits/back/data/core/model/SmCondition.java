package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class SmCondition extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8278722028749945100L;
	
	private Long thresholdOperationId;
	private Long thresholdVal1Id;
	private Long thresholdVal2Id;
	private Long elementTypeId;
	private Long elementTypeParamId;
	private Long paramTypeId;
	private Long timerId;
	private Integer minElementsMatchThreshold;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "thresholdVal1Id=id" })
	private SmThreshold threshold1;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "thresholdVal2Id=id" })
	private SmThreshold threshold2;
	
}