package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.front.core.model.helper.ElementTypeStateHelper;
import com.revenga.rits.back.front.core.model.vo.ElementTypeStateVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ElementTypeStateGraphql extends ElementTypeStateVO {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2449413185795787144L;
	
	private ElementTypeState elementTypeState;

	public ElementTypeStateGraphql(final ElementTypeState elementTypeState) {

		ElementTypeStateHelper.assign(elementTypeState, this);
		this.elementTypeState = elementTypeState;
	}
}
