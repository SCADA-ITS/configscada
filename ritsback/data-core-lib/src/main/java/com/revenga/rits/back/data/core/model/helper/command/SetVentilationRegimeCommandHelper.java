package com.revenga.rits.back.data.core.model.helper.command;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.command.virtual.SetVentilationRegimeCommand;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SetVentilationRegimeCommandHelper {

	private SetVentilationRegimeCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetVentilationRegimeCommand get(final Long elementTypeId, final Long elementId, final String regime) {

		SetVentilationRegimeCommand setVentilationRegimeCommand = null;

		if (elementTypeId != null && elementId != null && regime != null) {

			setVentilationRegimeCommand = new SetVentilationRegimeCommand();
			setVentilationRegimeCommand.setElementTypeId(elementTypeId);
			setVentilationRegimeCommand.setElementId(elementId);

			try {

				setVentilationRegimeCommand.setRegime(Long.parseLong(regime));

			} catch (NumberFormatException e) {

				setVentilationRegimeCommand = null;
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		return setVentilationRegimeCommand;
	}
}
