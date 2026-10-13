package com.revenga.rits.back.equipment.manager.helper;


import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.command.virtual.SetCircuitsOnCommand;
import com.revenga.rits.back.data.core.util.ClassesUtil;

public class SetCircuitsOnCommandHelper {

	private SetCircuitsOnCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetCircuitsOnCommand get(final PmCommandElement pmCommandElement) {

		SetCircuitsOnCommand setCircuitsOnCommand = null;

		if (pmCommandElement != null) {

			setCircuitsOnCommand = new SetCircuitsOnCommand();

			if (pmCommandElement.getElementId() != null && pmCommandElement.getElementTypeId() != null) {

				setCircuitsOnCommand.setElementTypeId(pmCommandElement.getElementTypeId());
				setCircuitsOnCommand.setElementId(pmCommandElement.getElementId());
			}

			if (pmCommandElement.getPmCommandElementValues() != null) {

				String value = null;

				for (PmCommandElementValue pmCommandElementValue : pmCommandElement.getPmCommandElementValues()) {

					if (Long.valueOf(CommandElementTypeParam.SEND_CIRCUITS_OFF_PARAM_IDS)
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

								setCircuitsOnCommand.addElement(element);
							}
						}
					}
				}
			}
		}
		return setCircuitsOnCommand;
	}

	public static SetCircuitsOnCommand get(final Long elementId) {

		SetCircuitsOnCommand setCircuitsOnCommand = null;

		if (elementId != null) {

			setCircuitsOnCommand = new SetCircuitsOnCommand();

			Element element = new Element(ElementType.ELEMENT_TYPE_CIRCUIT, elementId);

			setCircuitsOnCommand.setElementId(element.getId());
			setCircuitsOnCommand.setElementTypeId(element.getElementTypeId());
			setCircuitsOnCommand.addElement(element);
		}
		return setCircuitsOnCommand;
	}
}
