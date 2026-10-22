package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ThresholdOperation;
import com.revenga.rits.back.front.core.model.helper.ThresholdOperationHelper;
import com.revenga.rits.back.front.core.model.vo.ThresholdOperationVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ThresholdOperationGraphql extends ThresholdOperationVO {
	/**
	* 
	*/
	private static final long serialVersionUID = 1346763117606946317L;

	public ThresholdOperationGraphql(final ThresholdOperation alarmThresholdOperation) {

		ThresholdOperationHelper.assign(alarmThresholdOperation, this);

	}

}
