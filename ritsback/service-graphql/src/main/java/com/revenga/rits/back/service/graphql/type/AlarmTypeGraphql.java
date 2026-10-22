package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.AlarmType;
import com.revenga.rits.back.front.core.model.helper.AlarmTypeHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmTypeVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class AlarmTypeGraphql extends AlarmTypeVO {

	/**
	 * 
	 */

	private static final long serialVersionUID = -5081806387727870958L;

	private AlarmType alarmType;

	public AlarmTypeGraphql(final AlarmType alarmType) {

		AlarmTypeHelper.assign(alarmType, this);

	}
}
