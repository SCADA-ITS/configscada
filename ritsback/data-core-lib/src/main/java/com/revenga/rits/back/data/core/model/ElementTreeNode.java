package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class ElementTreeNode extends BasicElement {


	private static final long serialVersionUID = 588017229059298444L;
	
	private Long elementTreeId;

	private Long elementTypeId;
	private Long elementId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "elementId=id", "elementTypeId=elementTypeId"})
	private Element element;
	
	private Long position;
	
}
