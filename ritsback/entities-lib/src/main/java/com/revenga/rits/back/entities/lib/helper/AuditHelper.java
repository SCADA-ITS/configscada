package com.revenga.rits.back.entities.lib.helper;

import java.time.Instant;
import java.util.List;

import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.data.core.model.AuditLog;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetHistoricAuditLogCommand;

public class AuditHelper {

	private AuditHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());

	}

	public static boolean Audit(final Long logTypeId, final Long logSubtypeId, final Long userId, final Long level,
			final String... args) {

		SetHistoricAuditLogCommand command = SetHistoricAuditLogCommandHelper.get(logTypeId, logSubtypeId, userId,
				Instant.now().toEpochMilli(), level, args);

		return (command != null && CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME) != null)
				? true
				: false;
	}

	public static boolean Audit(List<AuditLog> auditLogs) {

		SetHistoricAuditLogCommand command = SetHistoricAuditLogCommandHelper.get(auditLogs);

		return (command != null && CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME) != null)
				? true
				: false;
	}
}
