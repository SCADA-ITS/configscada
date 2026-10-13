package com.revenga.rits.back.plan.manager.executor.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.revenga.rits.back.data.core.model.LogLevel;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.PlanScheduleState;
import com.revenga.rits.back.data.core.model.PlanScheduleStateType;
import com.revenga.rits.back.data.core.model.PlanSchedules;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetPlanScheduleActionsCommand;
import com.revenga.rits.back.data.core.model.command.SetPlanScheduleActionsCommand.Action;
import com.revenga.rits.back.data.core.model.command.SetPlanScheduleActionsCommand.PlanScheduleAction;
import com.revenga.rits.back.entities.lib.helper.AuditHelper;
import com.revenga.rits.back.data.core.model.command.SetPlanSchedulesStatePublishCommand;
import com.revenga.rits.back.plan.manager.executor.CommandPlanExecutor;
import com.revenga.rits.back.plan.manager.helper.PlanScheduleHelper;
import com.revenga.rits.back.plan.manager.service.EntitiesManager;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlanScheduleService {

	private Scheduler scheduler;

	@Getter
	private PlanService planService;

	public PlanScheduleService(CommandPlanExecutor commandPlanExecutor, PlanService planService) {

		this.planService = planService;
	}

	public Boolean init() {

		Boolean ok = true;

		if (planService != null) {

			SchedulerFactory sf = new StdSchedulerFactory();
			try {

				scheduler = sf.getScheduler();
				scheduler.getContext().put(planService.getClass().getSimpleName(), planService);

				PlanScheduleHelper.initPlanSchedule(scheduler);
				scheduler.start();

			} catch (SchedulerException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				ok = false;
			}
		}

		return ok;
	}

	public Boolean deInit() {
		Boolean ok = true;
		PlanScheduleHelper.deInitPlanSchedule(scheduler);

		try {

			log.debug("Shutdown scheduled jobs ...");

			scheduler.shutdown(true);

			log.debug("Shutdown Complete");
			log.debug("Executed " + scheduler.getMetaData().getNumberOfJobsExecuted() + " jobs.");

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		return ok;
	}

	public List<PlanSchedules> setPlanScheduleCommand(SetPlanScheduleActionsCommand setPlanScheduleCommand) {

		List<PlanSchedules> planSchedules = new ArrayList<>();

		SetPlanSchedulesStatePublishCommand setPlanSchedulesStatePublishCommand = null;

		if (!CollectionUtils.isEmpty(setPlanScheduleCommand.getPlanScheduleActions())) {

			for (PlanScheduleAction planScheduleAction : setPlanScheduleCommand.getPlanScheduleActions()) {

				PlanScheduleState planScheduleState = null;
				PlanSchedules planSchedule = planScheduleAction.getPlanSchedule();
				Action action = planScheduleAction.getAction();

				if (planSchedule != null && action != null && planSchedule.getId() != null) {

					if (action == Action.PLAN_SCHEDULE_ON) {

						planSchedule = EntitiesManager.getInstance().getPlanSchedule(planSchedule.getId());

						if (planSchedule != null && planSchedule.getPlanId() != null
								&& !Long.valueOf(PlanScheduleStateType.PLAN_SCHEDULE_STATE_TYPE_RUNNING).equals(
										EntitiesManager.getInstance().getStateByPlanSchedule(planSchedule.getId()))) {

							Plan plan = EntitiesManager.getInstance().getPlan(planSchedule.getPlanId());

							if (plan != null) {
								if (PlanScheduleHelper.playPlanSchedule(planSchedule, scheduler)) {

									planScheduleState = EntitiesManager.getInstance().putStateInPlanSchedule(
											planSchedule.getId(),
											PlanScheduleStateType.PLAN_SCHEDULE_STATE_TYPE_RUNNING);

									AuditHelper.Audit(LogType.LOG_TYPE_PLANS, LogSubtype.LOG_SUBTYPE_PLAN_SCHEDULED,
											User.USER_SYSTEM, LogLevel.TRACE, plan.getAlias(),
											planSchedule.getCronExpression());

								} else {
									planScheduleState = EntitiesManager.getInstance().putStateInPlanSchedule(
											planSchedule.getId(),
											PlanScheduleStateType.PLAN_SCHEDULE_STATE_TYPE_FAILED);
								}
							} else {
								log.error("Plan not " + planSchedule.getPlanId() + " found");
							}

						} else {
							log.info("Not found planSchedule");
						}

					} else if (action == Action.PLAN_SCHEDULE_PAUSE) {

						if (planSchedule != null && Long.valueOf(PlanScheduleStateType.PLAN_SCHEDULE_STATE_TYPE_RUNNING)
								.equals(EntitiesManager.getInstance().getStateByPlanSchedule(planSchedule.getId()))) {

							if (PlanScheduleHelper.stopPlanSchedule(planSchedule, scheduler)) {

								planScheduleState = EntitiesManager.getInstance().putStateInPlanSchedule(
										planSchedule.getId(), PlanScheduleStateType.PLAN_SCHEDULE_STATE_TYPE_PAUSE);

							} else {

								planScheduleState = EntitiesManager.getInstance().putStateInPlanSchedule(
										planSchedule.getId(), PlanScheduleStateType.PLAN_SCHEDULE_STATE_TYPE_FAILED);
							}
						}

					} else if (action == Action.PLAN_SCHEDULE_STOPPED) {

						PlanScheduleHelper.stopPlanSchedule(planSchedule, scheduler);

					} else if (action == Action.PLAN_SCHEDULE_RESET) {

						planSchedule = EntitiesManager.getInstance().getPlanSchedule(planSchedule.getId());

						if (planSchedule != null && planSchedule.getPlanId() != null) {

							Plan plan = EntitiesManager.getInstance().getPlanWithOrder(planSchedule.getPlanId());

							if (plan != null) {
								PlanScheduleHelper.stopPlanSchedule(planSchedule, scheduler);

								if (PlanScheduleHelper.playPlanSchedule(planSchedule, scheduler)) {

									planScheduleState = EntitiesManager.getInstance().putStateInPlanSchedule(
											planSchedule.getId(),
											PlanScheduleStateType.PLAN_SCHEDULE_STATE_TYPE_RUNNING);

									AuditHelper.Audit(LogType.LOG_TYPE_PLANS, LogSubtype.LOG_SUBTYPE_PLAN_SCHEDULED,
											User.USER_SYSTEM, LogLevel.TRACE, plan.getAlias(),
											planSchedule.getCronExpression());
								} else {

									planScheduleState = EntitiesManager.getInstance().putStateInPlanSchedule(
											planSchedule.getId(),
											PlanScheduleStateType.PLAN_SCHEDULE_STATE_TYPE_FAILED);
								}
							} else {

								log.error("Plan not " + planSchedule.getPlanId() + " found");
							}

						} else {
							log.info("Not found planSchedule");
						}
					}

					if (planScheduleState != null) {

						if (setPlanSchedulesStatePublishCommand == null) {

							setPlanSchedulesStatePublishCommand = new SetPlanSchedulesStatePublishCommand();
						}
						setPlanSchedulesStatePublishCommand.addPlanScheduleState(planScheduleState);
					}

				}
			}

			if (setPlanSchedulesStatePublishCommand != null) {
				setPlanSchedulesStatePublishCommand.setAction(Command.ACTION_UPDATE);
				EntitiesManager.getInstance().sendCommand(setPlanSchedulesStatePublishCommand);

			}
		}

		return planSchedules;

	}
}
