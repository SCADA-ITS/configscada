package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.AlarmConfigMeasure;
import com.revenga.rits.back.data.core.model.AlarmLevel;
import com.revenga.rits.back.data.core.model.AlarmTargetElement;
import com.revenga.rits.back.data.core.model.AlarmType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementStateAlarm;
import com.revenga.rits.back.data.core.model.ElementSubtype;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigMeasureVO;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigVO;
import com.revenga.rits.back.front.core.model.vo.AlarmTargetElementVO;
import com.revenga.rits.back.front.core.model.vo.ElementStateAlarmVO;

public class AlarmConfigHelper {

	private AlarmConfigHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());

	}

	public static AlarmConfigVO get(final AlarmConfig alarmConfig) {

		AlarmConfigVO alarmConfigVO = null;

		if (alarmConfig != null) {

			alarmConfigVO = new AlarmConfigVO();

			assign(alarmConfig, alarmConfigVO);
		}

		return alarmConfigVO;
	}

	public static AlarmConfig get(final AlarmConfigVO alarmConfigVO) {

		AlarmConfig alarmConfig = null;

		if (alarmConfigVO != null) {

			alarmConfig = new AlarmConfig();
			if (alarmConfigVO.getId() != null) {

				Key key = new Key(alarmConfigVO.getId());

				if (key.getClazz() == AlarmConfig.class) {

					alarmConfig.setId(Long.valueOf(key.getId(1)));
				}
			}

			alarmConfig.setAlias(alarmConfigVO.getAlias());
			alarmConfig.setDescription(alarmConfigVO.getDescription());
			alarmConfig.setLabelAlias(alarmConfigVO.getLabelAlias());
			alarmConfig.setLabelDescription(alarmConfigVO.getLabelDescription());
			alarmConfig.setAlarmType(AlarmTypeHelper.get(alarmConfigVO.getAlarmType()));
			alarmConfig.setMinTimeAlarmed(alarmConfigVO.getMinTimeAlarmed());
			alarmConfig.setThresholdTime(alarmConfigVO.getThresholdTime());
			
			if (alarmConfigVO.getAlarmTypeId() != null) {

				Key key = new Key(alarmConfigVO.getAlarmTypeId());

				if (key.getClazz() == AlarmType.class) {

					alarmConfig.setAlarmTypeId(Long.valueOf(key.getId(1)));
				}
			}
			
			alarmConfig.setAlarmLevel(AlarmLevelHelper.get(alarmConfigVO.getAlarmLevel()));
			
			if (alarmConfigVO.getAlarmLevelId() != null) {

				Key key = new Key(alarmConfigVO.getAlarmLevelId());

				if (key.getClazz() == AlarmLevel.class) {

					alarmConfig.setAlarmLevelId(Long.valueOf(key.getId(1)));
				}
			}
			
			alarmConfig.setMine(alarmConfigVO.getMine());
			alarmConfig.setTreatment(alarmConfigVO.getTreatment());

			if (alarmConfigVO.getElementTypeId() != null) {

				Key key = new Key(alarmConfigVO.getElementTypeId());

				if (key.getClazz() == ElementType.class) {

					alarmConfig.setElementTypeId(Long.valueOf(key.getId(1)));
				}
			}

			if (alarmConfigVO.getElementType() != null) {

				ElementType elementType = ElementTypeHelper.get(alarmConfigVO.getElementType());

				if (elementType != null) {

					if (alarmConfigVO.getElementTypeId() == null) {

						alarmConfig.setElementTypeId(elementType.getId());
					}
					alarmConfig.setElementType(elementType);
				}
			}
			
			if (alarmConfigVO.getElementSubtypeId() != null) {

				Key key = new Key(alarmConfigVO.getElementSubtypeId());

				if (key.getClazz() == ElementSubtype.class) {

					alarmConfig.setElementSubtypeId(Long.valueOf(key.getId(1)));
				}
			}
			
			if (alarmConfigVO.getElementSubtype() != null) {

				ElementSubtype elementSubtype = ElementSubtypeHelper.get(alarmConfigVO.getElementSubtype());

				if (elementSubtype != null) {

					if (alarmConfigVO.getElementSubtypeId() == null) {

						alarmConfig.setElementSubtypeId(elementSubtype.getId());
					}
					alarmConfig.setElementSubtype(elementSubtype);
				}
			}
			
			alarmConfig.setElementTypeState(ElementTypeStateHelper.get(alarmConfigVO.getElementTypeState()));
			
			if (alarmConfigVO.getElementTypeStateId() != null) {
				
				Key key = new Key(alarmConfigVO.getElementTypeStateId());

				if (key.getClazz() == ElementTypeState.class) {

					alarmConfig.setElementTypeStateId(Long.valueOf(key.getId(2)));
				}
			}

			if (alarmConfigVO.getElementId() != null) {

				Key key = new Key(alarmConfigVO.getElementId());

				if (key.getClazz() == Element.class) {

					alarmConfig.setElementTypeId(Long.valueOf(key.getId(1)));
					alarmConfig.setElementId(Long.valueOf(key.getId(2)));
				}
			}

			if (alarmConfigVO.getElement() != null) {

				Element element = ElementHelper.get(alarmConfigVO.getElement());

				if (element != null) {

					if (alarmConfigVO.getElementId() == null) {

						alarmConfig.setElementId(element.getId());
						alarmConfig.setElementTypeId(element.getElementTypeId());
					}
					alarmConfig.setElement(element);
				}
			}

			if (alarmConfigVO.getAlarmConfigMeasures() != null) {

				List<AlarmConfigMeasure> alarmConfigMeasures = new ArrayList<>();

				for (AlarmConfigMeasureVO alarmConfigMeasureVO : alarmConfigVO.getAlarmConfigMeasures()) {

					AlarmConfigMeasure alarmConfigMeasure = AlarmConfigMeasureHelper.get(alarmConfigMeasureVO);

					alarmConfigMeasures.add(alarmConfigMeasure);
				}

				alarmConfig.setAlarmConfigMeasures(alarmConfigMeasures);
			}

			if (alarmConfigVO.getAlarmTargetElements() != null) {

				List<AlarmTargetElement> alarmTargetElements = new ArrayList<>();

				for (AlarmTargetElementVO alarmTargetElementVO : alarmConfigVO.getAlarmTargetElements()) {

					AlarmTargetElement alarmTargetElement = AlarmTargetElementHelper.get(alarmTargetElementVO);

					alarmTargetElements.add(alarmTargetElement);
				}

				alarmConfig.setAlarmTargetElements(alarmTargetElements);
			}
			
			if (alarmConfigVO.getAlarmConfigStates() != null) {

				List<ElementStateAlarm> elementStatesAlarm = new ArrayList<>();

				for (ElementStateAlarmVO elementStateAlarmVO : alarmConfigVO.getAlarmConfigStates()) {

					ElementStateAlarm elementStateAlarm = ElementStateAlarmHelper.get(elementStateAlarmVO);

					elementStatesAlarm.add(elementStateAlarm);
				}

				alarmConfig.setAlarmConfigStates(elementStatesAlarm);			}

		}

		return alarmConfig;
	}

	public static void assign(final AlarmConfig alarmConfig, AlarmConfigVO alarmConfigVO) {

		if (ObjectUtils.allNotNull(alarmConfig, alarmConfigVO)) {

			BasicElementHelper.fillBasicProperties(alarmConfigVO, alarmConfig);
			
			if (alarmConfig.getAlarmTypeId() != null) {
			
				AlarmType alarmType = new AlarmType();
				alarmType.setId(alarmConfig.getAlarmTypeId());
				alarmConfigVO.setAlarmTypeId(BasicElementHelper.getIdFromElement(alarmType));
			}
			
			alarmConfigVO.setAlarmType(AlarmTypeHelper.get(alarmConfig.getAlarmType()));
			
			if (alarmConfig.getAlarmLevelId() != null) {
				
				AlarmLevel alarmLevel = new AlarmLevel();
				alarmLevel.setId(alarmConfig.getAlarmLevelId());
				alarmConfigVO.setAlarmLevelId(BasicElementHelper.getIdFromElement(alarmLevel));
			}
			
			alarmConfigVO.setAlarmLevel(AlarmLevelHelper.get(alarmConfig.getAlarmLevel()));
			
			alarmConfigVO.setMine(alarmConfig.getMine());
			alarmConfigVO.setTreatment(alarmConfig.getTreatment());
			
			alarmConfigVO.setMinTimeAlarmed(alarmConfig.getMinTimeAlarmed());
			alarmConfigVO.setThresholdTime(alarmConfig.getThresholdTime());

			if (alarmConfig.getElementTypeId() != null) {

				ElementType elementType = new ElementType();
				elementType.setId(alarmConfig.getElementTypeId());
				alarmConfigVO.setElementTypeId(BasicElementHelper.getIdFromElement(elementType));
			}

			alarmConfigVO.setElementType(ElementTypeHelper.get(alarmConfig.getElementType()));
			
			if (alarmConfig.getElementSubtypeId() != null) {

				ElementSubtype elementSubtype = new ElementSubtype();
				elementSubtype.setId(alarmConfig.getElementSubtypeId());
				alarmConfigVO.setElementSubtypeId(BasicElementHelper.getIdFromElement(elementSubtype));
			}

			alarmConfigVO.setElementSubtype(ElementSubtypeHelper.get(alarmConfig.getElementSubtype()));

			if (alarmConfig.getElementId() != null) {

				Element element = new Element();
				element.setId(alarmConfig.getElementId());
				element.setElementTypeId(alarmConfig.getElementTypeId());
				alarmConfigVO.setElementId(BasicElementHelper.getIdFromElement(element));
			}
			alarmConfigVO.setElement(ElementHelper.get(alarmConfig.getElement()));
			
			if (alarmConfig.getElementTypeStateId() != null) {
				
				ElementTypeState elementTypeState = new ElementTypeState();
				elementTypeState.setElementTypeId(alarmConfig.getElementTypeId());
				elementTypeState.setStateId(alarmConfig.getElementTypeStateId());
				
				alarmConfigVO.setElementTypeStateId(BasicElementHelper.getIdFromElement(elementTypeState));
			}
			
			alarmConfigVO.setElementTypeState(ElementTypeStateHelper.get(alarmConfig.getElementTypeState()));

			ModelVOHelper.translate(alarmConfigVO);
		}

	}

}
