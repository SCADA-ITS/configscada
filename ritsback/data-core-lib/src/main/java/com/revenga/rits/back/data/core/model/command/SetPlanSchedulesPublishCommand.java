package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.PlanSchedules;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetPlanSchedulesPublishCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9080162283248352852L;

	private List<PlanSchedules> planSchedules;

	public void addPlanSchedule(PlanSchedules planSchedule) {

		if (planSchedule != null) {

			if (planSchedules == null) {

				planSchedules = new ArrayList<>();
			}

			planSchedules.add(planSchedule);
		}
	}

	public SetPlanSchedulesPublishCommand() {

		super(Command.SET_PLAN_SCHEDULE_PUBLISH);
	}

}
