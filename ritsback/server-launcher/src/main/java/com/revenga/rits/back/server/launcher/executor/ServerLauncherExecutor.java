package com.revenga.rits.back.server.launcher.executor;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.command.StopProcessesCommand;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;
import com.revenga.rits.back.server.launcher.service.ServerLauncherManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ServerLauncherExecutor extends GenericExecutor {

	private ServerLauncherManager serverLauncherManager;

	public ServerLauncherExecutor(Executor executor, short threadNum) {
		
		super(executor, threadNum);
		serverLauncherManager = ServerLauncherManager.getInstance();
	}

	@Override
	protected Object prepare(Object queueItem) {

		try {

			if (queueItem instanceof StopProcessesCommand) {

				StopProcessesCommand stopProcessesCommand = (StopProcessesCommand) queueItem;
				serverLauncherManager.stopProcesses(stopProcessesCommand);
			}

		} catch (

		Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return null;
	}

	@Override
	protected Object publish() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void evaluate() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

}
