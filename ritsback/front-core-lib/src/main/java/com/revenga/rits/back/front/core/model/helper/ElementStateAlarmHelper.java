package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ElementStateAlarm;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.front.core.model.vo.ElementStateAlarmVO;

public class ElementStateAlarmHelper {

	private ElementStateAlarmHelper() {

		super();
	}

	public static ElementStateAlarmVO get(ElementStateAlarm elementStateAlarm) {

		ElementStateAlarmVO elementStateAlarmVO = null;

		if (elementStateAlarm != null) {

			elementStateAlarmVO = new ElementStateAlarmVO();

			assign(elementStateAlarm, elementStateAlarmVO);
		}

		return elementStateAlarmVO;
	}

	public static ElementStateAlarm get(ElementStateAlarmVO elementStateAlarmVO) {

		ElementStateAlarm elementStateAlarm = null;

		if (elementStateAlarmVO != null) {

			elementStateAlarm = new ElementStateAlarm();

			if (elementStateAlarmVO.getElementTypeId() != null) {

				Key key = new Key(elementStateAlarmVO.getElementTypeId());

				if (key.getClazz() == ElementType.class) {

					elementStateAlarm.setElementTypeId(Long.valueOf(key.getId(1)));
				}
			}
			
			if (elementStateAlarmVO.getStateId() != null) {

				Key key = new Key(elementStateAlarmVO.getStateId());

				if (key.getClazz() == ElementTypeState.class) {

					elementStateAlarm.setStateId(Long.valueOf(key.getId(2)));
				}
			}

			elementStateAlarm.setAlias(elementStateAlarmVO.getAlias());
			elementStateAlarm.setDescription((elementStateAlarmVO.getDescription()));
			elementStateAlarm.setAlarmConfig(AlarmConfigHelper.get(elementStateAlarmVO.getAlarmConfig()));
		}

		return elementStateAlarm;
	}

	public static void assign(final ElementStateAlarm elementStateAlarm, ElementStateAlarmVO elementStateAlarmVO) {

		if (elementStateAlarm != null && elementStateAlarmVO != null) {

			BasicElementHelper.fillBasicProperties(elementStateAlarmVO, elementStateAlarm);
			
			if (elementStateAlarm.getElementTypeId() != null) {
				
				ElementType elementType = new ElementType();
				elementType.setId(elementStateAlarm.getElementTypeId());
				elementStateAlarmVO.setElementTypeId(BasicElementHelper.getIdFromElement(elementType));
				
				elementStateAlarmVO.setElementType(ElementTypeHelper.get(elementType));
			}
			
			if (elementStateAlarm.getStateId() != null) {
				
				ElementTypeState elementTypeState = new ElementTypeState();
				elementTypeState.setElementTypeId(elementStateAlarm.getElementTypeId());
				elementTypeState.setStateId(elementStateAlarm.getStateId());
				elementStateAlarmVO.setStateId(BasicElementHelper.getIdFromElement(elementTypeState));
				
				elementStateAlarmVO.setElementTypeState(ElementTypeStateHelper.get(elementTypeState));
			}
		}
	}
}
