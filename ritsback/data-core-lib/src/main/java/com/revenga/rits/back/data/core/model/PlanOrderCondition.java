package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id", "planId", "planOrderId" })
public class PlanOrderCondition extends BasicElement implements Comparable<PlanOrderCondition> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3133997270915196731L;

	private Long planId;
	private Long planOrderId;

	private Long elementTypeId;
	private Long paramTypeId;
	private Long elementTypeParamId;
	private Long elementId;
	private Long thresholdOperationId;

	private String thresholdVal1;
	private String thresholdVal2;

	private Long elementTypeStateId;

	private Integer planOrderConditionOrder;

	private Boolean nexusAnd;
	
	@Override
	public int compareTo(PlanOrderCondition planOrderCondition) {

		return (planOrderCondition == null || planOrderCondition.getPlanOrderConditionOrder() == null) ? 1
				: (this.planOrderConditionOrder == null ? -1 : this.planOrderConditionOrder.compareTo(planOrderCondition.getPlanOrderConditionOrder()));
	}

}
