package com.revenga.rits.back.user.manager.executor;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.command.SetKillUserCommand;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;
import com.revenga.rits.back.user.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommandUserSessionExecutor extends GenericExecutor {

	EntitiesManager entitiesManager;
	CommandUserSessionValidator commandUserSessionValidator;
	
	public CommandUserSessionExecutor(Executor executor, short thread) {

		super(executor, thread);
		
		commandUserSessionValidator = new CommandUserSessionValidator(this);
	}

	@Override
	protected Object prepare(Object queueItem) {
		
		try {
			
			if (queueItem instanceof SetKillUserCommand) {
				
				SetKillUserCommand setKillUserCommand = (SetKillUserCommand) queueItem;
				
				commandUserSessionValidator.killUser(setKillUserCommand);
			}

		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return null;
	}

	@Override
	protected Object publish() {
			
		return null;
	}

	@Override
	protected void initialize() {
		
		EntitiesManager.getInstance().delSessions();
	}

	@Override
	protected void end() {
		
		EntitiesManager.getInstance().delSessions();
	}

	@Override
	protected void evaluate() {
		// TODO Auto-generated method stub
		
	}
}
