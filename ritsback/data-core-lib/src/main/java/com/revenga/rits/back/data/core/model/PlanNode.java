package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class PlanNode extends BasicElement {

		
	/**
	 * 
	 */
	private static final long serialVersionUID = 6354124084582503830L;

	/**
	 * scenarioId Propertie
	 */
	private Long planId;
		
}
