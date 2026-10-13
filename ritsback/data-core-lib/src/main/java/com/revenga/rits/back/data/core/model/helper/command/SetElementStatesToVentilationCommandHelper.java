package com.revenga.rits.back.data.core.model.helper.command;

import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementStatesToVentilationCommand;

public class SetElementStatesToVentilationCommandHelper {

	private SetElementStatesToVentilationCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetElementStatesToVentilationCommand get(final Long elementTypeId, final Long elementId,
			final Long stateId) {

		SetElementStatesToVentilationCommand setElementStatesToVentilationCommand = null;

		if (elementTypeId != null && elementId != null) {

			ElementState elementState = new ElementState();
			elementState.setElementId(elementId);
			elementState.setElementTypeId(elementTypeId);
			elementState.setStateId(stateId);

			setElementStatesToVentilationCommand = new SetElementStatesToVentilationCommand();
			setElementStatesToVentilationCommand.addElementState(elementState);
		}
		return setElementStatesToVentilationCommand;
	}

}
