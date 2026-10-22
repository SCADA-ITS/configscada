package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class PlanOrderConditionVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5465192615450035660L;

	private String elementId;
	private String elementTypeParamId;
	private String elementTypeStateId;
	private String thresholdOperationId;
	private String thresholdVal1;
	private String thresholdVal2;
	private Integer planOrderConditionOrder;
	private Boolean nexusAnd;

	@EqualsAndHashCode.Exclude
	private ElementVO element;

	@EqualsAndHashCode.Exclude
	private ElementTypeParamVO elementTypeParam;

	@EqualsAndHashCode.Exclude
	private ElementTypeStateVO elementTypeState;

	@EqualsAndHashCode.Exclude
	private ThresholdOperationVO thresholdOperation;

}
