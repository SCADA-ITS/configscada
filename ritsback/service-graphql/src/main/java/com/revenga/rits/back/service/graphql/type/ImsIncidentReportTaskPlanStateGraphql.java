package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskPlanState;
import com.revenga.rits.back.data.core.model.ImsIncidentTaskPlan;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentReportTaskPlanStateHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportTaskPlanStateVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTaskPlanVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
public class ImsIncidentReportTaskPlanStateGraphql extends ImsIncidentReportTaskPlanStateVO {/**
	 * 
	 */
	private static final long serialVersionUID = -1476566500504350663L;
	
	private ImsIncidentReportTaskPlanState incidentReportTaskPlanState;

	public ImsIncidentReportTaskPlanStateGraphql(final ImsIncidentReportTaskPlanState imsIncidentReportTaskPlanState) {

		ImsIncidentReportTaskPlanStateHelper.assign(imsIncidentReportTaskPlanState, this);
		this.incidentReportTaskPlanState = imsIncidentReportTaskPlanState;
	}
	
	@Override
	public ImsIncidentTaskPlanVO getIncidentTaskPlan() {

		if (super.getIncidentTaskPlan() == null && incidentReportTaskPlanState != null
				&& incidentReportTaskPlanState.getIncidentTaskPlanId() != null) {

			ImsIncidentTaskPlan imsIncidentTaskPlan = EntitiesManager.getInstance().getImsIncidentTaskPlanRepository()
					.get(incidentReportTaskPlanState.getIncidentTaskPlanId(), false);

			if (imsIncidentTaskPlan != null) {

				super.setIncidentTaskPlan(new ImsIncidentTaskPlanGraphql(imsIncidentTaskPlan));
			}
		}

		return super.getIncidentTaskPlan();
	}

}
