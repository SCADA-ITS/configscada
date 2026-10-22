package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "graphicGroupId", "elementTypeId", "elementId" })
public class VmsGraphicGroupElement extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 396064300542434037L;
	
	private Long graphicGroupId;
	private Long elementTypeId;
	private Long elementId;	

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "elementTypeId=elementTypeId", "elementId=id" })
	private Element element;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "graphicGroupId=graphicGroupId" })
	private VmsGraphicGroup vmsGraphicGroup;	

}