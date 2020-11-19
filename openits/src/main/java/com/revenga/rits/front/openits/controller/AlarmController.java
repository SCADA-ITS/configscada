package com.revenga.rits.front.openits.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openits.bo.AlarmBO;
import com.revenga.rits.front.openits.bo.AlarmConfigBO;
import com.revenga.rits.front.openits.bo.AlarmLevelBO;
import com.revenga.rits.front.openits.bo.AlarmMutedBO;
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
	
	@Autowired
	AlarmMutedBO alarmMutedBO;
	
	@GetMapping
	@ApiOperation(value = "Get all alarms", notes = "Get all alarms")
	public ResponseEntity<Object> getAlarms() {
		
		return new ResponseEntity<>(alarmBO.getList(), HttpStatus.OK);
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
		
		return new ResponseEntity<>(alarmBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarms/{sourceElementId}")
	@ApiOperation(value = "Get all alarms by Element Source", notes = "Get all alarms by Element Source")
	public ResponseEntity<Object> getAlarmsBySource(
			@ApiParam(
				name = "sourceElementId",
				type = "String",
				value = "Source Element id",
				example = "Element:1:1",
				required = true) @PathVariable String sourceElementId) {
		
		return new ResponseEntity<>(alarmBO.getAlarmsBySource(sourceElementId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{sourceElementId}/{alarmTypeId}")
	@ApiOperation(value = "Get all alarms by Element Source and Alarm Type", notes = "Get all alarms by Element Source and Alarm Type")
	public ResponseEntity<Object> getAlarmsBySourceAndAlarmType(
			@ApiParam(
				name = "sourceElementId",
				type = "String",
				value = "Source Element id",
				example = "Element:1:1",
				required = true) @PathVariable String sourceElementId,
			@ApiParam(
				name = "alarmTypeId",
				type = "String",
				value = "Alarm Type id",
				example = "AlarmType:1",
				required = true) @PathVariable String alarmTypeId) {
		
		return new ResponseEntity<>(alarmBO.getAlarmsBySourceAndAlarmType(sourceElementId, alarmTypeId), HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/alarm_configs")
	@ApiOperation(value = "Get all alarm configs", notes = "Get all alarm configs")
	public ResponseEntity<Object> getAlarmConfigs() {
		
		return new ResponseEntity<>(alarmConfigBO.getList(), HttpStatus.OK);
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
		
		return new ResponseEntity<>(alarmConfigBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarm_types")
	@ApiOperation(value = "Get all alarm types", notes = "Get all alarm types")
	public ResponseEntity<Object> getAlarmTypes() {
		
		return new ResponseEntity<>(alarmTypeBO.getList(), HttpStatus.OK);
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
		
		return new ResponseEntity<>(alarmTypeBO.getItem(id), HttpStatus.OK);
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
		
		return new ResponseEntity<>(alarmBO.getAlarmsByAlarmType(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarm_levels")
	@ApiOperation(value = "Get all alarm levels", notes = "Get all alarm levels")
	public ResponseEntity<Object> getAlarmLevels() {
		
		return new ResponseEntity<>(alarmLevelBO.getList(), HttpStatus.OK);
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
		
		return new ResponseEntity<>(alarmLevelBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarm_muteds")
	@ApiOperation(value = "Get all alarm muteds", notes = "Get all alarm muteds")
	public ResponseEntity<Object> getAlarmMuteds() {
		
		return new ResponseEntity<>(alarmMutedBO.getAlarmMuteds(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarm_muted/{id}")
	@ApiOperation(value = "Get alarm muted by id", notes = "Get alarm muted by id")
	public ResponseEntity<Object> getAlarmMuted(
			@ApiParam(
			name = "id",
			type = "String",
			value = "AlarmMuted by id",
			example = "AlarmMuted:2:1:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(alarmMutedBO.getAlarmMuted(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarm_muted/alarm_configs/{id}")
	@ApiOperation(value = "Get alarm muted by alarm config id", notes = "Get alarm muted by alarm config id")
	public ResponseEntity<Object> getAlarmMutedsByAlarmConfig(
			@ApiParam(
			name = "id",
			type = "String",
			value = "AlarmMuted by alarm config id",
			example = "AlarmConfig:2",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(alarmMutedBO.getAlarmMutedsByAlarmConfig(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarm_muted/element/{id}")
	@ApiOperation(value = "Get alarm muted by element id", notes = "Get alarm muted by element id")
	public ResponseEntity<Object> getAlarmMutedsByElement(
			@ApiParam(
			name = "id",
			type = "String",
			value = "AlarmMuted by element id",
			example = "Element:1:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(alarmMutedBO.getAlarmMutedsByElement(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/createAlarmConfig")
	@ApiOperation(value = "Create Alarm config", notes = "Create Alarm config")
	public ResponseEntity<Object> createAlarmConfig(@RequestBody Object alarmConfig) {
		
		return new ResponseEntity<>(alarmConfigBO.createAlarmConfig(alarmConfig), HttpStatus.OK);
	}
	
	@PutMapping(value = "/updateAlarmConfig")
	@ApiOperation(value = "Update Alarm config", notes = "Update Alarm config")
	public ResponseEntity<Object> updateAlarmConfig(@RequestBody Object alarmConfig) {
		
		return new ResponseEntity<>(alarmConfigBO.updateAlarmConfig(alarmConfig), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deleteAlarmConfig/{id}")
	@ApiOperation(value = "Delete Alarm config", notes = "Delete Alarm config")
	public ResponseEntity<Object> deleteAlarmConfig(@ApiParam(
			name = "id",
			type = "String",
			value = "Alarm config id",
			example = "AlarmConfig:3",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(alarmConfigBO.deleteAlarmConfig(id), HttpStatus.OK);
	}
}
