package com.revenga.rits.front.openits.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openits.bo.AuditLogBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/audit_logs", produces = { "application/json", "application/xml" })
@Api(value = "AuditLog", tags = { "AUDIT LOGS" })
public class AuditLogController {
	
	@Autowired
	AuditLogBO auditLogBO;
	
	@GetMapping
	@ApiOperation(value = "Get all audit logs", notes = "Get all audit logs")
	public ResponseEntity<Object> getAuditLogs() {
		
		return new ResponseEntity<>(auditLogBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get audit logby id", notes = "Get audit log by id")
	public ResponseEntity<Object> getLogType(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Audit log id",
			example = "AuditLog:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(auditLogBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/log_type/{id}")
	@ApiOperation(value = "Get audit logs by log type", notes = "Get audit logs by log type")
	public ResponseEntity<Object> getByLogType(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Log type id",
			example = "LogType:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(auditLogBO.getByLogType(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/send")
	@ApiOperation(value = "Send Audit Logs", notes = "Send Audit Logs")
	public ResponseEntity<Boolean> send(@RequestBody Object auditLogs) {
		
		return new ResponseEntity<>(auditLogBO.send(auditLogs), HttpStatus.OK);
	}

	@PostMapping(value = "/send_recognize")
	@ApiOperation(value = "Send Recognize Audit Logs", notes = "Send Recognize Audit logs")
	public ResponseEntity<Boolean> sendRecognize(@RequestBody Object auditLogRecognizes) {
		
		return new ResponseEntity<>(auditLogBO.sendRecognize(auditLogRecognizes), HttpStatus.OK);
	}
}
