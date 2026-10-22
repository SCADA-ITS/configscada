package com.revenga.rits.back.service.graphql.type;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmConditionAlarm;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmConditionAlarmType;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentAlarmConditionAlarmHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentAlarmConditionAlarmTypeVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentAlarmConditionAlarmVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
public class ImsIncidentAlarmConditionAlarmGraphql extends ImsIncidentAlarmConditionAlarmVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1796800459643239257L;
	
	private ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarm;

	public ImsIncidentAlarmConditionAlarmGraphql(final ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarm) {

		ImsIncidentAlarmConditionAlarmHelper.assign(imsIncidentAlarmConditionAlarm, this);
		this.imsIncidentAlarmConditionAlarm = imsIncidentAlarmConditionAlarm;

	}
	
	@Override
	public AlarmConfigVO getAlarmConfig() {

		if (super.getAlarmConfig() == null && ObjectUtils.allNotNull(imsIncidentAlarmConditionAlarm)) {

			AlarmConfig alarmConfig = EntitiesManager.getInstance().getAlarmConfigRepository()
					.get(imsIncidentAlarmConditionAlarm.getAlarmConfigId(), false);

			if (alarmConfig != null) {

				super.setAlarmConfig(new AlarmConfigGraphql(alarmConfig));
			}
		}

		return super.getAlarmConfig();
	}

	
	@Override
	public ElementTypeVO getElementType() {

		if (super.getElementType() == null) {

			if (imsIncidentAlarmConditionAlarm != null && imsIncidentAlarmConditionAlarm.getElementTypeId() != null) {

				ElementType elementType = EntitiesManager.getInstance().getElementTypeRepository()
						.get(imsIncidentAlarmConditionAlarm.getElementTypeId(), false);

				if (elementType != null) {

					super.setElementType(new ElementTypeGraphql(elementType));
				}

			}

		}

		return super.getElementType();
	}

	@Override
	public ElementVO getElement() {

		if (super.getElement() == null) {

			if (imsIncidentAlarmConditionAlarm != null
					&& ObjectUtils.allNotNull(imsIncidentAlarmConditionAlarm.getElementTypeId(), imsIncidentAlarmConditionAlarm.getElementId())) {

				Element element = EntitiesManager.getInstance().getElementRepository()
						.get(imsIncidentAlarmConditionAlarm.getElementTypeId(), imsIncidentAlarmConditionAlarm.getElementId(), false);

				if (element != null) {

					super.setElement(new ElementGraphql(element));
				}

			}

		}

		return super.getElement();
	}
	
	@Override
	public ImsIncidentAlarmConditionAlarmTypeVO getIncidentAlarmConditionAlarmType() {

		if (super.getIncidentAlarmConditionAlarmType() == null) {

			if (imsIncidentAlarmConditionAlarm != null
					&& ObjectUtils.allNotNull(imsIncidentAlarmConditionAlarm.getIncidentAlarmConditionAlarmTypeId())) {

				ImsIncidentAlarmConditionAlarmType imsIncidentAlarmConditionAlarmType = EntitiesManager.getInstance().getImsIncidentAlarmConditionAlarmTypeRepository()
						.get(imsIncidentAlarmConditionAlarm.getIncidentAlarmConditionAlarmTypeId(), false);

				if (imsIncidentAlarmConditionAlarmType != null) {

					super.setIncidentAlarmConditionAlarmType(new ImsIncidentAlarmConditionAlarmTypeGraphql(imsIncidentAlarmConditionAlarmType));
				}

			}

		}

		return super.getIncidentAlarmConditionAlarmType();
	}

}
