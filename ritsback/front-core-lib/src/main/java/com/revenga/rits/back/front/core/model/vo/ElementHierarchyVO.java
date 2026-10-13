package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ElementHierarchyVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6070239306757903730L;
	
	@EqualsAndHashCode.Exclude
	private ElementVO parent;
	
	@EqualsAndHashCode.Exclude
	private ElementVO child;	
	
}
