package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

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
import com.revenga.rits.back.front.core.model.helper.AlarmConfigHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigMeasureVO;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigVO;
import com.revenga.rits.back.front.core.model.vo.AlarmLevelVO;
import com.revenga.rits.back.front.core.model.vo.AlarmTargetElementVO;
import com.revenga.rits.back.front.core.model.vo.AlarmTypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementStateAlarmVO;
import com.revenga.rits.back.front.core.model.vo.ElementSubtypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class AlarmConfigGraphql extends AlarmConfigVO {

	/**
	 * 
	 */

	private static final long serialVersionUID = -5081806387727870958L;

	private AlarmConfig alarmConfig;

	public AlarmConfigGraphql(final AlarmConfig alarmConfig) {

		AlarmConfigHelper.assign(alarmConfig, this);

		this.alarmConfig = alarmConfig;
	}

	@Override
	public ElementTypeVO getElementType() {

		if (super.getElementType() == null) {

			if (alarmConfig != null && alarmConfig.getElementTypeId() != null) {

				ElementType elementType = EntitiesManager.getInstance().getElementTypeRepository()
						.get(alarmConfig.getElementTypeId(), false);

				if (elementType != null) {

					super.setElementType(new ElementTypeGraphql(elementType));
				}

			}

		}

		return super.getElementType();
	}
	
	@Override
	public ElementSubtypeVO getElementSubtype() {

		if (super.getElementSubtype() == null) {

			if (alarmConfig != null && alarmConfig.getElementSubtypeId() != null && alarmConfig.getElementTypeId() != null) {

				ElementSubtype elementSubtype = EntitiesManager.getInstance().getElementSubtypeRepository()
						.get(alarmConfig.getElementSubtypeId(), alarmConfig.getElementTypeId(), false);

				if (elementSubtype != null) {

					super.setElementSubtype(new ElementSubtypeGraphql(elementSubtype));
				}
			}
		}

		return super.getElementSubtype();
	}

	@Override
	public ElementVO getElement() {

		if (super.getElement() == null) {

			if (alarmConfig != null
					&& ObjectUtils.allNotNull(alarmConfig.getElementTypeId(), alarmConfig.getElementId())) {

				Element element = EntitiesManager.getInstance().getElementRepository()
						.get(alarmConfig.getElementTypeId(), alarmConfig.getElementId(), false);

				if (element != null) {

					super.setElement(new ElementGraphql(element));
				}

			}

		}

		return super.getElement();
	}
	
	@Override
	public AlarmLevelVO getAlarmLevel() {

		if (super.getAlarmLevel() == null && alarmConfig != null && alarmConfig.getAlarmLevelId() != null) {

			AlarmLevel alarmLevel = EntitiesManager.getInstance().getAlarmLevelRepository().get(alarmConfig.getAlarmLevelId(), false);

			if (alarmLevel != null) {

				super.setAlarmLevel(new AlarmLevelGraphql(alarmLevel));
			}
		}

		return super.getAlarmLevel();
	}
	
	@Override
	public ElementTypeStateVO getElementTypeState() {

		if (super.getElementTypeState() == null && alarmConfig != null && alarmConfig.getElementTypeStateId() != null) {

			ElementTypeState elementTypeState = EntitiesManager.getInstance().getElementTypeStateRepository()
					.get(alarmConfig.getElementTypeId(), alarmConfig.getElementTypeStateId(), false);

			if (elementTypeState != null) {

				super.setElementTypeState(new ElementTypeStateGraphql(elementTypeState));
			}
		}

		return super.getElementTypeState();
	}
	
	@Override
	public AlarmTypeVO getAlarmType() {

		if (super.getAlarmType() == null && alarmConfig != null && alarmConfig.getAlarmTypeId() != null) {

			AlarmType alarmType = EntitiesManager.getInstance().getAlarmTypeRepository().get(alarmConfig.getAlarmTypeId(), false);

			if (alarmType != null) {

				super.setAlarmType(new AlarmTypeGraphql(alarmType));
			}
		}

		return super.getAlarmType();
	}

	@Override
	public List<AlarmConfigMeasureVO> getAlarmConfigMeasures() {

		if (super.getAlarmConfigMeasures() == null) {

			if (alarmConfig != null) {

				List<AlarmConfigMeasure> alarmConfigMeasurese = EntitiesManager.getInstance()
						.getAlarmConfigMeasureRepository().getsByAlarmConfig(alarmConfig.getId(), false);

				if (!CollectionUtils.isEmpty(alarmConfigMeasurese)) {

					List<AlarmConfigMeasureVO> alarmConfigMeasuresVO = new ArrayList<>();

					for (AlarmConfigMeasure alarmConfigMeasure : alarmConfigMeasurese) {

						alarmConfigMeasuresVO.add(new AlarmConfigMeasureGraphql(alarmConfigMeasure));
					}
					super.setAlarmConfigMeasures(alarmConfigMeasuresVO);
				}

			} else {

				log.error("AlarmConfig has null pointer");
			}
		}

		return super.getAlarmConfigMeasures();

	}
	
	@Override
	public List<ElementStateAlarmVO> getAlarmConfigStates() {

		if (super.getAlarmConfigStates() == null) {

			if (alarmConfig != null) {

				List<ElementStateAlarm> elementStatesAlarm = EntitiesManager.getInstance()
						.getElementStateAlarmRepository().getsByAlarmConfig(alarmConfig.getId(), false);

				if (!CollectionUtils.isEmpty(elementStatesAlarm)) {

					List<ElementStateAlarmVO> elementStatesAlarmVO = new ArrayList<>();

					for (ElementStateAlarm elementStateAlarm : elementStatesAlarm) {

						elementStatesAlarmVO.add(new ElementStateAlarmGraphql(elementStateAlarm));
					}
					super.setAlarmConfigStates(elementStatesAlarmVO);				}

			} else {

				log.error("AlarmConfig has null pointer");
			}
		}

		return super.getAlarmConfigStates();

	}

	@Override
	public List<AlarmTargetElementVO> getAlarmTargetElements() {

		if (super.getAlarmTargetElements() == null) {

			if (alarmConfig != null) {

				List<AlarmTargetElement> alarmTargetElements = EntitiesManager.getInstance()
						.getAlarmTargetElementRepository().getsByAlarmConfig(alarmConfig.getId(), false);

				if (!CollectionUtils.isEmpty(alarmTargetElements)) {

					List<AlarmTargetElementVO> alarmTargetElementsVO = new ArrayList<>();

					for (AlarmTargetElement alarmTargetElement : alarmTargetElements) {

						alarmTargetElementsVO.add(new AlarmTargetElementGraphql(alarmTargetElement));
					}
					super.setAlarmTargetElements(alarmTargetElementsVO);
				}

			} else {

				log.error("AlarmConfig has null pointer");
			}
		}

		return super.getAlarmTargetElements();
	}
}
