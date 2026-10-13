package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentState;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsTaskType;
import com.revenga.rits.back.data.core.model.RoadImpact;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeTaskVO;

public class ImsIncidentTypeTaskHelper {

	private ImsIncidentTypeTaskHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentTypeTaskVO get(final ImsIncidentTypeTask imsIncidentTypeTask) {

		ImsIncidentTypeTaskVO imsIncidentTypeTaskVO = null;

		if (imsIncidentTypeTask != null) {

			imsIncidentTypeTaskVO = new ImsIncidentTypeTaskVO();

			assign(imsIncidentTypeTask, imsIncidentTypeTaskVO);
		}

		return imsIncidentTypeTaskVO;
	}

	public static ImsIncidentTypeTask get(final ImsIncidentTypeTaskVO imsIncidentTypeTaskVO) {

		ImsIncidentTypeTask imsIncidentTypeTask = null;

		if (imsIncidentTypeTaskVO != null) {

			imsIncidentTypeTask = new ImsIncidentTypeTask();
			if (imsIncidentTypeTaskVO.getId() != null) {

				Key key = new Key(imsIncidentTypeTaskVO.getId());

				if (key.getClazz() == ImsIncidentTypeTask.class) {

					imsIncidentTypeTask.setId(Long.valueOf(key.getId(1)));
				}
			}

			imsIncidentTypeTask.setPosition(imsIncidentTypeTaskVO.getPosition());
			imsIncidentTypeTask.setOperationDescription(imsIncidentTypeTaskVO.getOperationDescription());
			imsIncidentTypeTask.setMandatory(imsIncidentTypeTaskVO.getMandatory());
			imsIncidentTypeTask.setAuto(imsIncidentTypeTaskVO.getAuto());
			imsIncidentTypeTask.setOnChange(imsIncidentTypeTaskVO.getOnChange());
			imsIncidentTypeTask.setRerun(imsIncidentTypeTaskVO.getRerun());
			imsIncidentTypeTask.setAlias(imsIncidentTypeTaskVO.getAlias());
			
			if (imsIncidentTypeTaskVO.getDelay() != null) {
				
				imsIncidentTypeTask.setDelay(imsIncidentTypeTaskVO.getDelay().longValue());
			}
			

			if (imsIncidentTypeTaskVO.getIncidentTypeId() != null) {

				Key key = new Key(imsIncidentTypeTaskVO.getIncidentTypeId());

				if (key.getClazz() == ImsIncidentType.class) {

					imsIncidentTypeTask.setIncidentTypeId(Long.valueOf(key.getId(1)));
				}
			}
			
			if (imsIncidentTypeTaskVO.getStretchId() != null) {

				Key key = new Key(imsIncidentTypeTaskVO.getStretchId());

				if (key.getClazz() == Stretch.class) {

					imsIncidentTypeTask.setStretchId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsIncidentTypeTaskVO.getTaskTypeId() != null) {

				Key key = new Key(imsIncidentTypeTaskVO.getTaskTypeId());

				if (key.getClazz() == ImsTaskType.class) {

					imsIncidentTypeTask.setTaskTypeId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsIncidentTypeTaskVO.getIncidentStateId() != null) {

				Key key = new Key(imsIncidentTypeTaskVO.getIncidentStateId());

				if (key.getClazz() == ImsIncidentState.class) {

					imsIncidentTypeTask.setIncidentStateId(Long.valueOf(key.getId(1)));
				}
			}
			
			if (imsIncidentTypeTaskVO.getRoadImpactId() != null) {

				Key key = new Key(imsIncidentTypeTaskVO.getRoadImpactId());

				if (key.getClazz() == RoadImpact.class) {

					imsIncidentTypeTask.setRoadImpactId(Long.valueOf(key.getId(1)));
				}
			}
		}

		return imsIncidentTypeTask;
	}

	public static void assign(final ImsIncidentTypeTask imsIncidentTypeTask,
			ImsIncidentTypeTaskVO imsIncidentTypeTaskVO) {

		if (ObjectUtils.allNotNull(imsIncidentTypeTask, imsIncidentTypeTaskVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentTypeTaskVO, imsIncidentTypeTask);

			imsIncidentTypeTaskVO.setPosition(imsIncidentTypeTask.getPosition());
			imsIncidentTypeTaskVO.setOperationDescription(imsIncidentTypeTask.getOperationDescription());
			imsIncidentTypeTaskVO.setMandatory(imsIncidentTypeTask.getMandatory());
			imsIncidentTypeTaskVO.setAuto(imsIncidentTypeTask.getAuto());
			imsIncidentTypeTaskVO.setOnChange(imsIncidentTypeTask.getOnChange());
			imsIncidentTypeTaskVO.setAlias(imsIncidentTypeTask.getAlias());
			
			if (imsIncidentTypeTask.getDelay() != null) {
				
				imsIncidentTypeTaskVO.setDelay(imsIncidentTypeTask.getDelay().intValue());	
			}
			
			imsIncidentTypeTaskVO.setRerun(imsIncidentTypeTask.getRerun());

			if (imsIncidentTypeTask.getIncidentTypeId() != null) {

				ImsIncidentType imsIncidentType = new ImsIncidentType();
				imsIncidentType.setId(imsIncidentTypeTask.getIncidentTypeId());
				imsIncidentTypeTaskVO.setIncidentTypeId(BasicElementHelper.getIdFromElement(imsIncidentType));
			}
			
			if (imsIncidentTypeTask.getStretchId() != null) {

				Stretch stretch = new Stretch();
				stretch.setId(imsIncidentTypeTask.getStretchId());
				imsIncidentTypeTaskVO.setStretchId(BasicElementHelper.getIdFromElement(stretch));
			}

			if (imsIncidentTypeTask.getIncidentStateId() != null) {

				ImsIncidentState imsIncidentState = new ImsIncidentState();
				imsIncidentState.setId(imsIncidentTypeTask.getIncidentStateId());
				imsIncidentTypeTaskVO.setIncidentStateId(BasicElementHelper.getIdFromElement(imsIncidentState));
			}

			if (imsIncidentTypeTask.getTaskTypeId() != null) {

				ImsTaskType imsTaskType = new ImsTaskType();
				imsTaskType.setId(imsIncidentTypeTask.getTaskTypeId());
				imsIncidentTypeTaskVO.setTaskTypeId(BasicElementHelper.getIdFromElement(imsTaskType));
			}

		}
	}

}
