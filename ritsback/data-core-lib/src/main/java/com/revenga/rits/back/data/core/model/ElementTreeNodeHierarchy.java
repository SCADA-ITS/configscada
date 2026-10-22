package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class ElementTreeNodeHierarchy extends BasicElement {

	private static final long serialVersionUID = -7910037360120737142L;

	private Long elementTreeId;
	
	private Long nodeParentId;
	
	private Long nodeChildId;
	
		
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "nodeParentId=id"})
	private ElementTreeNode parent;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "nodeChildId=id"})
	private ElementTreeNode child;	
	
	
}
