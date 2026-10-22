package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;

public class CommandElementHelper {

	private CommandElementHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static CommandElementVO get(CommandElement commandElement) {

		CommandElementVO commandElementVO = null;

		if (commandElement != null) {

			commandElementVO = new CommandElementVO();

			assign(commandElement, commandElementVO);
		}

		return commandElementVO;
	}

	public static void assign(final CommandElement commandElement, CommandElementVO commandElementVO) {

		if (ObjectUtils.allNotNull(commandElement, commandElementVO)) {

			BasicElementHelper.fillBasicProperties(commandElementVO, commandElement);

			if (commandElement.getCommandElementTypeId() != null) {

				CommandElementType commandElementType = new CommandElementType();
				commandElementType.setId(commandElement.getCommandElementTypeId());
				commandElementVO.setCommandElementTypeId(BasicElementHelper.getIdFromElement(commandElementType));
			}

			commandElementVO
					.setCommandElementType(CommandElementTypeHelper.get(commandElement.getCommandElementType()));

			if (commandElement.getCommandElementTypeId() == null && commandElement.getCommandElementType() != null
					&& commandElement.getCommandElementType().getId() != null) {

				commandElementVO.setCommandElementTypeId(
						BasicElementHelper.getIdFromElement(commandElement.getCommandElementType()));
			}

			if (commandElement.getCommandElementValues() != null) {

				List<CommandElementValueVO> commandElementValues = new ArrayList<>();

				for (CommandElementValue commandElementValue : commandElement.getCommandElementValues()) {

					if (ObjectUtils.allNotNull(commandElementValue.getCommandElementId(),
							commandElementValue.getCommandElementTypeId(),
							commandElementValue.getCommandElementTypeParamId(), commandElement.getElementId())) {

						commandElementValues.add(CommandElementValueHelper.get(commandElementValue));
					}
				}

				commandElementVO.setCommandElementValues(commandElementValues);
			}

			if (commandElement.getElementId() != null) {

				Element element = new Element();
				element.setId(commandElement.getElementId());
				commandElementVO.setElementId(BasicElementHelper.getIdFromElement(element));
			}

		}

	}

	public static CommandElement get(final CommandElementVO commandElementVO) {

		CommandElement commandElement = null;

		if (commandElementVO != null) {

			commandElement = new CommandElement();

			if (commandElementVO.getCommandElementTypeId() != null) {

				Key key = new Key(commandElementVO.getCommandElementTypeId());

				if (key.getClazz() == CommandElementType.class) {

					commandElement.setCommandElementTypeId(Long.valueOf(key.getId(1)));
					commandElement.setElementTypeId(Long.valueOf(key.getId(2)));
				}
			}

			if (commandElementVO.getElementId() != null) {

				Key key = new Key(commandElementVO.getElementId());

				if (key.getClazz() == Element.class) {

					commandElement.setElementTypeId(Long.valueOf(key.getId(1)));
					commandElement.setElementId(Long.valueOf(key.getId(2)));
				}
			}

			if (commandElementVO.getId() != null) {

				Key key = new Key(commandElementVO.getId());

				if (key.getClazz() == CommandElement.class) {

					commandElement.setId(Long.valueOf(key.getId(3)));
				}
			}

			if (commandElementVO.getCommandElementValues() != null) {

				List<CommandElementValue> commandElementValues = new ArrayList<>();

				for (CommandElementValueVO commandElementValueVO : commandElementVO.getCommandElementValues()) {

					CommandElementValue commandElementValue = CommandElementValueHelper.get(commandElementValueVO);

					commandElementValues.add(commandElementValue);
				}

				commandElement.setCommandElementValues(commandElementValues);
			}

		}

		return commandElement;
	}
}
