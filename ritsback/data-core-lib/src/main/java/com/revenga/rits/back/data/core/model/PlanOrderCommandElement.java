package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"planId", "planOrderId" })
public class PlanOrderCommandElement extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4168196699894936108L;

	/**
	 * 
	 */

	private Long commandElementTypeId;
	private Long elementTypeId;
	private Long commandElementId;
	private Long planId;
	private Long planOrderId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "commandElementTypeId=commandElementTypeId", "elementTypeId=elementTypeId", "commandElementId=id"})
	private CommandElement commandElement;
}
