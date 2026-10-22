package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.AlarmFiltered;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.front.core.model.vo.AlarmFilteredVO;

public class AlarmFilteredHelper {

	private AlarmFilteredHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static AlarmFilteredVO get(final AlarmFiltered alarmFiltered) {

		AlarmFilteredVO alarmFilteredVO = null;

		if (alarmFiltered != null) {

			alarmFilteredVO = new AlarmFilteredVO();

			assign(alarmFiltered, alarmFilteredVO);
		}

		return alarmFilteredVO;
	}
	
	public static AlarmFiltered get(final AlarmFilteredVO alarmFilteredVO) {

		AlarmFiltered alarmFiltered = null;

		if (alarmFilteredVO != null) {

			alarmFiltered = new AlarmFiltered();

			if (alarmFilteredVO.getId() != null) {

				Key key = new Key(alarmFilteredVO.getId());

				if (key.getClazz() == AlarmFiltered.class) {

					alarmFiltered.setId(Long.valueOf(key.getId(1)));
				}
			}

			// AlarmConfig
			if (alarmFilteredVO.getAlarmConfigId() != null) {

				Key key = new Key(alarmFilteredVO.getAlarmConfigId());

				if (key.getClazz() == AlarmConfig.class) {

					alarmFiltered.setAlarmConfigId(Long.valueOf(key.getId(1)));
				}
			}

			if (alarmFilteredVO.getAlarmConfig() != null) {

				AlarmConfig alarmConfig = AlarmConfigHelper.get(alarmFilteredVO.getAlarmConfig());

				if (alarmConfig != null) {

					if (alarmFilteredVO.getAlarmConfigId() == null) {

						alarmFiltered.setAlarmConfigId(alarmConfig.getId());
					}
					alarmFiltered.setAlarmConfig(alarmConfig);
				}
			}

			// Element
			if (alarmFilteredVO.getElementId() != null) {

				Key key = new Key(alarmFilteredVO.getElementId());

				if (key.getClazz() == Element.class) {

					alarmFiltered.setElementTypeId(Long.valueOf(key.getId(1)));
					alarmFiltered.setElementId(Long.valueOf(key.getId(2)));
				}
			}

			if (alarmFilteredVO.getElement() != null) {

				Element element = ElementHelper.get(alarmFilteredVO.getElement());

				if (element != null) {

					if (alarmFilteredVO.getElementId() == null) {

						alarmFiltered.setElementTypeId(element.getElementTypeId());
						alarmFiltered.setElementId(element.getId());
					}
					alarmFiltered.setElement(element);
				}
			}

			alarmFiltered.setFilteredUntil(alarmFilteredVO.getFilteredUntil());			
			alarmFiltered.setEnabled(alarmFilteredVO.getEnable());
			alarmFiltered.setAlarmDisabled(alarmFilteredVO.getAlarmDisabled());
			
			if (!StringUtils.isEmpty(alarmFilteredVO.getUserId())) {

				Key key = new Key(alarmFilteredVO.getUserId());

				if (key.getClazz() == User.class) {

					alarmFiltered.setUserId(Long.valueOf(key.getId(1)));
				}
			}
			
			alarmFiltered.setComment(alarmFilteredVO.getComment());			
		}
			
		return alarmFiltered;
	}

	public static void assign(final AlarmFiltered alarmFiltered, AlarmFilteredVO alarmFilteredVO) {

		if (ObjectUtils.allNotNull(alarmFiltered, alarmFilteredVO)) {

			BasicElementHelper.fillBasicProperties(alarmFilteredVO, alarmFiltered);

			// AlarmConfig
			if (alarmFiltered.getAlarmConfigId() != null) {

				AlarmConfig alarmConfig = new AlarmConfig();
				alarmConfig.setId(alarmFiltered.getAlarmConfigId());
				alarmFilteredVO.setAlarmConfigId(BasicElementHelper.getIdFromElement(alarmConfig));
			}
			alarmFilteredVO.setAlarmConfig(AlarmConfigHelper.get(alarmFiltered.getAlarmConfig()));

			if (alarmFiltered.getAlarmConfigId() == null && alarmFiltered.getAlarmConfig() != null
					&& alarmFiltered.getAlarmConfig().getId() != null) {

				alarmFilteredVO.setAlarmConfigId(BasicElementHelper.getIdFromElement(alarmFiltered.getAlarmConfig()));
			}

			// Element
			if (ObjectUtils.allNotNull(alarmFiltered.getElementId(), alarmFiltered.getElementTypeId())) {

				Element element = new Element();
				element.setId(alarmFiltered.getElementId());
				element.setElementTypeId(alarmFiltered.getElementTypeId());

				alarmFilteredVO.setElementId(BasicElementHelper.getIdFromElement(element));
			}
			alarmFilteredVO.setElement(ElementHelper.get(alarmFiltered.getElement()));

			if (alarmFiltered.getElementId() == null && alarmFiltered.getElement() != null
					&& alarmFiltered.getElement().getId() != null && alarmFiltered.getElement().getElementTypeId() != null) {

				alarmFilteredVO.setElementId(BasicElementHelper.getIdFromElement(alarmFiltered.getElement()));
			}

			alarmFilteredVO.setFilteredUntil(alarmFiltered.getFilteredUntil());
			alarmFilteredVO.setEnable(alarmFiltered.getEnabled());
			alarmFilteredVO.setAlarmDisabled(alarmFiltered.getAlarmDisabled());
			
			if (alarmFiltered.getUserId() != null) {

				User user = new User();
				user.setId(alarmFiltered.getUserId());
				alarmFilteredVO.setUserId(BasicElementHelper.getIdFromElement(user));
			}
			
			alarmFilteredVO.setComment(alarmFiltered.getComment());
		}
	}
}
