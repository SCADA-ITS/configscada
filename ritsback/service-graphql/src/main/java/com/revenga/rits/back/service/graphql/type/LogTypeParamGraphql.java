package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.LogTypeParam;
import com.revenga.rits.back.front.core.model.helper.LogTypeParamHelper;
import com.revenga.rits.back.front.core.model.vo.LogTypeParamVO;

public class LogTypeParamGraphql extends LogTypeParamVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2024413709955798391L;

	public LogTypeParamGraphql(final LogTypeParam logTypeParam) {

		LogTypeParamHelper.assign(logTypeParam, this);
	}

}
