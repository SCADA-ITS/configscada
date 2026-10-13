package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.AlarmTargetElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.front.core.model.vo.AlarmTargetElementVO;

public class AlarmTargetElementHelper {

	private AlarmTargetElementHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static AlarmTargetElementVO get(final AlarmTargetElement alarmTargetElement) {

		AlarmTargetElementVO alarmTargetElementVO = null;

		if (alarmTargetElement != null) {

			alarmTargetElementVO = new AlarmTargetElementVO();

			assign(alarmTargetElement, alarmTargetElementVO);
		}

		return alarmTargetElementVO;
	}

	public static AlarmTargetElement get(final AlarmTargetElementVO alarmTargetElementVO) {

		AlarmTargetElement alarmTargetElement = null;

		if (alarmTargetElementVO != null) {

			alarmTargetElement = new AlarmTargetElement();

			if (alarmTargetElementVO.getSourceElementId() != null) {

				Key key = new Key(alarmTargetElementVO.getSourceElementId());

				if (key.getClazz() == Element.class) {

					alarmTargetElement.setSourceElementTypeId(Long.valueOf(key.getId(1)));
					alarmTargetElement.setSourceElementId(Long.valueOf(key.getId(2)));
					
					Element element = new Element();
					element.setId(alarmTargetElement.getSourceElementId());
					element.setElementTypeId(alarmTargetElement.getSourceElementTypeId());
					
					alarmTargetElementVO.setSourceElement(ElementHelper.get(element));
				}
			}

			if (alarmTargetElementVO.getSourceElement() != null) {

				Element element = ElementHelper.get(alarmTargetElementVO.getSourceElement());

				if (element != null) {

					if (alarmTargetElementVO.getSourceElementId() == null) {

						alarmTargetElement.setSourceElementId(element.getId());
						alarmTargetElement.setSourceElementTypeId(element.getElementTypeId());
					}
					alarmTargetElement.setSourceElement(element);
				}
			}

			if (alarmTargetElementVO.getTargetElementId() != null) {

				Key key = new Key(alarmTargetElementVO.getTargetElementId());

				if (key.getClazz() == Element.class) {

					alarmTargetElement.setTargetElementTypeId(Long.valueOf(key.getId(1)));
					alarmTargetElement.setTargetElementId(Long.valueOf(key.getId(2)));

					Element element = new Element();
					element.setId(alarmTargetElement.getTargetElementId());
					element.setElementTypeId(alarmTargetElement.getTargetElementTypeId());
					
					alarmTargetElementVO.setTargetElement(ElementHelper.get(element));
				}
			}

			if (alarmTargetElementVO.getTargetElement() != null) {

				Element element = ElementHelper.get(alarmTargetElementVO.getTargetElement());

				if (element != null) {

					if (alarmTargetElementVO.getTargetElementId() == null) {

						alarmTargetElement.setTargetElementId(element.getId());
						alarmTargetElement.setTargetElementTypeId(element.getElementTypeId());
					}
					alarmTargetElement.setTargetElement(element);
				}
			}

			if (alarmTargetElementVO.getAlarmConfigId() != null) {

				Key key = new Key(alarmTargetElementVO.getAlarmConfigId());

				if (key.getClazz() == AlarmConfig.class) {

					alarmTargetElement.setAlarmConfigId(Long.valueOf(key.getId(1)));
				}
			}

			if (alarmTargetElementVO.getId() != null) {

				Key key = new Key(alarmTargetElementVO.getId());

				if (key.getClazz() == AlarmTargetElement.class) {

					alarmTargetElement.setAlarmConfigId(Long.valueOf(key.getId(1)));
					alarmTargetElement.setSourceElementTypeId(Long.valueOf(key.getId(2)));
					alarmTargetElement.setSourceElementId(Long.valueOf(key.getId(3)));
					alarmTargetElement.setTargetElementTypeId(Long.valueOf(key.getId(4)));
					alarmTargetElement.setTargetElementId(Long.valueOf(key.getId(5)));

				}
			}

		}

		return alarmTargetElement;
	}

	public static void assign(final AlarmTargetElement alarmTargetElement, AlarmTargetElementVO alarmTargetElementVO) {

		if (ObjectUtils.allNotNull(alarmTargetElement, alarmTargetElementVO)) {

			BasicElementHelper.fillBasicProperties(alarmTargetElementVO, alarmTargetElement);

		}

	}
}
