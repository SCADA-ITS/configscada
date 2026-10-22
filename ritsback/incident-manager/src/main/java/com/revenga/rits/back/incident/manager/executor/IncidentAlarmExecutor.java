package com.revenga.rits.back.incident.manager.executor;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetAlarmsPublishCommand;
import com.revenga.rits.back.incident.manager.executor.processor.AlarmsProcessor;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class IncidentAlarmExecutor extends GenericExecutor {

	AlarmsProcessor alarmsProcessor;

	public IncidentAlarmExecutor(Executor executor, short thread) {

		super(executor, thread);
		alarmsProcessor = new AlarmsProcessor(this);
	}

	@Override
	protected Object prepare(Object queueItem) {
		
		try {
			if (queueItem instanceof SetAlarmsPublishCommand) {

				List<ImsIncidentReport> incidents = null;
				
				SetAlarmsPublishCommand setAlarmsPublishCommand = (SetAlarmsPublishCommand) queueItem;

				if(CollectionUtils.isNotEmpty(setAlarmsPublishCommand.getAlarms())){
					
					incidents = alarmsProcessor.processAlarms(setAlarmsPublishCommand.getAlarms());
					
					IncidentEntitiesManager.getInstance().publishIncidentReports(Command.ACTION_CREATE, incidents);
				}

			}
		} catch (Exception e) {

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

		log.debug(this.getClass().getSimpleName() + "[" + threadNum + "] now is running");
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
