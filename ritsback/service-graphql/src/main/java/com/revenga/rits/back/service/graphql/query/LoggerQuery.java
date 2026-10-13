package com.revenga.rits.back.service.graphql.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.front.core.model.vo.AuditLogVO;
import com.revenga.rits.back.front.core.model.vo.LogTypeVO;
import com.revenga.rits.back.service.graphql.service.LogTypeService;
import com.revenga.rits.back.service.graphql.service.LoggerService;

@Component
public class LoggerQuery implements GraphQLQueryResolver {

	@Autowired
	private LogTypeService logTypeService;
	@Autowired
	private LoggerService loggerService;

	public Optional<LogTypeVO> getLogType(final String logTypeId) {

		return logTypeService.get(logTypeId);
	}

	public List<LogTypeVO> getLogTypes() {

		return logTypeService.getAll();
	}

	public List<AuditLogVO> getAuditLogs() {

		return loggerService.getAll();
	}

	public Optional<AuditLogVO> getAuditLog(final String auditLogId) {

		return loggerService.get(auditLogId);
	}

	public List<AuditLogVO> getAuditLogsByType(final String logTypeId) {

		return loggerService.getByType(logTypeId);
	}
}
