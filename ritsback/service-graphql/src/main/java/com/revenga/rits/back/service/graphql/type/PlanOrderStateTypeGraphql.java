package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.PlanOrderStateType;
import com.revenga.rits.back.front.core.model.helper.PlanOrderStateTypeHelper;
import com.revenga.rits.back.front.core.model.vo.PlanOrderStateTypeVO;

public class PlanOrderStateTypeGraphql extends PlanOrderStateTypeVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4063183730078360932L;

	public PlanOrderStateTypeGraphql(final PlanOrderStateType planOrderStateType) {

		PlanOrderStateTypeHelper.assign(planOrderStateType, this);
	}

}
