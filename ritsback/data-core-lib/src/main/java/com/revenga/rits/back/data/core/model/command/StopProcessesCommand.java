package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class StopProcessesCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4027753526912077249L;

	private List<Long> pids;

	public StopProcessesCommand() {

		super(Command.STOP_PROCESSES);
	}

	public void addProcess(Long pid) {

		if (pid != null) {

			if (pids == null) {

				pids = new ArrayList<>();
			}

			pids.add(pid);
		}
	}
}
