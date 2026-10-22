package com.revenga.rits.back.manager.lib.executor;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.command.ResponseCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.manager.lib.service.ResponseManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ResponseCommandExecutor extends GenericExecutor {

	protected static final String FILE_PROPERTIES = "manager-lib.properties";

	private static final Long CLEAR_MAP = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_PROPERTIES, "service.command_service.clear_map"));

	private Long receivedCommands;

	public ResponseCommandExecutor(Executor executor, short thread) {

		super(executor, thread);
		receivedCommands = 0L;
	}

	@Override
	protected Object prepare(Object queueItem) {

		try {

			if (queueItem instanceof ResponseCommand) {

				ResponseCommand responseCommand = (ResponseCommand) queueItem;

				if (responseCommand.getUIdSource() != null) {

					ResponseManager.getInstance().add(responseCommand);
					receivedCommands++;
				}
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		if (receivedCommands % CLEAR_MAP == 0) {
			
			log.info("Received commands:" + receivedCommands + " clear map.");
			ResponseManager.getInstance().clearMap();
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
