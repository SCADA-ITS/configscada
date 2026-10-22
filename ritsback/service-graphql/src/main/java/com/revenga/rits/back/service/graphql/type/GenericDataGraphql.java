package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.GenericData;
import com.revenga.rits.back.front.core.model.helper.GenericDataHelper;
import com.revenga.rits.back.front.core.model.vo.GenericDataVO;

public class GenericDataGraphql extends GenericDataVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7880442964139460503L;

	public GenericDataGraphql(final GenericData genericData) {

		GenericDataHelper.assign(genericData, this);

	}
}
