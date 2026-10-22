package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.AlarmLevel;
import com.revenga.rits.back.front.core.model.helper.AlarmLevelHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmLevelVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class AlarmLevelGraphql extends AlarmLevelVO {

	/**
	 * 
	 */

	private static final long serialVersionUID = -5081806387727870958L;

	private AlarmLevel alarmLevel;

	public AlarmLevelGraphql(final AlarmLevel alarmLevel) {

		AlarmLevelHelper.assign(alarmLevel, this);

	}
}
