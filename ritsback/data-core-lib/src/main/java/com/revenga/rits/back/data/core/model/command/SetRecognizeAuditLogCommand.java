package com.revenga.rits.back.data.core.model.command;

import java.util.List;

import com.revenga.rits.back.data.core.model.AuditLog;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetRecognizeAuditLogCommand extends Command  {/**
	 * 
	 */
	private static final long serialVersionUID = 3148594124245641104L;
	
	private List<AuditLog> auditLogs;
	
	public SetRecognizeAuditLogCommand() {

		super(Command.SET_RECOGNIZE_AUDIT_LOG);
	}

}
