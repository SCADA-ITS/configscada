package com.revenga.rits.back.data.core.model.helper.command;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.virtual.SetFansStartCommand;

public class SetFansStartCommandHelper {

	private SetFansStartCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetFansStartCommand get(final Long elementTypeId, final Long elementId,
			final List<Element> elements) {

		SetFansStartCommand setFansStartCommand = null;

		if (CollectionUtils.isNotEmpty(elements)) {

			setFansStartCommand = new SetFansStartCommand();

			setFansStartCommand.setElementTypeId(elementTypeId);
			setFansStartCommand.setElementId(elementId);

			for (Element element : elements) {

				setFansStartCommand.addElement(element);
			}
		}

		return setFansStartCommand;
	}

	public static SetFansStartCommand get(final Long elementTypeId, final Long elementId) {

		SetFansStartCommand setFansStartCommand = null;

		if (elementTypeId != null && elementId != null) {
			
			Element element = new Element(elementTypeId, elementId);
			
			setFansStartCommand = new SetFansStartCommand();

			setFansStartCommand.setElementTypeId(elementTypeId);
			setFansStartCommand.setElementId(elementId);

			setFansStartCommand.addElement(element);	
		}

		return setFansStartCommand;
	}
}
