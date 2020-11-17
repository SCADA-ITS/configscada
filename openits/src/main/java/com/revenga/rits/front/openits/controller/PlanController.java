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

import com.revenga.rits.front.openits.bo.PlanBO;
import com.revenga.rits.front.openits.bo.PlanOrderTypeBO;
import com.revenga.rits.front.openits.bo.PlanScheduleBO;
import com.revenga.rits.front.openits.bo.PlanStateTypeBO;
import com.revenga.rits.front.openits.bo.PlanSubtypeBO;
import com.revenga.rits.front.openits.bo.PlanTypeBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/plans", produces = { "application/json", "application/xml" })
@Api(value = "Plan", tags = { "PLANS" })
public class PlanController {
	
	@Autowired
	PlanBO planBO;
	
	@Autowired
	PlanTypeBO planTypeBO;
	
	@Autowired
	PlanSubtypeBO planSubtypeBO;
	
	@Autowired
	PlanOrderTypeBO planOrderTypeBO;
	
	@Autowired
	PlanStateTypeBO planStateTypeBO;
	
	@Autowired
	PlanScheduleBO planScheduleBO;
	
	@GetMapping
	@ApiOperation(value = "Get all plans", notes = "Get all plans")
	public ResponseEntity<Object> getPlans() {
		
		return new ResponseEntity<>(planBO.getPlans(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get plan by id", notes = "Get plan by id")
	public ResponseEntity<Object> getPlan(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Plan by id",
			example = "Plan:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(planBO.getPlan(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/plan_types")
	@ApiOperation(value = "Get all plan types", notes = "Get all plan types")
	public ResponseEntity<Object> getPlanTypes() {
		
		return new ResponseEntity<>(planTypeBO.getPlanTypes(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/plan_types/{id}")
	@ApiOperation(value = "Get plan type by id", notes = "Get plan type by id")
	public ResponseEntity<Object> getPlanType(
			@ApiParam(
			name = "id",
			type = "String",
			value = "PlanType by id",
			example = "PlanType:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(planTypeBO.getPlanType(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/plan_subtypes")
	@ApiOperation(value = "Get all plan subtypes", notes = "Get all plan subtypes")
	public ResponseEntity<Object> getPlanSubtypes() {
		
		return new ResponseEntity<>(planSubtypeBO.getPlanSubtypes(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/plan_subtypes/{id}")
	@ApiOperation(value = "Get plan subtype by id", notes = "Get plan subtype by id")
	public ResponseEntity<Object> getPlanSubtype(
			@ApiParam(
			name = "id",
			type = "String",
			value = "PlanSubtype by id",
			example = "PlanSubtype:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(planSubtypeBO.getPlanSubtype(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/plan_order_types")
	@ApiOperation(value = "Get all plan order types", notes = "Get all plan order types")
	public ResponseEntity<Object> getPlanOrderTypes() {
		
		return new ResponseEntity<>(planOrderTypeBO.getPlanOrderTypes(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/plan_order_types/{id}")
	@ApiOperation(value = "Get plan order type by id", notes = "Get plan order type by id")
	public ResponseEntity<Object> getPlanOrderType(
			@ApiParam(
			name = "id",
			type = "String",
			value = "PlanOrderType by id",
			example = "PlanOrderType:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(planOrderTypeBO.getPlanOrderType(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/plan_state_types")
	@ApiOperation(value = "Get all plan state types", notes = "Get all plan state types")
	public ResponseEntity<Object> getPlanStateTypes() {
		
		return new ResponseEntity<>(planStateTypeBO.getPlanStateTypes(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/plan_order_state_types")
	@ApiOperation(value = "Get all plan order state types", notes = "Get all plan order state types")
	public ResponseEntity<Object> getPlanOrderStateTypes() {
		
		return new ResponseEntity<>(planStateTypeBO.getPlanOrderStateTypes(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/plan_schedule_state_types")
	@ApiOperation(value = "Get all plan_schedule state types", notes = "Get all plan_schedule state types")
	public ResponseEntity<Object> getPlanScheduleStateTypes() {
		
		return new ResponseEntity<>(planStateTypeBO.getPlanScheduleStateTypes(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/plan_schedules")
	@ApiOperation(value = "Get all plan schedules", notes = "Get all plan schedules")
	public ResponseEntity<Object> getPlanSchedules() {
		
		return new ResponseEntity<>(planScheduleBO.getPlanSchedules(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/plan_schedules/{id}")
	@ApiOperation(value = "Get plan schedule by id", notes = "Get plan schedule by id")
	public ResponseEntity<Object> getPlanSchedule(
			@ApiParam(
			name = "id",
			type = "String",
			value = "PlanSchedule by id",
			example = "PlanSchedules:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(planScheduleBO.getPlanSchedule(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/plan_schedules/plan/{id}")
	@ApiOperation(value = "Get plan schedule by plan id", notes = "Get plan schedule by plan id")
	public ResponseEntity<Object> getPlanScheduleByPlan(
			@ApiParam(
			name = "id",
			type = "String",
			value = "PlanSchedule  by plan id",
			example = "Plan:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(planScheduleBO.getPlanScheduleByPlan(id), HttpStatus.OK);
	}

}
