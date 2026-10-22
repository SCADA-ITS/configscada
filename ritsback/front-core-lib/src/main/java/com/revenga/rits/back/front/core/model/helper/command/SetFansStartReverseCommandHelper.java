package com.revenga.rits.back.front.core.model.helper.command;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansStartReverseCommand;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;

@Deprecated
public class SetFansStartReverseCommandHelper {

	private SetFansStartReverseCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetFansStartReverseCommand get(final CommandElementVO commandElementVO) {

		SetFansStartReverseCommand setFansStartReverseCommand = null;

		if (commandElementVO != null) {

			setFansStartReverseCommand = new SetFansStartReverseCommand();

			if (commandElementVO.getElementId() != null) {

				Key key = new Key(commandElementVO.getElementId());

				if (key.getClazz() == Element.class) {

					setFansStartReverseCommand.setElementTypeId(Long.valueOf(key.getId(1)));
					setFansStartReverseCommand.setElementId(Long.valueOf(key.getId(2)));
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
									.equals(CommandElementTypeParam.SEND_FANS_START_REVERSE_PARAM_IDS)) {

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

								setFansStartReverseCommand.addElement(element);
							}
						}
					}
				}
			}
		}
		return setFansStartReverseCommand;
	}
	
	public static SetFansStartReverseCommand get(final String elementId) {

		SetFansStartReverseCommand setFansStartReverseCommand = null;

		if (elementId != null) {

			setFansStartReverseCommand = new SetFansStartReverseCommand();

			Key keyElement = new Key(elementId);

			if (keyElement.getClazz() == Element.class) {

				Element element = new Element();
				element.setPks(keyElement.getIds());
				
				setFansStartReverseCommand.setElementTypeId(element.getId());
				setFansStartReverseCommand.setElementId(element.getElementTypeId());
				setFansStartReverseCommand.addElement(element);
			}
		}
		return setFansStartReverseCommand;
	}
}
