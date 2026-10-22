package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "planId", "planOrderId", "elementTypeId", "elementTypeStateId", "elementId" })
public class PlanOrderElementTypeState extends BasicElement {

	private static final long serialVersionUID = 6599713631247785087L;

	private Long planId;
	private Long planOrderId;
	private Long elementTypeId;
	private Long elementTypeStateId;
	private Long elementId;

}