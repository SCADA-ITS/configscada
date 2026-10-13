package com.revenga.rits.back.historical.manager.executor;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.HistoricalAuditLogParserV1;
import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.AuditLog;
import com.revenga.rits.back.data.core.model.LogTypeParam;
import com.revenga.rits.back.data.core.model.command.SetHistoricAuditLogCommand;
import com.revenga.rits.back.data.core.model.command.SetRecognizeAuditLogCommand;
import com.revenga.rits.back.historical.manager.executor.adapter.AuditLogAdapter;
import com.revenga.rits.back.historical.manager.executor.service.AuditLogService;
import com.revenga.rits.back.historical.manager.executor.service.EntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuditLogCommandExecutor extends GenericExecutor {

	AuditLogService auditLogService;

	public AuditLogCommandExecutor(Executor executor, short threadNum) {

		super(executor, threadNum);
		auditLogService = new AuditLogService();
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
				auditLogService.recognizeAuditLogs(setRecognizeAuditLogCommand.getAuditLogs());
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
		// Auto-generated method stub

	}

	@Override
	protected void evaluate() {
		// TODO Auto-generated method stub

	}

}
