package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ImsIncidentTaskPlan;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentTaskPlanHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTaskPlanVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeTaskVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeVO;
import com.revenga.rits.back.front.core.model.vo.PlanVO;
import com.revenga.rits.back.front.core.model.vo.StretchVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class ImsIncidentTaskPlanGraphql extends ImsIncidentTaskPlanVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2488249148831156655L;
	
	private ImsIncidentTaskPlan imsIncidentTaskPlan;

	public ImsIncidentTaskPlanGraphql(final ImsIncidentTaskPlan imsIncidentTaskPlan) {

		ImsIncidentTaskPlanHelper.assign(imsIncidentTaskPlan, this);
		this.imsIncidentTaskPlan = imsIncidentTaskPlan;

	}

	@Override
	public StretchVO getStretch() {

		if (super.getStretch() == null && imsIncidentTaskPlan != null
				&& imsIncidentTaskPlan.getStretchId() != null) {

			Stretch stretch = EntitiesManager.getInstance().getStretchRepository()
					.get(imsIncidentTaskPlan.getStretchId(), false);

			if (stretch != null) {

				super.setStretch(new StretchGraphql(stretch));
			}
		}

		return super.getStretch();
	}
	
	@Override
	public ImsIncidentTypeTaskVO getIncidentTypeTask() {

		if (super.getIncidentTypeTask() == null && imsIncidentTaskPlan != null
				&& imsIncidentTaskPlan.getIncidentTypeTaskId() != null) {

			ImsIncidentTypeTask imsIncidentTypeTask = EntitiesManager.getInstance().getImsIncidentTypeTaskRepository()
					.get(imsIncidentTaskPlan.getIncidentTypeTaskId(), false);

			if (imsIncidentTypeTask != null) {

				super.setIncidentTypeTask(new ImsIncidentTypeTaskGraphql(imsIncidentTypeTask));
			}
		}

		return super.getIncidentTypeTask();
	}
	
	@Override
	public ImsIncidentTypeVO getIncidentType() {

		if (super.getIncidentType() == null && imsIncidentTaskPlan != null
				&& imsIncidentTaskPlan.getIncidentTypeId() != null) {

			ImsIncidentType imsIncidentType = EntitiesManager.getInstance().getImsIncidentTypeRepository()
					.get(imsIncidentTaskPlan.getIncidentTypeId(), false);

			if (imsIncidentType != null) {

				super.setIncidentType(new ImsIncidentTypeGraphql(imsIncidentType));
			}
		}

		return super.getIncidentType();
	}
	
	@Override
	public PlanVO getPlan() {

		if (super.getPlan() == null) {

			if (imsIncidentTaskPlan != null) {

				Plan plan = EntitiesManager.getInstance().getPlanRepository()
						.get(imsIncidentTaskPlan.getPlanId(), false);

				if (plan != null) {

					super.setPlan(new PlanGraphql(plan));
				}

			} else {

				log.error("imsIncidentTaskPlan has null pointer");
			}

		}

		return super.getPlan();
	}
}
