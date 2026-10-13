package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.CommandElementError;
import com.revenga.rits.back.front.core.model.vo.CommandElementErrorVO;

public class CommandElementErrorHelper {

	private CommandElementErrorHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static CommandElementErrorVO get(CommandElementError commandElementError) {

		CommandElementErrorVO commandElementErrorVO = null;

		if (commandElementError != null) {

			commandElementErrorVO = new CommandElementErrorVO();

			assign(commandElementError, commandElementErrorVO);
		}

		return commandElementErrorVO;
	}

	public static void assign(final CommandElementError commandElementError,
			CommandElementErrorVO commandElementErrorVO) {

		if (ObjectUtils.allNotNull(commandElementError, commandElementErrorVO)) {

			commandElementErrorVO.setError(commandElementError.getError());
		}

	}

}
