package com.revenga.rits.back.front.core.model.helper.command;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.command.SetPlanActionsCommand;
import com.revenga.rits.back.data.core.model.command.SetPlanActionsCommand.Action;
import com.revenga.rits.back.front.core.model.vo.PlanActionVO;
import com.revenga.rits.back.front.core.model.vo.PlanActionVO.PlanAction;

public class SetPlanActionsCommandHelper {

	private SetPlanActionsCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetPlanActionsCommand get(final List<String> planIds, PlanAction planAction) {

		SetPlanActionsCommand setPlanActionsCommand = null;

		if (!CollectionUtils.isEmpty(planIds) && planAction != null) {

			Action action = null;

			switch (planAction) {

			case PLAY:
				action = Action.PLAN_PLAY;
				break;

			case PAUSE:
				action = Action.PLAN_PAUSE;
				break;

			case STOP:
				action = Action.PLAN_STOP;
				break;

			case CANCEL:
				action = Action.PLAN_CANCEL;
				break;
			}

			if (action != null) {
				
				setPlanActionsCommand = new SetPlanActionsCommand();
				
				for (String planId : planIds) {

					Key key = new Key(planId);

					if (key.getClazz() == Plan.class) {

						Long id = Long.valueOf(key.getId(1));
						
						if (planId != null) {
							
							setPlanActionsCommand.addPlanAction(id, action);
						}
					}
				}
			}

		}

		return setPlanActionsCommand;
	}

	public static SetPlanActionsCommand get(final List<PlanActionVO> planActions) {

		SetPlanActionsCommand setPlanActionsCommand = null;

		if (!CollectionUtils.isEmpty(planActions)) {

			setPlanActionsCommand = new SetPlanActionsCommand();

			for (PlanActionVO planAction : planActions) {

				if (planAction.getPlanId() != null && planAction.getAction() != null) {

					Key key = new Key(planAction.getPlanId());

					if (key.getClazz() == Plan.class) {

						Long planId = Long.valueOf(key.getId(1));

						if (planId != null) {
							switch (planAction.getAction()) {

							case PLAY:
								setPlanActionsCommand.addPlanAction(planId, Action.PLAN_PLAY);
								break;

							case PAUSE:
								setPlanActionsCommand.addPlanAction(planId, Action.PLAN_PAUSE);
								break;

							case STOP:
								setPlanActionsCommand.addPlanAction(planId, Action.PLAN_STOP);
								break;

							case CANCEL:
								setPlanActionsCommand.addPlanAction(planId, Action.PLAN_CANCEL);
								break;
							}
						}
					}
				}
			}
		}

		return setPlanActionsCommand;
	}
}
