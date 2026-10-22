package com.revenga.rits.back.data.core.model.command;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.PmQueuePriority;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetPriorityComand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7572932161403887950L;

	private PmQueuePriority priority;
	private Element  element;
	private Boolean enable;

	public SetPriorityComand() {

		super(Command.SET_PRIORITY_COMMAND);
	}
}
