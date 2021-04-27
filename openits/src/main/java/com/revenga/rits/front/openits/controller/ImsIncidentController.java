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
import com.revenga.rits.front.openits.bo.ImsIncidentGroupBO;
import com.revenga.rits.front.openits.bo.ImsIncidentLevelBO;
import com.revenga.rits.front.openits.bo.ImsIncidentReportBO;
import com.revenga.rits.front.openits.bo.ImsIncidentStateBO;
import com.revenga.rits.front.openits.bo.ImsIncidentTaskPlanBO;
import com.revenga.rits.front.openits.bo.ImsIncidentTypeTaskBO;
import com.revenga.rits.front.openits.bo.ImsTaskTypeBO;
import com.revenga.rits.front.openits.bo.ImsIncidentTypeBO;

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
	
	@Autowired
	ImsIncidentGroupBO imsIncidentGroupBO;
	
	@Autowired
	ImsIncidentTypeBO imsIncidentTypeBO;	
	
	@GetMapping(value = "/reports")
	@ApiOperation(value = "Get all incident reports", notes = "Get all incident reports")
	public ResponseEntity<Object> getImsIncidentReports() {
		
		return new ResponseEntity<>(imsIncidentReportBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/reports/{id}")
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
	
	@GetMapping(value = "/summary/{id}")
	@ApiOperation(value = "Get summary of incident report by id", notes = "Get summary of incident report by id")
	public ResponseEntity<Object> getImsIncidentReportSummary(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Incident report id",
			example = "ImsIncidentReport:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentReportBO.getItemSumary(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/reports/create")
	@ApiOperation(value = "Create Incident Report", notes = "Create Incident Report")
	public ResponseEntity<Object> createIncidentReport(@RequestBody Object report) {
		
		return new ResponseEntity<>(imsIncidentReportBO.create(report), HttpStatus.OK);
	}
	
	@PostMapping(value = "/reports/{id}/confirm")
	@ApiOperation(value = "Confirm Incident Report", notes = "Confirm Incident Report")
	public ResponseEntity<Boolean> confirmIncidentReport(@ApiParam(
			name = "id",
			type = "String",
			value = "Incident report id",
			example = "ImsIncidentReport:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentReportBO.confirm(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/reports/{id}/forward")
	@ApiOperation(value = "Forward Incident Report", notes = "Forward Incident Report")
	public ResponseEntity<Boolean> forwardIncidentReport(@ApiParam(
			name = "id",
			type = "String",
			value = "Incident report id",
			example = "ImsIncidentReport:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentReportBO.forward(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/reports/{id}/cancel")
	@ApiOperation(value = "Cancel Incident Report", notes = "Cancel Incident Report")
	public ResponseEntity<Object> cancelIncidentReport(@ApiParam(
			name = "id",
			type = "String",
			value = "Incident report id",
			example = "ImsIncidentReport:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentReportBO.cancel(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/reports/{id}/user/{userId}")
	@ApiOperation(value = "Assign user Incident Report", notes = "Assing user Incident Report")
	public ResponseEntity<Object> assignUserIncidentReport(
			@ApiParam(
					name = "id",
					type = "String",
					value = "Incident report id",
					example = "ImsIncidentReport:1",
					required = true) @PathVariable String id,
			@ApiParam(
					name = "userId",
					type = "String",
					value = "User id",
					example = "User:1",
					required = true) @PathVariable String userId) {
		
		return new ResponseEntity<>(imsIncidentReportBO.assignUser(id, userId), HttpStatus.OK);
	}
	

	@PostMapping(value = "/reports/{id}/cause/{causeId}")
	@ApiOperation(value = "Assign cause Incident Report", notes = "Assing cause Incident Report")
	public ResponseEntity<Object> assignCauseIncidentReport(
			@ApiParam(
					name = "id",
					type = "String",
					value = "Incident report id",
					example = "ImsIncidentReport:1",
					required = true) @PathVariable String id,
			@ApiParam(
					name = "causeId",
					type = "String",
					value = "Cause id",
					example = "ImsIncidentTypeCause:1:1",
					required = true) @PathVariable String causeId) {
		
		return new ResponseEntity<>(imsIncidentReportBO.assignCause(id, causeId), HttpStatus.OK);
	}
	
	@PostMapping(value = "/reports/{id}/finalize_task/{taskId}")
	@ApiOperation(value = "Finalize task Incident Report", notes = "Finalize task Incident Report")
	public ResponseEntity<Object> finalizeTaskIncidentReport(
			@ApiParam(
				name = "id",
				type = "String",
				value = "Incident report id",
				example = "ImsIncidentReport:1",
				required = true) @PathVariable String id,
			@ApiParam(
				name = "taskId",
				type = "String",
				value = "Incident type task",
				example = "ImsIncidentTypeTask:701",
				required = true) @PathVariable String taskId) {
		
		return new ResponseEntity<>(imsIncidentReportBO.finalizeTask(id, taskId), HttpStatus.OK);
	}
	
	@PostMapping(value = "/reports/{id}/execute_task_plan/{taskPlanId}")
	@ApiOperation(value = "Finalize task Incident Report", notes = "Finalize task Incident Report")
	public ResponseEntity<Object> executeTaskPlanIncidentReport(
			@ApiParam(
					name = "id",
					type = "String",
					value = "Incident report id",
					example = "ImsIncidentReport:1",
					required = true) @PathVariable String id,
			@ApiParam(
				name = "taskPlanId",
				type = "String",
				value = "",
				example = "",
				required = true) @PathVariable String taskPlanId) {
		
		return new ResponseEntity<>(imsIncidentReportBO.executeTaskPlan(id, taskPlanId), HttpStatus.OK);
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
	
	@GetMapping(value = "/imsIncidentGroups")
	@ApiOperation(value = "Get incident groups", notes = "Get all incident groups")
	public ResponseEntity<Object> getImsIncidentGroups() {
		
		return new ResponseEntity<>(imsIncidentGroupBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/imsIncidentGroups/{id}")
	@ApiOperation(value = "Get incident group by id", notes = "Get incident group by id")
	public ResponseEntity<Object> getImsIncidentGroup(
			@ApiParam(
			name = "id",
			type = "String",
			value = "IncidentGroup by id",
			example = "ImsIncidentGroup:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentGroupBO.getItem(id), HttpStatus.OK);
	}

	@GetMapping(value = "/imsIncidentTypes")
	@ApiOperation(value = "Get ext incident types", notes = "Get ext incident types")
	public ResponseEntity<Object> getImsIncidentTypes() {
		
		return new ResponseEntity<>(imsIncidentTypeBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/imsIncidentType/{id}")
	@ApiOperation(value = "Get ext incident type by id", notes = "Get ext incident type by id")
	public ResponseEntity<Object> getImsIncidentType(
			@ApiParam(
			name = "id",
			type = "String",
			value = "ExtIncidentType by id",
			example = "ImsIncidentType:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentTypeBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/stretch/{id}")
	@ApiOperation(value = "Get incident types by stretch id", notes = "Get incident types by stretch id")
	public ResponseEntity<Object> getImsIncidentTypesByStretchId(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Stretch by id",
			example = "Stretch:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentTypeBO.getListByStretch(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/add_log_incident_report")
	@ApiOperation(value = "Add log in incident report", notes = "Add log in incident report")
	public ResponseEntity<Boolean> addLogIncidentReport(@RequestBody Object incidentReportLogs) {
		
		return new ResponseEntity<>(imsIncidentReportBO.addLog(incidentReportLogs), HttpStatus.OK);
	}


	@PostMapping(value = "/delete_log_incident_report/{id}")
	@ApiOperation(value = "Delete log incident report", notes = "Delete log incident report")
	public ResponseEntity<Boolean> deleteLogIncidentReport(@ApiParam(
			name = "id",
			type = "String",
			value = "Incident report log id",
			example = "ImsIncidentReportLog:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentReportBO.deleteLog(id), HttpStatus.OK);
	}

}
