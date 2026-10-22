package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.PlanSchedules;
import com.revenga.rits.back.front.core.model.vo.PlanSchedulesVO;

public class PlanScheduleHelper {
	
	private PlanScheduleHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static PlanSchedulesVO get(final PlanSchedules planSchedule) {

		PlanSchedulesVO planScheduleVO = null;

		if (planSchedule != null) {

			planScheduleVO = new PlanSchedulesVO();

			assign(planSchedule, planScheduleVO);
		}

		return planScheduleVO;
	}

	public static PlanSchedules get(final PlanSchedulesVO planScheduleVO) {

		PlanSchedules planSchedule = null;

		if (planScheduleVO != null) {

			planSchedule = new PlanSchedules();
			
			if (planScheduleVO.getId() != null) {

				Key key = new Key(planScheduleVO.getId());

				if (key.getClazz() == PlanSchedules.class) {

					planSchedule.setId(Long.valueOf(key.getId(1)));
				}
			}

			planSchedule.setAlias(planScheduleVO.getAlias());
			planSchedule.setDescription((planScheduleVO.getDescription()));
			planSchedule.setCronExpression(planScheduleVO.getCronExpression());

			if (planScheduleVO.getPlanId() != null) {

				Key key = new Key(planScheduleVO.getPlanId());

				if (key.getClazz() == Plan.class) {

					planSchedule.setPlanId(Long.valueOf(key.getId(1)));
				}
			}

			if (planScheduleVO.getPlan() != null) {

				Plan plan = PlanHelper.get(planScheduleVO.getPlan());

				if (plan != null) {

					if (planScheduleVO.getPlanId() == null) {

						planSchedule.setPlanId(plan.getId());
					}
					planSchedule.setPlan(plan);
				}
			}


		}

		return planSchedule;
	}

	public static void assign(final PlanSchedules planSchedule, PlanSchedulesVO planScheduleVO) {

		if (ObjectUtils.allNotNull(planSchedule, planScheduleVO)) {

			BasicElementHelper.fillBasicProperties(planScheduleVO, planSchedule);

			planScheduleVO.setCronExpression(planSchedule.getCronExpression());
			
			planScheduleVO.setPlan(PlanHelper.get(planSchedule.getPlan()));
		}
	}

}
