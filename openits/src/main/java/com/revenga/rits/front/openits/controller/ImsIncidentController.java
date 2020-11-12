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
import com.revenga.rits.front.openits.bo.ImsIncidentTypeBO;
import com.revenga.rits.front.openits.bo.ImsIncidentTypeStateBO;
import com.revenga.rits.front.openits.bo.RoadImpactBO;

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
	
	@Autowired
	ImsIncidentTypeBO imsIncidentTypeBO;
	
	@Autowired
	ImsIncidentTypeStateBO imsIncidentTypeStateBO;
	
	@Autowired
	RoadImpactBO roadImpactBO;
	
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
	
	@GetMapping(value = "/incidentTypes")
	@ApiOperation(value = "Get incidentTypes", notes = "Get incidentTypes")
	public ResponseEntity<Object> getIncidentTypes() {
		
		return new ResponseEntity<>(imsIncidentTypeBO.getIncidentTypes(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentType/{id}")
	@ApiOperation(value = "Get incidentType by id", notes = "Get incidentType by id")
	public ResponseEntity<Object> getIncidentType(
			@ApiParam(
			name = "id",
			type = "String",
			value = "IncidentType by id",
			example = "ImsIncidentType:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentTypeBO.getIncidentType(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentTypeStates")
	@ApiOperation(value = "Get incidentTypeStates", notes = "Get incidentTypeStates")
	public ResponseEntity<Object> getIncidentTypeStates() {
		
		return new ResponseEntity<>(imsIncidentTypeStateBO.getIncidentTypeStates(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentNextState/incident/{id}")
	@ApiOperation(value = "Get nextState by incident id", notes = "Get nextState by incident id")
	public ResponseEntity<Object> getNextState(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Next state by incident id",
			example = "ImsIncident:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentTypeStateBO.getNextState(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/roadImpacts")
	@ApiOperation(value = "Get road impacts", notes = "Get road impacts")
	public ResponseEntity<Object> getRoadImpacts() {
		
		return new ResponseEntity<>(roadImpactBO.getRoadImpacts(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/roadImpact/{id}")
	@ApiOperation(value = "Get road impact by id", notes = "Get road impact by id")
	public ResponseEntity<Object> getRoadImpact(
			@ApiParam(
			name = "id",
			type = "String",
			value = "RoadImpact by id",
			example = "RoadImpact:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(roadImpactBO.getRoadImpact(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/initRoadImpacts/incidentType/{id}")
	@ApiOperation(value = "Get init road impact by incidentType id", notes = "Get init road impact by incidentType id")
	public ResponseEntity<Object> getInitRoadImpacts(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Init RoadImpact by incidentType id",
			example = "ImsIncidentType:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(roadImpactBO.getInitRoadImpacts(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/roadImpact/incident/{id}")
	@ApiOperation(value = "Get road impact by incident id", notes = "Get road impact by incident id")
	public ResponseEntity<Object> getRoadImpactsByIncident(
			@ApiParam(
			name = "id",
			type = "String",
			value = "RoadImpact by incident id",
			example = "ImsIncident:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(roadImpactBO.getRoadImpactsByIncident(id), HttpStatus.OK);
	}

}
