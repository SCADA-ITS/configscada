package com.revenga.rits.back.data.core.model.command;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetKillUserCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4027753526912077249L;

	private List<Long> userIds;
	private Integer lockedTimeS;
	
	public SetKillUserCommand() {

		super(Command.SET_KILL_USER);
	}
}
