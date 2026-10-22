package com.revenga.rits.back.front.core.model.helper.command;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansResetTotalHoursCommand;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;

@Deprecated
public class SetFansResetTotalHoursCommandHelper {

	private SetFansResetTotalHoursCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetFansResetTotalHoursCommand get(final CommandElementVO commandElementVO) {

		SetFansResetTotalHoursCommand setFansResetTotalHoursCommand = null;

		if (commandElementVO != null) {

			setFansResetTotalHoursCommand = new SetFansResetTotalHoursCommand();

			if (commandElementVO.getElementId() != null) {

				Key key = new Key(commandElementVO.getElementId());

				if (key.getClazz() == Element.class) {

					setFansResetTotalHoursCommand.setElementTypeId(Long.valueOf(key.getId(1)));
					setFansResetTotalHoursCommand.setElementId(Long.valueOf(key.getId(2)));
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
									.equals(CommandElementTypeParam.SEND_FANS_RESET_TOTAL_HOURS_PARAM_IDS)) {

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

								setFansResetTotalHoursCommand.addElement(element);
							}
						}
					}
				}
			}
		}
		return setFansResetTotalHoursCommand;
	}

	public static SetFansResetTotalHoursCommand get(final String elementId) {

		SetFansResetTotalHoursCommand setFansResetTotalHoursCommand = null;

		if (elementId != null) {

			setFansResetTotalHoursCommand = new SetFansResetTotalHoursCommand();

			Key keyElement = new Key(elementId);

			if (keyElement.getClazz() == Element.class) {

				Element element = new Element();
				element.setPks(keyElement.getIds());
				
				setFansResetTotalHoursCommand.setElementTypeId(element.getId());
				setFansResetTotalHoursCommand.setElementId(element.getElementTypeId());
				setFansResetTotalHoursCommand.addElement(element);
			}
		}
		return setFansResetTotalHoursCommand;
	}
}
