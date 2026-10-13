package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.ImsIncidentAlarmCondition;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmConditionAlarm;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.RoadImpact;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentAlarmConditionHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentAlarmConditionAlarmVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentAlarmConditionVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeVO;
import com.revenga.rits.back.front.core.model.vo.RoadImpactVO;
import com.revenga.rits.back.front.core.model.vo.StretchVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class ImsIncidentAlarmConditionGraphql extends ImsIncidentAlarmConditionVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 430479695032514841L;

	private ImsIncidentAlarmCondition imsIncidentAlarmCondition;

	public ImsIncidentAlarmConditionGraphql(final ImsIncidentAlarmCondition imsIncidentAlarmCondition) {

		ImsIncidentAlarmConditionHelper.assign(imsIncidentAlarmCondition, this);
		this.imsIncidentAlarmCondition = imsIncidentAlarmCondition;

	}

	@Override
	public StretchVO getStretch() {

		if (super.getStretch() == null && imsIncidentAlarmCondition != null
				&& imsIncidentAlarmCondition.getStretchId() != null) {

			Stretch stretch = EntitiesManager.getInstance().getStretchRepository()
					.get(imsIncidentAlarmCondition.getStretchId(), false);

			if (stretch != null) {

				super.setStretch(new StretchGraphql(stretch));
			}
		}

		return super.getStretch();
	}
	
	@Override
	public ImsIncidentTypeVO getIncidentType() {

		if (super.getStretch() == null && imsIncidentAlarmCondition != null
				&& imsIncidentAlarmCondition.getIncidentTypeId() != null) {

			ImsIncidentType imsIncidentType = EntitiesManager.getInstance().getImsIncidentTypeRepository()
					.get(imsIncidentAlarmCondition.getIncidentTypeId(), false);

			if (imsIncidentType != null) {

				super.setIncidentType(new ImsIncidentTypeGraphql(imsIncidentType));
			}
		}

		return super.getIncidentType();
	}
	
	@Override
	public RoadImpactVO getRoadImpact() {

		if (super.getRoadImpact() == null && imsIncidentAlarmCondition != null && imsIncidentAlarmCondition.getRoadImpactId() != null) {

			RoadImpact roadImpact = EntitiesManager.getInstance().getRoadImpactRepository()
					.get(imsIncidentAlarmCondition.getRoadImpactId(), false);

			if (roadImpact != null) {

				super.setRoadImpact(new RoadImpactGraphql(roadImpact));
			}
		}

		return super.getRoadImpact();
	}
	
	@Override
	public List<ImsIncidentAlarmConditionAlarmVO> getIncidentAlarmConditionAlarms() {

		if (super.getIncidentAlarmConditionAlarms() == null) {

			if (imsIncidentAlarmCondition != null) {

				List<ImsIncidentAlarmConditionAlarm> imsIncidentAlarmConditionAlarms = EntitiesManager.getInstance().getImsIncidentAlarmConditionAlarmRepository()
						.getsByIncidentAlarmCondition(imsIncidentAlarmCondition.getId(), false);

				if (!CollectionUtils.isEmpty(imsIncidentAlarmConditionAlarms)) {

					List<ImsIncidentAlarmConditionAlarmVO> imsIncidentAlarmConditionAlarmsVO = new ArrayList<>();

					for (ImsIncidentAlarmConditionAlarm imsIncidentAlarmConditionAlarm : imsIncidentAlarmConditionAlarms) {

						imsIncidentAlarmConditionAlarmsVO.add(new ImsIncidentAlarmConditionAlarmGraphql(imsIncidentAlarmConditionAlarm));
					}
					super.setIncidentAlarmConditionAlarms(imsIncidentAlarmConditionAlarmsVO);
				}

			} else {

				log.error("imsIncidentAlarmCondition has null pointer");
			}
		}

		return super.getIncidentAlarmConditionAlarms();
	}
	
}
