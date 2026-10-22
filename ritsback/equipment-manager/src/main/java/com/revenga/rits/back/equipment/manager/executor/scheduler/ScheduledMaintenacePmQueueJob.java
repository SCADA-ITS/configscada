package com.revenga.rits.back.equipment.manager.executor.scheduler;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.equipment.manager.executor.service.SignallingCommandService;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScheduledMaintenacePmQueueJob implements Job {

	SignallingCommandService signallingCommandService = null;

	public ScheduledMaintenacePmQueueJob() {

		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		SchedulerContext schedulerContext = null;

		try {

			schedulerContext = context.getScheduler().getContext();

			signallingCommandService = (SignallingCommandService) schedulerContext
					.get(SignallingCommandService.class.getSimpleName());

			if (signallingCommandService != null) {

				if (!signallingCommandService.isRunning()) {

					signallingCommandService.setRunning(true);

					Instant instant = Instant.now();

					process();

					log.info(this.getClass().getSimpleName() + " => Total time for process pm_queue maintenance = "
							+ Duration.between(instant, Instant.now()).toMillis() + " ms.");

					signallingCommandService.setRunning(false);

				} else {

					log.warn(this.getClass().getSimpleName() + " => The previous scheduler is still running...");
				}
			}

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	private void process() {

		Set<Pair<Long, Element>> pmQueueElementToProcess = new HashSet<>();
		Set<Element> changedElements = signallingCommandService.deleteExpiredCommand(pmQueueElementToProcess);

		if (changedElements != null && CollectionUtils.isNotEmpty(pmQueueElementToProcess)) {

			List<PmCommandElement> commandsToProcess = signallingCommandService
					.proccessPmQueuesElements(pmQueueElementToProcess);

			if (CollectionUtils.isNotEmpty(commandsToProcess)) {

				commandsToProcess
						.forEach(be -> changedElements.add(new Element(be.getElementTypeId(), be.getElementId())));

				signallingCommandService.proccessCommands(commandsToProcess, null, true);
			}

			if (CollectionUtils.isNotEmpty(changedElements)) {
					
				List<Element> listElement = changedElements.stream().collect(Collectors.toList());
				signallingCommandService.updatenConflict(listElement);
				signallingCommandService.publishPmQueue(listElement);
			}
		}

		EntitiesManager.getInstance().dbSyncPmCommand();
	}
}
