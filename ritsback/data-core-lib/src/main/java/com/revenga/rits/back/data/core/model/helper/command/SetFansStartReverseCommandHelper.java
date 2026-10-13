package com.revenga.rits.back.data.core.model.helper.command;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansStartReverseCommand;

public class SetFansStartReverseCommandHelper {

	private SetFansStartReverseCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetFansStartReverseCommand get(final Long elementTypeId, final Long elementId,
			final List<Element> elements) {

		SetFansStartReverseCommand setFansStartReverseCommand = null;

		if (CollectionUtils.isNotEmpty(elements)) {

			setFansStartReverseCommand = new SetFansStartReverseCommand();

			setFansStartReverseCommand.setElementTypeId(elementTypeId);
			setFansStartReverseCommand.setElementId(elementId);

			for (Element element : elements) {

				setFansStartReverseCommand.addElement(element);
			}
		}

		return setFansStartReverseCommand;
	}
	
	public static SetFansStartReverseCommand get(final Long elementTypeId, final Long elementId) {

		SetFansStartReverseCommand setFansStartReverseCommand = null;

		if (elementTypeId != null && elementId != null) {
			
			Element element = new Element(elementTypeId, elementId);
			
			setFansStartReverseCommand = new SetFansStartReverseCommand();

			setFansStartReverseCommand.setElementTypeId(elementTypeId);
			setFansStartReverseCommand.setElementId(elementId);

			setFansStartReverseCommand.addElement(element);	
		}

		return setFansStartReverseCommand;
	}
}
