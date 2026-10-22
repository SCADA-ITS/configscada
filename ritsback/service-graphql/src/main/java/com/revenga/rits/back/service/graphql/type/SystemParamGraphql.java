package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.SystemParam;
import com.revenga.rits.back.front.core.model.helper.SystemParamHelper;
import com.revenga.rits.back.front.core.model.vo.SystemParamVO;

public class SystemParamGraphql extends SystemParamVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2507411041387758229L;


	public SystemParamGraphql(final SystemParam systemParam) {
		
		SystemParamHelper.assign(systemParam, this);

	}
}
