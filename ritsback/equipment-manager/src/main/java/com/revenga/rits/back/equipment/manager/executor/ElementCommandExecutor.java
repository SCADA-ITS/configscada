package com.revenga.rits.back.equipment.manager.executor;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetAlarmsFilteredCommand;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetRecognizeAlarmsCommand;
import com.revenga.rits.back.equipment.manager.executor.service.ElementCommandService;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementCommandExecutor extends GenericExecutor {

	ElementCommandService elementCommandService;

	public ElementCommandExecutor(Executor executor, short thread) {

		super(executor, thread);

		elementCommandService = new ElementCommandService(this);
	}

	@Override
	protected Object prepare(Object queueItem) {

		try {
			
			if (queueItem instanceof SetActivationAlarmsCommand) {

				SetActivationAlarmsCommand setActivationAlarmsCommand = (SetActivationAlarmsCommand) queueItem;

				elementCommandService.setActivationAlarmsCommand(setActivationAlarmsCommand);
				
			} else if (queueItem instanceof SetDeactivationAlarmsCommand) {

				SetDeactivationAlarmsCommand setDeactivationAlarmsCommand = (SetDeactivationAlarmsCommand) queueItem;

				elementCommandService.setDeactivationAlarmsCommand(setDeactivationAlarmsCommand);
			
			} else if (queueItem instanceof SetRecognizeAlarmsCommand) {

				SetRecognizeAlarmsCommand setRecognizeAlarmsCommand = (SetRecognizeAlarmsCommand) queueItem;

				elementCommandService.setRecognizeAlarmsCommand(setRecognizeAlarmsCommand);
			
			} else if (queueItem instanceof SetAlarmsFilteredCommand) {

				SetAlarmsFilteredCommand setAlarmsFilteredCommand = (SetAlarmsFilteredCommand) queueItem;

				elementCommandService.setAlarmsFilteredCommand(setAlarmsFilteredCommand);
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
