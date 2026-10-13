package com.revenga.rits.back.front.core.model.helper.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AuditLog;
import com.revenga.rits.back.data.core.model.command.SetRecognizeAuditLogCommand;

public class SetRecognizeAuditLogCommandHelper {

	private SetRecognizeAuditLogCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetRecognizeAuditLogCommand get(List<String> auditLogIds) {

		SetRecognizeAuditLogCommand setRecognizeAuditLogCommand = null;

		if (!CollectionUtils.isEmpty(auditLogIds)) {

			setRecognizeAuditLogCommand = new SetRecognizeAuditLogCommand();

			List<AuditLog> list = new ArrayList<>();

			for (String auditLogId : auditLogIds) {

				Key key = new Key(auditLogId);

				if (key.getClazz() == AuditLog.class) {

					AuditLog auditLog = new AuditLog();
					auditLog.setPks(key.getIds());
					list.add(auditLog);
				}
			}
			setRecognizeAuditLogCommand.setAuditLogs(list);
		}

		return setRecognizeAuditLogCommand;
	}

}
