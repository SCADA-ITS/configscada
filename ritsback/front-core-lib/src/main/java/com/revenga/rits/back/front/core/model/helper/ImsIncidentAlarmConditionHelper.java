package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmCondition;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmConditionAlarm;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.RoadImpact;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentAlarmConditionAlarmVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentAlarmConditionVO;

public class ImsIncidentAlarmConditionHelper {

	private ImsIncidentAlarmConditionHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentAlarmConditionVO get(final ImsIncidentAlarmCondition imsIncidentAlarmCondition) {

		ImsIncidentAlarmConditionVO imsIncidentAlarmConditionVO = null;

		if (imsIncidentAlarmCondition != null) {

			imsIncidentAlarmConditionVO = new ImsIncidentAlarmConditionVO();

			assign(imsIncidentAlarmCondition, imsIncidentAlarmConditionVO);
		}

		return imsIncidentAlarmConditionVO;
	}

	public static ImsIncidentAlarmCondition get(final ImsIncidentAlarmConditionVO imsIncidentAlarmConditionVO) {

		ImsIncidentAlarmCondition imsIncidentAlarmCondition = null;

		if (imsIncidentAlarmConditionVO != null) {

			imsIncidentAlarmCondition = new ImsIncidentAlarmCondition();
			if (imsIncidentAlarmConditionVO.getId() != null) {

				Key key = new Key(imsIncidentAlarmConditionVO.getId());

				if (key.getClazz() == ImsIncidentAlarmCondition.class) {

					imsIncidentAlarmCondition.setId(Long.valueOf(key.getId(1)));
				}
			}

			imsIncidentAlarmCondition.setAuto(imsIncidentAlarmConditionVO.getAuto());

			if (imsIncidentAlarmConditionVO.getIncidentTypeId() != null) {

				Key key = new Key(imsIncidentAlarmConditionVO.getIncidentTypeId());

				if (key.getClazz() == ImsIncidentType.class) {

					imsIncidentAlarmCondition.setIncidentTypeId(Long.valueOf(key.getId(1)));
				}
			}
			
			if (imsIncidentAlarmConditionVO.getRoadImpactId() != null) {

				Key key = new Key(imsIncidentAlarmConditionVO.getRoadImpactId());

				if (key.getClazz() == RoadImpact.class) {

					imsIncidentAlarmCondition.setRoadImpactId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsIncidentAlarmConditionVO.getStretchId() != null) {

				Key key = new Key(imsIncidentAlarmConditionVO.getStretchId());

				if (key.getClazz() == Stretch.class) {

					imsIncidentAlarmCondition.setStretchId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsIncidentAlarmConditionVO.getIncidentAlarmConditionAlarms() != null) {

				List<ImsIncidentAlarmConditionAlarm> imsIncidentAlarmConditionAlarms = new ArrayList<>();

				for (ImsIncidentAlarmConditionAlarmVO imsIncidentAlarmConditionAlarmVO : imsIncidentAlarmConditionVO
						.getIncidentAlarmConditionAlarms()) {

					ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarm = ImsIncidentAlarmConditionAlarmHelper
							.get(imsIncidentAlarmConditionAlarmVO);

					imsIncidentAlarmConditionAlarms.add(imsIncidentAlarmConditionAlarm);
				}

				imsIncidentAlarmCondition.setImsIncidentAlarmConditionAlarms(imsIncidentAlarmConditionAlarms);
			}

		}

		return imsIncidentAlarmCondition;
	}

	public static void assign(final ImsIncidentAlarmCondition imsIncidentAlarmCondition,
			ImsIncidentAlarmConditionVO imsIncidentAlarmConditionVO) {

		if (ObjectUtils.allNotNull(imsIncidentAlarmCondition, imsIncidentAlarmConditionVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentAlarmConditionVO, imsIncidentAlarmCondition);

			imsIncidentAlarmConditionVO.setAuto(imsIncidentAlarmCondition.getAuto());

			if (imsIncidentAlarmCondition.getIncidentTypeId() != null) {

				ImsIncidentType imsIncidentType = new ImsIncidentType();
				imsIncidentType.setId(imsIncidentAlarmCondition.getIncidentTypeId());
				imsIncidentAlarmConditionVO.setIncidentTypeId(BasicElementHelper.getIdFromElement(imsIncidentType));
			}

			if (imsIncidentAlarmCondition.getStretchId() != null) {

				Stretch stretch = new Stretch();
				stretch.setId(imsIncidentAlarmCondition.getStretchId());
				imsIncidentAlarmConditionVO.setStretchId(BasicElementHelper.getIdFromElement(stretch));
			}

			if (imsIncidentAlarmCondition.getImsIncidentAlarmConditionAlarms() != null) {

				List<ImsIncidentAlarmConditionAlarmVO> imsIncidentAlarmConditionAlarms = new ArrayList<>();

				for (ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarm : imsIncidentAlarmCondition
						.getImsIncidentAlarmConditionAlarms()) {

					if (imsIncidentAlarmCondition.getId() != null) {

						imsIncidentAlarmConditionAlarms
								.add(ImsIncidentAlarmConditionAlarmHelper.get(imsIncidentAlarmConditionAlarm));
					}
				}

				imsIncidentAlarmConditionVO.setIncidentAlarmConditionAlarms(imsIncidentAlarmConditionAlarms);
			}

		}
	}

}
