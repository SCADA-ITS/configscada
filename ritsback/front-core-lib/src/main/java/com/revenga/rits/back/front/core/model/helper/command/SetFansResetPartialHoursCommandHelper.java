package com.revenga.rits.back.front.core.model.helper.command;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansResetPartialHoursCommand;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;

@Deprecated
public class SetFansResetPartialHoursCommandHelper {

	private SetFansResetPartialHoursCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetFansResetPartialHoursCommand get(final CommandElementVO commandElementVO) {

		SetFansResetPartialHoursCommand setFansResetPartialHoursCommand = null;

		if (commandElementVO != null) {

			setFansResetPartialHoursCommand = new SetFansResetPartialHoursCommand();

			if (commandElementVO.getElementId() != null) {

				Key key = new Key(commandElementVO.getElementId());

				if (key.getClazz() == Element.class) {

					setFansResetPartialHoursCommand.setElementTypeId(Long.valueOf(key.getId(1)));
					setFansResetPartialHoursCommand.setElementId(Long.valueOf(key.getId(2)));
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
									.equals(CommandElementTypeParam.SEND_FANS_RESET_PARTIAL_HOURS_PARAM_IDS)) {

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

								setFansResetPartialHoursCommand.addElement(element);
							}
						}
					}
				}
			}
		}
		return setFansResetPartialHoursCommand;
	}

	public static SetFansResetPartialHoursCommand get(final String elementId) {

		SetFansResetPartialHoursCommand setFansResetPartialHoursCommand = null;

		if (elementId != null) {

			setFansResetPartialHoursCommand = new SetFansResetPartialHoursCommand();

			Key keyElement = new Key(elementId);

			if (keyElement.getClazz() == Element.class) {

				Element element = new Element();
				element.setPks(keyElement.getIds());
				
				setFansResetPartialHoursCommand.setElementTypeId(element.getId());
				setFansResetPartialHoursCommand.setElementId(element.getElementTypeId());
				setFansResetPartialHoursCommand.addElement(element);
			}
		}
		return setFansResetPartialHoursCommand;
	}
}
