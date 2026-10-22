package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskElement;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskPlanState;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskState;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskValue;
import com.revenga.rits.back.data.core.model.ImsIncidentState;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportTaskStateVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentSequenceStateVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentStateVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class ImsIncidentSequenceStateGraphql extends ImsIncidentSequenceStateVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7379608137603613133L;

	private ImsIncidentState incidentState;
	private ImsIncidentReport incidentReport;
	private List<ImsIncidentReportTaskState> incidentReportTaskStates;
	private List<ImsIncidentReportTaskValue> incidentReportTaskValues;
	private List<ImsIncidentReportTaskElement> incidentReportTaskElements;

	private List<ImsIncidentReportTaskPlanState> incidentReportTaskPlanStates;

	public ImsIncidentSequenceStateGraphql(final ImsIncidentReport incidentReport, final ImsIncidentState incidentState,
			final List<ImsIncidentReportTaskState> incidentReportTaskStates,
			List<ImsIncidentReportTaskValue> incidentReportTaskValues,
			List<ImsIncidentReportTaskElement> incidentReportTaskElements,
			final List<ImsIncidentReportTaskPlanState> incidentReportTaskPlanStates) {

		this.incidentReport = incidentReport;
		this.incidentReportTaskStates = incidentReportTaskStates;
		this.incidentReportTaskValues = incidentReportTaskValues;
		this.incidentReportTaskElements = incidentReportTaskElements;
		this.incidentReportTaskPlanStates = incidentReportTaskPlanStates;
		this.incidentState = incidentState;
	}

	@Override
	public ImsIncidentStateVO getState() {

		if (super.getState() == null) {

			if (incidentState != null) {

				super.setState(new ImsIncidentStateGraphql(incidentState));

			} else {
				log.error("imsIncidentState has null pointer");
			}
		}

		return super.getState();
	}

	@Override
	public List<ImsIncidentReportTaskStateVO> getTaskStates() {

		if (super.getTaskStates() == null) {

			if (incidentReport != null && incidentState != null && incidentState.getId() != null) {

				if (!CollectionUtils.isEmpty(incidentReportTaskStates)) {

					List<ImsIncidentTypeTask> incidentTypeTasks = EntitiesManager.getInstance()
							.getImsIncidentTypeTaskRepository().gets(incidentReport.getIncidentTypeId(),
									incidentState.getId(), incidentReport.getAffectionStretchId(), false);

					if (!CollectionUtils.isEmpty(incidentTypeTasks)) {

						List<ImsIncidentReportTaskStateVO> imsIncidentReportTaskStateVO = new ArrayList<>();

						incidentReportTaskStates.forEach(i -> {

							ImsIncidentTypeTask imsIncidentTypeTask = incidentTypeTasks.stream()
									.filter(j -> j.getId().equals(i.getIncidentTypeTaskId())).findFirst().orElse(null);

							if (imsIncidentTypeTask != null) {

								imsIncidentReportTaskStateVO.add(new ImsIncidentReportTaskStateGraphql(i,
										incidentReportTaskValues, incidentReportTaskElements, incidentReportTaskPlanStates, imsIncidentTypeTask));
							}

						});
						super.setTaskStates(imsIncidentReportTaskStateVO);
					}
				}

			} else {

				log.error("incidentReport or incidentState has null pointer");
			}
		}

		return super.getTaskStates();
	}

}
