package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmLevel;
import com.revenga.rits.back.front.core.model.vo.AlarmLevelVO;

public class AlarmLevelHelper {

	private AlarmLevelHelper() {

		super();
	}

	public static AlarmLevelVO get(AlarmLevel alarmLevel) {

		AlarmLevelVO alarmLevelVO = null;

		if (alarmLevel != null) {

			alarmLevelVO = new AlarmLevelVO();

			assign(alarmLevel, alarmLevelVO);
		}

		return alarmLevelVO;
	}

	public static AlarmLevel get(AlarmLevelVO alarmLevelVO) {

		AlarmLevel alarmLevel = null;

		if (alarmLevelVO != null) {

			alarmLevel = new AlarmLevel();

			if (alarmLevelVO.getId() != null) {

				Key key = new Key(alarmLevelVO.getId());

				if (key.getClazz() == AlarmLevel.class) {

					alarmLevel.setId(Long.valueOf(key.getId(1)));
				}
			}

			alarmLevel.setAlias(alarmLevelVO.getAlias());
			alarmLevel.setDescription((alarmLevelVO.getDescription()));

		}

		return alarmLevel;
	}

	public static void assign(final AlarmLevel alarmLevel, AlarmLevelVO alarmLevelVO) {

		if (alarmLevel != null && alarmLevelVO != null) {

			BasicElementHelper.fillBasicProperties(alarmLevelVO, alarmLevel);

		}
	}
}
