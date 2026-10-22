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

import com.revenga.rits.front.openits.bo.LogTypeBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/log_types", produces = { "application/json", "application/xml" })
@Api(value = "LogType", tags = { "LOG TYPES" })
public class LogTypeController {
	
	@Autowired
	LogTypeBO logTypeBO;
	
	@GetMapping
	@ApiOperation(value = "Get all log types", notes = "Get all log types")
	public ResponseEntity<Object> getLogTypes() {
		
		return new ResponseEntity<>(logTypeBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get log type by id", notes = "Get log type by id")
	public ResponseEntity<Object> getLogType(
			@ApiParam(
			name = "id",
			type = "String",
			value = "LogType id",
			example = "LogType:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(logTypeBO.getItem(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/send_audit_logs")
	@ApiOperation(value = "Send Audit Logs", notes = "Send Audit Logs")
	public ResponseEntity<Boolean> sendAuditLogs(@RequestBody Object auditLog) {
		
		return new ResponseEntity<>(logTypeBO.sendAuditLogs(auditLog), HttpStatus.OK);
	}

	@PostMapping(value = "/send_recognize_audit_logs")
	@ApiOperation(value = "Send Recognize Audit Logs", notes = "Send Recognize Alarm")
	public ResponseEntity<Boolean> sendRecognizeAuditLogs(@RequestBody Object auditLogRecognize) {
		
		return new ResponseEntity<>(logTypeBO.sendRecognizeAuditLogs(auditLogRecognize), HttpStatus.OK);
	}
	

}
