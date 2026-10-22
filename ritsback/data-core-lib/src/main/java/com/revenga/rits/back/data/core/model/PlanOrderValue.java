package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Defines values for plan Order
 * 
 * @author agomez
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"planId", "planOrderId", "planOrderTypeId", "planOrderTypeParamId"})
public class PlanOrderValue extends BasicElement {

	private static final long serialVersionUID = 6599713631247785087L;
	
	private Long planId;
	private Long planOrderId;
	private Long planOrderTypeId;
	private Long planOrderTypeParamId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"planOrderTypeId=planOrderTypeId", "planOrderTypeParamId=id"})
	private PlanOrderTypeParam planOrderTypeParam;

	private String value;

}
	

