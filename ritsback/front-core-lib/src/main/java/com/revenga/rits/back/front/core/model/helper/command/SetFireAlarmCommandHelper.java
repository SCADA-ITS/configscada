package com.revenga.rits.back.front.core.model.helper.command;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.command.virtual.SetFireAlarmCommand;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;

@Deprecated
public class SetFireAlarmCommandHelper {

	private SetFireAlarmCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetFireAlarmCommand get(final CommandElementVO commandElementVO) {

		SetFireAlarmCommand setFireAlarmCommand = null;

		if (commandElementVO != null) {

			setFireAlarmCommand = new SetFireAlarmCommand();

			if (commandElementVO.getElementId() != null) {

				Key key = new Key(commandElementVO.getElementId());

				if (key.getClazz() == Element.class) {

					Element element = new Element();
					element.setPks(key.getIds());

					setFireAlarmCommand.setElement(element);
				}
			}

			if (commandElementVO.getCommandElementValues() != null) {

				String stretchId = null;

				for (CommandElementValueVO commandElementValue : commandElementVO.getCommandElementValues()) {

					if (commandElementValue.getCommandElementTypeParamId() != null) {

						Key keyCommandElementTypeParam = new Key(commandElementValue.getCommandElementTypeParamId());

						if (keyCommandElementTypeParam.getClazz() == CommandElementTypeParam.class) {

							CommandElementTypeParam commandElementTypeParam = new CommandElementTypeParam();
							commandElementTypeParam.setPks(keyCommandElementTypeParam.getIds());

							if (commandElementTypeParam.getId()
									.equals(CommandElementTypeParam.SEND_VENTILATION_FIRE_PARAM_STRETCH_ID)) {

								stretchId = commandElementValue.getValue();
								break;
							}

						}
					}
				}

				if (stretchId != null) {

					Key keyElement = new Key(stretchId);

					if (keyElement.getClazz() == Stretch.class) {

						Stretch stretch = new Stretch();
						stretch.setPks(keyElement.getIds());
						setFireAlarmCommand.setStretch(stretch);
					}
				}
			}
		}
		return setFireAlarmCommand;
	}

}
