package com.revenga.rits.back.equipment.manager.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.command.virtual.SetFireAlarmCommand;

public class SetFireAlarmCommandHelper {

	private SetFireAlarmCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetFireAlarmCommand get(final PmCommandElement pmCommandElement) {

		SetFireAlarmCommand setFireAlarmCommand = null;

		if (pmCommandElement != null) {

			setFireAlarmCommand = new SetFireAlarmCommand();

			Element element = new Element(pmCommandElement.getElementTypeId(), pmCommandElement.getElementId());

			setFireAlarmCommand.setElement(element);

			if (pmCommandElement.getPmCommandElementValues() != null) {

				String stretchId = null;

				for (PmCommandElementValue pmCommandElementValue : pmCommandElement.getPmCommandElementValues()) {

					if (Long.valueOf(CommandElementTypeParam.SEND_VENTILATION_FIRE_PARAM_STRETCH_ID)
							.equals(pmCommandElementValue.getCommandElementTypeParamId())) {

						stretchId = pmCommandElementValue.getValue();
						break;
					}
				}

				if (stretchId != null) {

					Key keyElement = new Key(stretchId);

					if (keyElement.getClazz() == Stretch.class) {

						Stretch stretch = new Stretch();
						stretch.setPks(keyElement.getIds());
						setFireAlarmCommand.setStretch(stretch);
					}
				}
			}
		}
		return setFireAlarmCommand;
	}

}
