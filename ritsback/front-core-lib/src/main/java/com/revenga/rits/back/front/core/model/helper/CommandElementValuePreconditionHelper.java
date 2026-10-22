package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.CommandElementValuePrecondition;
import com.revenga.rits.back.front.core.model.vo.CommandElementValuePreconditionVO;

public class CommandElementValuePreconditionHelper {

	private CommandElementValuePreconditionHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());

	}

	public static CommandElementValuePreconditionVO get(
			CommandElementValuePrecondition commandELementValuePrecondition) {

		CommandElementValuePreconditionVO commandElementValuePreconditionVO = null;

		if (commandELementValuePrecondition != null) {

			commandElementValuePreconditionVO = new CommandElementValuePreconditionVO();

			assign(commandELementValuePrecondition, commandElementValuePreconditionVO);
		}

		return commandElementValuePreconditionVO;
	}

	public static void assign(final CommandElementValuePrecondition commandElementValuePrecondition,
			CommandElementValuePreconditionVO commandElementValuePreconditionVO) {

		if (ObjectUtils.allNotNull(commandElementValuePrecondition, commandElementValuePreconditionVO)) {

			BasicElementHelper.fillBasicProperties(commandElementValuePreconditionVO, commandElementValuePrecondition);

			commandElementValuePreconditionVO.setThresholdVal1(commandElementValuePrecondition.getThresholdVal1());
			commandElementValuePreconditionVO.setThresholdVal2(commandElementValuePrecondition.getThresholdVal2());

		}

	}

}
