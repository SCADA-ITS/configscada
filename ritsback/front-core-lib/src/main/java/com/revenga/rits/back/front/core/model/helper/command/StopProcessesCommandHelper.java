package com.revenga.rits.back.front.core.model.helper.command;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.command.StopProcessesCommand;

public class StopProcessesCommandHelper {

	private StopProcessesCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static StopProcessesCommand get(List<Long> pids) {

		if (CollectionUtils.isNotEmpty(pids)) {

			StopProcessesCommand command = new StopProcessesCommand();

			pids.forEach(pid -> command.addProcess(pid));

			return command;

		} else {
			
			return null;
		}

	}

}
