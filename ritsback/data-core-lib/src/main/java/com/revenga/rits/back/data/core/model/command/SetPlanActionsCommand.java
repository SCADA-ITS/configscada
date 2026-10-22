package com.revenga.rits.back.data.core.model.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetPlanActionsCommand extends Command {
	/**
	* 
	*/
	private static final long serialVersionUID = 8857584229274626570L;

	public enum Action {
		
		PLAN_PLAY, PLAN_PAUSE, PLAN_STOP, PLAN_CANCEL
	}

	@Data
	public static class PlanAction implements Serializable {
		/**
		* 
		*/
		private static final long serialVersionUID = -4486882725474869617L;

		Long planId;
		Action action;

	}

	private List<PlanAction> planActions;
	private String source;
	
	public void addPlanAction(Long planId, Action action) {

		if (planId != null && action != null) {

			if (planActions == null) {

				planActions = new ArrayList<>();
			}

			PlanAction planAction = new PlanAction();

			planAction.setPlanId(planId);
			planAction.setAction(action);

			planActions.add(planAction);
		}
	}

	public SetPlanActionsCommand() {
		super(Command.SET_PLAN_ACTIONS);
	}
}