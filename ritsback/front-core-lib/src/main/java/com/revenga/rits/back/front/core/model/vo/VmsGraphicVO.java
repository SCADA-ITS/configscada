package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import com.revenga.rits.back.front.core.annotation.I18nField;
import com.revenga.rits.back.front.core.annotation.TranslatedField;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class VmsGraphicVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2525072578233411108L;

	private Long graphicType;
	
	private String graphicCategoryId;
	
	@EqualsAndHashCode.Exclude
	private VmsGraphicCategoryVO graphicCategory;
	
	/**
	 * 
	 * graphicTypeLabel = "VmsGraphicVO.graphicTypeLabel" + graphicType;
	 */
	@TranslatedField(references = { "graphicType" })
	@I18nField(model = "VmsGraphicType", labelFieldName = "labelAlias", relations = { "id=graphicType" })
	protected String graphicTypeLabel;
	
}
