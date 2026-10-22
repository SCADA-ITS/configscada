package com.revenga.rits.back.data.core.model.helper.command;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.command.virtual.SetFireAlarmCommand;

public class SetFireAlarmCommandHelper {

	private SetFireAlarmCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetFireAlarmCommand get(final Long elementTypeId, final Long elementId, final Long stretchId) {

		SetFireAlarmCommand setFireAlarmCommand = null;

		if (elementTypeId != null && elementId != null && stretchId != null) {

			setFireAlarmCommand = new SetFireAlarmCommand();

			Element element = new Element();
			element.setId(elementId);
			element.setElementTypeId(elementTypeId);

			Stretch stretch = new Stretch();
			stretch.setId(stretchId);

			setFireAlarmCommand.setElement(element);
			setFireAlarmCommand.setStretch(stretch);
		}

		return setFireAlarmCommand;
	}

}
