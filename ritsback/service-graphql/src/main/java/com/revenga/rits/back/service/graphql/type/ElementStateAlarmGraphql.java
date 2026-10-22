package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.ElementStateAlarm;
import com.revenga.rits.back.front.core.model.helper.ElementStateAlarmHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigVO;
import com.revenga.rits.back.front.core.model.vo.ElementStateAlarmVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
public class ElementStateAlarmGraphql extends ElementStateAlarmVO {

	/**
	 * 
	 */

	private static final long serialVersionUID = -5081806387727870958L;

	private ElementStateAlarm elementStateAlarm;

	public ElementStateAlarmGraphql(final ElementStateAlarm elementStateAlarm) {

		ElementStateAlarmHelper.assign(elementStateAlarm, this);

		this.elementStateAlarm = elementStateAlarm;
	}

	@Override
	public AlarmConfigVO getAlarmConfig() {

		if (super.getAlarmConfig() == null) {

			if (elementStateAlarm != null && elementStateAlarm.getAlarmConfigId() != null) {

				AlarmConfig alarmConfig = EntitiesManager.getInstance().getAlarmConfigRepository()
						.get(elementStateAlarm.getAlarmConfigId(), false);

				if (alarmConfig != null) {

					super.setAlarmConfig(new AlarmConfigGraphql(alarmConfig));
				}

			}

		}

		return super.getAlarmConfig();
	}
	
}