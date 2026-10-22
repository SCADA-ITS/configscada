package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ImsIncidentAlarmConditionAlarmType;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentAlarmConditionAlarmTypeHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentAlarmConditionAlarmTypeVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ImsIncidentAlarmConditionAlarmTypeGraphql extends ImsIncidentAlarmConditionAlarmTypeVO  {/**
	 * 
	 */
	private static final long serialVersionUID = 2511049697967632396L;
	
	public ImsIncidentAlarmConditionAlarmTypeGraphql(final ImsIncidentAlarmConditionAlarmType imsIncidentAlarmConditionAlarmType) {

		ImsIncidentAlarmConditionAlarmTypeHelper.assign(imsIncidentAlarmConditionAlarmType, this);

	}

}
