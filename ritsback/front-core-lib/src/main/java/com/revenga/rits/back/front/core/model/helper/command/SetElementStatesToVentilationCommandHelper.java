package com.revenga.rits.back.front.core.model.helper.command;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementStatesToVentilationCommand;

@Deprecated
public class SetElementStatesToVentilationCommandHelper {

	private SetElementStatesToVentilationCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetElementStatesToVentilationCommand get(final String elementId, final Long stateId) {

		SetElementStatesToVentilationCommand setElementStatesToVentilationCommand = null;

		if (elementId != null) {

			Key key = new Key(elementId);

			if (key.getClazz() == Element.class) {

				Element element = new Element();
				element.setPks(key.getIds());

				if (element.getId() != null && element.getElementTypeId() != null && stateId != null) {
					ElementState elementState = new ElementState();
					elementState.setElementId(element.getId());
					elementState.setElementTypeId(element.getElementTypeId());
					elementState.setStateId(stateId);

					setElementStatesToVentilationCommand = new SetElementStatesToVentilationCommand();
					setElementStatesToVentilationCommand.addElementState(elementState);
				}
			}
		}
		return setElementStatesToVentilationCommand;
	}

}
