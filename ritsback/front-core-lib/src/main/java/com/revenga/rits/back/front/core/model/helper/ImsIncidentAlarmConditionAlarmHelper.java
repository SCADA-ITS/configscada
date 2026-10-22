package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmConditionAlarm;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmConditionAlarmType;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentAlarmConditionAlarmVO;

public class ImsIncidentAlarmConditionAlarmHelper {

	private ImsIncidentAlarmConditionAlarmHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentAlarmConditionAlarmVO get(
			final ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarm) {

		ImsIncidentAlarmConditionAlarmVO imsIncidentAlarmConditionAlarmVO = null;

		if (imsIncidentAlarmConditionAlarm != null) {

			imsIncidentAlarmConditionAlarmVO = new ImsIncidentAlarmConditionAlarmVO();

			assign(imsIncidentAlarmConditionAlarm, imsIncidentAlarmConditionAlarmVO);
		}

		return imsIncidentAlarmConditionAlarmVO;
	}

	public static ImsIncidentAlarmConditionAlarm get(
			final ImsIncidentAlarmConditionAlarmVO imsIncidentAlarmConditionAlarmVO) {

		ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarm = null;

		if (imsIncidentAlarmConditionAlarmVO != null) {

			imsIncidentAlarmConditionAlarm = new ImsIncidentAlarmConditionAlarm();
			if (imsIncidentAlarmConditionAlarmVO.getId() != null) {

				Key key = new Key(imsIncidentAlarmConditionAlarmVO.getId());

				if (key.getClazz() == ImsIncidentAlarmConditionAlarm.class) {

					imsIncidentAlarmConditionAlarm.setId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsIncidentAlarmConditionAlarmVO.getAlarmConfigId() != null) {

				Key key = new Key(imsIncidentAlarmConditionAlarmVO.getAlarmConfigId());

				if (key.getClazz() == AlarmConfig.class) {

					imsIncidentAlarmConditionAlarm.setAlarmConfigId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsIncidentAlarmConditionAlarmVO.getElementTypeId() != null) {

				Key key = new Key(imsIncidentAlarmConditionAlarmVO.getElementTypeId());

				if (key.getClazz() == ElementType.class) {

					imsIncidentAlarmConditionAlarm.setElementTypeId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsIncidentAlarmConditionAlarmVO.getElementId() != null) {

				Key key = new Key(imsIncidentAlarmConditionAlarmVO.getElementId());

				if (key.getClazz() == Element.class) {

					imsIncidentAlarmConditionAlarm.setElementTypeId(Long.valueOf(key.getId(1)));
					imsIncidentAlarmConditionAlarm.setElementId(Long.valueOf(key.getId(2)));
				}
			}
			
			if (imsIncidentAlarmConditionAlarmVO.getIncidentAlarmConditionAlarmTypeId() != null) {

				Key key = new Key(imsIncidentAlarmConditionAlarmVO.getIncidentAlarmConditionAlarmTypeId());

				if (key.getClazz() == ImsIncidentAlarmConditionAlarmType.class) {

					imsIncidentAlarmConditionAlarm.setIncidentAlarmConditionAlarmTypeId(Long.valueOf(key.getId(1)));
				}
			}

		}

		return imsIncidentAlarmConditionAlarm;
	}

	public static void assign(final ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarm,
			ImsIncidentAlarmConditionAlarmVO imsIncidentAlarmConditionAlarmVO) {

		if (ObjectUtils.allNotNull(imsIncidentAlarmConditionAlarm, imsIncidentAlarmConditionAlarmVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentAlarmConditionAlarmVO, imsIncidentAlarmConditionAlarm);

			if (imsIncidentAlarmConditionAlarm.getAlarmConfigId() != null) {

				AlarmConfig alarmConfig = new AlarmConfig();
				alarmConfig.setId(imsIncidentAlarmConditionAlarm.getAlarmConfigId());
				imsIncidentAlarmConditionAlarmVO.setAlarmConfigId(BasicElementHelper.getIdFromElement(alarmConfig));
			}

			if (imsIncidentAlarmConditionAlarm.getElementTypeId() != null) {

				ElementType elementType = new ElementType();
				elementType.setId(imsIncidentAlarmConditionAlarm.getElementTypeId());
				imsIncidentAlarmConditionAlarmVO.setElementTypeId(BasicElementHelper.getIdFromElement(elementType));
			}

			if (imsIncidentAlarmConditionAlarm.getElementId() != null) {

				Element element = new Element();
				element.setId(imsIncidentAlarmConditionAlarm.getElementId());
				imsIncidentAlarmConditionAlarmVO.setElementId(BasicElementHelper.getIdFromElement(element));
			}
			
			if (imsIncidentAlarmConditionAlarm.getIncidentAlarmConditionAlarmTypeId() != null) {

				ImsIncidentAlarmConditionAlarmType incidentAlarmConditionAlarmType = new ImsIncidentAlarmConditionAlarmType();
				incidentAlarmConditionAlarmType.setId(imsIncidentAlarmConditionAlarm.getIncidentAlarmConditionAlarmTypeId());
				imsIncidentAlarmConditionAlarmVO.setIncidentAlarmConditionAlarmTypeId(BasicElementHelper.getIdFromElement(incidentAlarmConditionAlarmType));
			}

		}
	}

}
