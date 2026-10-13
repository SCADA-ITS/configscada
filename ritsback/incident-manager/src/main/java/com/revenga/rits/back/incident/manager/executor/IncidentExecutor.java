package com.revenga.rits.back.incident.manager.executor;

import java.time.Instant;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetAddLogIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetAssignCauseIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetAssignUserIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetBackwardIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetCancelIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetChangeIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetChangeRoadImpactIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetConfirmIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetCreateIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetDeleteLogIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetExecuteTaskPlanIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetFinalizeTaskIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetForwardIncidentReportCommand;
import com.revenga.rits.back.incident.manager.executor.service.IncidentService;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class IncidentExecutor extends GenericExecutor {

	IncidentService incidentService;
	long lastTraceEvaluate;

	public IncidentExecutor(Executor executor, short thread) {

		super(executor, thread);
		incidentService = new IncidentService(this);
	}

	@Override
	protected Object prepare(Object queueItem) {

		List<ImsIncidentReport> incidents = null;
		Integer action = Command.ACTION_UPDATE;

		try {

			if (queueItem instanceof SetCreateIncidentReportCommand) {

				SetCreateIncidentReportCommand setCreateIncidentReportCommand = (SetCreateIncidentReportCommand) queueItem;

				incidents = incidentService.setCreateIncidentReportCommand(setCreateIncidentReportCommand);

				action = Command.ACTION_CREATE;
				
			} else if (queueItem instanceof SetChangeIncidentReportCommand) {

				SetChangeIncidentReportCommand setChangeIncidentReportCommand = (SetChangeIncidentReportCommand) queueItem;

				incidents = incidentService
						.setChangeIncidentReportCommand(setChangeIncidentReportCommand);

			} else if (queueItem instanceof SetChangeRoadImpactIncidentReportCommand) {

				SetChangeRoadImpactIncidentReportCommand setChangeRoadImpactIncidentReportCommand = (SetChangeRoadImpactIncidentReportCommand) queueItem;

				incidents = incidentService
						.setChangeRoadImpactIncidentReportCommand(setChangeRoadImpactIncidentReportCommand);

			} else if (queueItem instanceof SetConfirmIncidentReportCommand) {

				SetConfirmIncidentReportCommand setConfirmIncidentReportCommand = (SetConfirmIncidentReportCommand) queueItem;

				incidents = incidentService.setConfirmIncidentReportCommand(setConfirmIncidentReportCommand);
			}

			else if (queueItem instanceof SetForwardIncidentReportCommand) {

				SetForwardIncidentReportCommand setForwardIncidentReportCommand = (SetForwardIncidentReportCommand) queueItem;

				incidents = incidentService.setForwardIncidentReportCommand(setForwardIncidentReportCommand);

			} else if (queueItem instanceof SetBackwardIncidentReportCommand) {

				SetBackwardIncidentReportCommand setBackwardIncidentReportCommand = (SetBackwardIncidentReportCommand) queueItem;

				incidents = incidentService.setBackwardIncidentReportCommand(setBackwardIncidentReportCommand);

			} else if (queueItem instanceof SetCancelIncidentReportCommand) {

				SetCancelIncidentReportCommand setCancelIncidentReportCommand = (SetCancelIncidentReportCommand) queueItem;

				incidents = incidentService.setCancelIncidentReportCommand(setCancelIncidentReportCommand);

			} else if (queueItem instanceof SetAssignUserIncidentReportCommand) {

				SetAssignUserIncidentReportCommand setAssignUserIncidentReportCommand = (SetAssignUserIncidentReportCommand) queueItem;

				incidents = incidentService.setAssignUserIncidentReportCommand(setAssignUserIncidentReportCommand);

			} else if (queueItem instanceof SetFinalizeTaskIncidentReportCommand) {

				SetFinalizeTaskIncidentReportCommand setFinalizeTaskIncidentReportCommand = (SetFinalizeTaskIncidentReportCommand) queueItem;

				incidents = incidentService.setFinalizeTaskIncidentReportCommand(setFinalizeTaskIncidentReportCommand);

			} else if (queueItem instanceof SetExecuteTaskPlanIncidentReportCommand) {

				SetExecuteTaskPlanIncidentReportCommand setExecuteTaskPlanIncidentReportCommand = (SetExecuteTaskPlanIncidentReportCommand) queueItem;

				incidents = incidentService
						.setExecuteTaskPlanIncidentReportCommand(setExecuteTaskPlanIncidentReportCommand);

			} else if (queueItem instanceof SetAssignCauseIncidentReportCommand) {

				SetAssignCauseIncidentReportCommand setAssignCauseIncidentReportCommand = (SetAssignCauseIncidentReportCommand) queueItem;

				incidents = incidentService.setAssignCauseIncidentReportCommand(setAssignCauseIncidentReportCommand);

			} else if (queueItem instanceof SetAddLogIncidentReportCommand) {

				SetAddLogIncidentReportCommand setAddLogIncidentReportCommand = (SetAddLogIncidentReportCommand) queueItem;

				incidents = incidentService.setAddLogIncidentReportCommand(setAddLogIncidentReportCommand);

			} else if (queueItem instanceof SetDeleteLogIncidentReportCommand) {

				SetDeleteLogIncidentReportCommand setDeleteLogIncidentReportCommand = (SetDeleteLogIncidentReportCommand) queueItem;

				incidents = incidentService.setDeleteLogIncidentReportCommand(setDeleteLogIncidentReportCommand);
			}
			
			IncidentEntitiesManager.getInstance().response(queueItem);
			IncidentEntitiesManager.getInstance().publishIncidentReports(action, incidents);

		} catch (

		Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return null;
	}

	@Override
	protected Object publish() {

		return null;
	}

	@Override
	protected void initialize() {

		log.debug(this.getClass().getSimpleName() + "[" + threadNum + "] now is running");
		if (threadNum == 0) {

			incidentService.init();
		}

	}

	@Override
	protected void end() {

		if (threadNum == 0) {

			incidentService.deInit();
		}
	}

	@Override
	protected void evaluate() {

		if ((Instant.now().getEpochSecond() % 10) == 0 && Instant.now().getEpochSecond() != lastTraceEvaluate
				&& queueItems.size() > 10) {

			log.info(this.getClass().getSimpleName() + " [" + threadNum + "] Queued items remaining = "
					+ queueItems.size());

			lastTraceEvaluate = Instant.now().getEpochSecond();
		}
	}
}
