package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Defines subtype of element
 * 
 * @author agomez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id", "elementTypeId" })
public class ElementSubtype extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Long elementTypeId;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "id=elementSubtypeId", "elementTypeId=elementTypeId" })
	private List<ElementSubtypeActiveParam> elementSubtypeActiveParams;
}
