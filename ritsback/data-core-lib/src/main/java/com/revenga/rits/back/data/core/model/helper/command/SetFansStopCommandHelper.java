package com.revenga.rits.back.data.core.model.helper.command;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansStopCommand;

public class SetFansStopCommandHelper {

	private SetFansStopCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetFansStopCommand get(final Long elementTypeId, final Long elementId, final List<Element> elements) {

		SetFansStopCommand setFansStopCommand = null;

		if (CollectionUtils.isNotEmpty(elements)) {

			setFansStopCommand = new SetFansStopCommand();

			setFansStopCommand.setElementTypeId(elementTypeId);
			setFansStopCommand.setElementId(elementId);

			for (Element element : elements) {

				setFansStopCommand.addElement(element);
			}
		}

		return setFansStopCommand;
	}

	public static SetFansStopCommand get(final Long elementTypeId, final Long elementId) {

		SetFansStopCommand setFansStopCommand = null;

		if (elementTypeId != null && elementId != null) {
			
			Element element = new Element(elementTypeId, elementId);
			
			setFansStopCommand = new SetFansStopCommand();

			setFansStopCommand.setElementTypeId(elementTypeId);
			setFansStopCommand.setElementId(elementId);

			setFansStopCommand.addElement(element);	
		}

		return setFansStopCommand;
	}
}
