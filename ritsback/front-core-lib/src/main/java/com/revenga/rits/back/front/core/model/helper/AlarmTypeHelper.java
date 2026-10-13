package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmType;
import com.revenga.rits.back.front.core.model.vo.AlarmTypeVO;

public class AlarmTypeHelper {

	private AlarmTypeHelper() {

		super();
	}

	public static AlarmTypeVO get(AlarmType alarmType) {

		AlarmTypeVO alarmTypeVO = null;

		if (alarmType != null) {

			alarmTypeVO = new AlarmTypeVO();

			assign(alarmType, alarmTypeVO);
		}

		return alarmTypeVO;
	}

	public static AlarmType get(AlarmTypeVO alarmTypeVO) {

		AlarmType alarmType = null;

		if (alarmTypeVO != null) {

			alarmType = new AlarmType();

			if (alarmTypeVO.getId() != null) {

				Key key = new Key(alarmTypeVO.getId());

				if (key.getClazz() == AlarmType.class) {

					alarmType.setId(Long.valueOf(key.getId(1)));
				}
			}

			alarmType.setAlias(alarmTypeVO.getAlias());
			alarmType.setDescription((alarmTypeVO.getDescription()));

		}

		return alarmType;
	}

	public static void assign(final AlarmType alarmType, AlarmTypeVO alarmTypeVO) {

		if (alarmType != null && alarmTypeVO != null) {

			BasicElementHelper.fillBasicProperties(alarmTypeVO, alarmType);

		}
	}
}
