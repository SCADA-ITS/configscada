package com.revenga.rits.front.openits.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openits.bo.AlarmBO;
import com.revenga.rits.front.openits.bo.AlarmConfigBO;
import com.revenga.rits.front.openits.bo.AlarmLevelBO;
import com.revenga.rits.front.openits.bo.AlarmTypeBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/alarms", produces = { "application/json", "application/xml" })
@Api(value = "Alarms", tags = { "ALARMS" })
public class AlarmController {
	
	@Autowired
	AlarmBO alarmBO;
	
	@Autowired
	AlarmConfigBO alarmConfigBO;
	
	@Autowired
	AlarmTypeBO alarmTypeBO;
	
	@Autowired
	AlarmLevelBO alarmLevelBO;
	
	@GetMapping
	@ApiOperation(value = "Get all alarms", notes = "Get all alarms")
	public ResponseEntity<Object> getAlarms() {
		
		return new ResponseEntity<>(alarmBO.getAlarms(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get alarm by id", notes = "Get alarm by id")
	public ResponseEntity<Object> getAlarm(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Alarm id",
			example = "Alarm:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(alarmBO.getAlarm(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarm_configs")
	@ApiOperation(value = "Get all alarm configs", notes = "Get all alarm configs")
	public ResponseEntity<Object> getAlarmConfigs() {
		
		return new ResponseEntity<>(alarmConfigBO.getAlarmConfigs(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarm_configs/{id}")
	@ApiOperation(value = "Get alarm config by id", notes = "Get alarm config by id")
	public ResponseEntity<Object> getAlarmConfig(
			@ApiParam(
			name = "id",
			type = "String",
			value = "AlarmConfig by id",
			example = "AlarmConfig:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(alarmConfigBO.getAlarmConfig(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarm_types")
	@ApiOperation(value = "Get all alarm types", notes = "Get all alarm types")
	public ResponseEntity<Object> getAlarmTypes() {
		
		return new ResponseEntity<>(alarmTypeBO.getAlarmTypes(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarm_types/{id}")
	@ApiOperation(value = "Get alarm type by id", notes = "Get alarm type by id")
	public ResponseEntity<Object> getAlarmType(
			@ApiParam(
			name = "id",
			type = "String",
			value = "AlarmType by id",
			example = "AlarmType:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(alarmTypeBO.getAlarmType(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarm_types/{id}/alarms")
	@ApiOperation(value = "Get alarms by alarm type id", notes = "Get alarms by alarm type id")
	public ResponseEntity<Object> getAlarmsByAlarmType(
			@ApiParam(
			name = "id",
			type = "String",
			value = "AlarmType by id",
			example = "AlarmType:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(alarmTypeBO.getAlarmsByAlarmType(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarm_levels")
	@ApiOperation(value = "Get all alarm levels", notes = "Get all alarm levels")
	public ResponseEntity<Object> getAlarmLevels() {
		
		return new ResponseEntity<>(alarmLevelBO.getAlarmLevels(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarm_levels/{id}")
	@ApiOperation(value = "Get alarm level by id", notes = "Get alarm level by id")
	public ResponseEntity<Object> getAlarmLevel(
			@ApiParam(
			name = "id",
			type = "String",
			value = "AlarmLevel by id",
			example = "AlarmLevel:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(alarmLevelBO.getAlarmLevel(id), HttpStatus.OK);
	}
}
