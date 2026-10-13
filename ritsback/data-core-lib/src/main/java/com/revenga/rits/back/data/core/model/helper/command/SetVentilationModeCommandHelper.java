package com.revenga.rits.back.data.core.model.helper.command;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.command.virtual.SetVentilationModeCommand;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SetVentilationModeCommandHelper {

	private SetVentilationModeCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetVentilationModeCommand get(final Long elementTypeId, final Long elementId, final String mode) {

		SetVentilationModeCommand setVentilationModeCommand = null;

		if (elementTypeId != null && elementId != null) {

			setVentilationModeCommand = new SetVentilationModeCommand();

			setVentilationModeCommand.setElementTypeId(elementTypeId);
			setVentilationModeCommand.setElementId(elementId);

			try {

				setVentilationModeCommand.setMode(Long.parseLong(mode));

			} catch (NumberFormatException e) {

				setVentilationModeCommand = null;
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		return setVentilationModeCommand;
	}
}
