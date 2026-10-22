package com.revenga.rits.back.front.core.model.helper.command;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansStartCommand;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;

@Deprecated
public class SetFansStartCommandHelper {

	private SetFansStartCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetFansStartCommand get(final CommandElementVO commandElementVO) {

		SetFansStartCommand setFansStartCommand = null;

		if (commandElementVO != null) {

			setFansStartCommand = new SetFansStartCommand();

			if (commandElementVO.getElementId() != null) {

				Key key = new Key(commandElementVO.getElementId());

				if (key.getClazz() == Element.class) {

					setFansStartCommand.setElementTypeId(Long.valueOf(key.getId(1)));
					setFansStartCommand.setElementId(Long.valueOf(key.getId(2)));
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
									.equals(CommandElementTypeParam.SEND_FANS_START_PARAM_IDS)) {

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

								setFansStartCommand.addElement(element);
							}
						}
					}
				}
			}
		}
		return setFansStartCommand;
	}

	public static SetFansStartCommand get(final String elementId) {

		SetFansStartCommand setFansStartCommand = null;

		if (elementId != null) {

			setFansStartCommand = new SetFansStartCommand();

			Key keyElement = new Key(elementId);

			if (keyElement.getClazz() == Element.class) {

				Element element = new Element();
				element.setPks(keyElement.getIds());
				
				setFansStartCommand.setElementTypeId(element.getId());
				setFansStartCommand.setElementId(element.getElementTypeId());
				setFansStartCommand.addElement(element);
			}
		}
		return setFansStartCommand;
	}
}
