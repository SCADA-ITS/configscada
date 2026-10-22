package com.revenga.rits.back.data.core.model.helper.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;

public class PmCommandElementHelper {

	private PmCommandElementHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static PmCommandElement get(final CommandElement commandElement, PmQueuePriority pmQueuePriority,
			String source) {

		PmCommandElement pmCommandElement = null;

		if (commandElement != null) {

			pmCommandElement = new PmCommandElement();

			pmCommandElement.setCommandElementTypeId(commandElement.getCommandElementTypeId());
			pmCommandElement.setElementTypeId(commandElement.getElementTypeId());
			pmCommandElement.setElementId(commandElement.getElementId());

			if (pmQueuePriority != null) {

				pmCommandElement.setQueueId(pmQueuePriority.getQueueId());
				pmCommandElement.setPriorityId(pmQueuePriority.getId());
			}

			pmCommandElement.setSource(source);

			if (CollectionUtils.isNotEmpty(commandElement.getCommandElementValues())) {

				List<PmCommandElementValue> pmCommandElementValues = new ArrayList<>();

				for (CommandElementValue commandElementValue : commandElement.getCommandElementValues()) {

					PmCommandElementValue pmCommandElementValue = new PmCommandElementValue();

					pmCommandElementValue.setQueueId(pmCommandElement.getQueueId());
					pmCommandElementValue.setCommandElementTypeId(commandElement.getCommandElementTypeId());
					pmCommandElementValue.setElementTypeId(commandElement.getElementTypeId());
					pmCommandElementValue.setElementId(commandElement.getElementId());
					pmCommandElementValue
							.setCommandElementTypeParamId(commandElementValue.getCommandElementTypeParamId());

					pmCommandElementValue.setValue(commandElementValue.getValue());

					pmCommandElementValues.add(pmCommandElementValue);
				}

				pmCommandElement.setPmCommandElementValues(pmCommandElementValues);
			}
		}

		return pmCommandElement;
	}

	public static PmCommandElement get(final SignallingCommand signallingCommand, PmQueuePriority pmQueuePriority,
			String source) {

		PmCommandElement pmCommandElement = null;

		if (signallingCommand != null) {

			pmCommandElement = new PmCommandElement();

			pmCommandElement.setCommandElementTypeId(signallingCommand.getSignallingCommandId());
			pmCommandElement.setElementTypeId(signallingCommand.getElementTypeId());
			pmCommandElement.setElementId(signallingCommand.getElementId());

			if (pmQueuePriority != null) {

				pmCommandElement.setQueueId(pmQueuePriority.getQueueId());
				pmCommandElement.setPriorityId(pmQueuePriority.getId());
			}

			pmCommandElement.setSource(source);

			if (CollectionUtils.isNotEmpty(signallingCommand.getSignallingParams())) {

				List<PmCommandElementValue> pmCommandElementValues = new ArrayList<>();

				for (SignallingParam signallingParam : signallingCommand.getSignallingParams()) {

					PmCommandElementValue pmCommandElementValue = new PmCommandElementValue();

					pmCommandElementValue.setQueueId(pmCommandElement.getQueueId());
					pmCommandElementValue.setCommandElementTypeId(signallingCommand.getSignallingCommandId());
					pmCommandElementValue.setElementTypeId(signallingCommand.getElementTypeId());
					pmCommandElementValue.setElementId(signallingCommand.getElementId());
					pmCommandElementValue.setCommandElementTypeParamId(Long.valueOf(signallingParam.getId()));

					pmCommandElementValue.setValue(signallingParam.getValue());

					pmCommandElementValues.add(pmCommandElementValue);
				}

				pmCommandElement.setPmCommandElementValues(pmCommandElementValues);
			}
		}

		return pmCommandElement;
	}
}
