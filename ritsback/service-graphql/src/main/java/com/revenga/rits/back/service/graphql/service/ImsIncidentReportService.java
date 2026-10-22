package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.Road;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.command.ResponseToServiceGraphqlCommand;
import com.revenga.rits.back.data.core.model.command.SetAssignCauseIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetAssignUserIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetBackwardIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetCancelIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetChangeIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetChangeRoadImpactIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetConfirmIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetCreateIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetExecuteTaskPlanIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetFinalizeTaskIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetForwardIncidentReportCommand;
import com.revenga.rits.back.front.core.model.helper.command.SetAssignCauseIncidentReportCommandHelper;
import com.revenga.rits.back.front.core.model.helper.command.SetAssignUserIncidentReportCommandHelper;
import com.revenga.rits.back.front.core.model.helper.command.SetBackwardIncidentReportCommandHelper;
import com.revenga.rits.back.front.core.model.helper.command.SetCancelIncidentReportCommandHelper;
import com.revenga.rits.back.front.core.model.helper.command.SetChangeIncidentReportCommandHelper;
import com.revenga.rits.back.front.core.model.helper.command.SetChangeRoadImpactIncidentReportCommandHelper;
import com.revenga.rits.back.front.core.model.helper.command.SetConfirmIncidentReportCommandHelper;
import com.revenga.rits.back.front.core.model.helper.command.SetCreateIncidentReportCommandHelper;
import com.revenga.rits.back.front.core.model.helper.command.SetExecuteTaskPlanIncidentReportCommandHelper;
import com.revenga.rits.back.front.core.model.helper.command.SetFinalizeTaskIncidentReportCommandHelper;
import com.revenga.rits.back.front.core.model.helper.command.SetForwardIncidentReportCommandHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeTaskValueVO;
import com.revenga.rits.back.manager.lib.service.ResponseManager;
import com.revenga.rits.back.service.graphql.type.ImsIncidentReportGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ImsIncidentReportService implements QueryInterface<ImsIncidentReportVO> {

	public List<ImsIncidentReportVO> getAll(Long managementAreaId) {

		List<ImsIncidentReportVO> imsIncidentReports = new ArrayList<>();

		for (ImsIncidentReport imsIncidentReport : EntitiesManager.getInstance().getImsIncidentReportRepository()
				.getAll(false)) {

			if (checkManagementArea(imsIncidentReport, managementAreaId)){
				
				imsIncidentReports.add(new ImsIncidentReportGraphql(imsIncidentReport));
			}
				
		}

		return imsIncidentReports;
	}

	@Override
	public Optional<ImsIncidentReportVO> get(String imsIncidentReportId) {

		Optional<ImsIncidentReportVO> imsIncidentReportVO = Optional.empty();

		try {
			Key key = new Key(imsIncidentReportId);

			if (key.getClazz() == ImsIncidentReport.class) {

				ImsIncidentReport imsIncidentReport = EntitiesManager.getInstance().getImsIncidentReportRepository()
						.get(Long.valueOf(key.getId(1)), false);

				if (imsIncidentReport != null) {

					imsIncidentReportVO = Optional.of(new ImsIncidentReportGraphql(imsIncidentReport));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: imsIncidentReport");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return imsIncidentReportVO;
	}

	@Override
	public List<ImsIncidentReportVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	public Boolean sendCreate(String incidentTypeId, String locationId, List<String> alarmIds,
			List<String> affectionStretchIds, String userId, String roadImpactId) {

		SetCreateIncidentReportCommand command = SetCreateIncidentReportCommandHelper.get(incidentTypeId, locationId,
				alarmIds, affectionStretchIds, userId, roadImpactId);

		return (command != null && EntitiesManager.getInstance().sendCommand(command) != null);
	}

	public Boolean sendChangeIncidentReport(String incidentReportId, String locationId, String affectionStretchId,
			String incidentTypeId, String roadImpactId) {

		SetChangeIncidentReportCommand command = SetChangeIncidentReportCommandHelper.get(incidentReportId, locationId,
				affectionStretchId, incidentTypeId, roadImpactId);

		return command != null && EntitiesManager.getInstance().sendCommand(command) != null;
	}

	public Boolean sendChangeRoadImpactIncidentReport(String incidentReportId, String roadImpactId) {

		SetChangeRoadImpactIncidentReportCommand command = SetChangeRoadImpactIncidentReportCommandHelper
				.get(incidentReportId, roadImpactId);

		return command != null && EntitiesManager.getInstance().sendCommand(command) != null;
	}

	public Boolean sendForward(String incidentReportId) {

		SetForwardIncidentReportCommand command = SetForwardIncidentReportCommandHelper.get(incidentReportId);

		return command != null && EntitiesManager.getInstance().sendCommand(command) != null;
	}

	public Boolean sendBackward(String incidentReportId) {

		SetBackwardIncidentReportCommand command = SetBackwardIncidentReportCommandHelper.get(incidentReportId);

		return command != null && EntitiesManager.getInstance().sendCommand(command) != null;
	}

	public Boolean sendConfirm(String incidentReportId, String userId) {

		SetConfirmIncidentReportCommand command = SetConfirmIncidentReportCommandHelper.get(incidentReportId, userId);

		return command != null && EntitiesManager.getInstance().sendCommand(command) != null;
	}

	public Boolean sendCancel(String incidentReportId, String userId) {

		SetCancelIncidentReportCommand command = SetCancelIncidentReportCommandHelper.get(incidentReportId, userId);
		command.setResponseCommand(new ResponseToServiceGraphqlCommand());

		return ResponseManager.getInstance().sendCommandSync(command) != null;
	}

	public Boolean sendAssignUser(String incidentReportId, String userId) {

		SetAssignUserIncidentReportCommand command = SetAssignUserIncidentReportCommandHelper.get(incidentReportId,
				userId);

		return command != null && EntitiesManager.getInstance().sendCommand(command) != null;
	}

	public Boolean sendAssignCause(String incidentReportId, String incidentTypeCauseId) {

		SetAssignCauseIncidentReportCommand command = SetAssignCauseIncidentReportCommandHelper.get(incidentReportId,
				incidentTypeCauseId);

		return command != null && EntitiesManager.getInstance().sendCommand(command) != null;
	}

	public Boolean sendFinalizeTask(String incidentReportId, String incidentTypeTaskId,
			List<ImsIncidentTypeTaskValueVO> incidentTypeTaskValues) {

		SetFinalizeTaskIncidentReportCommand command = SetFinalizeTaskIncidentReportCommandHelper.get(incidentReportId,
				incidentTypeTaskId, incidentTypeTaskValues);

		return command != null && EntitiesManager.getInstance().sendCommand(command) != null;
	}

	public Boolean sendExecuteTaskPlan(String incidentReportId, String incidentTaskPlanId) {

		SetExecuteTaskPlanIncidentReportCommand command = SetExecuteTaskPlanIncidentReportCommandHelper
				.get(incidentReportId, incidentTaskPlanId);

		return command != null && EntitiesManager.getInstance().sendCommand(command) != null;
	}

	@Override
	public List<ImsIncidentReportVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean checkManagementArea(final ImsIncidentReport imsIncidentReport, final Long managementAreaId) {


		return imsIncidentReport != null && (imsIncidentReport.getManagementAreaId() == null
				|| imsIncidentReport.getManagementAreaId().equals(managementAreaId));
	}
}
