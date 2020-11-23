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

import com.revenga.rits.front.openits.bo.ImsExtIncidentBO;
import com.revenga.rits.front.openits.bo.ImsExtIncidentTypeBO;
import com.revenga.rits.front.openits.bo.ImsIncidentBO;
import com.revenga.rits.front.openits.bo.ImsIncidentDocumentBO;
import com.revenga.rits.front.openits.bo.ImsIncidentDocumentTypeBO;
import com.revenga.rits.front.openits.bo.ImsIncidentSourceBO;
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
	
	@Autowired
	ImsIncidentSourceBO imsIncidentSourceBO;
	
	@Autowired
	ImsExtIncidentBO imsExtIncidentBO;
	
	@Autowired
	ImsExtIncidentTypeBO imsExtIncidentTypeBO;
	
	@Autowired
	ImsIncidentDocumentBO imsIncidentDocumentBO;
	
	@Autowired
	ImsIncidentDocumentTypeBO imsIncidentDocumentTypeBO;
	
	@GetMapping
	@ApiOperation(value = "Get all incidents", notes = "Get all incidents")
	public ResponseEntity<Object> getIncidents() {
		
		return new ResponseEntity<>(imsIncidentBO.getList(), HttpStatus.OK);
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
		
		return new ResponseEntity<>(imsIncidentBO.getItem(id), HttpStatus.OK);
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
		
		return new ResponseEntity<>(imsIncidentTypeBO.getList(), HttpStatus.OK);
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
		
		return new ResponseEntity<>(imsIncidentTypeBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentTypeStates")
	@ApiOperation(value = "Get incidentTypeStates", notes = "Get incidentTypeStates")
	public ResponseEntity<Object> getIncidentTypeStates() {
		
		return new ResponseEntity<>(imsIncidentTypeStateBO.getList(), HttpStatus.OK);
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
	
	@GetMapping(value = "/incidentsSource")
	@ApiOperation(value = "Get incidents source", notes = "Get incidents source")
	public ResponseEntity<Object> getIncidentsSource() {
		
		return new ResponseEntity<>(imsIncidentSourceBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentSource/{id}")
	@ApiOperation(value = "Get incident source by id", notes = "Get incident source by id")
	public ResponseEntity<Object> getIncidentSource(
			@ApiParam(
			name = "id",
			type = "String",
			value = "IncidentSource by id",
			example = "ImsIncidentSource:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentSourceBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/extIncidents")
	@ApiOperation(value = "Get ext incidents", notes = "Get ext incidents")
	public ResponseEntity<Object> getExtIncidents() {
		
		return new ResponseEntity<>(imsExtIncidentBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/extIncident/{id}")
	@ApiOperation(value = "Get ext incident by id", notes = "Get ext incident by id")
	public ResponseEntity<Object> getExtIncident(
			@ApiParam(
			name = "id",
			type = "String",
			value = "ExtIncident by id",
			example = "ImsExtIncident:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsExtIncidentBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/extIncidentTypes")
	@ApiOperation(value = "Get ext incident types", notes = "Get ext incident types")
	public ResponseEntity<Object> getExtIncidentTypes() {
		
		return new ResponseEntity<>(imsExtIncidentTypeBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/extIncidentType/{id}")
	@ApiOperation(value = "Get ext incident type by id", notes = "Get ext incident type by id")
	public ResponseEntity<Object> getExtIncidentType(
			@ApiParam(
			name = "id",
			type = "String",
			value = "ExtIncidentType by id",
			example = "ImsExtIncidentType:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsExtIncidentTypeBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentDocuments")
	@ApiOperation(value = "Get incident documents", notes = "Get incident documents")
	public ResponseEntity<Object> getIncidenDocuments() {
		
		return new ResponseEntity<>(imsIncidentDocumentBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentDocument/{id}")
	@ApiOperation(value = "Get incident document by id", notes = "Get incident document by id")
	public ResponseEntity<Object> getIncidenDocument(
			@ApiParam(
			name = "id",
			type = "String",
			value = "ImsIncidentDocument by id",
			example = "ImsIncidentDocument:1:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentDocumentBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentDocumentTypes")
	@ApiOperation(value = "Get incident document types", notes = "Get incident document types")
	public ResponseEntity<Object> getIncidenDocumentTypes() {
		
		return new ResponseEntity<>(imsIncidentDocumentTypeBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentDocumentType/{id}")
	@ApiOperation(value = "Get incident document type by id", notes = "Get incident type document by id")
	public ResponseEntity<Object> getIncidenDocumentType(
			@ApiParam(
			name = "id",
			type = "String",
			value = "ImsIncidentDocumentType by id",
			example = "ImsIncidentDocumentType:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentDocumentTypeBO.getItem(id), HttpStatus.OK);
	}

}
