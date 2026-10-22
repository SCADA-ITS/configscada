package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id", "elementTypeId", "elementId"})
public class CCTVScenarioElement extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6181656229584460354L;
	
	private Long elementTypeId;
	private Long elementId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "elementId=id", "elementTypeId=elementTypeId"})
	private Element element;
	
	private int nOrder;
}
