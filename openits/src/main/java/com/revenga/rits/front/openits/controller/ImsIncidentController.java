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

import com.revenga.rits.front.openits.bo.ImsIncidentBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/incidents", produces = { "application/json", "application/xml" })
@Api(value = "ImsIncident", tags = { "INCIDENTS" })
public class ImsIncidentController {
	
	@Autowired
	ImsIncidentBO imsIncidentBO;
	
	@GetMapping
	@ApiOperation(value = "Get all incidents", notes = "Get all incidents")
	public ResponseEntity<Object> getIncidents() {
		
		return new ResponseEntity<>(imsIncidentBO.getIncidents(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get incident by id", notes = "Get incident by id")
	public ResponseEntity<Object> getIncident(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Incident id",
			example = "ImsIncident:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentBO.getIncident(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incident/incident_type/{id}")
	@ApiOperation(value = "Get incident by incident type id", notes = "Get incident by incident type id")
	public ResponseEntity<Object> getIncidentByType(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Incident by incident type id",
			example = "ImsIncidentType:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentBO.getIncidentByType(id), HttpStatus.OK);
	}

}
