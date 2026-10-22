package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AuditLog;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.command.SetHistoricAuditLogCommand;
import com.revenga.rits.back.data.core.model.command.SetRecognizeAuditLogCommand;
import com.revenga.rits.back.front.core.model.helper.command.SetHistoricAuditLogCommandHelper;
import com.revenga.rits.back.front.core.model.helper.command.SetRecognizeAuditLogCommandHelper;
import com.revenga.rits.back.front.core.model.vo.AuditLogVO;
import com.revenga.rits.back.service.graphql.type.AuditLogGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class LoggerService implements QueryInterface<AuditLogVO> {

	@Override
	public List<AuditLogVO> getAll() {

		List<AuditLogVO> auditLogs = new ArrayList<>();

		for (AuditLog auditLog : EntitiesManager.getInstance().getAuditLogRepository().getAll(false)) {

			auditLogs.add(new AuditLogGraphql(auditLog));
		}
		return auditLogs;
	}

	@Override
	public Optional<AuditLogVO> get(String auditLogId) {

		Optional<AuditLogVO> auditLogVO = null;
		try {
			Key key = new Key(auditLogId);

			if (key.getClazz() == AuditLog.class) {

				AuditLog auditLog = EntitiesManager.getInstance().getAuditLogRepository()
						.get(Long.valueOf(key.getId(2)), Long.valueOf(key.getId(1)), false);

				if (auditLog != null) {

					auditLogVO = Optional.of(new AuditLogGraphql(auditLog));
				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: AuditLog");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return auditLogVO;
	}

	@Override
	public List<AuditLogVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<AuditLogVO> getByType(String logTypeId) {

		List<AuditLogVO> auditLogs = new ArrayList<>();

		Key key = new Key(logTypeId);

		if (key.getClazz() == LogType.class) {

			for (AuditLog auditLog : EntitiesManager.getInstance().getAuditLogRepository()
					.getsByLogType(Long.valueOf(key.getId(1)), false)) {

				auditLogs.add(new AuditLogGraphql(auditLog));
			}
		}

		return auditLogs;
	}

	public boolean sendAuditLogs(List<AuditLogVO> auditLogs) {

		SetHistoricAuditLogCommand command = SetHistoricAuditLogCommandHelper.get(auditLogs);

		return (command != null && EntitiesManager.getInstance().sendCommand(command) != null) ? true : false;
	}

	public boolean sendRecognizeAuditLogs(List<String> auditLogIds) {

		SetRecognizeAuditLogCommand command = SetRecognizeAuditLogCommandHelper.get(auditLogIds);

		return (command != null && EntitiesManager.getInstance().sendCommand(command) != null) ? true : false;
	}

}
