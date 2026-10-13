package com.revenga.rits.back.data.core.model.command;

import java.util.List;

import com.revenga.rits.back.data.core.model.AuditLog;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetHistoricAuditLogCommand extends Command  {/**
	 * 
	 */
	private static final long serialVersionUID = 3148594124245641104L;
	
	private List<AuditLog> auditLogs;
	
	public SetHistoricAuditLogCommand() {

		super(Command.SET_HISTORIC_AUDIT_LOG);
	}

}
