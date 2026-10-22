package com.revenga.rits.back.entities.lib.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.AuditLog;
import com.revenga.rits.back.data.core.model.AuditLogValue;
import com.revenga.rits.back.data.core.model.command.SetHistoricAuditLogCommand;

public class SetHistoricAuditLogCommandHelper {

	private SetHistoricAuditLogCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetHistoricAuditLogCommand get(List<AuditLog> auditLogs) {

		SetHistoricAuditLogCommand command = null;

		if (CollectionUtils.isNotEmpty(auditLogs)) {

			command = new SetHistoricAuditLogCommand();
			command.setAuditLogs(auditLogs);
		}
		return command;
	}

	public static SetHistoricAuditLogCommand get(final Long logTypeId, final Long logSubtypeId, final Long userId,
			final Long time, final Long level, final String... args) {

		SetHistoricAuditLogCommand command = null;

		if (ObjectUtils.allNotNull(logTypeId, userId, time, level)) {

			AuditLog auditLog = new AuditLog();
			auditLog.generateId();
			
			List<AuditLog> auditLogs = new ArrayList<>();
			auditLogs.add(auditLog);

			command = new SetHistoricAuditLogCommand();
			command.setAuditLogs(auditLogs);

			auditLog.setLogTypeId(logTypeId);
			auditLog.setLogSubtypeId(logSubtypeId);
			auditLog.setUserId(userId);
			auditLog.setTime(time);
			auditLog.setLevelId(level);

			if (ArrayUtils.isNotEmpty(args)) {

				List<AuditLogValue> auditLogValues = new ArrayList<>();
				Long logParamId = 1L;

				for (final String arg : args) {

					AuditLogValue logValue = new AuditLogValue();
					logValue.setAuditLogId(auditLog.getId());
					logValue.setLogTypeId(logTypeId);
					logValue.setLogParamId(logParamId);
					logValue.setValue(arg);

					auditLogValues.add(logValue);
					logParamId++;
				}
				auditLog.setAuditLogValues(auditLogValues);
			}
		}
		return command;
	}

}
