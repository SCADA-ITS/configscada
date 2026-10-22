package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.PlanStateType;
import com.revenga.rits.back.front.core.model.helper.PlanStateTypeHelper;
import com.revenga.rits.back.front.core.model.vo.PlanStateTypeVO;

public class PlanStateTypeGraphql extends PlanStateTypeVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1653079845999173128L;
	
	public PlanStateTypeGraphql(final PlanStateType planStateType) {

		PlanStateTypeHelper.assign(planStateType, this);
	}

}
