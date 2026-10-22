package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.BasicElement;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 531627585750483529L;

	private Long uIdSource; 
	private List<BasicElement> basicElements;

	public ResponseCommand() {

		super(Command.RESPONSE);
	}

	public void addElement(BasicElement basicElement) {

		if (basicElement != null) {

			if (basicElements == null) {

				basicElements = new ArrayList<>();
			}

			basicElements.add(basicElement);
		}
	}
}
