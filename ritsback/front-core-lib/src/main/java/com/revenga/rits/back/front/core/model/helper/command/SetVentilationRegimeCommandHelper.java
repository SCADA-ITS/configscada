package com.revenga.rits.back.front.core.model.helper.command;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.virtual.SetVentilationRegimeCommand;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Deprecated
public class SetVentilationRegimeCommandHelper {

	private SetVentilationRegimeCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetVentilationRegimeCommand get(final CommandElementVO commandElementVO) {

		SetVentilationRegimeCommand setVentilationRegimeCommand = null;

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
										.equals(CommandElementTypeParam.SEND_VENTILATION_REGIME_PARAM_REGIME)) {

									value = commandElementValue.getValue();
									break;
								}

							}
						}
					}

					if (value != null) {

						Key key = new Key(commandElementVO.getElementId());

						if (key.getClazz() == Element.class && key.getIds().size() >= 2) {

							setVentilationRegimeCommand = new SetVentilationRegimeCommand();

							setVentilationRegimeCommand.setElementTypeId(Long.valueOf(key.getId(1)));
							setVentilationRegimeCommand.setElementId(Long.valueOf(key.getId(2)));

							try {

								setVentilationRegimeCommand.setRegime(Long.parseLong(value));

							} catch (NumberFormatException e) {

								setVentilationRegimeCommand = null;
								log.error(e.getMessage());
								log.debug(ExceptionUtils.getStackTrace(e));
							}

						}
					}
				}
			}

		}
		return setVentilationRegimeCommand;
	}
}
