package com.revenga.rits.back.equipment.manager.helper;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.command.virtual.SetIlluminationModeCommand;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SetIlluminationModeCommandHelper {

	private SetIlluminationModeCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetIlluminationModeCommand get(final PmCommandElement pmCommandElement) {

		SetIlluminationModeCommand setIlluminationModeCommand = null;

		if (pmCommandElement != null && pmCommandElement.getPmCommandElementValues() != null) {

			String value = null;

			for (PmCommandElementValue pmCommandElementValue : pmCommandElement.getPmCommandElementValues()) {

				if (Long.valueOf(CommandElementTypeParam.SEND_ILLUMINATION_MODE_PARAM_MODE)
						.equals(pmCommandElementValue.getCommandElementTypeParamId())) {

					value = pmCommandElementValue.getValue();
					break;
				}
			}

			if (value != null) {

				setIlluminationModeCommand = new SetIlluminationModeCommand();

				setIlluminationModeCommand.setElementTypeId(pmCommandElement.getElementTypeId());
				setIlluminationModeCommand.setElementId(pmCommandElement.getElementId());

				try {

					setIlluminationModeCommand.setMode(Long.parseLong(value));

				} catch (NumberFormatException e) {

					setIlluminationModeCommand = null;
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}

			}
		}

		return setIlluminationModeCommand;
	}
}
