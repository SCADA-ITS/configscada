package com.revenga.rits.back.front.core.model.helper.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.AuditLog;
import com.revenga.rits.back.data.core.model.command.SetHistoricAuditLogCommand;
import com.revenga.rits.back.front.core.model.helper.AuditLogHelper;
import com.revenga.rits.back.front.core.model.vo.AuditLogVO;

public class SetHistoricAuditLogCommandHelper {

	private SetHistoricAuditLogCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetHistoricAuditLogCommand get(List<AuditLogVO> auditLogsVO) {

		SetHistoricAuditLogCommand setHistoricAuditLogCommand = null;

		if (!CollectionUtils.isEmpty(auditLogsVO)) {

			setHistoricAuditLogCommand = new SetHistoricAuditLogCommand();

			List<AuditLog> auditLogs = new ArrayList<>();

			for (AuditLogVO auditLogVO : auditLogsVO) {

				AuditLog auditLog = AuditLogHelper.get(auditLogVO);

				if (auditLog != null) {

					auditLogs.add(auditLog);
				}

			}
			setHistoricAuditLogCommand.setAuditLogs(auditLogs);
		}

		return setHistoricAuditLogCommand;
	}


}
