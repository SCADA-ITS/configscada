package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Defines element
 * 
 * @author agomez
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "parentElementTypeId", "parentElementId", "childElementTypeId", "childElementId" })
public class ElementHierarchy extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6776246816658981438L;
	/**
	 * 
	 */

	private Long parentElementTypeId;
	private Long parentElementId;
	
	private Long childElementTypeId;
	private Long childElementId;
	
	private Long childInheritableStateId;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "parentElementTypeId=elementTypeId", "parentElementId=id" })
	private Element parent;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "childElementTypeId=elementTypeId", "childElementId=id" })
	private Element child;
}
