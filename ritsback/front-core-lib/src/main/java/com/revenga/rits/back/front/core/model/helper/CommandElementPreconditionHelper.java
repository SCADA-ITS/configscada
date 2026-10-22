package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.CommandElementPrecondition;
import com.revenga.rits.back.front.core.model.vo.CommandElementPreconditionVO;

public class CommandElementPreconditionHelper {

	private CommandElementPreconditionHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());

	}

	public static void assign(final CommandElementPrecondition commandElementPrecondition,
			CommandElementPreconditionVO commandElementPreconditionVO) {

		if (ObjectUtils.allNotNull(commandElementPrecondition, commandElementPreconditionVO)) {

			BasicElementHelper.fillBasicProperties(commandElementPreconditionVO, commandElementPrecondition);

			commandElementPreconditionVO.setThresholdVal1(commandElementPrecondition.getThresholdVal1());
			commandElementPreconditionVO.setThresholdVal2(commandElementPrecondition.getThresholdVal2());

			if (commandElementPrecondition.getNegative() != null) {

				commandElementPreconditionVO.setNegative(commandElementPrecondition.getNegative());
			}

			if (commandElementPrecondition.getToStr() != null) {

				commandElementPreconditionVO.setToStr(commandElementPrecondition.getToStr());
			}
		}
	}

	public static CommandElementPreconditionVO get(CommandElementPrecondition commandElementPrecondition) {

		CommandElementPreconditionVO commandElementPreconditionVO = null;

		if (commandElementPrecondition != null) {

			commandElementPreconditionVO = new CommandElementPreconditionVO();

			assign(commandElementPrecondition, commandElementPreconditionVO);
		}

		return commandElementPreconditionVO;
	}

}
