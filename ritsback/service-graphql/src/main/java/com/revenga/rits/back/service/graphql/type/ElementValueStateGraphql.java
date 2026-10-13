package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ElementValueState;
import com.revenga.rits.back.front.core.model.helper.ElementValueStateHelper;
import com.revenga.rits.back.front.core.model.vo.ElementValueStateVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ElementValueStateGraphql extends ElementValueStateVO{/**
	 * 
	 */
	private static final long serialVersionUID = 5031568398536179111L;
	
	private ElementValueState elementValueState;

	public ElementValueStateGraphql(final ElementValueState elementValueState) {

		ElementValueStateHelper.assign(elementValueState, this);
		this.elementValueState = elementValueState;
	}

}
