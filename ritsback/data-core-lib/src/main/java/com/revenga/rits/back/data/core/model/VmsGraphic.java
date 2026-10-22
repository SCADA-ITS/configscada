package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "graphicTypeId", "id" })
public class VmsGraphic extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2030964128281730440L;

	private Long graphicTypeId;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "graphicTypeId=id" })
	private VmsGraphicType graphicType;
	
	private Long graphicCategoryId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "graphicCategoryId=id" })
	private VmsGraphicCategory graphicCategory;
}
