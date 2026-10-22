package com.revenga.rits.back.front.core.model.helper.command;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.virtual.SetCircuitsOffCommand;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;

@Deprecated
public class SetCircuitsOffCommandHelper {

	private SetCircuitsOffCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetCircuitsOffCommand get(final CommandElementVO commandElementVO) {

		SetCircuitsOffCommand setCircuitsOffCommand = null;

		if (commandElementVO != null) {

			setCircuitsOffCommand = new SetCircuitsOffCommand();

			if (commandElementVO.getElementId() != null) {

				Key key = new Key(commandElementVO.getElementId());

				if (key.getClazz() == Element.class) {

					setCircuitsOffCommand.setElementTypeId(Long.valueOf(key.getId(1)));
					setCircuitsOffCommand.setElementId(Long.valueOf(key.getId(2)));
				}
			}

			if (commandElementVO.getCommandElementValues() != null) {

				String value = null;

				for (CommandElementValueVO commandElementValue : commandElementVO.getCommandElementValues()) {

					if (commandElementValue.getCommandElementTypeParamId() != null) {

						Key keyCommandElementTypeParam = new Key(commandElementValue.getCommandElementTypeParamId());

						if (keyCommandElementTypeParam.getClazz() == CommandElementTypeParam.class) {

							CommandElementTypeParam commandElementTypeParam = new CommandElementTypeParam();
							commandElementTypeParam.setPks(keyCommandElementTypeParam.getIds());

							if (commandElementTypeParam.getId()
									.equals(CommandElementTypeParam.SEND_CIRCUITS_OFF_PARAM_IDS)) {

								value = commandElementValue.getValue();
								break;
							}

						}
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

	public static SetCircuitsOffCommand get(final String elementId) {

		SetCircuitsOffCommand setCircuitsOffCommand = null;

		if (elementId != null) {

			setCircuitsOffCommand = new SetCircuitsOffCommand();

			Key keyElement = new Key(elementId);

			if (keyElement.getClazz() == Element.class) {

				Element element = new Element();
				element.setPks(keyElement.getIds());
				
				setCircuitsOffCommand.setElementTypeId(element.getId());
				setCircuitsOffCommand.setElementId(element.getElementTypeId());
				setCircuitsOffCommand.addElement(element);
			}
		}
		return setCircuitsOffCommand;
	}
}
