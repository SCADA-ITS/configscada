package com.revenga.rits.back.front.core.model.helper.command;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansResetAlarmsCommand;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;

@Deprecated
public class SetFansResetAlarmsCommandHelper {

	private SetFansResetAlarmsCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetFansResetAlarmsCommand get(final CommandElementVO commandElementVO) {

		SetFansResetAlarmsCommand setFansResetAlarmsCommand = null;

		if (commandElementVO != null) {

			setFansResetAlarmsCommand = new SetFansResetAlarmsCommand();

			if (commandElementVO.getElementId() != null) {

				Key key = new Key(commandElementVO.getElementId());

				if (key.getClazz() == Element.class) {

					setFansResetAlarmsCommand.setElementTypeId(Long.valueOf(key.getId(1)));
					setFansResetAlarmsCommand.setElementId(Long.valueOf(key.getId(2)));
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
									.equals(CommandElementTypeParam.SEND_FANS_RESET_ALARMS_PARAM_IDS)) {

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

								setFansResetAlarmsCommand.addElement(element);
							}
						}
					}
				}
			}
		}
		return setFansResetAlarmsCommand;
	}

	public static SetFansResetAlarmsCommand get(final String elementId) {

		SetFansResetAlarmsCommand setFansResetAlarmsCommand = null;

		if (elementId != null) {

			setFansResetAlarmsCommand = new SetFansResetAlarmsCommand();

			Key keyElement = new Key(elementId);

			if (keyElement.getClazz() == Element.class) {

				Element element = new Element();
				element.setPks(keyElement.getIds());
				
				setFansResetAlarmsCommand.setElementTypeId(element.getId());
				setFansResetAlarmsCommand.setElementId(element.getElementTypeId());
				setFansResetAlarmsCommand.addElement(element);
			}
		}
		return setFansResetAlarmsCommand;
	}
}
