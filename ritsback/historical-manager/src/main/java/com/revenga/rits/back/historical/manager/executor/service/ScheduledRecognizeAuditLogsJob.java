package com.revenga.rits.back.historical.manager.executor.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.revenga.rits.back.data.core.model.AuditLog;
import com.revenga.rits.back.historical.manager.executor.AuditLogCommandExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScheduledRecognizeAuditLogsJob implements Job {

	protected static final String MAX_UNRECOGNIZED_AUDIT_LOGS = "maxUnrecognizedAuditLogs";

	public ScheduledRecognizeAuditLogsJob() {

		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		SchedulerContext schedulerContext = null;

		try {

			schedulerContext = context.getScheduler().getContext();

			AuditLogCommandExecutor auditLogCommandExecutor = (AuditLogCommandExecutor) schedulerContext
					.get(AuditLogCommandExecutor.class.getSimpleName());

			if (auditLogCommandExecutor != null && !auditLogCommandExecutor.isRunningJob()) {

				auditLogCommandExecutor.setRunningJob(true);

				Long maxUnrecognizedAuditLogs = context.getScheduler().getContext()
						.getLong(MAX_UNRECOGNIZED_AUDIT_LOGS);

				if (maxUnrecognizedAuditLogs != null) {

					List<AuditLog> auditLogs = EntitiesManager.getInstance().getAllAuditLog();

					if (!CollectionUtils.isEmpty(auditLogs) && auditLogs.size() > maxUnrecognizedAuditLogs) {

						List<AuditLog> auditLogsToRecognized = new ArrayList<>();

						auditLogs.sort(Comparator.comparing(AuditLog::getTime));

						Long nAuditToRecognize = auditLogs.size() - maxUnrecognizedAuditLogs;

						for (int i = 0; i < nAuditToRecognize; i++) {

							auditLogsToRecognized.add(auditLogs.get(i));
						}

						if (!CollectionUtils.isEmpty(auditLogsToRecognized)) {

							auditLogCommandExecutor.recognizeAuditLogs(auditLogsToRecognized);
						}
					}
				}

				auditLogCommandExecutor.setRunningJob(false);

			} else {

				log.warn(this.getClass().getSimpleName() + " => The previous scheduler is still running...");
			}

		} catch (

		SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

		}
	}

}
