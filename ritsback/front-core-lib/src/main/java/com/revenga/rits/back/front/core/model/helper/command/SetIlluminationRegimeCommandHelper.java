package com.revenga.rits.back.front.core.model.helper.command;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.virtual.SetIlluminationRegimeCommand;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Deprecated
public class SetIlluminationRegimeCommandHelper {

	private SetIlluminationRegimeCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetIlluminationRegimeCommand get(final CommandElementVO commandElementVO) {

		SetIlluminationRegimeCommand setIlluminationRegimeCommand = null;

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
										.equals(CommandElementTypeParam.SEND_ILLUMINATION_REGIME_PARAM_REGIME)) {

									value = commandElementValue.getValue();
									break;
								}

							}
						}
					}

					if (value != null) {

						Key key = new Key(commandElementVO.getElementId());

						if (key.getClazz() == Element.class && key.getIds().size() >= 2) {

							setIlluminationRegimeCommand = new SetIlluminationRegimeCommand();

							setIlluminationRegimeCommand.setElementTypeId(Long.valueOf(key.getId(1)));
							setIlluminationRegimeCommand.setElementId(Long.valueOf(key.getId(2)));

							try {

								setIlluminationRegimeCommand.setRegime(Long.parseLong(value));

							} catch (NumberFormatException e) {

								setIlluminationRegimeCommand = null;
								log.error(e.getMessage());
								log.debug(ExceptionUtils.getStackTrace(e));
							}

						}
					}
				}
			}

		}
		return setIlluminationRegimeCommand;
	}
}
