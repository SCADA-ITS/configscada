package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"planId"})
public class PlanState extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4622343366586425656L;

	private Long planId;
	private Long planStateTypeId;
	private Long userId;
	private Long currentPlanOrderId;
	private Long changedAt;
	
	public PlanState() {
		super();
	}
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"userId=id"})
	private User user;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"planId=planId"})
	private List<PlanOrderState> planOrderStates;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"planId=planId", "currentPlanOrderId=planOrderId"})
	private PlanOrderState currentPlanOrder;
}