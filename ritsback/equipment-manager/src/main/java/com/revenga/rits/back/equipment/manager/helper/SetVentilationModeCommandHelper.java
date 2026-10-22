package com.revenga.rits.back.equipment.manager.helper;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.command.virtual.SetVentilationModeCommand;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SetVentilationModeCommandHelper {

	private SetVentilationModeCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetVentilationModeCommand get(final PmCommandElement pmCommandElement) {

		SetVentilationModeCommand setVentilationModeCommand = null;

		if (pmCommandElement != null && pmCommandElement.getPmCommandElementValues() != null) {

			String value = null;

			for (PmCommandElementValue pmCommandElementValue : pmCommandElement.getPmCommandElementValues()) {

				if (Long.valueOf(CommandElementTypeParam.SEND_VENTILATION_MODE_PARAM_MODE)
						.equals(pmCommandElementValue.getCommandElementTypeParamId())) {

					value = pmCommandElementValue.getValue();
					break;
				}

			}

			if (value != null) {

				setVentilationModeCommand = new SetVentilationModeCommand();

				setVentilationModeCommand.setElementTypeId(pmCommandElement.getElementTypeId());
				setVentilationModeCommand.setElementId(pmCommandElement.getElementId());

				try {

					setVentilationModeCommand.setMode(Long.parseLong(value));

				} catch (NumberFormatException e) {

					setVentilationModeCommand = null;
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}

			}
		}

		return setVentilationModeCommand;
	}
}
