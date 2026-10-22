package com.revenga.rits.back.equipment.manager.helper;


import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.command.virtual.SetCircuitsOffCommand;
import com.revenga.rits.back.data.core.util.ClassesUtil;

public class SetCircuitsOffCommandHelper {

	private SetCircuitsOffCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetCircuitsOffCommand get(final PmCommandElement pmCommandElement) {

		SetCircuitsOffCommand setCircuitsOffCommand = null;

		if (pmCommandElement != null) {

			setCircuitsOffCommand = new SetCircuitsOffCommand();

			if (pmCommandElement.getElementId() != null && pmCommandElement.getElementTypeId() != null) {

				setCircuitsOffCommand.setElementTypeId(pmCommandElement.getElementTypeId());
				setCircuitsOffCommand.setElementId(pmCommandElement.getElementId());
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

								setCircuitsOffCommand.addElement(element);
							}
						}
					}
				}
			}
		}
		return setCircuitsOffCommand;
	}
	
	public static SetCircuitsOffCommand get(final Long elementId) {

		SetCircuitsOffCommand setCircuitsOffCommand = null;

		if (elementId != null) {

			setCircuitsOffCommand = new SetCircuitsOffCommand();

			Element element = new Element(ElementType.ELEMENT_TYPE_CIRCUIT, elementId);

			setCircuitsOffCommand.setElementId(element.getId());
			setCircuitsOffCommand.setElementTypeId(element.getElementTypeId());
			setCircuitsOffCommand.addElement(element);
		}
		return setCircuitsOffCommand;
	}
}
