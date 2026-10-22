package com.revenga.rits.front.openits.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openits.bo.SmThresholdBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/smThreshold", produces = { "application/json", "application/xml" })
@Api(value = "SmThreshold", tags = { "STATES MACHINE THRESHOLDS" })
public class SmThresholdController {
	
	@Autowired
	SmThresholdBO smThresholdBO;
	
	@GetMapping
	@ApiOperation(value = "Get all state machine thresholds", notes = "Get all state machine thresholds")	
	public ResponseEntity<Object> getSmThresholds() {
		
		return new ResponseEntity<>(smThresholdBO.getSmThresholds(null), HttpStatus.OK);
	}

	@GetMapping(value = "/stateMachineIds")
	@ApiOperation(value = "Get all state machines", notes = "Get all state machines")	
	public ResponseEntity<Object> getSmStateMachinesThresholds() {
		
		return new ResponseEntity<>(smThresholdBO.getSmStateMachines(null), HttpStatus.OK);
	}
	
	@GetMapping(value = "/stateMachineIds/{id}")
	@ApiOperation(value = "Get threshold by state machine id", notes = "Get threshold by state machine id")	
	public ResponseEntity<Object> getSmThresholdsByStateMachineId(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Threshold by state machine id", 
			example = "SmStateMachine:1000",
			required = false) @PathVariable String id) {
		
		return new ResponseEntity<>(smThresholdBO.getSmStateMachines(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get threshold by state machine id", notes = "Get threshold by state machine id")	
	public ResponseEntity<Object> getSmThresholdsByStateMachine(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Threshold by state machine id", 
			example = "SmStateMachine:1000",
			required = false) @PathVariable String id) {
		
		return new ResponseEntity<>(smThresholdBO.getSmThresholds(id), HttpStatus.OK);
	}
	
	@PutMapping(value = "/updateSmThresholds")
	@ApiOperation(value = "Update threshold of state machine", notes = "Update threshold of state machine")
	public ResponseEntity<Object> updateSmThresholds(@RequestBody Object smThresholds) {
		
		return new ResponseEntity<>(smThresholdBO.updateSmThresholds(smThresholds), HttpStatus.OK);
	}
}