package com.revenga.rits.back.equipment.manager.helper;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.command.virtual.SetIlluminationRegimeCommand;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SetIlluminationRegimeCommandHelper {

	private SetIlluminationRegimeCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetIlluminationRegimeCommand get(final PmCommandElement pmCommandElement) {

		SetIlluminationRegimeCommand setIlluminationRegimeCommand = null;

		if (pmCommandElement != null && pmCommandElement.getPmCommandElementValues() != null) {

			String value = null;

			for (PmCommandElementValue pmCommandElementValue : pmCommandElement.getPmCommandElementValues()) {

				if (Long.valueOf(CommandElementTypeParam.SEND_ILLUMINATION_REGIME_PARAM_REGIME)
						.equals(pmCommandElementValue.getCommandElementTypeParamId())) {

					value = pmCommandElementValue.getValue();
					break;
				}
			}

			if (value != null) {

				setIlluminationRegimeCommand = new SetIlluminationRegimeCommand();

				setIlluminationRegimeCommand.setElementTypeId(pmCommandElement.getElementTypeId());
				setIlluminationRegimeCommand.setElementId(pmCommandElement.getElementId());

				try {

					setIlluminationRegimeCommand.setRegime(Long.parseLong(value));

				} catch (NumberFormatException e) {

					setIlluminationRegimeCommand = null;
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}

			}
		}
		return setIlluminationRegimeCommand;
	}
}
