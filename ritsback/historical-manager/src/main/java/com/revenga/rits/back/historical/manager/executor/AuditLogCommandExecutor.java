package com.revenga.rits.back.historical.manager.executor;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;

import com.revenga.rits.back.adapter.lib.model.HistoricalAuditLogParserV1;
import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.AuditLog;
import com.revenga.rits.back.data.core.model.LogTypeParam;
import com.revenga.rits.back.data.core.model.SystemParamValue;
import com.revenga.rits.back.data.core.model.command.SetHistoricAuditLogCommand;
import com.revenga.rits.back.data.core.model.command.SetRecognizeAuditLogCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.repository.SystemParamValueRepository;
import com.revenga.rits.back.historical.manager.executor.adapter.AuditLogAdapter;
import com.revenga.rits.back.historical.manager.executor.service.AuditLogService;
import com.revenga.rits.back.historical.manager.executor.service.EntitiesManager;
import com.revenga.rits.back.historical.manager.executor.service.ScheduledRecognizeAuditLogsJob;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuditLogCommandExecutor extends GenericExecutor {

	private static final String CRON_RECOGNIZE_AUDIT_LOG = "cron_recognize_audit_log";
	
	protected static final String MAX_UNRECOGNIZED_AUDIT_LOGS = "maxUnrecognizedAuditLogs";
	
	protected static final String FILE_CONSTANTS = "application.properties";
	
	private static final long MAX_UNRECOGNIZED_AUDIT_LOGS_SYSTEM_PARAM_ID = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(FILE_CONSTANTS, "max_unrecognized_audit_logs"));
	
	private AuditLogService auditLogService;
	
	@Getter
	@Setter
	boolean runningJob;
	
	public AuditLogCommandExecutor(Executor executor, short threadNum) {

		super(executor, threadNum);
		auditLogService = new AuditLogService();
	}

	public boolean recognizeAuditLogs(List<AuditLog> auditLogs) {
		
		return auditLogService.recognizeAuditLogs(auditLogs);	
	}
	
	@Override
	protected Object prepare(Object queueItem) {

		try {

			if (queueItem instanceof SetHistoricAuditLogCommand) {

				SetHistoricAuditLogCommand setHistoricAuditLogCommand = (SetHistoricAuditLogCommand) queueItem;

				while (!setHistoricAuditLogCommand.getAuditLogs().isEmpty()) {

					Long logTypeId = null;

					for (int i = setHistoricAuditLogCommand.getAuditLogs().size() - 1; i >= 0
							&& logTypeId == null; i--) {

						logTypeId = setHistoricAuditLogCommand.getAuditLogs().get(i).getLogTypeId();

						if (logTypeId == null) {

							setHistoricAuditLogCommand.getAuditLogs().remove(i);
						}
					}

					if (logTypeId != null) {

						Long id = logTypeId;

						List<AuditLog> auditLogs = setHistoricAuditLogCommand.getAuditLogs().stream()
								.filter(b -> id.equals(b.getLogTypeId())).collect(Collectors.toList());

						setHistoricAuditLogCommand.getAuditLogs().removeAll(auditLogs);

						HistoricalAuditLogParserV1 adapter = AuditLogAdapter.getInstance().getAdapters()
								.get(logTypeId.shortValue());

						List<LogTypeParam> logTypeParams = EntitiesManager.getInstance().getLogTypeParams(logTypeId);

						auditLogService.setAdapter(adapter);
						auditLogService.setLogTypeParams(logTypeParams);

						if (!auditLogService.processAuditLogs(auditLogs)) {

							log.error("Process audit logs error");
						}

					}
				}
			}
			else if (queueItem instanceof SetRecognizeAuditLogCommand) {

				SetRecognizeAuditLogCommand setRecognizeAuditLogCommand = (SetRecognizeAuditLogCommand) queueItem;
				recognizeAuditLogs(setRecognizeAuditLogCommand.getAuditLogs());
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return null;
	}

	@Override
	protected void initialize() {
		
		if (threadNum == 0) {
			try {

				Map<Short, HistoricalAuditLogParserV1> adapters = AuditLogAdapter.getInstance().getAdapters();

				for (Entry<Short, HistoricalAuditLogParserV1> entry : adapters.entrySet()) {

					List<LogTypeParam> logTypeParams = EntitiesManager.getInstance()
							.getLogTypeParams(Long.valueOf(entry.getKey()));

					if (!CollectionUtils.isEmpty(logTypeParams)) {

						auditLogService.setAdapter(entry.getValue());
						auditLogService.setLogTypeParams(logTypeParams);

						if (!auditLogService.init()) {

							log.error("Init error");
						}
					}

				}
				launchRecognizeAuditLong();
				
			} catch (Exception e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
	}

	@Override
	protected Object publish() {
		// Auto-generated method stub
		return null;
	}

	@Override
	protected void end() {

		if (threadNum == 0) {

			try {

				log.debug("Shutdown scheduled jobs ...");

				EntitiesManager.getInstance().getScheduler().shutdown(true);

				log.debug("Shutdown Complete");
				log.debug("Executed "
						+ EntitiesManager.getInstance().getScheduler().getMetaData().getNumberOfJobsExecuted()
						+ " jobs.");

			} catch (SchedulerException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
	}

	@Override
	protected void evaluate() {
		// TODO Auto-generated method stub

	}
	
	private void launchRecognizeAuditLong() {

		try {
			String cronRecognizeAuditLogs = getParamValue(CRON_RECOGNIZE_AUDIT_LOG);
			Long maxUnrecognizedAuditLogs = getMaxUnrecognizedAuditLogs();

			if (!StringUtils.isEmpty(cronRecognizeAuditLogs) && maxUnrecognizedAuditLogs != null
					&& EntitiesManager.getInstance().getScheduler() != null) {

				EntitiesManager.getInstance().getScheduler().getContext().put(this.getClass().getSimpleName(), this);
				EntitiesManager.getInstance().getScheduler().getContext().put(MAX_UNRECOGNIZED_AUDIT_LOGS,
						maxUnrecognizedAuditLogs);
				createJob(ScheduledRecognizeAuditLogsJob.class, cronRecognizeAuditLogs);

				// will run until the scheduler has been started
				if (!EntitiesManager.getInstance().getScheduler().isStarted()) {

					EntitiesManager.getInstance().getScheduler().start();
				}
			} else {

				log.warn("Auto recognize logs is disabled");
			}

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	private void createJob(Class<? extends Job> jobClass, String cronExpression) {

		try {

			// jobs can be scheduled before sched.start() has been called
			// job 1 will run every cron value

			JobDetail job = newJob(jobClass)
					.withIdentity("job" + jobClass.getSimpleName(), "group" + jobClass.getSimpleName()).build();

			if (!StringUtils.isEmpty(cronExpression)) {

				CronTrigger trigger = newTrigger()
						.withIdentity("trigger" + jobClass.getSimpleName(), "group" + jobClass.getSimpleName())
						.withSchedule(cronSchedule(cronExpression)).build();

				Date dt = EntitiesManager.getInstance().getScheduler().scheduleJob(job, trigger);

				log.info(job.getKey() + " has been scheduled to run at: " + dt + " and repeat based on expression: "
						+ trigger.getCronExpression());
			} else {

				log.info(job.getKey() + " is not scheduled");
			}

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	private Long getMaxUnrecognizedAuditLogs() {

		Long maxUnrecognizedAuditLogs = null;

		SystemParamValue systemParamValue = (new SystemParamValueRepository())
				.get(MAX_UNRECOGNIZED_AUDIT_LOGS_SYSTEM_PARAM_ID, false);

		if (systemParamValue != null && systemParamValue.getValue() != null) {

			try {

				maxUnrecognizedAuditLogs = Long.valueOf(systemParamValue.getValue());

			} catch (NumberFormatException e) {

				log.error("Cannot get maxUnrecognizedAuditLogs, wrong number format");
			}
		}

		return maxUnrecognizedAuditLogs;
	}

}
