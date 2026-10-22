package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentTaskPlan;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTaskPlanVO;

public class ImsIncidentTaskPlanHelper {

	private ImsIncidentTaskPlanHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentTaskPlanVO get(final ImsIncidentTaskPlan imsIncidentTaskPlan) {

		ImsIncidentTaskPlanVO imsIncidentTaskPlanVO = null;

		if (imsIncidentTaskPlan != null) {

			imsIncidentTaskPlanVO = new ImsIncidentTaskPlanVO();

			assign(imsIncidentTaskPlan, imsIncidentTaskPlanVO);
		}

		return imsIncidentTaskPlanVO;
	}

	public static ImsIncidentTaskPlan get(final ImsIncidentTaskPlanVO imsIncidentTaskPlanVO) {

		ImsIncidentTaskPlan imsIncidentTaskPlan = null;

		if (imsIncidentTaskPlanVO != null) {

			imsIncidentTaskPlan = new ImsIncidentTaskPlan();
			if (imsIncidentTaskPlanVO.getId() != null) {

				Key key = new Key(imsIncidentTaskPlanVO.getId());

				if (key.getClazz() == ImsIncidentTaskPlan.class) {

					imsIncidentTaskPlan.setId(Long.valueOf(key.getId(1)));
				}
			}

			imsIncidentTaskPlan.setPosition(imsIncidentTaskPlanVO.getPosition());

			if (imsIncidentTaskPlanVO.getIncidentTypeId() != null) {

				Key key = new Key(imsIncidentTaskPlanVO.getIncidentTypeId());

				if (key.getClazz() == ImsIncidentType.class) {

					imsIncidentTaskPlan.setIncidentTypeId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsIncidentTaskPlanVO.getStretchId() != null) {

				Key key = new Key(imsIncidentTaskPlanVO.getStretchId());

				if (key.getClazz() == Stretch.class) {

					imsIncidentTaskPlan.setStretchId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsIncidentTaskPlanVO.getIncidentTypeTaskId() != null) {

				Key key = new Key(imsIncidentTaskPlanVO.getIncidentTypeTaskId());

				if (key.getClazz() == ImsIncidentTypeTask.class) {

					imsIncidentTaskPlan.setIncidentTypeTaskId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsIncidentTaskPlanVO.getPlanId() != null) {

				Key key = new Key(imsIncidentTaskPlanVO.getPlanId());

				if (key.getClazz() == Plan.class) {

					imsIncidentTaskPlan.setPlanId(Long.valueOf(key.getId(1)));
				}
			}

		}

		return imsIncidentTaskPlan;
	}

	public static void assign(final ImsIncidentTaskPlan imsIncidentTaskPlan,
			ImsIncidentTaskPlanVO imsIncidentTaskPlanVO) {

		if (ObjectUtils.allNotNull(imsIncidentTaskPlan, imsIncidentTaskPlanVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentTaskPlanVO, imsIncidentTaskPlan);

			imsIncidentTaskPlanVO.setPosition(imsIncidentTaskPlan.getPosition());

			if (imsIncidentTaskPlan.getIncidentTypeId() != null) {

				ImsIncidentType imsIncidentType = new ImsIncidentType();
				imsIncidentType.setId(imsIncidentTaskPlan.getIncidentTypeId());
				imsIncidentTaskPlanVO.setIncidentTypeId(BasicElementHelper.getIdFromElement(imsIncidentType));
			}

			if (imsIncidentTaskPlan.getStretchId() != null) {

				Stretch stretch = new Stretch();
				stretch.setId(imsIncidentTaskPlan.getStretchId());
				imsIncidentTaskPlanVO.setStretchId(BasicElementHelper.getIdFromElement(stretch));
			}

			if (imsIncidentTaskPlan.getIncidentTypeTaskId() != null) {

				ImsIncidentTypeTask imsIncidentTypeTask = new ImsIncidentTypeTask();
				imsIncidentTypeTask.setId(imsIncidentTaskPlan.getIncidentTypeTaskId());
				imsIncidentTaskPlanVO.setIncidentTypeTaskId(BasicElementHelper.getIdFromElement(imsIncidentTypeTask));
			}

			if (imsIncidentTaskPlan.getPlanId() != null) {

				Plan plan = new Plan();
				plan.setId(imsIncidentTaskPlan.getPlanId());
				imsIncidentTaskPlanVO.setPlanId(BasicElementHelper.getIdFromElement(plan));
			}

		}
	}

}
