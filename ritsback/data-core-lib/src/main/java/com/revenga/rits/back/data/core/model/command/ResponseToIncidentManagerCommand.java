package com.revenga.rits.back.data.core.model.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseToIncidentManagerCommand extends ResponseCommand {/**
	 * 
	 */
	private static final long serialVersionUID = 8531696991055420751L;

	public ResponseToIncidentManagerCommand() {

		super();
	}

}
