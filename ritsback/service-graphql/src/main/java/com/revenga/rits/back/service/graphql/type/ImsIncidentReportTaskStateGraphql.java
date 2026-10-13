package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskElement;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskPlanState;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskState;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskValue;
import com.revenga.rits.back.data.core.model.ImsIncidentTaskPlan;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentReportTaskStateHelper;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportTaskPlanStateVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportTaskStateVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportTaskValueVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeTaskVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class ImsIncidentReportTaskStateGraphql extends ImsIncidentReportTaskStateVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7146510590897491781L;

	private ImsIncidentTypeTask imsIncidentTypeTask;
	private ImsIncidentReportTaskState incidentReportTaskState;
	private List<ImsIncidentReportTaskValue> incidentReportTaskValues;
	private List<ImsIncidentReportTaskElement> incidentReportTaskElements;
	private List<ImsIncidentReportTaskPlanState> incidentReportTaskPlanStates;

	public ImsIncidentReportTaskStateGraphql(final ImsIncidentReportTaskState incidentReportTaskState,
			List<ImsIncidentReportTaskValue> incidentReportTaskValues,
			List<ImsIncidentReportTaskElement> incidentReportTaskElements,
			final List<ImsIncidentReportTaskPlanState> incidentReportTaskPlanStates,
			final ImsIncidentTypeTask imsIncidentTypeTask) {

		ImsIncidentReportTaskStateHelper.assign(incidentReportTaskState, this);

		this.incidentReportTaskState = incidentReportTaskState;
		this.incidentReportTaskValues = incidentReportTaskValues;
		this.incidentReportTaskElements = incidentReportTaskElements;
		this.incidentReportTaskPlanStates = incidentReportTaskPlanStates;
		this.imsIncidentTypeTask = imsIncidentTypeTask;
	}

	@Override
	public ImsIncidentTypeTaskVO getIncidentTypeTask() {

		if (super.getIncidentTypeTask() == null && incidentReportTaskState != null
				&& incidentReportTaskState.getIncidentTypeTaskId() != null && imsIncidentTypeTask != null) {

			super.setIncidentTypeTask(new ImsIncidentTypeTaskGraphql(imsIncidentTypeTask));

		}

		return super.getIncidentTypeTask();
	}

	@Override
	public List<ImsIncidentReportTaskPlanStateVO> getTaskPlanStates() {

		if (super.getTaskPlanStates() == null) {

			if (incidentReportTaskState != null) {

				List<ImsIncidentTaskPlan> incidentTaskPlans = EntitiesManager.getInstance()
						.getImsIncidentTaskPlanRepository()
						.getsByTypeTask(incidentReportTaskState.getIncidentTypeTaskId(), false);

				if (!CollectionUtils.isEmpty(incidentTaskPlans)) {

					List<ImsIncidentReportTaskPlanStateVO> incidentReportTaskPlanStateVO = new ArrayList<>();

					incidentReportTaskPlanStates.forEach(i -> {

						if (incidentTaskPlans.stream().filter(j -> j.getId().equals(i.getIncidentTaskPlanId()))
								.findFirst().orElse(null) != null)

							incidentReportTaskPlanStateVO.add(new ImsIncidentReportTaskPlanStateGraphql(i));
					});

					super.setTaskPlanStates(incidentReportTaskPlanStateVO);
				}

			} else {

				log.error("incidentReportTaskState has null pointer");
			}
		}

		return super.getTaskPlanStates();
	}

	@Override
	public List<ImsIncidentReportTaskValueVO> getIncidentReportTaskValues() {

		if (super.getIncidentReportTaskValues() == null && incidentReportTaskState != null
				&& incidentReportTaskState.getIncidentTypeTaskId() != null && imsIncidentTypeTask != null
				&& imsIncidentTypeTask.getTaskTypeId() != null && !CollectionUtils.isEmpty(incidentReportTaskValues)) {

			List<ImsIncidentReportTaskValueVO> imsIncidentReportTaskValuesVO = null;

			for (ImsIncidentReportTaskValue incidentReportTaskValue : incidentReportTaskValues) {

				if (incidentReportTaskState.getIncidentTypeTaskId()
						.equals(incidentReportTaskValue.getIncidentTypeTaskId())
						&& imsIncidentTypeTask.getTaskTypeId().equals(incidentReportTaskValue.getTaskTypeId())) {

					if (imsIncidentReportTaskValuesVO == null) {

						imsIncidentReportTaskValuesVO = new ArrayList<>();
					}
					imsIncidentReportTaskValuesVO.add(new ImsIncidentReportTaskValueGraphql(incidentReportTaskValue));
				}
			}

			super.setIncidentReportTaskValues(imsIncidentReportTaskValuesVO);

		}

		return super.getIncidentReportTaskValues();
	}

	@Override
	public List<ElementVO> getElements() {

		if (super.getElements() == null && incidentReportTaskState != null
				&& incidentReportTaskState.getIncidentTypeTaskId() != null && imsIncidentTypeTask != null
				&& imsIncidentTypeTask.getTaskTypeId() != null
				&& !CollectionUtils.isEmpty(incidentReportTaskElements)) {

			List<ElementVO> elements = null;

			for (ImsIncidentReportTaskElement incidentReportTaskElement : incidentReportTaskElements) {

				if (incidentReportTaskState.getIncidentTypeTaskId()
						.equals(incidentReportTaskElement.getIncidentTypeTaskId())) {

					Element element = EntitiesManager.getInstance().getElementRepository().get(
							incidentReportTaskElement.getElementTypeId(), incidentReportTaskElement.getElementId(),
							false);

					if (element != null) {

						if (elements == null) {

							elements = new ArrayList<>();
						}
						elements.add(new ElementGraphql(element));
					}
				}
			}

			super.setElements(elements);

		}

		return super.getElements();
	}
}
