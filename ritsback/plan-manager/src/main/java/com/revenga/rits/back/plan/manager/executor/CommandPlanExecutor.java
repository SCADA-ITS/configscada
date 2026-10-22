package com.revenga.rits.back.plan.manager.executor;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.command.SetPlanActionsCommand;
import com.revenga.rits.back.data.core.model.command.SetPlanActionsCommand.PlanAction;
import com.revenga.rits.back.data.core.model.command.SetPlanScheduleActionsCommand;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;
import com.revenga.rits.back.plan.manager.executor.service.PlanScheduleService;
import com.revenga.rits.back.plan.manager.executor.service.PlanService;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommandPlanExecutor extends GenericExecutor {

	private PlanService planService;
	private PlanScheduleService planScheduleService;

	public CommandPlanExecutor(Executor executor, short threadNum) {

		super(executor, threadNum);
		planService = new PlanService(this);
		planScheduleService = new PlanScheduleService(this, planService);
	}

	@Override
	protected Object prepare(Object queueItem) {

		try {

			if (queueItem instanceof SetPlanActionsCommand) {

				SetPlanActionsCommand setPlanActionsCommand = (SetPlanActionsCommand) queueItem;

				if (!CollectionUtils.isEmpty(setPlanActionsCommand.getPlanActions())) {
					
					for (PlanAction planAction : setPlanActionsCommand.getPlanActions()) {
						
						if (planAction.getAction() != null && planAction.getPlanId() != null) {
							
							switch (planAction.getAction()) {

							case PLAN_PLAY:
								planService.playPlanAction(planAction.getPlanId(), setPlanActionsCommand.getSource());
								break;
								
							case PLAN_PAUSE:
								planService.pausePlanAction(planAction.getPlanId());
								break;
							
							case PLAN_STOP:
								planService.stopPlanAction(planAction.getPlanId());
								break;
								
							case PLAN_CANCEL:
								planService.cancelPlanAction(planAction.getPlanId());
								break;

							}
						}
					}
				}

			} else if (queueItem instanceof SetPlanScheduleActionsCommand) {

				SetPlanScheduleActionsCommand setPlanScheduleCommand = (SetPlanScheduleActionsCommand) queueItem;

				planScheduleService.setPlanScheduleCommand(setPlanScheduleCommand);

			} else {

				log.warn("Unknown command " + queueItem.getClass().getSimpleName() + " dequeued by "
						+ this.getClass().getSimpleName());
			}

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

		if (threadNum == 0) {

			planService.init();
			planScheduleService.init();

		} else {
			end();
		}
	}

	@Override
	protected void end() {

		if (threadNum == 0) {

			planScheduleService.deInit();
			planService.deInit();
		}
	}

	@Override
	protected void evaluate() {
		// TODO Auto-generated method stub

	}

}
