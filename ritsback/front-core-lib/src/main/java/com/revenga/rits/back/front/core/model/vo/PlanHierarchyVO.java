package com.revenga.rits.back.front.core.model.vo;


import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class PlanHierarchyVO extends BasicElementVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2955907486682589441L;
	
	@EqualsAndHashCode.Exclude
	private PlanNodeVO parent;
	
	@EqualsAndHashCode.Exclude
	private PlanNodeVO child;
}
