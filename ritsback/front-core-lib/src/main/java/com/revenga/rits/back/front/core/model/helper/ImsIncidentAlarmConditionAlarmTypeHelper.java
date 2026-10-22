package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmConditionAlarmType;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentAlarmConditionAlarmTypeVO;

public class ImsIncidentAlarmConditionAlarmTypeHelper {
	
	private ImsIncidentAlarmConditionAlarmTypeHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentAlarmConditionAlarmTypeVO get(
			final ImsIncidentAlarmConditionAlarmType imsIncidentAlarmConditionAlarmType) {

		ImsIncidentAlarmConditionAlarmTypeVO imsIncidentAlarmConditionAlarmTypeVO = null;

		if (imsIncidentAlarmConditionAlarmType != null) {

			imsIncidentAlarmConditionAlarmTypeVO = new ImsIncidentAlarmConditionAlarmTypeVO();

			assign(imsIncidentAlarmConditionAlarmType, imsIncidentAlarmConditionAlarmTypeVO);
		}

		return imsIncidentAlarmConditionAlarmTypeVO;
	}

	public static ImsIncidentAlarmConditionAlarmType get(
			final ImsIncidentAlarmConditionAlarmTypeVO imsIncidentAlarmConditionAlarmTypeVO) {

		ImsIncidentAlarmConditionAlarmType imsIncidentAlarmConditionAlarmType = null;

		if (imsIncidentAlarmConditionAlarmTypeVO != null) {

			imsIncidentAlarmConditionAlarmType = new ImsIncidentAlarmConditionAlarmType();
			if (imsIncidentAlarmConditionAlarmTypeVO.getId() != null) {

				Key key = new Key(imsIncidentAlarmConditionAlarmTypeVO.getId());

				if (key.getClazz() == ImsIncidentAlarmConditionAlarmType.class) {

					imsIncidentAlarmConditionAlarmType.setId(Long.valueOf(key.getId(1)));
				}
			}

		}

		return imsIncidentAlarmConditionAlarmType;
	}

	public static void assign(final ImsIncidentAlarmConditionAlarmType imsIncidentAlarmConditionAlarmType,
			ImsIncidentAlarmConditionAlarmTypeVO imsIncidentAlarmConditionAlarmTypeVO) {

		if (ObjectUtils.allNotNull(imsIncidentAlarmConditionAlarmType, imsIncidentAlarmConditionAlarmTypeVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentAlarmConditionAlarmTypeVO, imsIncidentAlarmConditionAlarmType);

		}
	}


}
