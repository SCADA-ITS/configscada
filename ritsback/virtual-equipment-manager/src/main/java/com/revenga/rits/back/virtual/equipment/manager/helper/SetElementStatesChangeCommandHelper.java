package com.revenga.rits.back.virtual.equipment.manager.helper;

import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.command.SetElementStatesChangeCommand;

public class SetElementStatesChangeCommandHelper {

	private SetElementStatesChangeCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetElementStatesChangeCommand get(final Long elementId, final Long elementTypeId,
			final Long stateId) {

		SetElementStatesChangeCommand setElementStatesChangeCommand = null;

		if (elementId != null && elementTypeId != null && stateId != null) {

			setElementStatesChangeCommand = new SetElementStatesChangeCommand();
			
			ElementState elementState = new ElementState();
			elementState.setElementId(elementId);
			elementState.setElementTypeId(elementTypeId);
			elementState.setStateId(stateId);

			setElementStatesChangeCommand.addElementState(elementState);
		}

		return setElementStatesChangeCommand;
	}

}
