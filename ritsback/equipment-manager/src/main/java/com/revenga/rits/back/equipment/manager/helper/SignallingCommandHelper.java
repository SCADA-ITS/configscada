package com.revenga.rits.back.equipment.manager.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.data.core.util.ClassesUtil;

public class SignallingCommandHelper {

	private SignallingCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SignallingCommand get(final PmCommandElement commandElement) {

		SignallingCommand signallingCommand = null;

		if (commandElement != null) {

			signallingCommand = new SignallingCommand();

			signallingCommand.setSignallingCommandId(commandElement.getCommandElementTypeId());
			signallingCommand.setElementTypeId(commandElement.getElementTypeId());
			signallingCommand.setElementId(commandElement.getElementId());

			if (!CollectionUtils.isEmpty(commandElement.getPmCommandElementValues())) {

				List<SignallingParam> signallingParams = new ArrayList<>();

				for (PmCommandElementValue commandElementValue : commandElement.getPmCommandElementValues()) {

					if (commandElementValue.getCommandElementTypeParam() != null
							&& commandElementValue.getCommandElementTypeParamId() != null) {

						SignallingParam signallingParam = new SignallingParam();

						signallingParam.setId(commandElementValue.getCommandElementTypeParamId().intValue());
						signallingParam
								.setType(commandElementValue.getCommandElementTypeParam().getDataTypeId().intValue());
						signallingParam.setValue(commandElementValue.getValue());

						signallingParams.add(signallingParam);
					}

				}
				signallingCommand.setSignallingParams(signallingParams);
			}
		}
		return signallingCommand;
	}

	public static List<Command> get(final PmCommandElement commandElement, Long commandElementTypeId) {

		List<Command> commands = null;

		if (commandElement != null && commandElement.getPmCommandElementValues() != null
				&& !commandElement.getPmCommandElementValues().isEmpty()) {

			String value = commandElement.getPmCommandElementValues().get(0).getValue();

			if (value != null) {

				String[] values = ClassesUtil.getStringArray(value);

				if (values != null) {

					for (String elementId : values) {

						Key keyElement = new Key(elementId);

						if (keyElement.getClazz() == Element.class) {

							Element element = new Element();
							element.setPks(keyElement.getIds());

							PmCommandElement pmCommandElement = new PmCommandElement();
							pmCommandElement.setCommandElementTypeId(commandElementTypeId);
							pmCommandElement.setElementId(element.getId());
							pmCommandElement.setElementTypeId(element.getElementTypeId());

							Command cmd = SignallingCommandHelper.get(pmCommandElement);

							if (commands == null) {
								commands = new ArrayList<>();
							}
							commands.add(cmd);

						}

					}
				}
			}
		}

		return commands;

	}

	public static List<Command> get(final PmCommandElement commandElement,
			final CommandElementType commandElementType) {
	
		List<Command> commands = null;

		if (commandElement != null && CollectionUtils.isNotEmpty(commandElement.getPmCommandElementValues())
				&& (CollectionUtils.isEmpty(commandElementType.getCommandElementTypeParams())
						|| commandElement.getPmCommandElementValues().size() > commandElementType
								.getCommandElementTypeParams().size())) {

			String value = commandElement.getPmCommandElementValues().get(0).getValue();

			if (value != null) {

				String[] values = ClassesUtil.getStringArray(value);

				if (values != null) {

					for (String elementId : values) {

						Key keyElement = new Key(elementId);

						if (keyElement.getClazz() == Element.class) {

							Element element = new Element();
							element.setPks(keyElement.getIds());

							PmCommandElement pmCommandElement = new PmCommandElement();
							pmCommandElement.setCommandElementTypeId(commandElementType.getId());
							pmCommandElement.setElementId(element.getId());
							pmCommandElement.setElementTypeId(element.getElementTypeId());

							if (!CollectionUtils.isEmpty(commandElementType.getCommandElementTypeParams())) {

								List<PmCommandElementValue> pmCommandElementValues = new ArrayList<>();

								int index = 1;
								for (CommandElementTypeParam commandElementTypeParam : commandElementType
										.getCommandElementTypeParams()) {

									PmCommandElementValue commandElementValue = new PmCommandElementValue();

									commandElementValue.setElementId(element.getId());
									commandElementValue.setCommandElementTypeId(commandElementType.getId());
									commandElementValue.setElementTypeId(element.getElementTypeId());
									commandElementValue.setCommandElementTypeParamId(commandElementTypeParam.getId());
									commandElementValue.setCommandElementTypeParam(commandElementTypeParam);
									commandElementValue
											.setValue(commandElement.getPmCommandElementValues().get(index).getValue());
									
									pmCommandElementValues.add(commandElementValue);

									index++;
								}
								pmCommandElement.setPmCommandElementValues(pmCommandElementValues);
							}

							Command cmd = SignallingCommandHelper.get(pmCommandElement);

							if (commands == null) {
								commands = new ArrayList<>();
							}
							commands.add(cmd);

						}

					}
				}
			}
		}

		return commands;

	}
}
