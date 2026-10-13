package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "parentElementTypeId", "childElementTypeId"})
public class ElementHierarchyAllowed extends BasicElement {/**
	 * 
	 */
	private static final long serialVersionUID = -919036324961766676L;
	
	private Long parentElementTypeId;
	
	private Long childElementTypeId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "parentElementTypeId=id"})
	private ElementType parent;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "childElementTypeId=id"})
	private ElementType child;

}
