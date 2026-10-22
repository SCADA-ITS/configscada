package com.revenga.rits.back.incident.manager.helper;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.CommandElementError;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentState;
import com.revenga.rits.back.data.core.model.ImsIncidentTaskPlan;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.ImsTaskType;
import com.revenga.rits.back.data.core.model.LogLevel;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class IncidentHeper {

	private IncidentHeper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static Semaphore acquire(final GenericExecutor genericExecutor, final Long IncidentReportId) {

		boolean acquire = false;
		Semaphore mutex = IncidentEntitiesManager.getInstance().getIncidentReportMutex().get(IncidentReportId);

		try {
			if (mutex != null) {

				while (!genericExecutor.isStop() && !(acquire = mutex.tryAcquire(500, TimeUnit.MILLISECONDS)))
					;
			}

		} catch (InterruptedException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return acquire ? mutex : null;
	}

	public static ImsIncidentReport evalueIncidentReport(Long incidentReportId) {

		ImsIncidentReport incidentReport = null;

		Instant instantInit = Instant.now();

		IncidentEntitiesManager.getInstance().initIncidentTasks(incidentReportId);

		// Finalize task auto
		List<ImsIncidentTypeTask> incidentTypeTasks = IncidentEntitiesManager.getInstance()
				.getUncompletedAutoTasks(incidentReportId);

		if (!CollectionUtils.isEmpty(incidentTypeTasks)) {

			Collections.sort(incidentTypeTasks);
			for (ImsIncidentTypeTask incidentTypeTask : incidentTypeTasks) {

				if (Long.valueOf(ImsTaskType.SIGNALING).equals(incidentTypeTask.getTaskTypeId())) {

					List<ImsIncidentTaskPlan> incidentTaskPlans = IncidentEntitiesManager.getInstance()
							.getUncompletedTaskPlans(incidentReportId, incidentTypeTask.getId());

					if (!CollectionUtils.isEmpty(incidentTaskPlans)) {

						Collections.sort(incidentTaskPlans);
						for (ImsIncidentTaskPlan incidentTaskPlan : incidentTaskPlans) {

							if (IncidentEntitiesManager.getInstance().executeTaskPlanIncidentReport(incidentReportId,
									incidentTaskPlan.getId(), Boolean.TRUE) != null) {

								log.info("Execute task plan: " + incidentTaskPlan.getId() + " incidentReport:"
										+ incidentReportId);
							}
						}
					}
				}

				List<ImsIncidentTypeTaskValue> incidentTypeTaskValue = IncidentEntitiesManager.getInstance()
						.getIncidentTypeTaskValues(incidentTypeTask.getId(), null);

				List<CommandElementError> commandElementErrors = IncidentEntitiesManager.getInstance()
						.executeIncidentTypeTask(incidentReportId, incidentTypeTask.getId(), incidentTypeTaskValue);

				if (IncidentEntitiesManager.getInstance().finalizeTaskIncidentReport(incidentReportId,
						incidentTypeTask.getId(), incidentTypeTaskValue,
						CollectionUtils.isNotEmpty(commandElementErrors)) != null) {

					log.info("Finalize task: " + incidentTypeTask.getId() + " incidentReport:" + incidentReportId);
					IncidentEntitiesManager.getInstance().addLogIncidentReport(null, incidentReportId,
							LogSubtype.LOG_SUBTYPE_FINALIZED_TASK, LogLevel.INFO,
							incidentTypeTask.getAlias() + (incidentTypeTask.getDescription() != null
									? " - " + incidentTypeTask.getDescription()
									: ""));
				}

				if (CollectionUtils.isNotEmpty(commandElementErrors)) {

					IncidentEntitiesManager.getInstance().addLogError(incidentReportId, incidentTypeTask.getId(),
							commandElementErrors);
				}
			}
		}

		// Auto forward
		if (IncidentEntitiesManager.getInstance().isAutoForwardIncidentReport(incidentReportId)
				&& CollectionUtils
						.isEmpty(IncidentEntitiesManager.getInstance().getUncompletedMandatoryTasks(incidentReportId))
				&& (!IncidentEntitiesManager.getInstance().isNextStateFinal(incidentReportId)
						|| CollectionUtils.isEmpty(IncidentEntitiesManager.getInstance()
								.getUncompletedMandatoryIncidentTasks(incidentReportId)))) {

			ImsIncidentState currentState = IncidentEntitiesManager.getInstance().getCurrentState(incidentReportId);

			if (currentState != null) {
				incidentReport = IncidentEntitiesManager.getInstance().forwardIncidentReport(incidentReportId);

				if (incidentReport != null) {

					log.info("Forward incidentReport:" + incidentReport);
					IncidentEntitiesManager.getInstance().addLogIncidentReport(null, incidentReportId,
							LogSubtype.LOG_SUBTYPE_INCIDENT_REPORT_FORWARDED, LogLevel.INFO);

					incidentReport = evalueIncidentReport(incidentReportId);
				}
			}
		}

		log.info("IncidentReport: " + incidentReportId + " evalue in "
				+ Duration.between(instantInit, Instant.now()).toMillis() + " ms");

		return incidentReport;
	}
}
