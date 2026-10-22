package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.front.core.model.helper.ParamTypeHelper;
import com.revenga.rits.back.front.core.model.vo.ParamTypeVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ParamTypeGraphql extends ParamTypeVO {

	private static final long serialVersionUID = -5081806387727870958L;

	private ParamType paramType;

	public ParamTypeGraphql(final ParamType paramType) {

		ParamTypeHelper.assign(paramType, this);

	}

}
