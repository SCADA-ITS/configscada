package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.StretchType;
import com.revenga.rits.back.front.core.model.helper.StretchTypeHelper;
import com.revenga.rits.back.front.core.model.vo.StretchTypeVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class StretchTypeGraphql extends StretchTypeVO {/**
	 * 
	 */
	private static final long serialVersionUID = 453217902059456826L;

	public StretchTypeGraphql(final StretchType stretchType) {

		StretchTypeHelper.assign(stretchType, this);

	}

}
