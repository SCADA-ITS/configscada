package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.PlanScheduleState;
import com.revenga.rits.back.data.core.model.PlanScheduleStateType;
import com.revenga.rits.back.data.core.model.PlanSchedules;
import com.revenga.rits.back.front.core.model.helper.PlanScheduleHelper;
import com.revenga.rits.back.front.core.model.vo.PlanScheduleStateTypeVO;
import com.revenga.rits.back.front.core.model.vo.PlanSchedulesVO;
import com.revenga.rits.back.front.core.model.vo.PlanVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlanScheduleGraphql extends PlanSchedulesVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7391708477409548746L;

	private PlanSchedules planSchedule;

	public PlanScheduleGraphql(final PlanSchedules planSchedule) {

		PlanScheduleHelper.assign(planSchedule, this);
		this.planSchedule = planSchedule;
	}

	@Override
	public PlanVO getPlan() {

		if (super.getPlan() == null) {

			if (planSchedule != null) {

				Plan plan = EntitiesManager.getInstance().getPlanRepository().get(planSchedule.getPlanId(), false);

				if (plan != null) {

					super.setPlan(new PlanGraphql(plan));
				}

			} else {

				log.error("planSchedule has null pointer");
			}

		}

		return super.getPlan();
	}

	@Override
	public PlanScheduleStateTypeVO getPlanScheduleStateType() {

		if (super.getPlanScheduleStateType() == null) {

			if (planSchedule != null && planSchedule.getId() != null) {

				PlanScheduleState planScheduleState = EntitiesManager.getInstance().getPlanScheduleStateRepository()
						.get(planSchedule.getId(), false);

				if (planScheduleState != null) {

					PlanScheduleStateType planScheduleStateType = EntitiesManager.getInstance()
							.getPlanScheduleStateTypeRepository()
							.get(planScheduleState.getPlanScheduleStateTypeId(), false);

					if (planScheduleStateType != null) {

						super.setPlanScheduleStateType(new PlanScheduleStateTypeGraphql(planScheduleStateType));
					}
				}
			} else {

				log.error("planSchedule has null pointer");
			}
		}

		return super.getPlanScheduleStateType();
	}
}
