package com.revenga.rits.back.data.core.model.helper.command;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;

public class SignallingCommandHelper {

	private SignallingCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SignallingCommand get(final CommandElement commandElement) {

		SignallingCommand signallingCommand = null;

		if (commandElement != null) {

			signallingCommand = new SignallingCommand();

			signallingCommand.setSignallingCommandId(commandElement.getCommandElementTypeId());
			signallingCommand.setElementTypeId(commandElement.getElementTypeId());
			signallingCommand.setElementId(commandElement.getElementId());

			if (!CollectionUtils.isEmpty(commandElement.getCommandElementValues())) {

				List<SignallingParam> signallingParams = new ArrayList<>();

				for (CommandElementValue commandElementValue : commandElement.getCommandElementValues()) {

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
	
	public static SignallingCommand get(final Long elementId, final Long elementTypeId,
			final Long commandElementTypeId) {

		SignallingCommand signallingCommand = null;

		if (elementId != null && elementTypeId != null && commandElementTypeId != null) {

			signallingCommand = new SignallingCommand();

			signallingCommand.setElementId(elementId);
			signallingCommand.setElementTypeId(elementTypeId);
			signallingCommand.setSignallingCommandId(commandElementTypeId);
			signallingCommand.setCreatedAt(Instant.now().toEpochMilli());
		}
		return signallingCommand;
	}

	public static SignallingCommand get(final Element element, final Long commandElementTypeId) {

		SignallingCommand signallingCommand = null;

		if (element != null && element.getId() != null && element.getElementTypeId() != null
				&& commandElementTypeId != null) {

			signallingCommand = new SignallingCommand();

			signallingCommand.setElementId(element.getId());
			signallingCommand.setElementTypeId(element.getElementTypeId());
			signallingCommand.setSignallingCommandId(commandElementTypeId);
			signallingCommand.setCreatedAt(Instant.now().toEpochMilli());
		}
		return signallingCommand;
	}

	public static List<Command> gets(Set<Element> elements, Long commandTypeId) {

		List<Command> Commands = null;

		if (CollectionUtils.isNotEmpty(elements)) {

			for (Element element : elements) {

				SignallingCommand signallingCommand = SignallingCommandHelper.get(element.getId(),
						element.getElementTypeId(), commandTypeId);

				if (signallingCommand != null) {

					if (Commands == null) {

						Commands = new ArrayList<>();
					}

					Commands.add(signallingCommand);

				}
			}
		}

		return Commands;
	}

	public static CommandElement get(final SignallingCommand signallingCommand) {

		CommandElement commandElement = null;

		if (signallingCommand != null) {

			commandElement = new CommandElement();

			commandElement.setCommandElementTypeId(signallingCommand.getSignallingCommandId());
			commandElement.setElementTypeId(signallingCommand.getElementTypeId());
			commandElement.setElementId(signallingCommand.getElementId());

			if (CollectionUtils.isNotEmpty(signallingCommand.getSignallingParams())) {

				List<CommandElementValue> commandElementValues = new ArrayList<>();

				for (SignallingParam signallingParam : signallingCommand.getSignallingParams()) {

					CommandElementTypeParam commandElementTypeParam = new CommandElementTypeParam();
					commandElementTypeParam.setId((long) signallingParam.getId());
					commandElementTypeParam.setCommandElementTypeId(signallingCommand.getSignallingCommandId());
					commandElementTypeParam.setElementTypeId(signallingCommand.getElementTypeId());
					commandElementTypeParam.setDataTypeId((long) signallingParam.getType());

					CommandElementValue commandElementValue = new CommandElementValue();
					commandElementValue.setCommandElementTypeId(signallingCommand.getSignallingCommandId());
					commandElementValue.setElementTypeId(signallingCommand.getElementTypeId());
					commandElementValue.setCommandElementTypeParamId((long) signallingParam.getId());
					commandElementValue.setValue(signallingParam.getValue());
					commandElementValue.setCommandElementTypeParam(commandElementTypeParam);
					commandElementValues.add(commandElementValue);
				}

				commandElement.setCommandElementValues(commandElementValues);
			}
		}

		return commandElement;
	}
}
