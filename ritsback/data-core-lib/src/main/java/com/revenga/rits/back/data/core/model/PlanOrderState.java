package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"planId", "planOrderId" })
public class PlanOrderState extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4622343366586425656L;

	private Long planId;
	private Long planOrderId;
	private Long planOrderStateTypeId;
	private Integer nTimes;
	private Long changedAt;
	
	public PlanOrderState() {
		super();
	}
}