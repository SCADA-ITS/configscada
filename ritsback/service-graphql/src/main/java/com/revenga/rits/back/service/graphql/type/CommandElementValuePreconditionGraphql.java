package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.CommandElementValuePrecondition;
import com.revenga.rits.back.data.core.model.ThresholdOperation;
import com.revenga.rits.back.front.core.model.helper.CommandElementValuePreconditionHelper;
import com.revenga.rits.back.front.core.model.vo.CommandElementTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValuePreconditionVO;
import com.revenga.rits.back.front.core.model.vo.ThresholdOperationVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class CommandElementValuePreconditionGraphql extends CommandElementValuePreconditionVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3202652020531058254L;

	private CommandElementValuePrecondition commandElementValuePrecondition;

	public CommandElementValuePreconditionGraphql(
			final CommandElementValuePrecondition commandElementValuePrecondition) {

		CommandElementValuePreconditionHelper.assign(commandElementValuePrecondition, this);

		this.commandElementValuePrecondition = commandElementValuePrecondition;
	}

	@Override
	public CommandElementTypeParamVO getCommandElementTypeParam() {

		if (super.getCommandElementTypeParam() == null && commandElementValuePrecondition != null
				&& commandElementValuePrecondition.getCommandElementTypeId() != null
				&& commandElementValuePrecondition.getElementTypeId() != null
				&& commandElementValuePrecondition.getCommandElementTypeParamId() != null) {

			CommandElementTypeParam commandElementTypeParam = EntitiesManager.getInstance()
					.getCommandElementTypeParamRepository()
					.get(commandElementValuePrecondition.getCommandElementTypeId(),
							commandElementValuePrecondition.getElementTypeId(),
							commandElementValuePrecondition.getCommandElementTypeParamId(), false);

			if (commandElementTypeParam != null) {

				super.setCommandElementTypeParam(new CommandElementTypeParamGraphql(commandElementTypeParam));
			}
		}

		return super.getCommandElementTypeParam();
	}

	@Override
	public ThresholdOperationVO getThresholdOperation() {

		if (super.getThresholdOperation() == null && commandElementValuePrecondition != null
				&& commandElementValuePrecondition.getThresholdOperationId() != null) {

			ThresholdOperation thresholdOperation = EntitiesManager.getInstance().getThresholdOperationRepository()
					.get(commandElementValuePrecondition.getThresholdOperationId(), false);

			if (thresholdOperation != null) {

				super.setThresholdOperation(new ThresholdOperationGraphql(thresholdOperation));

			}
		}

		return super.getThresholdOperation();

	}
}
