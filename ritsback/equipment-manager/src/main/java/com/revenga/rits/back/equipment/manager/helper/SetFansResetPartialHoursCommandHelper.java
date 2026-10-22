package com.revenga.rits.back.equipment.manager.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansResetPartialHoursCommand;
import com.revenga.rits.back.data.core.util.ClassesUtil;

public class SetFansResetPartialHoursCommandHelper {

	private SetFansResetPartialHoursCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetFansResetPartialHoursCommand get(final PmCommandElement pmCommandElement) {

		SetFansResetPartialHoursCommand setFansResetPartialHoursCommand = null;

		if (pmCommandElement != null) {

			setFansResetPartialHoursCommand = new SetFansResetPartialHoursCommand();

			setFansResetPartialHoursCommand.setElementTypeId(pmCommandElement.getElementTypeId());
			setFansResetPartialHoursCommand.setElementId(pmCommandElement.getElementId());

			if (pmCommandElement.getPmCommandElementValues() != null) {

				String value = null;

				for (PmCommandElementValue pmCommandElementValue : pmCommandElement.getPmCommandElementValues()) {

					if (Long.valueOf(CommandElementTypeParam.SEND_FANS_RESET_PARTIAL_HOURS_PARAM_IDS)
							.equals(pmCommandElementValue.getCommandElementTypeParamId())) {

						value = pmCommandElementValue.getValue();
						break;
					}
				}

				if (value != null) {

					String[] values = ClassesUtil.getStringArray(value);

					if (values != null) {

						for (String elementId : values) {

							Key keyElement = new Key(elementId);

							if (keyElement.getClazz() == Element.class) {

								Element element = new Element();
								element.setPks(keyElement.getIds());

								setFansResetPartialHoursCommand.addElement(element);
							}
						}
					}
				}
			}
		}
		return setFansResetPartialHoursCommand;
	}
	
	public static SetFansResetPartialHoursCommand get(final Long elementId) {

		SetFansResetPartialHoursCommand comand = null;

		if (elementId != null) {

			comand = new SetFansResetPartialHoursCommand();

			Element element = new Element(ElementType.ELEMENT_TYPE_FAN, elementId);
			
			comand.setElementId(element.getId());
			comand.setElementTypeId(element.getElementTypeId());
			comand.addElement(element);
		}
		return comand;
	}
}
