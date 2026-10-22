package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.PlanScheduleStateType;
import com.revenga.rits.back.front.core.model.helper.PlanScheduleStateTypeHelper;
import com.revenga.rits.back.front.core.model.vo.PlanScheduleStateTypeVO;

public class PlanScheduleStateTypeGraphql extends PlanScheduleStateTypeVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5169565006488934235L;
	
	public PlanScheduleStateTypeGraphql(final PlanScheduleStateType planScheduleStateType) {

		PlanScheduleStateTypeHelper.assign(planScheduleStateType, this);
	}

}
