package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.PlanState;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetPlanStatePublishCommand extends Command {
	/**
	* 
	*/
	private static final long serialVersionUID = 4378860717528189231L;

	private List<PlanState> planStates;

	public void add(PlanState planState) {

		if (planState != null) {

			if (planStates == null) {

				planStates = new ArrayList<>();
			}

			planStates.add(planState);
		}
	}

	public SetPlanStatePublishCommand() {

		super(Command.SET_PLAN_STATE_PUBLISH);
	}

}
