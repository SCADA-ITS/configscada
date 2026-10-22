package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.CommandElementPrecondition;
import com.revenga.rits.back.data.core.model.CommandElementValuePrecondition;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ThresholdOperation;
import com.revenga.rits.back.front.core.model.helper.CommandElementPreconditionHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementPreconditionVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValuePreconditionVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.ThresholdOperationVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class CommandElementPreconditionGraphql extends CommandElementPreconditionVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5440305527088375997L;

	private CommandElementPrecondition commandElementPrecondition;

	public CommandElementPreconditionGraphql(final CommandElementPrecondition commandElementPrecondition) {

		CommandElementPreconditionHelper.assign(commandElementPrecondition, this);

		this.commandElementPrecondition = commandElementPrecondition;
	}

	@Override
	public ElementVO getTargetElement() {

		if (super.getTargetElement() == null && commandElementPrecondition != null
				&& commandElementPrecondition.getTargetElementTypeId() != null
				&& commandElementPrecondition.getTargetElementId() != null) {

			Element element = EntitiesManager.getInstance().getElementRepository().get(
					commandElementPrecondition.getTargetElementTypeId(),
					commandElementPrecondition.getTargetElementId(), false);

			if (element != null) {

				super.setTargetElement(new ElementGraphql(element));

			}
		}

		return super.getTargetElement();
	}

	@Override
	public ElementTypeParamVO getTargetElementTypeParam() {

		if (super.getTargetElementTypeParam() == null && commandElementPrecondition != null
				&& commandElementPrecondition.getTargetElementTypeId() != null
				&& commandElementPrecondition.getTargetElementTypeParamId() != null
				&& commandElementPrecondition.getTargetParamTypeId() != null) {

			ElementTypeParam elementTypeParam = EntitiesManager.getInstance().getElementTypeParamRepository().get(
					commandElementPrecondition.getTargetElementTypeId(),
					commandElementPrecondition.getTargetParamTypeId(),
					commandElementPrecondition.getTargetElementTypeParamId(), false);

			if (elementTypeParam != null) {

				super.setTargetElementTypeParam(new ElementTypeParamGraphql(elementTypeParam));
			}
		}

		return super.getTargetElementTypeParam();
	}

	@Override
	public ThresholdOperationVO getThresholdOperation() {

		if (super.getThresholdOperation() == null && commandElementPrecondition != null
				&& commandElementPrecondition.getThresholdOperationId() != null) {

			ThresholdOperation thresholdOperation = EntitiesManager.getInstance().getThresholdOperationRepository()
					.get(commandElementPrecondition.getThresholdOperationId(), false);

			if (thresholdOperation != null) {

				super.setThresholdOperation(new ThresholdOperationGraphql(thresholdOperation));

			}

		}

		return super.getThresholdOperation();

	}

	@Override
	public ElementTypeStateVO getElementTypeState() {

		if (super.getElementTypeState() == null && commandElementPrecondition != null
				&& commandElementPrecondition.getElementTypeId() != null
				&& commandElementPrecondition.getElementTypeStateId() != null) {

			ElementTypeState elementTypeState = EntitiesManager.getInstance().getElementTypeStateRepository().get(
					commandElementPrecondition.getElementTypeId(), commandElementPrecondition.getElementTypeStateId(),
					false);

			if (elementTypeState != null) {

				super.setElementTypeState(new ElementTypeStateGraphql(elementTypeState));

			}
		}

		return super.getElementTypeState();

	}

	@Override
	public AlarmConfigVO getActiveAlarmConfig() {

		if (super.getActiveAlarmConfig() == null && commandElementPrecondition != null
				&& commandElementPrecondition.getActiveAlarmConfigId() != null) {

			AlarmConfig activeAlarmConfig = EntitiesManager.getInstance().getAlarmConfigRepository()
					.get(commandElementPrecondition.getActiveAlarmConfigId(), false);

			if (activeAlarmConfig != null) {

				super.setActiveAlarmConfig(new AlarmConfigGraphql(activeAlarmConfig));

			}
		}

		return super.getActiveAlarmConfig();

	}

	@Override
	public List<CommandElementValuePreconditionVO> getCommandElementValuePreconditions() {

		if (super.getCommandElementValuePreconditions() == null && commandElementPrecondition != null
				&& commandElementPrecondition.getCommandElementTypeId() != null
				&& commandElementPrecondition.getId() != null && commandElementPrecondition.getElementTypeId() != null
				&& commandElementPrecondition.getElementId() != null) {

			List<CommandElementValuePrecondition> commandElementValuePreconditions = EntitiesManager.getInstance()
					.getCommandElementValuePreconditionRepository()
					.gets(commandElementPrecondition.getCommandElementTypeId(),
							commandElementPrecondition.getElementTypeId(), commandElementPrecondition.getElementId(),
							commandElementPrecondition.getId(), false);

			if (!CollectionUtils.isEmpty(commandElementValuePreconditions)) {

				List<CommandElementValuePreconditionVO> commandElementValuePreconditionsVO = new ArrayList<>();

				for (CommandElementValuePrecondition commandElementValuePrecondition : commandElementValuePreconditions) {

					commandElementValuePreconditionsVO
							.add(new CommandElementValuePreconditionGraphql(commandElementValuePrecondition));
				}

				super.setCommandElementValuePreconditions(commandElementValuePreconditionsVO);

			}

		}

		return super.getCommandElementValuePreconditions();

	}

}
