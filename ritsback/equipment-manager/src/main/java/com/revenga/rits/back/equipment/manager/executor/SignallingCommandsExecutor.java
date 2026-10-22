package com.revenga.rits.back.equipment.manager.executor;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.SetAddCommandsCommand;
import com.revenga.rits.back.data.core.model.command.SetDeleteCommandsCommand;
import com.revenga.rits.back.data.core.model.command.SetPriorityComand;
import com.revenga.rits.back.data.core.model.command.SetSignallingsCommand;
import com.revenga.rits.back.equipment.manager.executor.service.SignallingCommandService;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SignallingCommandsExecutor extends GenericExecutor {

	private static final String CRON_MAINTENANCE_PM_QUEUE = "cron_maintenance_pm_queue";

	SignallingCommandService signallingCommandService;

	public SignallingCommandsExecutor(Executor executor, short thread) {

		super(executor, thread);
		
		signallingCommandService = new SignallingCommandService(this);
	}

	@Override
	protected Object prepare(Object queueItem) {

		List<Element> elements = null;

		try {
			if (queueItem instanceof SetSignallingsCommand) {

				SetSignallingsCommand setSignallingsCommand = (SetSignallingsCommand) queueItem;

				signallingCommandService.setSignallingsCommand(setSignallingsCommand);

			} else if (queueItem instanceof SetAddCommandsCommand) {

				SetAddCommandsCommand setAddCommandsCommand = (SetAddCommandsCommand) queueItem;

				elements = signallingCommandService.setAddCommandsCommand(setAddCommandsCommand);

			} else if (queueItem instanceof SetDeleteCommandsCommand) {

				SetDeleteCommandsCommand setDeleteCommandsCommand = (SetDeleteCommandsCommand) queueItem;

				elements = signallingCommandService.setDeleteCommandsCommand(setDeleteCommandsCommand);

			} else if (queueItem instanceof SetPriorityComand) {

				SetPriorityComand setPriorityComand = (SetPriorityComand) queueItem;

				elements = signallingCommandService.setPriorityComand(setPriorityComand);
			}

			signallingCommandService.updatenConflict(elements);
			signallingCommandService.publishPmQueue(elements);

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
		
		if (threadNum == 0) {

			String cronMaintenacePmQueue = getParamValue(CRON_MAINTENANCE_PM_QUEUE);

			signallingCommandService.init(cronMaintenacePmQueue);
		}

	}

	@Override
	protected void evaluate() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void end() {

		if (threadNum == 0) {

			signallingCommandService.deInit();
		}
	}

}
