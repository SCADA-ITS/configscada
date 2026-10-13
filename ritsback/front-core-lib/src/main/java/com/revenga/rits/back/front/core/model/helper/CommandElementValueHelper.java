package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;

public class CommandElementValueHelper {

	private CommandElementValueHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static CommandElementValueVO get(CommandElementValue commandElementValue) {

		CommandElementValueVO commandElementValueVO = null;

		if (commandElementValue != null) {

			commandElementValueVO = new CommandElementValueVO();

			assign(commandElementValue, commandElementValueVO);
		}

		return commandElementValueVO;
	}

	public static void assign(final CommandElementValue commandElementValue,
			CommandElementValueVO commandElementValueVO) {

		if (ObjectUtils.allNotNull(commandElementValue, commandElementValueVO)) {

			BasicElementHelper.fillBasicProperties(commandElementValueVO, commandElementValue);

			commandElementValueVO.setValue(commandElementValue.getValue());

			if (commandElementValue.getCommandElementTypeParamId() != null) {

				CommandElementTypeParam commandElementTypeParam = new CommandElementTypeParam();
				commandElementTypeParam.setId(commandElementValue.getCommandElementTypeParamId());
				commandElementValueVO
						.setCommandElementTypeParamId(BasicElementHelper.getIdFromElement(commandElementTypeParam));
			}

			commandElementValueVO.setCommandElementTypeParam(
					CommandElementTypeParamHelper.get(commandElementValue.getCommandElementTypeParam()));

			if (commandElementValue.getCommandElementTypeParamId() == null
					&& commandElementValue.getCommandElementTypeParam() != null
					&& commandElementValue.getCommandElementTypeParam().getId() != null) {

				commandElementValueVO.setCommandElementTypeParamId(
						BasicElementHelper.getIdFromElement(commandElementValue.getCommandElementTypeParam()));
			}

			ModelVOHelper.translate(commandElementValueVO);

		}

	}

	public static CommandElementValue get(final CommandElementValueVO commandElementValueVO) {

		CommandElementValue commandElementValue = null;

		if (commandElementValueVO != null) {

			commandElementValue = new CommandElementValue();

			if (commandElementValueVO.getCommandElementTypeParamId() != null) {

				Key key = new Key(commandElementValueVO.getCommandElementTypeParamId());

				if (key.getClazz() == CommandElementTypeParam.class) {

					commandElementValue.setCommandElementTypeId(Long.valueOf(key.getId(1)));
					commandElementValue.setElementTypeId(Long.valueOf(key.getId(2)));
					commandElementValue.setCommandElementTypeParamId(Long.valueOf(key.getId(3)));
				}
			}

			if (commandElementValueVO.getId() != null) {

				Key key = new Key(commandElementValueVO.getId());

				if (key.getClazz() == CommandElement.class) {

					commandElementValue.setCommandElementTypeId(Long.valueOf(key.getId(1)));
					commandElementValue.setElementTypeId(Long.valueOf(key.getId(2)));
					commandElementValue.setCommandElementTypeParamId(Long.valueOf(key.getId(3)));
					commandElementValue.setCommandElementId(Long.valueOf(key.getId(4)));

				}
			}

			commandElementValue.setValue(commandElementValueVO.getValue());

			if (commandElementValueVO.getCommandElementTypeParam() != null) {

				CommandElementTypeParam commandElementTypeParam = CommandElementTypeParamHelper
						.get(commandElementValueVO.getCommandElementTypeParam());

				if (commandElementTypeParam != null) {

					if (commandElementValueVO.getCommandElementTypeParamId() == null) {

						commandElementValue.setCommandElementTypeId(commandElementTypeParam.getCommandElementTypeId());
						commandElementValue.setElementTypeId(commandElementTypeParam.getElementTypeId());
						commandElementValue.setCommandElementTypeParamId(commandElementTypeParam.getId());
					}

					commandElementValue.setCommandElementTypeParam(commandElementTypeParam);
				}
			}

		}

		return commandElementValue;
	}

	public static CommandElementValueVO get(PmCommandElementValue pmCommandElementValue) {

		CommandElementValueVO commandElementValueVO = null;

		if (pmCommandElementValue != null) {

			commandElementValueVO = new CommandElementValueVO();

			assign(pmCommandElementValue, commandElementValueVO);
		}

		return commandElementValueVO;
	}

	public static void assign(final PmCommandElementValue pmCommandElementValue,
			CommandElementValueVO commandElementValueVO) {

		if (ObjectUtils.allNotNull(pmCommandElementValue, commandElementValueVO)) {

			CommandElementValue commandElementValue = new CommandElementValue();
			commandElementValue.setCommandElementTypeId(pmCommandElementValue.getCommandElementTypeId());
			commandElementValue.setElementTypeId(pmCommandElementValue.getElementTypeId());
			commandElementValue.setCommandElementTypeParamId(pmCommandElementValue.getCommandElementTypeParamId());
			commandElementValue.setCommandElementId(pmCommandElementValue.getPmCommandElementId());
			commandElementValue.setValue(pmCommandElementValue.getValue());

			assign(commandElementValue, commandElementValueVO);
		}

	}
}
