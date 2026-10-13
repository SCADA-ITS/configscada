package com.revenga.rits.back.equipment.manager.helper;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.command.virtual.SetVentilationRegimeCommand;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SetVentilationRegimeCommandHelper {

	private SetVentilationRegimeCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetVentilationRegimeCommand get(final PmCommandElement pmCommandElement) {

		SetVentilationRegimeCommand setVentilationRegimeCommand = null;

		if (pmCommandElement != null && pmCommandElement.getPmCommandElementValues() != null) {

			String value = null;

			for (PmCommandElementValue pmCommandElementValue : pmCommandElement.getPmCommandElementValues()) {

				if (Long.valueOf(CommandElementTypeParam.SEND_VENTILATION_REGIME_PARAM_REGIME)
						.equals(pmCommandElementValue.getCommandElementTypeParamId())) {

					value = pmCommandElementValue.getValue();
					break;
				}
			}

			if (value != null) {

				setVentilationRegimeCommand = new SetVentilationRegimeCommand();

				setVentilationRegimeCommand.setElementTypeId(pmCommandElement.getElementTypeId());
				setVentilationRegimeCommand.setElementId(pmCommandElement.getElementId());

				try {

					setVentilationRegimeCommand.setRegime(Long.parseLong(value));

				} catch (NumberFormatException e) {

					setVentilationRegimeCommand = null;
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}

			}
		}
		return setVentilationRegimeCommand;
	}
}
