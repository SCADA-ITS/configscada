package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.Element;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetPmQueuePublishCommand extends Command {
	/**
	* 
	*/
	private static final long serialVersionUID = 4378860717528189231L;

	private List<Element> elements;

	public void add(Element element) {

		if (element != null) {

			if (elements == null) {

				elements = new ArrayList<>();
			}

			elements.add(element);
		}
	}

	public SetPmQueuePublishCommand() {

		super(Command.SET_PM_QUEUE_PUBLISH);
	}

}
