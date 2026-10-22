package com.revenga.rits.back.front.core.model.helper.command;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansStopCommand;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;

@Deprecated
public class SetFansStopCommandHelper {

	private SetFansStopCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetFansStopCommand get(final CommandElementVO commandElementVO) {

		SetFansStopCommand setFansStopCommand = null;

		if (commandElementVO != null) {

			setFansStopCommand = new SetFansStopCommand();

			if (commandElementVO.getElementId() != null) {

				Key key = new Key(commandElementVO.getElementId());

				if (key.getClazz() == Element.class) {

					setFansStopCommand.setElementTypeId(Long.valueOf(key.getId(1)));
					setFansStopCommand.setElementId(Long.valueOf(key.getId(2)));
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
									.equals(CommandElementTypeParam.SEND_FANS_STOP_PARAM_IDS)) {

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

								setFansStopCommand.addElement(element);
							}
						}
					}
				}
			}
		}
		return setFansStopCommand;
	}
	
	public static SetFansStopCommand get(final String elementId) {

		SetFansStopCommand setFansStopCommand = null;

		if (elementId != null) {

			setFansStopCommand = new SetFansStopCommand();

			Key keyElement = new Key(elementId);

			if (keyElement.getClazz() == Element.class) {

				Element element = new Element();
				element.setPks(keyElement.getIds());
				
				setFansStopCommand.setElementTypeId(element.getId());
				setFansStopCommand.setElementId(element.getElementTypeId());
				setFansStopCommand.addElement(element);
			}
		}
		return setFansStopCommand;
	}
}
