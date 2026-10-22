package com.revenga.rits.back.front.core.model.helper.command;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.virtual.SetCircuitsOnCommand;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;

@Deprecated
public class SetCircuitsOnCommandHelper {

	private SetCircuitsOnCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetCircuitsOnCommand get(final CommandElementVO commandElementVO) {

		SetCircuitsOnCommand setCircuitsOnCommand = null;

		if (commandElementVO != null) {

			setCircuitsOnCommand = new SetCircuitsOnCommand();

			if (commandElementVO.getElementId() != null) {

				Key key = new Key(commandElementVO.getElementId());

				if (key.getClazz() == Element.class) {

					setCircuitsOnCommand.setElementTypeId(Long.valueOf(key.getId(1)));
					setCircuitsOnCommand.setElementId(Long.valueOf(key.getId(2)));
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
									.equals(CommandElementTypeParam.SEND_CIRCUITS_ON_PARAM_IDS)) {

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

								setCircuitsOnCommand.addElement(element);
							}
						}
					}
				}
			}
		}
		return setCircuitsOnCommand;
	}

	public static SetCircuitsOnCommand get(final String elementId) {

		SetCircuitsOnCommand setCircuitsOnCommand = null;

		if (elementId != null) {

			setCircuitsOnCommand = new SetCircuitsOnCommand();

			Key keyElement = new Key(elementId);

			if (keyElement.getClazz() == Element.class) {

				Element element = new Element();
				element.setPks(keyElement.getIds());
				
				setCircuitsOnCommand.setElementTypeId(element.getId());
				setCircuitsOnCommand.setElementId(element.getElementTypeId());
				setCircuitsOnCommand.addElement(element);
			}
		}
		return setCircuitsOnCommand;
	}
}
