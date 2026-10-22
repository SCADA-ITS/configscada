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

import com.revenga.rits.front.openits.bo.ImsExtIncidentBO;
import com.revenga.rits.front.openits.bo.ImsExtIncidentTypeBO;
import com.revenga.rits.front.openits.bo.ImsIncidentAlarmConditionBO;
import com.revenga.rits.front.openits.bo.ImsIncidentGroupBO;
import com.revenga.rits.front.openits.bo.ImsIncidentLevelBO;
import com.revenga.rits.front.openits.bo.ImsIncidentReportBO;
import com.revenga.rits.front.openits.bo.ImsIncidentStateBO;
import com.revenga.rits.front.openits.bo.ImsIncidentTaskPlanBO;
import com.revenga.rits.front.openits.bo.ImsIncidentTypeBO;
import com.revenga.rits.front.openits.bo.ImsIncidentTypeStateTransitionBO;
import com.revenga.rits.front.openits.bo.ImsIncidentTypeStretchBO;
import com.revenga.rits.front.openits.bo.ImsIncidentTypeTaskBO;
import com.revenga.rits.front.openits.bo.ImsTaskTypeBO;

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
	
	@Autowired
	ImsIncidentTypeStretchBO imsIncidentTypeStretchBO;
	
	@Autowired
	ImsIncidentAlarmConditionBO imsIncidentAlarmConditionBO;
	
	@Autowired
	ImsIncidentTypeStateTransitionBO imsIncidentTypeStateTransitionBO;

	@GetMapping(value = "/reports")
	@ApiOperation(value = "Get all incident reports", notes = "Get all incident reports")
	public ResponseEntity<Object> getImsIncidentReports() {

		return new ResponseEntity<>(imsIncidentReportBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/reports/summary")
	@ApiOperation(value = "Get all plans summary", notes = "Get all plans summary")
	public ResponseEntity<Object> getImsIncidentReportsSummary() {
		
		return new ResponseEntity<>(imsIncidentReportBO.getSummaryList(), HttpStatus.OK);
	}

	@GetMapping(value = "/reports/{id}")
	@ApiOperation(value = "Get incident report by id", notes = "Get incident report by id")
	public ResponseEntity<Object> getImsIncidentReport(
			@ApiParam(name = "id", type = "String", value = "Incident report id", example = "ImsIncidentReport:1", required = true) @PathVariable String id) {

		return new ResponseEntity<>(imsIncidentReportBO.getItem(id), HttpStatus.OK);
	}

	@GetMapping(value = "/reports/{id}/summary")
	@ApiOperation(value = "Get summary of incident report by id", notes = "Get summary of incident report by id")
	public ResponseEntity<Object> getImsIncidentReportSummary(
			@ApiParam(name = "id", type = "String", value = "Incident report id", example = "ImsIncidentReport:1", required = true) @PathVariable String id) {

		return new ResponseEntity<>(imsIncidentReportBO.getItemSumary(id), HttpStatus.OK);
	}

	@PostMapping(value = "/reports/create")
	@ApiOperation(value = "Create Incident Report", notes = "Create Incident Report")
	public ResponseEntity<Object> createIncidentReport(@RequestBody Object report) {

		return new ResponseEntity<>(imsIncidentReportBO.create(report), HttpStatus.OK);
	}

	@PostMapping(value = "/reports/{id}/confirm")
	@ApiOperation(value = "Confirm Incident Report", notes = "Confirm Incident Report")
	public ResponseEntity<Boolean> confirmIncidentReport(
			@ApiParam(name = "id", type = "String", value = "Incident report id", example = "ImsIncidentReport:1", required = true) @PathVariable String id) {

		return new ResponseEntity<>(imsIncidentReportBO.confirm(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/reports/{id}/confirm/{userId}")
	@ApiOperation(value = "Confirm Incident Report with user", notes = "Confirm Incident Report with user")
	public ResponseEntity<Boolean> confirmIncidentReportWithUser(
			@ApiParam(name = "id", type = "String", value = "Incident report id", example = "ImsIncidentReport:1", required = true) @PathVariable String id,
			@ApiParam(name = "userId", type = "String", value = "User id", example = "User:1", required = true) @PathVariable String userId) {

		return new ResponseEntity<>(imsIncidentReportBO.confirm(id, userId), HttpStatus.OK);
	}

	@PostMapping(value = "/reports/{id}/forward")
	@ApiOperation(value = "Forward Incident Report", notes = "Forward Incident Report")
	public ResponseEntity<Boolean> forwardIncidentReport(
			@ApiParam(name = "id", type = "String", value = "Incident report id", example = "ImsIncidentReport:1", required = true) @PathVariable String id) {

		return new ResponseEntity<>(imsIncidentReportBO.forward(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/reports/{id}/backward")
	@ApiOperation(value = "Backward Incident Report", notes = "Backward Incident Report")
	public ResponseEntity<Boolean> backwardIncidentReport(
			@ApiParam(name = "id", type = "String", value = "Incident report id", example = "ImsIncidentReport:1", required = true) @PathVariable String id) {

		return new ResponseEntity<>(imsIncidentReportBO.backward(id), HttpStatus.OK);
	}

	@PostMapping(value = "/reports/{id}/cancel")
	@ApiOperation(value = "Cancel Incident Report", notes = "Cancel Incident Report")
	public ResponseEntity<Object> cancelIncidentReport(
			@ApiParam(name = "id", type = "String", value = "Incident report id", example = "ImsIncidentReport:1", required = true) @PathVariable String id) {

		return new ResponseEntity<>(imsIncidentReportBO.cancel(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/reports/{id}/cancel/{userId}")
	@ApiOperation(value = "Cancel Incident Report with user", notes = "Cancel Incident Report with user")
	public ResponseEntity<Object> cancelIncidentReportWithUser(
			@ApiParam(name = "id", type = "String", value = "Incident report id", example = "ImsIncidentReport:1", required = true) @PathVariable String id,
			@ApiParam(name = "userId", type = "String", value = "User id", example = "User:1", required = true) @PathVariable String userId) {

		return new ResponseEntity<>(imsIncidentReportBO.cancel(id, userId), HttpStatus.OK);
	}

	@PostMapping(value = "/reports/{id}/user/{userId}")
	@ApiOperation(value = "Assign user Incident Report", notes = "Assing user Incident Report")
	public ResponseEntity<Object> assignUserIncidentReport(
			@ApiParam(name = "id", type = "String", value = "Incident report id", example = "ImsIncidentReport:1", required = true) @PathVariable String id,
			@ApiParam(name = "userId", type = "String", value = "User id", example = "User:1", required = true) @PathVariable String userId) {

		return new ResponseEntity<>(imsIncidentReportBO.assignUser(id, userId), HttpStatus.OK);
	}

	@PostMapping(value = "/reports/{id}/cause/{causeId}")
	@ApiOperation(value = "Assign cause Incident Report", notes = "Assing cause Incident Report")
	public ResponseEntity<Object> assignCauseIncidentReport(
			@ApiParam(name = "id", type = "String", value = "Incident report id", example = "ImsIncidentReport:1", required = true) @PathVariable String id,
			@ApiParam(name = "causeId", type = "String", value = "Cause id", example = "ImsIncidentTypeCause:1:1", required = true) @PathVariable String causeId) {

		return new ResponseEntity<>(imsIncidentReportBO.assignCause(id, causeId), HttpStatus.OK);
	}
	
	@PostMapping(value = "/reports/{id}/road_impact/{roadImpactId}")
	@ApiOperation(value = "Change road impact Incident Report", notes = "Change road impact Incident Report")
	public ResponseEntity<Object> changeRoadImpactIncidentReport(
			@ApiParam(name = "id", type = "String", value = "Incident report id", example = "ImsIncidentReport:1", required = true) @PathVariable String id,
			@ApiParam(name = "causeId", type = "String", value = "Cause id", example = "RoadImpact:1", required = true) @PathVariable String roadImpactId) {

		return new ResponseEntity<>(imsIncidentReportBO.changeRoadImpact(id, roadImpactId), HttpStatus.OK);
	}

	@PostMapping(value = "/reports/{id}/finalize_task/{taskId}")
	@ApiOperation(value = "Finalize task Incident Report", notes = "Finalize task Incident Report")
	public ResponseEntity<Object> finalizeTaskIncidentReport(
			@ApiParam(name = "id", type = "String", value = "Incident report id", example = "ImsIncidentReport:1", required = true) @PathVariable String id,
			@ApiParam(name = "taskId", type = "String", value = "Incident type task", example = "ImsIncidentTypeTask:701", required = true) @PathVariable String taskId,
			@RequestBody Object incidentTypeTaskValues) {

		return new ResponseEntity<>(imsIncidentReportBO.finalizeTask(id, taskId, incidentTypeTaskValues), HttpStatus.OK);
	}

	@PostMapping(value = "/reports/{id}/execute_task_plan/{taskPlanId}")
	@ApiOperation(value = "Finalize task Incident Report", notes = "Finalize task Incident Report")
	public ResponseEntity<Object> executeTaskPlanIncidentReport(
			@ApiParam(name = "id", type = "String", value = "Incident report id", example = "ImsIncidentReport:1", required = true) @PathVariable String id,
			@ApiParam(name = "taskPlanId", type = "String", value = "", example = "", required = true) @PathVariable String taskPlanId) {

		return new ResponseEntity<>(imsIncidentReportBO.executeTaskPlan(id, taskPlanId), HttpStatus.OK);
	}

	@PostMapping(value = "/reports/{id}/location/{locationId}/affection_stretch/{stretchId}/type/{incidentTypeId}/road_impact/{roadImpactId}")
	@ApiOperation(value = "Change location Incident Report", notes = "Change location in Incident Report")
	public ResponseEntity<Object> changeIncidentReport(
			@ApiParam(
					name = "id",
					type = "String",
					value = "Incident report id",
					example = "ImsIncidentReport:1",
					required = true) @PathVariable String id,
			@ApiParam(
				name = "locationId",
				type = "String",
				value = "",
				example = "Location:1",
				required = true) @PathVariable String locationId,
			@ApiParam(
					name = "stretchId",
					type = "String",
					value = "",
					example = "Stretch:1",
					required = true) @PathVariable String stretchId,
			@ApiParam(
					name = "incidentTypeId",
					type = "String",
					value = "",
					example = "ImsIncidentType:1",
					required = true) @PathVariable String incidentTypeId,
			@ApiParam(
					name = "incidentTypeId",
					type = "String",
					value = "",
					example = "RoadImpact:1",
					required = true) @PathVariable String roadImpactId){
		
		return new ResponseEntity<>(imsIncidentReportBO.changeIncidentReport(id, locationId, stretchId, incidentTypeId, roadImpactId), HttpStatus.OK);
	}

	@GetMapping(value = "/incidentStates")
	@ApiOperation(value = "Get incident states", notes = "Get incident states")
	public ResponseEntity<Object> getImsIncidentStates() {

		return new ResponseEntity<>(imsIncidentStateBO.getList(), HttpStatus.OK);
	}

	@GetMapping(value = "/incidentState/{id}")
	@ApiOperation(value = "Get incident state by id", notes = "Get incident state by id")
	public ResponseEntity<Object> getImsIncidentState(
			@ApiParam(name = "id", type = "String", value = "Incident State by id", example = "ImsIncidentState:1", required = true) @PathVariable String id) {

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
			@ApiParam(name = "id", type = "String", value = "Incident Level by id", example = "ImsIncidentLevel:1", required = true) @PathVariable String id) {

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
			@ApiParam(name = "id", type = "String", value = "Task Type by id", example = "ImsTaskType:1", required = true) @PathVariable String id) {

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
			@ApiParam(name = "stretchId", type = "String", value = "Stretch id", example = "Stretch:1", required = true) @PathVariable String stretchId,
			@ApiParam(name = "incidentTypeId", type = "String", value = "Incident Type id", example = "ImsIncidentType:1", required = true) @PathVariable String incidentTypeId,
			@ApiParam(name = "incidentTypeTaskId", type = "String", value = "Incident Type Task id", example = "ImsIncidentTypeTask:1", required = true) @PathVariable String incidentTypeTaskId) {

		return new ResponseEntity<>(imsIncidentTaskPlanBO.getTaskPlansByStretchAndIncidentTypeAndIncidentTypeTask(
				stretchId, incidentTypeId, incidentTypeTaskId), HttpStatus.OK);
	}

	@GetMapping(value = "/incidentTypeTasks")
	@ApiOperation(value = "Get incident type tasks", notes = "Get incident type tasks")
	public ResponseEntity<Object> getAllImsIncidentTypeTasks() {

		return new ResponseEntity<>(imsIncidentTypeTaskBO.getList(), HttpStatus.OK);
	}

	@GetMapping(value = "/{incidentTypeId}/{incidentStateId}")
	@ApiOperation(value = "Get all incident type tasks by Incident Type and Incident State", notes = "Get all incident type tasks by Incident Type and Incident State")
	public ResponseEntity<Object> getIncidentTypeTaskByIncidentTypeAndIncidentState(
			@ApiParam(name = "incidentTypeId", type = "String", value = "Incident Type id", example = "ImsIncidentType:1", required = true) @PathVariable String incidentTypeId,
			@ApiParam(name = "incidentStateId", type = "String", value = "Incident State id", example = "ImsIncidentState:1", required = true) @PathVariable String incidentStateId) {

		return new ResponseEntity<>(imsIncidentTypeTaskBO
				.getIncidentTypeTaskByIncidentTypeAndIncidentState(incidentTypeId, incidentStateId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentTypeTasks/{incidentTypeId}")
	@ApiOperation(value = "Get all incident type tasks by Incident Type", notes = "Get all incident type tasks by Incident Type")
	public ResponseEntity<Object> getIncidentTypeTaskByIncidentType(
			@ApiParam(name = "incidentTypeId", type = "String", value = "Incident Type id", example = "ImsIncidentType:1", required = true) @PathVariable String incidentTypeId) {

		return new ResponseEntity<>(imsIncidentTypeTaskBO
				.getIncidentTypeTaskByIncidentTypeAndIncidentState(incidentTypeId, null), HttpStatus.OK);
	}

	@GetMapping(value = "/extIncidents")
	@ApiOperation(value = "Get ext incidents", notes = "Get ext incidents")
	public ResponseEntity<Object> getExtIncidents() {

		return new ResponseEntity<>(imsExtIncidentBO.getList(), HttpStatus.OK);
	}

	@GetMapping(value = "/extIncident/{id}")
	@ApiOperation(value = "Get ext incident by id", notes = "Get ext incident by id")
	public ResponseEntity<Object> getExtIncident(
			@ApiParam(name = "id", type = "String", value = "ExtIncident by id", example = "ImsExtIncident:1", required = true) @PathVariable String id) {

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
			@ApiParam(name = "id", type = "String", value = "ExtIncidentType by id", example = "ImsExtIncidentType:1", required = true) @PathVariable String id) {

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
			@ApiParam(name = "id", type = "String", value = "IncidentGroup by id", example = "ImsIncidentGroup:1", required = true) @PathVariable String id) {

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
			@ApiParam(name = "id", type = "String", value = "ExtIncidentType by id", example = "ImsIncidentType:1", required = true) @PathVariable String id) {

		return new ResponseEntity<>(imsIncidentTypeBO.getItem(id), HttpStatus.OK);
	}

	@GetMapping(value = "/stretch/{id}")
	@ApiOperation(value = "Get incident types by stretch id", notes = "Get incident types by stretch id")
	public ResponseEntity<Object> getImsIncidentTypesByStretchId(
			@ApiParam(name = "id", type = "String", value = "Stretch by id", example = "Stretch:1", required = true) @PathVariable String id) {

		return new ResponseEntity<>(imsIncidentTypeBO.getListByStretch(id), HttpStatus.OK);
	}

	@PostMapping(value = "/add_log_incident_report")
	@ApiOperation(value = "Add log in incident report", notes = "Add log in incident report")
	public ResponseEntity<Boolean> addLogIncidentReport(@RequestBody Object incidentReportLogs) {

		return new ResponseEntity<>(imsIncidentReportBO.addLog(incidentReportLogs), HttpStatus.OK);
	}

	@PostMapping(value = "/delete_log_incident_report/{id}")
	@ApiOperation(value = "Delete log incident report", notes = "Delete log incident report")
	public ResponseEntity<Boolean> deleteLogIncidentReport(
			@ApiParam(name = "id", type = "String", value = "Incident report log id", example = "ImsIncidentReportLog:1", required = true) @PathVariable String id) {

		return new ResponseEntity<>(imsIncidentReportBO.deleteLog(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentTypeStretchs")
	@ApiOperation(value = "Get all stretchs associated to incident types", notes = "Get incident type stretchs")
	public ResponseEntity<Object> getImsIncidentTypeStretchs() {

		return new ResponseEntity<>(imsIncidentTypeStretchBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/incidentTypeStretchs/{incidentTypeId}")
	@ApiOperation(value = "Get all stretchs associated to incident type", notes = "Get incident type stretchs")
	public ResponseEntity<Object> getImsIncidentTypeStretchsByIncidentType(
			@ApiParam(name = "incidentTypeId", type = "String", value = "Incident type id", example = "ImsIncidentType:34", required = true) @PathVariable String incidentTypeId) {

		return new ResponseEntity<>(imsIncidentTypeStretchBO.getListByIncidenType(incidentTypeId), HttpStatus.OK);
	}

	@GetMapping(value = "/incidentTypeStretchs/{incidentTypeId}/{stretchId}")
	@ApiOperation(value = "Get stretch associated to incident type", notes = "Get incident type stretch id")
	public ResponseEntity<Object> getImsIncidentTypeStretch(
			@ApiParam(name = "incidentTypeId", type = "String", value = "Incident type id", example = "ImsIncidentType:34", required = true) @PathVariable String incidentTypeId,
			@ApiParam(name = "stretchId", type = "String", value = "Stretch id", example = "Stretch:1000", required = true) @PathVariable String stretchId) {

		return new ResponseEntity<>(imsIncidentTypeStretchBO.getItem(incidentTypeId, stretchId), HttpStatus.OK);
	}
	
	@PostMapping(value = "/create")
	@ApiOperation(value = "Create Incident Type", notes = "Create Incident Type")
	public ResponseEntity<Object> createIncidentType(@RequestBody Object incidentType) {

		return new ResponseEntity<>(imsIncidentTypeBO.create(incidentType), HttpStatus.OK);
	}
	
	@PutMapping(value = "/update")
	@ApiOperation(value = "Update Incident Type", notes = "Update Incident Type")
	public ResponseEntity<Object> updateIncidentType(@RequestBody Object incidentType) {

		return new ResponseEntity<>(imsIncidentTypeBO.update(incidentType), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Delete Incident Type", notes = "Delete Incident Type")
	public ResponseEntity<Object> deleteIncidentType(@ApiParam(
			name = "id",
			type = "String",
			value = "Incident Type id",
			example = "ImsIncidentType:3",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentTypeBO.delete(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/incidentTypeStretchs/create")
	@ApiOperation(value = "Create Incident Type Stretch", notes = "Create Incident Type Stretch")
	public ResponseEntity<Object> createIncidentTypeStretch(@RequestBody Object incidentTypeStretch) {

		return new ResponseEntity<>(imsIncidentTypeStretchBO.create(incidentTypeStretch), HttpStatus.OK);
	}
	
	@PutMapping(value = "/incidentTypeStretchs/update")
	@ApiOperation(value = "Update Incident Type Stretch", notes = "Update Incident Type Stretch")
	public ResponseEntity<Object> updateIncidentTypeStretch(@RequestBody Object incidentTypeStretch) {

		return new ResponseEntity<>(imsIncidentTypeStretchBO.update(incidentTypeStretch), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/incidentTypeStretchs/{id}")
	@ApiOperation(value = "Delete Incident Type Stretch", notes = "Delete Incident Type Stretch")
	public ResponseEntity<Object> deleteIncidentTypeStretch(@ApiParam(
			name = "id",
			type = "String",
			value = "Incident Type Stretch id",
			example = "ImsIncidentTypeStretch:3:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentTypeStretchBO.delete(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/incidentAlarmConditions/create")
	@ApiOperation(value = "Create Incident Type Stretch", notes = "Create Incident Type Stretch")
	public ResponseEntity<Object> createIncidentAlarmCondition(@RequestBody Object incidentAlarmCondition) {

		return new ResponseEntity<>(imsIncidentAlarmConditionBO.create(incidentAlarmCondition), HttpStatus.OK);
	}
	
	@PutMapping(value = "/incidentAlarmConditions/update")
	@ApiOperation(value = "Update Incident Type Alarm conditions", notes = "Update Incident Type Alarm conditions")
	public ResponseEntity<Object> updateIncidentAlarmCondition(@RequestBody Object incidentAlarmCondition) {

		return new ResponseEntity<>(imsIncidentAlarmConditionBO.update(incidentAlarmCondition), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/incidentAlarmConditions/{id}")
	@ApiOperation(value = "Delete Incident Type Stretch", notes = "Delete Incident Type Stretch")
	public ResponseEntity<Object> deleteIncidentAlarmCondition(@ApiParam(
			name = "id",
			type = "String",
			value = "Incident Alarm Condition id",
			example = "ImsIncidentAlarmCondition:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentAlarmConditionBO.delete(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/incidentTypeTasks/create")
	@ApiOperation(value = "Create Incident Type Task", notes = "Create Incident Type Task")
	public ResponseEntity<Object> createIncidentAlarmTask(@RequestBody Object incidentTypeTask) {

		return new ResponseEntity<>(imsIncidentTypeTaskBO.create(incidentTypeTask), HttpStatus.OK);
	}
	
	@PutMapping(value = "/incidentTypeTasks/update")
	@ApiOperation(value = "Update Incident Type Task", notes = "Update Incident Type Task")
	public ResponseEntity<Object> updateIncidentAlarmTask(@RequestBody Object incidentTypeTask) {

		return new ResponseEntity<>(imsIncidentTypeTaskBO.update(incidentTypeTask), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/incidentTypeTasks/{id}")
	@ApiOperation(value = "Delete Incident Type Task", notes = "Delete Incident Type Task")
	public ResponseEntity<Object> deleteIncidentAlarmTask(@ApiParam(
			name = "id",
			type = "String",
			value = "Incident Type Task id",
			example = "ImsIncidentTypeTask:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentTypeTaskBO.delete(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/incidentTaskPlans/create")
	@ApiOperation(value = "Create Incident Task Plan", notes = "Create Incident Task Plan")
	public ResponseEntity<Object> createIncidentTaskPlan(@RequestBody Object incidentTaskPlan) {

		return new ResponseEntity<>(imsIncidentTaskPlanBO.create(incidentTaskPlan), HttpStatus.OK);
	}
	
	@PutMapping(value = "/incidentTaskPlans/update")
	@ApiOperation(value = "Update Incident Task Task", notes = "Update Incident Task Task")
	public ResponseEntity<Object> updateIncidentTaskPlan(@RequestBody Object incidentTaskPlan) {

		return new ResponseEntity<>(imsIncidentTaskPlanBO.update(incidentTaskPlan), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/incidentTaskPlans/{id}")
	@ApiOperation(value = "Delete Incident Task Plan", notes = "Delete Incident Task Plan")
	public ResponseEntity<Object> deleteIncidentTaskPlan(@ApiParam(
			name = "id",
			type = "String",
			value = "Incident Task Plan",
			example = "ImsIncidentTaskPlan:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentTaskPlanBO.delete(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/incidentTypeStateTransitions/create")
	@ApiOperation(value = "Create Incident Type State Transition", notes = "Create Type State Transition")
	public ResponseEntity<Object> createIncidentTypeStateTransition(@RequestBody Object incidentTypeStateTransition) {

		return new ResponseEntity<>(imsIncidentTypeStateTransitionBO.create(incidentTypeStateTransition), HttpStatus.OK);
	}
	
	@PutMapping(value = "/incidentTypeStateTransitions/update")
	@ApiOperation(value = "Update Type State Transition", notes = "Update Type State Transition")
	public ResponseEntity<Object> updateIncidentTypeStateTransition(@RequestBody Object incidentTypeStateTransition) {

		return new ResponseEntity<>(imsIncidentTypeStateTransitionBO.update(incidentTypeStateTransition), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/incidentTypeStateTransitions/{id}")
	@ApiOperation(value = "Delete Type State Transition", notes = "Delete Type State Transition")
	public ResponseEntity<Object> deleteIncidentTypeStateTransition(@ApiParam(
			name = "id",
			type = "String",
			value = "Incident Type State Transition",
			example = "ImsIncidentTypeStateTransition:1:2",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(imsIncidentTypeStateTransitionBO.delete(id), HttpStatus.OK);
	}
}
