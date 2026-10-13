package com.revenga.rits.back.incident.manager.executor.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.CommandElementError;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;
import com.revenga.rits.back.data.core.model.ImsIncidentReportLog;
import com.revenga.rits.back.data.core.model.ImsIncidentTaskPlan;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.ImsTaskType;
import com.revenga.rits.back.data.core.model.LogLevel;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.SystemParam;
import com.revenga.rits.back.data.core.model.SystemParamValue;
import com.revenga.rits.back.data.core.model.User;
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
import com.revenga.rits.back.entities.lib.repository.SystemParamValueRepository;
import com.revenga.rits.back.incident.manager.executor.IncidentExecutor;
import com.revenga.rits.back.incident.manager.executor.scheduler.SchedulerDeleteIncidentsJob;
import com.revenga.rits.back.incident.manager.helper.IncidentHeper;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class IncidentService {

	GenericExecutor genericExecutor;
	MaintenacePendingIncidentsThread confirmIncidentReportThread;
	ExecuteDelayedTasksThread exetuteDelayedTasksThread;
	Scheduler scheduler;

	@Getter
	@Setter
	boolean running;

	private boolean initQuartzScheduler() {

		Boolean ok = true;

		SchedulerFactory sf = new StdSchedulerFactory();
		try {

			scheduler = sf.getScheduler();
			scheduler.getContext().put(this.getClass().getSimpleName(), this);
			scheduler.start();

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			ok = false;
		}

		return ok;
	}

	private void createJob(Class<? extends Job> jobClass, String cronExpression) {

		try {
			JobDetail job = newJob(jobClass)
					.withIdentity("job_" + jobClass.getSimpleName(), "group_" + jobClass.getSimpleName()).build();

			if (!StringUtils.isEmpty(cronExpression)) {

				CronTrigger trigger = newTrigger()
						.withIdentity("trigger_" + jobClass.getSimpleName(), "group_" + jobClass.getSimpleName())
						.withSchedule(cronSchedule(cronExpression)).build();

				Date dt = scheduler.scheduleJob(job, trigger);

				log.info(job.getKey() + " has been scheduled to run at: " + dt + " and repeat based on expression: "
						+ trigger.getCronExpression());

			} else {

				log.debug(job.getKey() + " is not scheduled");
			}

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	private void launchJob(String cronExpression) {

		if (!StringUtils.isEmpty(cronExpression)) {

			if (scheduler == null) {

				initQuartzScheduler();
			}
			createJob(SchedulerDeleteIncidentsJob.class, cronExpression);

		} else {

			log.error("Not found cronPeriodTrafficData");
		}
	}

	private List<ImsIncidentReport> confirmIncidentReport(Long incidentReportId, Long userId) {

		List<ImsIncidentReport> incidentReports = null;

		if (incidentReportId != null) {

			Semaphore mutex = IncidentHeper.acquire(genericExecutor, incidentReportId);

			if (mutex != null) {

				if (!IncidentEntitiesManager.getInstance().isIncidentReportCanceled(incidentReportId)) {

					ImsIncidentReport incidentReport = IncidentEntitiesManager.getInstance()
							.confirmIncidentReport(incidentReportId, userId);

					if (incidentReport != null) {

						if (userId != null) {

							User user = EntitiesManager.getInstance().getUser(userId);

							if (user != null) {

								IncidentEntitiesManager.getInstance().addLogIncidentReport(null, incidentReportId,
										LogSubtype.LOG_SUBTYPE_ASSIGNED_USER, LogLevel.INFO, user.getName());
							}
						}

						log.info("IncidentReport confirmed: " + incidentReport.getId());
						incidentReports = new ArrayList<>();
						incidentReports.add(incidentReport);

						IncidentHeper.evalueIncidentReport(incidentReport.getId());
					}
				} else {
					log.warn("IncidentReport " + incidentReportId + " is canceled");
				}

				mutex.release();

			} else {
				log.warn("Mutex not found: " + incidentReportId);
			}
		}
		return incidentReports;
	}

	private List<ImsIncidentReport> cancelIncidentReportCommand(Long incidentReportId, Long userId) {

		List<ImsIncidentReport> incidentReports = null;

		if (incidentReportId != null) {

			Semaphore mutex = IncidentHeper.acquire(genericExecutor, incidentReportId);

			if (mutex != null) {

				if (!IncidentEntitiesManager.getInstance().isIncidentReportCanceled(incidentReportId)) {

					/*
					 * if (command.getUserId() != null && !IncidentEntitiesManager.getInstance()
					 * .isIncidentReportConfirmed(command.getIncidentReportId())) {
					 * 
					 * User user = EntitiesManager.getInstance().getUser(command.getUserId());
					 * 
					 * if (user != null) {
					 * 
					 * IncidentEntitiesManager.getInstance().addLogIncidentReport(null,
					 * command.getIncidentReportId(), LogSubtype.LOG_SUBTYPE_ASSIGNED_USER,
					 * LogLevel.INFO, user.getName()); } }
					 */

					ImsIncidentReport incidentReport = IncidentEntitiesManager.getInstance()
							.cancelIncidentReport(incidentReportId, userId);

					if (incidentReport != null) {

						log.info("IncidentReport canceled: " + incidentReport.getId());

						executeCancelTask(incidentReportId);

						incidentReports = new ArrayList<>();
						incidentReports.add(incidentReport);

						/*
						 * if (!IncidentEntitiesManager.getInstance().isIncidentReportConfirmed(
						 * incidentReport.getId())) {
						 * 
						 * Instant instantInit = Instant.now();
						 * 
						 * IncidentEntitiesManager.getInstance().deleteIncidentReport(incidentReport.
						 * getId());
						 * 
						 * log.info("IncidentReport: " + incidentReport.getId() + " deleted in " +
						 * Duration.between(instantInit, Instant.now()).toMillis() + " ms");
						 * 
						 * }
						 */
					}
				} else {
					log.warn("IncidentReport " + incidentReportId + " is canceled");
				}

				mutex.release();

			} else {
				log.warn("Mutex not found: " + incidentReportId);

			}
		}
		return incidentReports;
	}

	private List<ImsIncidentReport> finalizeTaskIncidentReport(final Long incidentReportId,
			final Long incidentTypeTaskId, final List<ImsIncidentTypeTaskValue> newIncidentTypeTaskValues) {

		List<ImsIncidentReport> incidentReports = null;

		if (incidentReportId != null && incidentTypeTaskId != null) {

			Semaphore mutex = IncidentHeper.acquire(genericExecutor, incidentReportId);

			if (mutex != null) {

				List<ImsIncidentTypeTaskValue> incidentTypeTaskValue = IncidentEntitiesManager.getInstance()
						.getIncidentTypeTaskValues(incidentTypeTaskId, newIncidentTypeTaskValues);

				List<CommandElementError> commandElementErrors = IncidentEntitiesManager.getInstance()
						.executeIncidentTypeTask(incidentReportId, incidentTypeTaskId, incidentTypeTaskValue);

				if (CollectionUtils.isNotEmpty(commandElementErrors)) {

					IncidentEntitiesManager.getInstance().addLogError(incidentReportId, incidentTypeTaskId,
							commandElementErrors);
				}

				ImsIncidentReport incidentReport = IncidentEntitiesManager.getInstance().finalizeTaskIncidentReport(
						incidentReportId, incidentTypeTaskId, incidentTypeTaskValue,
						CollectionUtils.isNotEmpty(commandElementErrors));

				if (incidentReport != null) {

					log.info("Finished task: " + incidentTypeTaskId + " incidentReport: " + incidentReportId);

					incidentReports = new ArrayList<>();
					incidentReports.add(incidentReport);

					IncidentHeper.evalueIncidentReport(incidentReport.getId());
				}

				mutex.release();

			} else {
				log.warn("Mutex not found: " + incidentReportId);
			}
		}

		return incidentReports;
	}

	private ImsIncidentReport deleteIncidentReport(Long incidentReportId) {

		ImsIncidentReport incidentReport = null;

		if (incidentReportId != null) {

			Semaphore mutex = IncidentHeper.acquire(genericExecutor, incidentReportId);

			if (mutex != null) {

				Instant instantInit = Instant.now();

				incidentReport = IncidentEntitiesManager.getInstance().deleteIncidentReport(incidentReportId);

				log.info("IncidentReport: " + incidentReport.getId() + " deleted in "
						+ Duration.between(instantInit, Instant.now()).toMillis() + " ms");

				mutex.release();

			} else {
				log.warn("Mutex not found: " + incidentReportId);
			}
		}
		return incidentReport;
	}

	private ImsIncidentReport executeCancelTask(Long incidentReportId) {

		ImsIncidentReport incidentReport = null;

		Instant instantInit = Instant.now();

		IncidentEntitiesManager.getInstance().initIncidentTasks(incidentReportId);

		// Finalize task auto
		List<ImsIncidentTypeTask> incidentTypeTasks = IncidentEntitiesManager.getInstance()
				.getCancelTasks(incidentReportId);

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

		log.info("IncidentReport: " + incidentReportId + ", execute cancel task in "
				+ Duration.between(instantInit, Instant.now()).toMillis() + " ms");

		return incidentReport;
	}

	public IncidentService(IncidentExecutor incidentExecutor) {

		this.genericExecutor = incidentExecutor;
		running = false;
	}

	public Boolean init() {

		Boolean ok = true;

		IncidentEntitiesManager.getInstance();

		if (confirmIncidentReportThread != null) {

			confirmIncidentReportThread.stop();
		}
		confirmIncidentReportThread = new MaintenacePendingIncidentsThread(this);
		String threadNameService = "Thread" + confirmIncidentReportThread.getClass().getSimpleName();
		(new Thread(confirmIncidentReportThread, threadNameService)).start();

		if (exetuteDelayedTasksThread != null) {

			exetuteDelayedTasksThread.stop();
		}
		exetuteDelayedTasksThread = new ExecuteDelayedTasksThread(this);
		threadNameService = "Thread" + exetuteDelayedTasksThread.getClass().getSimpleName();
		(new Thread(exetuteDelayedTasksThread, threadNameService)).start();

		SystemParamValue systemParamValuePeriod = (new SystemParamValueRepository(false))
				.get(Long.valueOf(SystemParam.SYSTEM_PARAM_SYSTEM_PARAM_CRON_EXPRESION_DELETE_INCIDETS), false);

		if (systemParamValuePeriod != null && systemParamValuePeriod.getValue() != null) {

			launchJob(systemParamValuePeriod.getValue());

		} else {

			log.error("Not fount system param cron expresion delete incidents");
			ok = false;
		}

		return ok;
	}

	public Boolean deInit() {

		Boolean ok = true;

		try {
			if (scheduler != null) {

				log.debug("Shutdown scheduled jobs ...");
				scheduler.shutdown(true);
				log.debug("Shutdown Complete");
				log.debug("Executed " + scheduler.getMetaData().getNumberOfJobsExecuted() + " jobs.");
			}

		} catch (SchedulerException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			ok = false;
		}

		if (confirmIncidentReportThread != null) {

			try {
				confirmIncidentReportThread.stop();

				Instant start;
				Duration timeElapsed;
				boolean stopped;
				start = Instant.now();

				do {
					stopped = !confirmIncidentReportThread.isAlive();
					Thread.sleep(1L);

					timeElapsed = Duration.between(start, Instant.now());

				} while (timeElapsed.toMillis() < 5000L && !stopped);

			} catch (InterruptedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
				ok = false;
			}
		}

		if (exetuteDelayedTasksThread != null) {

			try {
				exetuteDelayedTasksThread.stop();

				Instant start;
				Duration timeElapsed;
				boolean stopped;
				start = Instant.now();

				do {
					stopped = !exetuteDelayedTasksThread.isAlive();
					Thread.sleep(1L);

					timeElapsed = Duration.between(start, Instant.now());

				} while (timeElapsed.toMillis() < 5000L && !stopped);

			} catch (InterruptedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
				ok = false;
			}
		}

		return ok;
	}

	/**
	 * 
	 * @param command
	 * @return
	 */
	public List<ImsIncidentReport> setCreateIncidentReportCommand(SetCreateIncidentReportCommand command) {

		if (command.getIncidentTypeId() != null) {

			List<ImsIncidentReportAlarm> incidentReportAlarms = null;

			if (!CollectionUtils.isEmpty(command.getAlarmIds())) {

				for (Long alarmId : command.getAlarmIds()) {

					Alarm alarm = EntitiesManager.getInstance().getAlarm(alarmId);

					if (alarm != null) {

						ImsIncidentReportAlarm incidentReportAlarm = new ImsIncidentReportAlarm();
						incidentReportAlarm.setAlarmConfigId(alarm.getAlarmConfigId());
						incidentReportAlarm.setElementId(alarm.getSourceElementId());
						incidentReportAlarm.setElementTypeId(alarm.getSourceElementTypeId());
						incidentReportAlarm.setActivation(alarm.getActivation());

						if (incidentReportAlarms == null) {

							incidentReportAlarms = new ArrayList<>();
						}

						incidentReportAlarms.add(incidentReportAlarm);
					}
				}
			}

			List<ImsIncidentReport> incidentReports = IncidentEntitiesManager.getInstance().createIncidentReport(
					command.getIncidentTypeId(), command.getLocationId(), command.getAffectionStretchIds(),
					incidentReportAlarms, genericExecutor, command.getUserId(), command.getRoadImpactId(), true);

			if (!CollectionUtils.isEmpty(incidentReports)) {

				for (ImsIncidentReport incidentReport : incidentReports) {

					if (incidentReport.getId() != null) {

						Semaphore mutex = IncidentHeper.acquire(genericExecutor, incidentReport.getId());

						if (mutex != null) {

							log.info("IncidentReport created: " + incidentReport.getId());
							IncidentHeper.evalueIncidentReport(incidentReport.getId());

							if (IncidentEntitiesManager.getInstance().confirmIncidentReport(incidentReport.getId(),
									null) != null) {

								log.info("IncidentReport confirmed: " + incidentReport.getId());
								IncidentEntitiesManager.getInstance().addLogIncidentReport(null, incidentReport.getId(),
										LogSubtype.LOG_SUBTYPE_INCIDENT_REPORT_CONFIRMED, LogLevel.INFO);

								IncidentHeper.evalueIncidentReport(incidentReport.getId());
							}

							mutex.release();

						} else {
							log.warn("Mutex not found: " + incidentReport.getId());
						}
					}
				}
			}
			return incidentReports;
		}
		return Collections.emptyList();

	}

	public List<ImsIncidentReport> setChangeIncidentReportCommand(SetChangeIncidentReportCommand command) {
		List<ImsIncidentReport> incidentReports = null;

		if (command.getIncidentReportId() != null) {

			Semaphore mutex = IncidentHeper.acquire(genericExecutor, command.getIncidentReportId());

			if (mutex != null) {

				if (!IncidentEntitiesManager.getInstance().isIncidentReportConfirmed(command.getIncidentReportId())
						&& !IncidentEntitiesManager.getInstance()
								.isIncidentReportCanceled(command.getIncidentReportId())) {

					if (!IncidentEntitiesManager.getInstance()
							.isAffectionStretch(command.getIncidentReportId(), command.getAffectionStretchId())
							.booleanValue()
							|| !IncidentEntitiesManager.getInstance()
									.isIncidentType(command.getIncidentReportId(), command.getIncidentTypeId())
									.booleanValue()) {

						executeCancelTask(command.getIncidentReportId());
					}

					ImsIncidentReport incidentReport = IncidentEntitiesManager.getInstance().changeIncidentReport(
							command.getIncidentReportId(), command.getLocationId(), command.getAffectionStretchId(),
							command.getIncidentTypeId(), command.getRoadImpactId());

					if (incidentReport != null && incidentReport.getId() != null) {

						log.info("Changed incidentReport: " + incidentReport);
						IncidentHeper.evalueIncidentReport(incidentReport.getId());

						incidentReports = new ArrayList<>();
						incidentReports.add(incidentReport);
					}

				} else {
					log.warn("IncidentReport " + command.getIncidentReportId() + " is Confirmed");
				}

				mutex.release();

			} else {
				log.warn("Mutex not found: " + command.getIncidentReportId());

			}
		}

		return incidentReports;
	}

	public List<ImsIncidentReport> setChangeRoadImpactIncidentReportCommand(
			SetChangeRoadImpactIncidentReportCommand command) {
		List<ImsIncidentReport> incidentReports = null;

		if (command.getIncidentReportId() != null) {

			Semaphore mutex = IncidentHeper.acquire(genericExecutor, command.getIncidentReportId());

			if (mutex != null) {

				if (!IncidentEntitiesManager.getInstance().isIncidentReportCanceled(command.getIncidentReportId())) {

					ImsIncidentReport incidentReport = IncidentEntitiesManager.getInstance()
							.updateRoadImpact(command.getIncidentReportId(), command.getRoadImpactId());

					if (incidentReport != null && incidentReport.getId() != null) {

						log.info("ChangedRoadImpact incidentReport: " + incidentReport);
						IncidentHeper.evalueIncidentReport(incidentReport.getId());

						incidentReports = new ArrayList<>();
						incidentReports.add(incidentReport);
					}
				}

				mutex.release();

			} else {
				log.warn("Mutex not found: " + command.getIncidentReportId());

			}
		}

		return incidentReports;
	}

	public List<ImsIncidentReport> setConfirmIncidentReportCommand(SetConfirmIncidentReportCommand command) {

		return confirmIncidentReport(command.getIncidentReportId(), command.getUserId());
	}

	public List<ImsIncidentReport> setForwardIncidentReportCommand(SetForwardIncidentReportCommand command) {

		List<ImsIncidentReport> incidentReports = null;

		if (command.getIncidentReportId() != null) {

			Long incidentReportId = command.getIncidentReportId();

			Semaphore mutex = IncidentHeper.acquire(genericExecutor, incidentReportId);

			if (mutex != null) {

				boolean forwardIncidentReport = false;

				if (IncidentEntitiesManager.getInstance().isIncidentReportOpen(incidentReportId)) {

					List<ImsIncidentTypeTask> incidentTypeTasks = IncidentEntitiesManager.getInstance()
							.getUncompletedMandatoryTasks(incidentReportId);

					if (CollectionUtils.isEmpty(incidentTypeTasks)) {

						if (IncidentEntitiesManager.getInstance().isNextStateFinal(incidentReportId)) {

							incidentTypeTasks = IncidentEntitiesManager.getInstance()
									.getUncompletedMandatoryIncidentTasks(incidentReportId);

							if (CollectionUtils.isEmpty(incidentTypeTasks)) {

								forwardIncidentReport = true;

							} else {
								log.error("Uncompleted mandatory tasks: "
										+ incidentTypeTasks.stream().map(e -> e.getId()).collect(Collectors.toList())
										+ " in incident: " + incidentReportId);
							}

						} else {
							forwardIncidentReport = true;
						}

					} else {
						log.error("Uncompleted mandatory tasks: "
								+ incidentTypeTasks.stream().map(e -> e.getId()).collect(Collectors.toList())
								+ " in incident: " + incidentReportId);
					}

				} else {
					log.error("The incident: " + incidentReportId + " isn't open");
				}

				if (forwardIncidentReport) {

					ImsIncidentReport incidentReport = IncidentEntitiesManager.getInstance()
							.forwardIncidentReport(incidentReportId);

					if (incidentReport != null) {

						log.info("IncidentReport forwarded: " + incidentReport.getId());

						incidentReports = new ArrayList<>();
						incidentReports.add(incidentReport);

						IncidentHeper.evalueIncidentReport(incidentReport.getId());

					}
				}
				mutex.release();

			} else {
				log.warn("Mutex not found: " + command.getIncidentReportId());
			}
		}

		return incidentReports;
	}

	public List<ImsIncidentReport> setBackwardIncidentReportCommand(SetBackwardIncidentReportCommand command) {

		List<ImsIncidentReport> incidentReports = null;

		if (command.getIncidentReportId() != null) {

			Long incidentReportId = command.getIncidentReportId();

			Semaphore mutex = IncidentHeper.acquire(genericExecutor, incidentReportId);

			if (mutex != null) {

				if (!IncidentEntitiesManager.getInstance().isIncidentReportCanceled(command.getIncidentReportId())) {

					ImsIncidentReport incidentReport = IncidentEntitiesManager.getInstance()
							.backwardIncidentReport(incidentReportId);

					if (incidentReport != null) {

						log.info("IncidentReport backward: " + incidentReport.getId());

						incidentReports = new ArrayList<>();
						incidentReports.add(incidentReport);

						IncidentHeper.evalueIncidentReport(incidentReport.getId());
					}
				}
				mutex.release();

			} else {
				log.warn("Mutex not found: " + command.getIncidentReportId());
			}
		}

		return incidentReports;
	}

	public List<ImsIncidentReport> setCancelIncidentReportCommand(SetCancelIncidentReportCommand command) {

		return cancelIncidentReportCommand(command.getIncidentReportId(), command.getUserId());
	}

	public List<ImsIncidentReport> setAssignUserIncidentReportCommand(SetAssignUserIncidentReportCommand command) {

		List<ImsIncidentReport> incidentReports = null;

		if (command.getIncidentReportId() != null) {

			Semaphore mutex = IncidentHeper.acquire(genericExecutor, command.getIncidentReportId());

			if (mutex != null) {

				if (ObjectUtils.allNotNull(command.getUserId())) {

					ImsIncidentReport incidentReport = IncidentEntitiesManager.getInstance()
							.updateCurrentUser(command.getIncidentReportId(), command.getUserId());

					if (incidentReport != null) {

						log.info("User " + command.getUserId() + " assign in incidentReport: " + incidentReport);

						incidentReports = new ArrayList<>();
						incidentReports.add(incidentReport);
					}
				} else {
					log.error("The incident: " + command.getIncidentReportId() + " user id is null");
				}

				mutex.release();

			} else {
				log.warn("Mutex not found: " + command.getIncidentReportId());
			}
		}

		return incidentReports;
	}

	/**
	 * 
	 * @param command
	 * @return
	 */
	public List<ImsIncidentReport> setAssignCauseIncidentReportCommand(SetAssignCauseIncidentReportCommand command) {

		List<ImsIncidentReport> incidentReports = null;

		if (command.getIncidentReportId() != null) {

			Semaphore mutex = IncidentHeper.acquire(genericExecutor, command.getIncidentReportId());

			if (mutex != null) {

				if (ObjectUtils.allNotNull(command.getIncidentTypeCause())
						&& IncidentEntitiesManager.getInstance()
								.isIncidentReportConfirmed(command.getIncidentReportId())
						&& IncidentEntitiesManager.getInstance().checkIncidentReportType(command.getIncidentReportId(),
								command.getIncidentTypeCause().getIncidentTypeId())) {

					ImsIncidentReport incidentReport = IncidentEntitiesManager.getInstance()
							.updateCurrentCause(command.getIncidentReportId(), command.getIncidentTypeCause().getId());

					if (incidentReport != null) {

						log.info("Cause " + command.getIncidentTypeCause() + " assigned in incidentReport: "
								+ incidentReport);

						incidentReports = new ArrayList<>();
						incidentReports.add(incidentReport);
					}
				}

				mutex.release();

			} else {
				log.warn("Mutex not found: " + command.getIncidentReportId());
			}
		}

		return incidentReports;
	}

	public List<ImsIncidentReport> setFinalizeTaskIncidentReportCommand(SetFinalizeTaskIncidentReportCommand command) {

		List<ImsIncidentReport> incidentReports = null;

		if (command.getIncidentReportId() != null
				&& IncidentEntitiesManager.getInstance().isIncidentReportOpen(command.getIncidentReportId())) {

			incidentReports = finalizeTaskIncidentReport(command.getIncidentReportId(), command.getIncidentTypeTaskId(),
					command.getIncidentTypeTaskValues());
		}

		return incidentReports;
	}

	public List<ImsIncidentReport> setExecuteTaskPlanIncidentReportCommand(
			SetExecuteTaskPlanIncidentReportCommand command) {

		List<ImsIncidentReport> incidentReports = null;

		if (command.getIncidentReportId() != null) {
			Semaphore mutex = IncidentHeper.acquire(genericExecutor, command.getIncidentReportId());

			if (mutex != null) {

				if (command.getIncidentTaskPlanId() != null
						&& IncidentEntitiesManager.getInstance().isIncidentReportOpen(command.getIncidentReportId())) {

					ImsIncidentReport incidentReport = IncidentEntitiesManager.getInstance()
							.executeTaskPlanIncidentReport(command.getIncidentReportId(),
									command.getIncidentTaskPlanId(), Boolean.FALSE);

					if (incidentReport != null) {

						log.info("Task plan executed: " + command.getIncidentTaskPlanId() + " incidentReport: "
								+ incidentReport.getId());

						incidentReports = new ArrayList<>();
						incidentReports.add(incidentReport);

					}

				} else {
					log.error("The incident: " + command.getIncidentReportId() + " isn't open");
				}
				mutex.release();

			} else {
				log.warn("Mutex not found: " + command.getIncidentReportId());
			}
		}
		return incidentReports;
	}

	public List<ImsIncidentReport> setAddLogIncidentReportCommand(SetAddLogIncidentReportCommand command) {

		List<ImsIncidentReport> incidentReports = null;

		if (!CollectionUtils.isEmpty(command.getIncidentReportLogs())) {

			for (ImsIncidentReportLog incidentReportLog : command.getIncidentReportLogs()) {

				if (incidentReportLog.getIncidentReportId() != null) {

					ImsIncidentReport incidentReport = new ImsIncidentReport();
					incidentReport.setId(incidentReportLog.getIncidentReportId());

					if (incidentReports == null) {

						incidentReports = new ArrayList<>();
					}

					incidentReports.add(incidentReport);

					Semaphore mutex = IncidentHeper.acquire(genericExecutor, incidentReportLog.getIncidentReportId());

					if (mutex != null) {

						IncidentEntitiesManager.getInstance().createIncidetReportLog(incidentReportLog);

						mutex.release();

					} else {
						log.warn("Mutex not found: " + incidentReportLog.getIncidentReportId());
					}
				}
			}
		}
		return incidentReports;
	}

	public List<ImsIncidentReport> setDeleteLogIncidentReportCommand(SetDeleteLogIncidentReportCommand command) {

		List<ImsIncidentReport> incidentReports = null;

		if (command.getIncidentReportLogId() != null) {

			ImsIncidentReportLog incidentReportLog = IncidentEntitiesManager.getInstance()
					.getIncidetReportLog(command.getIncidentReportLogId());

			if (incidentReportLog.getIncidentReportId() != null) {

				Semaphore mutex = IncidentHeper.acquire(genericExecutor, incidentReportLog.getIncidentReportId());

				if (mutex != null) {

					if (IncidentEntitiesManager.getInstance().deleteIncidetReportLog(incidentReportLog) != null) {

						ImsIncidentReport incidentReport = new ImsIncidentReport();
						incidentReport.setId(incidentReportLog.getIncidentReportId());

						if (incidentReports == null) {

							incidentReports = new ArrayList<>();
						}
						incidentReports.add(incidentReport);
					}
					mutex.release();

				} else {
					log.warn("Mutex not found: " + incidentReportLog.getIncidentReportId());
				}
			}
		}
		return incidentReports;
	}

	public List<ImsIncidentReport> checkIncidentPendingToConfirm(Long now, Long systemParamAutoConfirm) {

		List<ImsIncidentReport> incidentReports = null;

		List<Pair<Long, Boolean>> incidentReportIds = IncidentEntitiesManager.getInstance()
				.getIncidentPendingToConfirm(now, systemParamAutoConfirm);

		if (!CollectionUtils.isEmpty(incidentReportIds)) {

			incidentReports = new ArrayList<>();

			for (Pair<Long, Boolean> pair : incidentReportIds) {

				if (pair.getRight().booleanValue()) {

					List<ImsIncidentReport> incidentReportsAux = cancelIncidentReportCommand(pair.getLeft(), null);

					if (incidentReportsAux != null) {

						/*
						 * IncidentEntitiesManager.getInstance().addLogIncidentReport(null,
						 * pair.getLeft(), LogSubtype.LOG_SUBTYPE_INCIDENT_REPORT_CANCELED,
						 * LogLevel.INFO);
						 */

						incidentReports.addAll(incidentReportsAux);
					}
				} else {

					List<ImsIncidentReport> incidentReportsAux = confirmIncidentReport(pair.getLeft(), null);

					if (incidentReportsAux != null) {

						IncidentEntitiesManager.getInstance().addLogIncidentReport(null, pair.getLeft(),
								LogSubtype.LOG_SUBTYPE_INCIDENT_REPORT_CONFIRMED, LogLevel.INFO);

						incidentReports.addAll(incidentReportsAux);
					}
				}
			}

		}
		return incidentReports;

	}

	public List<ImsIncidentReport> checkIncidentExpired(Long now) {

		List<ImsIncidentReport> incidentReports = null;

		List<Long> incidentReportIds = IncidentEntitiesManager.getInstance().getExpiredIncidents(now);

		if (!CollectionUtils.isEmpty(incidentReportIds)) {

			incidentReports = new ArrayList<>();

			for (Long incidentReportId : incidentReportIds) {

				List<ImsIncidentReport> incidentReportsAux = cancelIncidentReportCommand(incidentReportId, null);

				if (incidentReportsAux != null) {

					IncidentEntitiesManager.getInstance().addLogIncidentReport(null, incidentReportId,
							LogSubtype.LOG_SUBTYPE_INCIDENT_REPORT_CANCELED, LogLevel.INFO,
							"LBL_INCIDENT_REPORT_EXPIRED");

					incidentReports.addAll(incidentReportsAux);
				}

			}

		}
		return incidentReports;

	}

	public List<ImsIncidentReport> checkIncidentPendingToDelete() {

		List<ImsIncidentReport> incidentReports = null;

		List<Long> incidentReportIds = IncidentEntitiesManager.getInstance().getIncidentPendingToDelete();

		if (!CollectionUtils.isEmpty(incidentReportIds)) {

			incidentReports = new ArrayList<>();

			for (Long incidentReportId : incidentReportIds) {

				if (IncidentEntitiesManager.getInstance().saveIncidentReport(incidentReportId) == null) {

					log.error("IncidentReport " + incidentReportId + " not saved");
				}

				ImsIncidentReport incidentReport = deleteIncidentReport(incidentReportId);

				if (incidentReport != null) {

					incidentReports.add(incidentReport);

				} else {
					log.error("IncidentReport " + incidentReportId + " not deleted");
				}
			}
		}
		return incidentReports;
	}

	public List<ImsIncidentReport> checkTasksPendingToFinalize(Long now) {

		List<ImsIncidentReport> incidentReports = null;

		List<Pair<Long, Long>> tasksPendingToFinalize = IncidentEntitiesManager.getInstance()
				.getTasksPendingToFinalize(now);

		if (!CollectionUtils.isEmpty(tasksPendingToFinalize)) {

			incidentReports = new ArrayList<>();

			for (Pair<Long, Long> taskPendingToFinalize : tasksPendingToFinalize) {

				Long incidentReportId = taskPendingToFinalize.getKey();
				Long incidentTypeTaskId = taskPendingToFinalize.getValue();

				ImsIncidentTypeTask incidentTypeTask = IncidentEntitiesManager.getInstance()
						.getIncidentTypeTask(incidentTypeTaskId);

				if (incidentTypeTask != null) {

					List<ImsIncidentReport> incidentReportsAux = finalizeTaskIncidentReport(incidentReportId,
							incidentTypeTaskId, null);

					if (!CollectionUtils.isEmpty(incidentReportsAux)) {

						incidentReports.addAll(incidentReportsAux);

						log.info(
								"Finalize delayed task: " + incidentTypeTaskId + " incidentReport:" + incidentReportId);
						IncidentEntitiesManager.getInstance().addLogIncidentReport(null, incidentReportId,
								LogSubtype.LOG_SUBTYPE_FINALIZED_TASK, LogLevel.INFO,
								incidentTypeTask.getAlias() + (incidentTypeTask.getDescription() != null
										? " - " + incidentTypeTask.getDescription()
										: ""));
					}
				}

			}
		}
		return incidentReports;
	}

}
