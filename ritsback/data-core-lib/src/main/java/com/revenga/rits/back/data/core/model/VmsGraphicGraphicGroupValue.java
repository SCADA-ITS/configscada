package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "graphicTypeId", "graphicId", "graphicGroupId" })
public class VmsGraphicGraphicGroupValue extends BasicElement {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -7081955635843855691L;
	
	private Long graphicTypeId;
	private Long graphicId;
	private Long graphicGroupId;
	private String value;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "graphicId=id" })
	private VmsGraphic graphic;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "graphicGroupId=graphicGroupId" })
	private VmsGraphicGroup vmsGraphicGroup;
	
}
