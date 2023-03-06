package com.revenga.rits.front.openits.controller;

import java.util.List;

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
import com.revenga.rits.front.openits.bo.AlarmFilteredBO;
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
	AlarmFilteredBO alarmFilteredBO;
	
	@GetMapping
	@ApiOperation(value = "Get all alarms", notes = "Get all alarms")
	public ResponseEntity<Object> getAlarms() {
		
		return new ResponseEntity<>(alarmBO.getList(), HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = "Get selected alarms", notes = "Get selected alarms")
	public ResponseEntity<Object> getAlarms(@RequestBody List<String> ids) {
		
		return new ResponseEntity<>(alarmBO.getAlarms(ids), HttpStatus.OK);
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
	
	@GetMapping(value = "/by_alarm_config/{alarmConfigId}")
	@ApiOperation(value = "Get all alarms by Alarm config", notes = "Get all alarms by Alarm config")
	public ResponseEntity<Object> getAlarmsByAlarmConfig(
			@ApiParam(
				name = "AlarmConfigId",
				type = "String",
				value = "Alarm config id",
				example = "AlarmConfig:1",
				required = true) @PathVariable String alarmConfigId) {
		
		return new ResponseEntity<>(alarmBO.getAlarmsByAlarmConfig(alarmConfigId), HttpStatus.OK);
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
	
	@GetMapping(value = "/alarm_configs/alarm_type/{id}")
	@ApiOperation(value = "Get alarm configs by alarm type id", notes = "Get alarm configs by alarm type id")
	public ResponseEntity<Object> getAlarmConfigsByAlarmType(
			@ApiParam(
			name = "id",
			type = "String",
			value = "AlarmType by id",
			example = "AlarmType:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(alarmConfigBO.getAlarmConfigsByAlarmType(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarm_configs/alarm_level/{id}")
	@ApiOperation(value = "Get alarm configs by alarm level id", notes = "Get alarm configs by alarm level id")
	public ResponseEntity<Object> getAlarmConfigsByAlarmLevel(
			@ApiParam(
			name = "id",
			type = "String",
			value = "AlarmLevel by id",
			example = "AlarmLevel:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(alarmConfigBO.getAlarmConfigsByAlarmLevel(id), HttpStatus.OK);
	}

	@GetMapping(value = "/alarm_configs/element_type/{id}")
	@ApiOperation(value = "Get alarm configs by element type id", notes = "Get alarm configs by element type id")
	public ResponseEntity<Object> getAlarmConfigsByElementType(
			@ApiParam(
			name = "id",
			type = "String",
			value = "ElementType by id",
			example = "ElementType:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(alarmConfigBO.getAlarmConfigsByElementType(id), HttpStatus.OK);
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
	
	@GetMapping(value = "/alarm_types/alarms/{id}")
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
	
	@GetMapping(value = "/alarm_filtereds")
	@ApiOperation(value = "Get all alarm filtereds", notes = "Get all alarm filtereds")
	public ResponseEntity<Object> getAlarmFiltereds() {
		
		return new ResponseEntity<>(alarmFilteredBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarm_filtered/{id}")
	@ApiOperation(value = "Get alarm filtered by id", notes = "Get alarm filtered by id")
	public ResponseEntity<Object> getAlarmFiltered(
			@ApiParam(
			name = "id",
			type = "String",
			value = "AlarmFiltered by id",
			example = "AlarmFiltered:2:1:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(alarmFilteredBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarm_filtered/alarm_configs/{id}")
	@ApiOperation(value = "Get alarm filtered by alarm config id", notes = "Get alarm filtered by alarm config id")
	public ResponseEntity<Object> getAlarmFilteredsByAlarmConfig(
			@ApiParam(
			name = "id",
			type = "String",
			value = "AlarmFiltered by alarm config id",
			example = "AlarmConfig:2",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(alarmFilteredBO.getAlarmFilteredsByAlarmConfig(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/alarm_filtered/element/{id}")
	@ApiOperation(value = "Get alarm filtered by element id", notes = "Get alarm filtered by element id")
	public ResponseEntity<Object> getAlarmFilteredsByElement(
			@ApiParam(
			name = "id",
			type = "String",
			value = "AlarmFiltered by element id",
			example = "Element:1:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(alarmFilteredBO.getAlarmFilteredsByElement(id), HttpStatus.OK);
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
	
	@PostMapping(value = "/send_alarm_filtered_actions")
	@ApiOperation(value = "Send Alarm Filtered Actions", notes = "Send Alarm Filtered Actions")
	public ResponseEntity<Boolean> sendAlarmFilteredActions(@RequestBody Object alarmFiltered) {
		
		return new ResponseEntity<>(alarmFilteredBO.sendAlarmFilteredActions(alarmFiltered), HttpStatus.OK);
	}
	
	@PostMapping(value = "/send_alarms")
	@ApiOperation(value = "Send Alarms", notes = "Send Alarms")
	public ResponseEntity<Boolean> sendAlarms(@RequestBody Object alarms) {
		
		return new ResponseEntity<>(alarmBO.sendAlarms(alarms), HttpStatus.OK);
	}
	
	@PostMapping(value = "/send_recognize_alarm")
	@ApiOperation(value = "Send Recognize Alarm", notes = "Send Recognize Alarm")
	public ResponseEntity<Boolean> sendRecognizeAlarms(@RequestBody Object alarmRecognize) {
		
		return new ResponseEntity<>(alarmBO.sendRecognizeAlarms(alarmRecognize), HttpStatus.OK);
	}
	
	@PostMapping(value = "/send_deactivation_alarm")
	@ApiOperation(value = "Send Deactivation Alarm", notes = "Send Deactivation Alarm")
	public ResponseEntity<Boolean> sendDeactivationAlarms(@RequestBody Object alarmDeactivation) {
		
		return new ResponseEntity<>(alarmBO.sendDeactivationAlarms(alarmDeactivation), HttpStatus.OK);
	}
}
