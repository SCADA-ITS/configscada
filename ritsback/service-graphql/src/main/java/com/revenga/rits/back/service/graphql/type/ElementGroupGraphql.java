package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ElementGroup;
import com.revenga.rits.back.front.core.model.helper.ElementGroupHelper;
import com.revenga.rits.back.front.core.model.vo.ElementGroupVO;

public class ElementGroupGraphql extends ElementGroupVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2024413709955798391L;


	public ElementGroupGraphql(final ElementGroup elementGroup) {

		ElementGroupHelper.assign(elementGroup, this);

	}

}
