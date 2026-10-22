package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.front.core.model.vo.CommandElementTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementTypeVO;

public class CommandElementTypeHelper {

	private CommandElementTypeHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static void assign(final CommandElementType commandElementType, CommandElementTypeVO commandElementTypeVO) {

		if (ObjectUtils.allNotNull(commandElementType, commandElementTypeVO)) {

			BasicElementHelper.fillBasicProperties(commandElementTypeVO, commandElementType);

			if (commandElementType.getCommandElementTypeParams() != null) {

				List<CommandElementTypeParamVO> paramTypes = new ArrayList<>();

				for (CommandElementTypeParam param : commandElementType.getCommandElementTypeParams()) {

					if (param.getId() != null) {

						paramTypes.add(CommandElementTypeParamHelper.get(param));
					}
				}

				commandElementTypeVO.setCommandElementTypeParams(paramTypes);
			}
			
			commandElementTypeVO.setPmQueueCompatibility(commandElementType.getPmQueueCompatibility());
		}

	}

	public static CommandElementTypeVO get(CommandElementType commandElementType) {

		CommandElementTypeVO planOrderTypeParamVO = null;

		if (commandElementType != null) {

			planOrderTypeParamVO = new CommandElementTypeVO();

			assign(commandElementType, planOrderTypeParamVO);
		}

		return planOrderTypeParamVO;
	}

}
