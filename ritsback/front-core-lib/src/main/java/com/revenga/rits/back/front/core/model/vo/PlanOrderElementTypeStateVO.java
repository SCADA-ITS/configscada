package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class PlanOrderElementTypeStateVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3705596807983859314L;
	
	protected String elementId;
	protected String elementTypeStateId;

	@EqualsAndHashCode.Exclude
	private ElementVO element;

	@EqualsAndHashCode.Exclude
	private ElementTypeStateVO elementTypeState;

}
