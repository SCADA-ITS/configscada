package com.revenga.rits.back.equipment.manager.executor;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.command.RequestCommand;
import com.revenga.rits.back.data.core.model.command.SetElementStatesChangeCommand;
import com.revenga.rits.back.equipment.manager.executor.service.ElementStateService;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementStateExecutor extends GenericExecutor {
	
	ElementStateService elementStateService;
	
	public ElementStateExecutor(Executor executor, short thread) {

		super(executor, thread);
		
		elementStateService = new ElementStateService(this);
	}

	@Override
	protected Object prepare(Object queueItem) {
		
		try {
			
			if (queueItem instanceof SetElementStatesChangeCommand) {
				
				elementStateService.setElementStatesChangeCommand((SetElementStatesChangeCommand) queueItem);
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
		
		log.debug(this.getClass().getSimpleName() + "[" + threadNum + "] now is running");
		
		RequestCommand requestCommand = new RequestCommand();
		
		requestCommand.setRequestTypeId(RequestCommand.REQ_STATE);
		
		EntitiesManager.getInstance().sendCommand(requestCommand);
	}
	
	@Override
	protected void end() {
		
		// Without implementation
	}

	@Override
	protected void evaluate() {
		
		// Without implementation
	}
}
