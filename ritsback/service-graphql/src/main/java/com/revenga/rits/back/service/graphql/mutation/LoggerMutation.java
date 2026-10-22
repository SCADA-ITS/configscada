package com.revenga.rits.back.service.graphql.mutation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.revenga.rits.back.front.core.model.vo.AuditLogVO;
import com.revenga.rits.back.service.graphql.service.LoggerService;

@Component
public class LoggerMutation implements GraphQLMutationResolver {
	
	@Autowired
	private LoggerService loggerService;
	
	public Boolean sendAuditLogs(final List<AuditLogVO> auditLogs) {

		return loggerService.sendAuditLogs(auditLogs);
	}

	public Boolean sendRecognizeAuditLogs(final List<String> auditLogIds) {

		return loggerService.sendRecognizeAuditLogs(auditLogIds);
	}
}
