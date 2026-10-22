package com.revenga.rits.back.front.core.model.helper.command;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.virtual.SetIlluminationModeCommand;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Deprecated
public class SetIlluminationModeCommandHelper {

	private SetIlluminationModeCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetIlluminationModeCommand get(final CommandElementVO commandElementVO) {

		SetIlluminationModeCommand setIlluminationModeCommand = null;

		if (commandElementVO != null) {

			if (commandElementVO.getElementId() != null) {

				if (commandElementVO.getCommandElementValues() != null) {

					String value = null;

					for (CommandElementValueVO commandElementValue : commandElementVO.getCommandElementValues()) {

						if (commandElementValue.getCommandElementTypeParamId() != null) {

							Key keyCommandElementTypeParam = new Key(
									commandElementValue.getCommandElementTypeParamId());

							if (keyCommandElementTypeParam.getClazz() == CommandElementTypeParam.class) {

								CommandElementTypeParam commandElementTypeParam = new CommandElementTypeParam();
								commandElementTypeParam.setPks(keyCommandElementTypeParam.getIds());

								if (commandElementTypeParam.getId()
										.equals(CommandElementTypeParam.SEND_ILLUMINATION_MODE_PARAM_MODE)) {

									value = commandElementValue.getValue();
									break;
								}

							}
						}
					}

					if (value != null) {

						Key key = new Key(commandElementVO.getElementId());

						if (key.getClazz() == Element.class && key.getIds().size() >= 2) {

							setIlluminationModeCommand = new SetIlluminationModeCommand();

							setIlluminationModeCommand.setElementTypeId(Long.valueOf(key.getId(1)));
							setIlluminationModeCommand.setElementId(Long.valueOf(key.getId(2)));

							try {

								setIlluminationModeCommand.setMode(Long.parseLong(value));

							} catch (NumberFormatException e) {

								setIlluminationModeCommand = null;
								log.error(e.getMessage());
								log.debug(ExceptionUtils.getStackTrace(e));
							}

						}
					}
				}
			}

		}
		return setIlluminationModeCommand;
	}
}
