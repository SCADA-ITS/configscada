package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Defines element subtype active param
 * 
 * @author agomez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "elementSubtypeId", "elementTypeId", "id", "paramTypeId" })
public class ElementSubtypeActiveParam extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3425067969753670676L;
	/**
	 * 
	 */
	private Long elementSubtypeId;
	private Long elementTypeId;
	private Long paramTypeId;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "elementTypeId=elementTypeId", "paramTypeId=paramTypeId", "id=id" })
	private ElementTypeParam elementTypeParam;
}
