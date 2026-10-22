package com.revenga.rits.back.data.core.model.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.PlanSchedules;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetPlanScheduleActionsCommand extends Command {
	/**
	* 
	*/
	private static final long serialVersionUID = 8857584229274626570L;

	public enum Action {
		PLAN_SCHEDULE_ON, PLAN_SCHEDULE_PAUSE, PLAN_SCHEDULE_STOPPED, PLAN_SCHEDULE_RESET
	}

	@Data
	public static class PlanScheduleAction implements Serializable {
		/**
		* 
		*/
		private static final long serialVersionUID = -4486882725474869617L;

		PlanSchedules planSchedule;
		Action action;

	}

	private List<PlanScheduleAction> planScheduleActions;

	public void addPlanScheduleAction(PlanSchedules planSchedule, Action action) {

		if (planSchedule != null && action != null) {

			if (planScheduleActions == null) {

				planScheduleActions = new ArrayList<>();
			}

			PlanScheduleAction planScheduleAction = new PlanScheduleAction();

			planScheduleAction.setPlanSchedule(planSchedule);
			planScheduleAction.setAction(action);

			planScheduleActions.add(planScheduleAction);
		}
	}

	public SetPlanScheduleActionsCommand() {
		
		super(Command.SET_PLAN_SCHEDULE_ACTIONS);
	}
}