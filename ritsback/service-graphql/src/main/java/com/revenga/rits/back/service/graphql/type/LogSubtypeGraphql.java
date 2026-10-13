package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.front.core.model.helper.LogSubtypeHelper;
import com.revenga.rits.back.front.core.model.vo.LogSubtypeVO;


public class LogSubtypeGraphql extends LogSubtypeVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2024413709955798391L;

	public LogSubtypeGraphql(final LogSubtype logSubtype) {

		LogSubtypeHelper.assign(logSubtype, this);
	}

}
