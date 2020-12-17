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

import com.revenga.rits.front.openits.bo.ImsExtIncidentBO;
import com.revenga.rits.front.openits.bo.ImsExtIncidentTypeBO;
import com.revenga.rits.front.openits.bo.ImsIncidentLevelBO;
import com.revenga.rits.front.openits.bo.ImsIncidentReportBO;
import com.revenga.rits.front.openits.bo.ImsIncidentStateBO;
import com.revenga.rits.front.openits.bo.ImsIncidentTaskPlanBO;
import com.revenga.rits.front.openits.bo.ImsIncidentTypeTaskBO;
import com.revenga.rits.front.openits.bo.ImsTaskTypeBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/incidents", produces = { "application/json", "application/xml" })
@Api(value = "ImsIncidentReport", tags = { "INCIDENT REPORTS" })
public class ImsIncidentController {
	
	@Autowired
	ImsIncidentReportBO imsIncidentReportBO;
	
	@Autowired
	ImsIncidentStateBO imsIncidentStateBO;
	
	@Autowired
	ImsIncidentLevelBO imsIncidentLevelBO;
	
	@Autowired
	ImsTaskTypeBO imsTaskTypeBO;
	
	@Autowired
	ImsIncidentTaskPlanBO imsIncidentTaskPlanBO;
	
	@Autowired
	ImsIncidentTypeTaskBO imsIncidentTypeTaskBO;
			
	@Autowired
	ImsExtIncidentBO imsExtIncidentBO;
	
	@Autowired
	ImsExtIncidentTypeBO imsExtIncidentTypeBO;	
	
	@GetMapping
	@ApiOperation(value = "Get all incident reports", notes = "Get all incident reports")
	public ResponseEntity<Object> getImsIncidentReports() {
		
		return new ResponseEntity<>(imsIncidentReportBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get incident report by id", notes = "Get incident report by id")
	public ResponseEntity<Object> getImsIncidentReport(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Incident report id",
			example = "ImsIncidentReport:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentReportBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentStates")
	@ApiOperation(value = "Get incident states", notes = "Get incident states")
	public ResponseEntity<Object> getImsIncidentStates() {
		
		return new ResponseEntity<>(imsIncidentStateBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentState/{id}")
	@ApiOperation(value = "Get incident state by id", notes = "Get incident state by id")
	public ResponseEntity<Object> getImsIncidentState(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Incident State by id",
			example = "ImsIncidentState:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentStateBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentLevels")
	@ApiOperation(value = "Get incident levels", notes = "Get incident levels")
	public ResponseEntity<Object> getImsIncidentLevels() {
		
		return new ResponseEntity<>(imsIncidentLevelBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentLevel/{id}")
	@ApiOperation(value = "Get incident level by id", notes = "Get incident level by id")
	public ResponseEntity<Object> getImsIncidentLevel(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Incident Level by id",
			example = "ImsIncidentLevel:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentLevelBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/taskTypes")
	@ApiOperation(value = "Get task types", notes = "Get task types")
	public ResponseEntity<Object> getImsTaskTypes() {
		
		return new ResponseEntity<>(imsTaskTypeBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/taskType/{id}")
	@ApiOperation(value = "Get task type by id", notes = "Get task type by id")
	public ResponseEntity<Object> getImsTaskType(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Task Type by id",
			example = "ImsTaskType:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsTaskTypeBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentTaskPlans")
	@ApiOperation(value = "Get incident task plans", notes = "Get incident task plans")
	public ResponseEntity<Object> getAllImsTaskPlans() {
		
		return new ResponseEntity<>(imsIncidentTaskPlanBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{stretchId}/{incidentTypeId}/{incidentTypeTaskId}")
	@ApiOperation(value = "Get all task plans by Stretch, Incident Type and Incident Type Task", notes = "Get all task plans by Stretch, Incident Type and Incident Type Task")
	public ResponseEntity<Object> getTaskPlansByStretchAndIncidentTypeAndIncidentTypeTask(
			@ApiParam(
				name = "stretchId",
				type = "String",
				value = "Stretch id",
				example = "Stretch:1",
				required = true) @PathVariable String stretchId,
			@ApiParam(
				name = "incidentTypeId",
				type = "String",
				value = "Incident Type id",
				example = "ImsIncidentType:1",
				required = true) @PathVariable String incidentTypeId,
			@ApiParam(
				name = "incidentTypeTaskId",
				type = "String",
				value = "Incident Type Task id",
				example = "ImsIncidentTypeTask:1",
				required = true) @PathVariable String incidentTypeTaskId){
		
		return new ResponseEntity<>(imsIncidentTaskPlanBO.getTaskPlansByStretchAndIncidentTypeAndIncidentTypeTask(stretchId, incidentTypeId, incidentTypeTaskId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentTypeTasks")
	@ApiOperation(value = "Get incident type tasks", notes = "Get incident type tasks")
	public ResponseEntity<Object> getAllImsIncidentTypeTasks() {
		
		return new ResponseEntity<>(imsIncidentTypeTaskBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{incidentTypeId}/{incidentStateId}")
	@ApiOperation(value = "Get all incident type tasks by Incident Type and Incident State", notes = "Get all incident type tasks by Incident Type and Incident State")
	public ResponseEntity<Object> getIncidentTypeTaskByIncidentTypeAndIncidentState(
			@ApiParam(
				name = "incidentTypeId",
				type = "String",
				value = "Incident Type id",
				example = "ImsIncidentType:1",
				required = true) @PathVariable String incidentTypeId,
			@ApiParam(
				name = "incidentStateId",
				type = "String",
				value = "Incident State id",
				example = "ImsIncidentState:1",
				required = true) @PathVariable String incidentStateId){
		
		return new ResponseEntity<>(imsIncidentTypeTaskBO.getIncidentTypeTaskByIncidentTypeAndIncidentState(incidentTypeId, incidentStateId), HttpStatus.OK);
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

}
