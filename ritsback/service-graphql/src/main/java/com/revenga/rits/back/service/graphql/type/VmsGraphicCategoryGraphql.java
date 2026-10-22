package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.VmsGraphicCategory;
import com.revenga.rits.back.front.core.model.helper.VmsGraphicCategoryHelper;
import com.revenga.rits.back.front.core.model.vo.VmsGraphicCategoryVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class VmsGraphicCategoryGraphql extends VmsGraphicCategoryVO {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7429221335966982392L;
	
	public VmsGraphicCategoryGraphql(final VmsGraphicCategory vmsGraphicCategory) {
		
		VmsGraphicCategoryHelper.assign(vmsGraphicCategory, this);
	}
}
