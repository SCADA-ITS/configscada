package com.revenga.rits.back.data.core.model.command.virtual;

import java.util.HashSet;
import java.util.Set;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetFansStartReverseCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7572932161403887950L;

	Long elementTypeId;
	Long elementId;

	private Set<Element> elements = null;

	public void addElement(Element element) {

		if (element != null) {

			if (elements == null) {

				elements = new HashSet<>();
			}
			elements.add(element);
		}
	}

	public SetFansStartReverseCommand() {

		super(Command.SET_FANS_START_REVERSE);
	}
}
