package com.revenga.rits.back.front.core.model.vo;


import java.util.List;

import javax.persistence.Entity;

import com.revenga.rits.back.front.core.annotation.I18nField;
import com.revenga.rits.back.front.core.annotation.TranslatedField;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class PlanOrderTypeVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = -1247364292345275391L;/**
	 * 
	 */
	
	@EqualsAndHashCode.Exclude
	protected List<PlanOrderTypeParamVO> planOrderTypeParams;
	
	@TranslatedField(references = { "id" })
	@I18nField(model = "PlanOrderType", labelFieldName = "labelAlias", relations = { "id=id.1" })
	protected String planOrderTypeLabel;
}
