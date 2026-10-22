package com.revenga.rits.back.equipment.manager.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansStartReverseCommand;
import com.revenga.rits.back.data.core.util.ClassesUtil;

public class SetFansStartReverseCommandHelper {

	private SetFansStartReverseCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetFansStartReverseCommand get(final PmCommandElement pmCommandElement) {

		SetFansStartReverseCommand setFansStartReverseCommand = null;

		if (pmCommandElement != null) {

			setFansStartReverseCommand = new SetFansStartReverseCommand();

			setFansStartReverseCommand.setElementTypeId(pmCommandElement.getElementTypeId());
			setFansStartReverseCommand.setElementId(pmCommandElement.getElementId());

			if (pmCommandElement.getPmCommandElementValues() != null) {

				String value = null;

				for (PmCommandElementValue pmCommandElementValue : pmCommandElement.getPmCommandElementValues()) {

					if (Long.valueOf(CommandElementTypeParam.SEND_FANS_START_REVERSE_PARAM_IDS)
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

								setFansStartReverseCommand.addElement(element);
							}
						}
					}
				}
			}
		}
		return setFansStartReverseCommand;
	}

	public static SetFansStartReverseCommand get(final Long elementId) {

		SetFansStartReverseCommand comand = null;

		if (elementId != null) {

			comand = new SetFansStartReverseCommand();

			Element element = new Element(ElementType.ELEMENT_TYPE_FAN, elementId);

			comand.setElementId(element.getId());
			comand.setElementTypeId(element.getElementTypeId());
			comand.addElement(element);
		}
		return comand;
	}
}
