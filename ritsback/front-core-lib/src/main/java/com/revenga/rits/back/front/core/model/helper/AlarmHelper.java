package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.front.core.model.vo.AlarmVO;

public class AlarmHelper {

	private AlarmHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static AlarmVO get(final Alarm alarm) {

		AlarmVO alarmVO = null;

		if (alarm != null) {

			alarmVO = new AlarmVO();

			assign(alarm, alarmVO);
		}

		return alarmVO;
	}

	public static Alarm get(final AlarmVO alarmVO) {

		Alarm alarm = null;

		if (alarmVO != null) {
		
			alarm = new Alarm();
			
			if (alarmVO.getId() != null) {

				Key key = new Key(alarmVO.getId());

				if (key.getClazz() == Alarm.class) {

					alarm.setId(Long.valueOf(key.getId(1)));
				}
			}
			
			//AlarmConfig
			if (alarmVO.getAlarmConfigId() != null) {

			Key key = new Key(alarmVO.getAlarmConfigId());

				if (key.getClazz() == AlarmConfig.class) {
	
					alarm.setAlarmConfigId(Long.valueOf(key.getId(1)));
				}
			}

			if (alarmVO.getAlarmConfig() != null) {

				AlarmConfig alarmConfig = AlarmConfigHelper.get(alarmVO.getAlarmConfig());

				if (alarmConfig != null) {

					if (alarmVO.getAlarmConfigId() == null) {

						alarm.setAlarmConfigId(alarmConfig.getId());
					}
					alarm.setAlarmConfig(alarmConfig);
				}
			}
				
			//SourceElement
			if (alarmVO.getSourceElementId() != null) {

			Key key = new Key(alarmVO.getSourceElementId());

				if (key.getClazz() == Element.class) {
	
					alarm.setSourceElementTypeId(Long.valueOf(key.getId(1)));
					alarm.setSourceElementId(Long.valueOf(key.getId(2)));
				}
			}

			if (alarmVO.getSourceElement() != null) {

				Element element = ElementHelper.get(alarmVO.getSourceElement());

				if (element != null) {

					if (alarmVO.getSourceElementId() == null) {

						alarm.setSourceElementTypeId(element.getElementTypeId());
						alarm.setSourceElementId(element.getId());
					}
					alarm.setSourceElement(element);
				}
			}
			
			//TargetElement
			if (alarmVO.getTargetElementId() != null) {

			Key key = new Key(alarmVO.getTargetElementId());

				if (key.getClazz() == Element.class) {
	
					alarm.setTargetElementTypeId(Long.valueOf(key.getId(1)));
					alarm.setTargetElementId(Long.valueOf(key.getId(2)));
				}
			}

			if (alarmVO.getTargetElement() != null) {

				Element element = ElementHelper.get(alarmVO.getTargetElement());

				if (element != null) {

					if (alarmVO.getTargetElementId() == null) {

						alarm.setTargetElementTypeId(element.getElementTypeId());
						alarm.setTargetElementId(element.getId());
					}
					alarm.setTargetElement(element);
				}
			}
			
			alarm.setRecognized(alarmVO.getRecognized());
			alarm.setRecognizedComments(alarmVO.getRecognizedComments());
			alarm.setActivation(alarmVO.getActivation());
			alarm.setActivationComments(alarmVO.getActivationComments());
			alarm.setDeactivation(alarmVO.getDeactivation());
			alarm.setDeactivationComments(alarmVO.getDeactivationComments());
			alarm.setFilteredUntil(alarmVO.getFilteredUntil());
			alarm.setDisagree(alarmVO.getDisagree());
			alarm.setCreatedAt(alarmVO.getCreatedAt());
		}
		return alarm;
	}

	public static void assign(final Alarm alarm, AlarmVO alarmVO) {

		if (ObjectUtils.allNotNull(alarm, alarmVO)) {

			BasicElementHelper.fillBasicProperties(alarmVO, alarm);

			// AlarmConfig
			if (alarm.getAlarmConfigId() != null) {

				AlarmConfig alarmConfig = new AlarmConfig();
				alarmConfig.setId(alarm.getAlarmConfigId());
				alarmVO.setAlarmConfigId(BasicElementHelper.getIdFromElement(alarmConfig));
			}
			alarmVO.setAlarmConfig(AlarmConfigHelper.get(alarm.getAlarmConfig()));

			if (alarm.getAlarmConfigId() == null && alarm.getAlarmConfig() != null
					&& alarm.getAlarmConfig().getId() != null) {

				alarmVO.setAlarmConfigId(BasicElementHelper.getIdFromElement(alarm.getAlarmConfig()));
			}

			// SourceElement
			if (ObjectUtils.allNotNull(alarm.getSourceElementId(), alarm.getSourceElementTypeId())) {

				Element element = new Element();
				element.setId(alarm.getSourceElementId());
				element.setElementTypeId(alarm.getSourceElementTypeId());

				alarmVO.setSourceElementId(BasicElementHelper.getIdFromElement(element));
			}
			alarmVO.setSourceElement(ElementHelper.get(alarm.getSourceElement()));

			if (alarm.getSourceElementId() == null && alarm.getSourceElement() != null
					&& alarm.getSourceElement().getId() != null
					&& alarm.getSourceElement().getElementTypeId() != null) {

				alarmVO.setSourceElementId(BasicElementHelper.getIdFromElement(alarm.getSourceElement()));
			}

			// TargetElement
			if (ObjectUtils.allNotNull(alarm.getTargetElementId(), alarm.getTargetElementTypeId())) {

				Element element = new Element();
				element.setId(alarm.getTargetElementId());
				element.setElementTypeId(alarm.getTargetElementTypeId());

				alarmVO.setTargetElementId(BasicElementHelper.getIdFromElement(element));
			}
			alarmVO.setTargetElement(ElementHelper.get(alarm.getTargetElement()));

			if (alarm.getTargetElementId() == null && alarm.getTargetElement() != null
					&& alarm.getTargetElement().getId() != null
					&& alarm.getTargetElement().getElementTypeId() != null) {

				alarmVO.setTargetElementId(BasicElementHelper.getIdFromElement(alarm.getTargetElement()));
			}

			if (alarm.getCreationUserId() != null) {

				User user = new User();
				user.setId(alarm.getCreationUserId());
				alarmVO.setCreationUserId(BasicElementHelper.getIdFromElement(user));
			}
			
			if (alarm.getRecognizedUserId() != null) {

				User user = new User();
				user.setId(alarm.getRecognizedUserId());
				alarmVO.setRecognizedUserId(BasicElementHelper.getIdFromElement(user));
			}			
			
			if (alarm.getDeactivationUserId() != null) {

				User user = new User();
				user.setId(alarm.getDeactivationUserId());
				alarmVO.setDeactivationUserId(BasicElementHelper.getIdFromElement(user));
			}
						
			alarmVO.setRecognized(alarm.getRecognized());
			alarmVO.setRecognizedComments(alarm.getRecognizedComments());
			alarmVO.setActivation(alarm.getActivation());
			alarmVO.setActivationComments(alarm.getActivationComments());
			alarmVO.setDeactivation(alarm.getDeactivation());
			alarmVO.setDeactivationComments(alarm.getDeactivationComments());
			alarmVO.setFilteredUntil(alarm.getFilteredUntil());
			alarmVO.setDisagree(alarm.getDisagree());
			alarmVO.setCreatedAt(alarm.getCreatedAt());
		}

	}

}
