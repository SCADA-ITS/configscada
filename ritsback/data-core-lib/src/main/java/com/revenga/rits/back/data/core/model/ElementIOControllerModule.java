package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"elementTypeId", "elementId", "moduleTypeId", "moduleId"})
public class ElementIOControllerModule extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6183850391601508984L;
	
	private Long elementTypeId;
	private Long elementId;
	
	@EqualsAndHashCode.Exclude
//	@AssociatedKeys(references = { "elementId=id", "elementTypeId=elementTypeId"})
	@ToString.Exclude
	private Element element;
	
	private Long moduleTypeId;
	private Long moduleId;
}
