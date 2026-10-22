package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.PlanScheduleState;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetPlanSchedulesStatePublishCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9080162283248352852L;

	private List<PlanScheduleState> planScheduleStates;

	public void addPlanScheduleState(PlanScheduleState planScheduleState) {

		if (planScheduleState != null) {

			if (planScheduleStates == null) {
				
				planScheduleStates = new ArrayList<>();
			}

			planScheduleStates.add(planScheduleState);
		}
	}

	public SetPlanSchedulesStatePublishCommand() {

		super(Command.SET_PLAN_SCHEDULE_STATE_PUBLISH);
	}

}
