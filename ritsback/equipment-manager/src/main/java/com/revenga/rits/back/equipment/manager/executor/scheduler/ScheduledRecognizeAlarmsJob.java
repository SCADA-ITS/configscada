package com.revenga.rits.back.equipment.manager.executor.scheduler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.command.SetRecognizeAlarmsCommand;
import com.revenga.rits.back.equipment.manager.data.RootData;
import com.revenga.rits.back.equipment.manager.executor.UnrecognizedAlarmsExecutor;
import com.revenga.rits.back.equipment.manager.executor.service.ElementCommandService;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScheduledRecognizeAlarmsJob implements Job {

	private RootData rootData;
	GenericExecutor genericExecutor = null;
	private ElementCommandService elementCommandService;

	protected static final String FILE_CONSTANTS = "application.properties";
	protected static final String AUTO_RECOGNIZED_COMMENTS = "Recognized automatically by the system.";
	protected static final String MAX_UNRECOGNIZED_ALARMS_ALLOWED = "maxUnrecognizedAlarmsAllowed";

	public ScheduledRecognizeAlarmsJob() {

		super();

		initialize();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		SchedulerContext schedulerContext = null;
		
		List<Long> alarmsIds = new ArrayList<>();

		try {

			schedulerContext = context.getScheduler().getContext();

			genericExecutor = (UnrecognizedAlarmsExecutor) schedulerContext
					.get(UnrecognizedAlarmsExecutor.class.getSimpleName());

			if (!EntitiesManager.getInstance().isRunningJob() && genericExecutor != null) {

				EntitiesManager.getInstance().setRunningJob(true);

				Long maxUnrecognizedAlarmsAllowed = context.getScheduler().getContext()
						.getLong(MAX_UNRECOGNIZED_ALARMS_ALLOWED);

				if (rootData != null && maxUnrecognizedAlarmsAllowed != null) {

					List<Alarm> alarms = rootData.getAlarms();

					if (!CollectionUtils.isEmpty(alarms)) {

						List<Alarm> unrecognizedAlarms = alarms.stream().filter(e -> (e.getRecognized() == null))
								.collect(Collectors.toList());

						if (!CollectionUtils.isEmpty(unrecognizedAlarms)
								&& unrecognizedAlarms.size() > maxUnrecognizedAlarmsAllowed) {

							SetRecognizeAlarmsCommand setRecognizeAlarmsCommand = new SetRecognizeAlarmsCommand();
							
							unrecognizedAlarms.sort(Comparator.comparing(Alarm::getActivation));

							Long nAlarmsToRecognize = unrecognizedAlarms.size() - maxUnrecognizedAlarmsAllowed;

							for (int i = 0; i < nAlarmsToRecognize; i++) {
								
								alarmsIds.add(unrecognizedAlarms.get(i).getId());	
							}
							
							if (!CollectionUtils.isEmpty(alarmsIds)) {
								
								setRecognizeAlarmsCommand.setAlarmIds(alarmsIds);
								setRecognizeAlarmsCommand.setRecognizedComments(AUTO_RECOGNIZED_COMMENTS);
								elementCommandService.setRecognizeAlarmsCommand(setRecognizeAlarmsCommand);
							}
						}
					}
				}

				EntitiesManager.getInstance().setRunningJob(false);

			} else {

				log.warn(this.getClass().getSimpleName() + " => The previous scheduler is still running...");
			}

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

		} 
	}

	public void initialize() {

		rootData = EntitiesManager.getInstance().getRootData();
		
		elementCommandService = new ElementCommandService(null);
	}

}
