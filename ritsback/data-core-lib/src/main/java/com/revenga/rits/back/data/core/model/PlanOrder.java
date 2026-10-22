package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "planId", "id" })
public class PlanOrder extends BasicElement implements Comparable<PlanOrder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -621190770991880462L;

	private Long planId;
	private Long planOrderTypeId;
	private Integer planOrderOrder;
	private Long  nextPlanOrderId;
	private Long  nextPlanId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "planOrderTypeId=id" })
	private PlanOrderType planOrderType;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "planId=planId", "id=planOrderId", "planOrderTypeId=planOrderTypeId" })
	private List<PlanOrderValue> planOrderValues;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "planId=planId", "id=planOrderId" })
	private List<PlanOrderElementValue> planOrderElementValues;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "planId=planId", "id=planOrderId" })
	private List<PlanOrderElementTypeState> planOrderElementTypeStates;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "planId=planId", "id=planOrderId" })
	private List<PlanOrderCondition> planOrderConditions;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "planId=planId", "id=planOrderId" })
	private PlanOrderCommandElement planOrderCommandElement;

	@Override
	public int compareTo(PlanOrder planOrder) {

		return (planOrder == null || planOrder.getPlanOrderOrder() == null) ? 1
				: (this.planOrderOrder == null ? -1 : this.planOrderOrder.compareTo(planOrder.getPlanOrderOrder()));
	}

}
