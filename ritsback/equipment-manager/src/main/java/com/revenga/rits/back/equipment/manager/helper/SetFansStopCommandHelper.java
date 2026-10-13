package com.revenga.rits.back.equipment.manager.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansStopCommand;
import com.revenga.rits.back.data.core.util.ClassesUtil;

public class SetFansStopCommandHelper {

	private SetFansStopCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetFansStopCommand get(final PmCommandElement pmCommandElement) {

		SetFansStopCommand setFansStopCommand = null;

		if (pmCommandElement != null) {

			setFansStopCommand = new SetFansStopCommand();

			setFansStopCommand.setElementTypeId(pmCommandElement.getElementTypeId());
			setFansStopCommand.setElementId(pmCommandElement.getElementId());

			if (pmCommandElement.getPmCommandElementValues() != null) {

				String value = null;

				for (PmCommandElementValue pmCommandElementValue : pmCommandElement.getPmCommandElementValues()) {

					if (Long.valueOf(CommandElementTypeParam.SEND_FANS_STOP_PARAM_IDS)
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

								setFansStopCommand.addElement(element);
							}
						}
					}
				}
			}
		}
		return setFansStopCommand;
	}

	public static SetFansStopCommand get(final Long elementId) {

		SetFansStopCommand setFansStopCommand = null;

		if (elementId != null) {

			setFansStopCommand = new SetFansStopCommand();

			Element element = new Element(ElementType.ELEMENT_TYPE_FAN, elementId);

			setFansStopCommand.setElementId(element.getId());
			setFansStopCommand.setElementTypeId(element.getElementTypeId());
			setFansStopCommand.addElement(element);
		}
		return setFansStopCommand;
	}
}
