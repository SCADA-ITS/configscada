package com.revenga.rits.back.equipment.manager.executor.scheduler;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.revenga.rits.back.equipment.manager.executor.DBSyncMeasuresExecutor;
import com.revenga.rits.back.equipment.manager.service.ElementMeasuresManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScheduledDBSyncMeasures implements Job {

	GenericExecutor genericExecutor = null;

	public ScheduledDBSyncMeasures() {

		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		SchedulerContext schedulerContext = null;

		try {

			schedulerContext = context.getScheduler().getContext();

			genericExecutor = (DBSyncMeasuresExecutor) schedulerContext
					.get(DBSyncMeasuresExecutor.class.getSimpleName());

			if (!ElementMeasuresManager.getInstance().isRunning() && genericExecutor != null) {

				ElementMeasuresManager.getInstance().setRunning(true);
				Instant instant = Instant.now();

				process();

				ElementMeasuresManager.getInstance().setRunning(false);
				
				log.info(this.getClass().getSimpleName() + " => Total time for updateElement value in db  = "
						+ Duration.between(instant, Instant.now()).toMillis() + " ms.");
			} else {

					log.warn(this.getClass().getSimpleName() + " => The previous scheduler is still running...");
			}

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			ElementMeasuresManager.getInstance().setRunning(false);
		}
	}

	private void process() {

		ElementMeasuresManager.getInstance().load();

		ElementMeasuresManager.getInstance()
				.updateElementValues(ElementMeasuresManager.getInstance().getElemenValues());
	}
}
