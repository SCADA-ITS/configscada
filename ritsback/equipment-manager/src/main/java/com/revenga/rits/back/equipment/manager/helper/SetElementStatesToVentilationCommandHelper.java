package com.revenga.rits.back.equipment.manager.helper;

import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementStatesToVentilationCommand;

public class SetElementStatesToVentilationCommandHelper {

	private SetElementStatesToVentilationCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetElementStatesToVentilationCommand get(final Long elementId, final Long stateId) {

		SetElementStatesToVentilationCommand setElementStatesToVentilationCommand = null;

		if (elementId != null && stateId != null) {

			ElementState elementState = new ElementState();
			elementState.setElementId(elementId);
			elementState.setElementTypeId(ElementType.ELEMENT_TYPE_VENTILATION);
			elementState.setStateId(stateId);

			setElementStatesToVentilationCommand = new SetElementStatesToVentilationCommand();
			setElementStatesToVentilationCommand.addElementState(elementState);
		}

		return setElementStatesToVentilationCommand;
	}

}
