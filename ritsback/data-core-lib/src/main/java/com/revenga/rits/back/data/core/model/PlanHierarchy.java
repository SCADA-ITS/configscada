package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"nodeParentId", "nodeChildId"})
public class PlanHierarchy extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8711364753684253734L;

	/**
	 * nodeParentId Propertie
	 */
	private Long nodeParentId;
	
	/**
	 * nodeChildId Propertie
	 */
	private Long nodeChildId;	
	
		
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "nodeParentId=id"})
	private PlanNode parent;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "nodeChildId=id"})
	private PlanNode child;	
	
	
}
