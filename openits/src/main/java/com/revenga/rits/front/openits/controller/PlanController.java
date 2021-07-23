package com.revenga.rits.front.openits.controller;

import java.util.List;
import java.util.Map;

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

import com.revenga.rits.front.openits.bo.PlanBO;
import com.revenga.rits.front.openits.bo.PlanOrderBO;
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
	
	@Autowired
	PlanOrderBO planOrderBO;
	
	@GetMapping
	@ApiOperation(value = "Get all plans", notes = "Get all plans")
	public ResponseEntity<Object> getPlans() {
		
		return new ResponseEntity<>(planBO.getList(), HttpStatus.OK);
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
		
		return new ResponseEntity<>(planBO.getItem(id), HttpStatus.OK);
	}

	@GetMapping(value = "/summary")
	@ApiOperation(value = "Get all plans summary", notes = "Get all plans summary")
	public ResponseEntity<Object> getPlansSummary() {
		
		return new ResponseEntity<>(planBO.getSummaryList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/plan_types")
	@ApiOperation(value = "Get all plan types", notes = "Get all plan types")
	public ResponseEntity<Object> getPlanTypes() {
		
		return new ResponseEntity<>(planTypeBO.getList(), HttpStatus.OK);
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
		
		return new ResponseEntity<>(planTypeBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/plan_subtypes")
	@ApiOperation(value = "Get all plan subtypes", notes = "Get all plan subtypes")
	public ResponseEntity<Object> getPlanSubtypes() {
		
		return new ResponseEntity<>(planSubtypeBO.getList(), HttpStatus.OK);
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
		
		return new ResponseEntity<>(planSubtypeBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/plan_order_types")
	@ApiOperation(value = "Get all plan order types", notes = "Get all plan order types")
	public ResponseEntity<Object> getPlanOrderTypes() {
		
		return new ResponseEntity<>(planOrderTypeBO.getList(), HttpStatus.OK);
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
		
		return new ResponseEntity<>(planOrderTypeBO.getItem(id), HttpStatus.OK);
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
		
		return new ResponseEntity<>(planScheduleBO.getList(), HttpStatus.OK);
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
		
		return new ResponseEntity<>(planScheduleBO.getItem(id), HttpStatus.OK);
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
	
	@GetMapping(value = "/plan_orders")
	@ApiOperation(value = "Get all plan orders", notes = "Get all plan orders")
	public ResponseEntity<Object> getPlanOrders() {
		
		return new ResponseEntity<>(planOrderBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/plan_orders/{id}")
	@ApiOperation(value = "Get plan order by id", notes = "Get plan order by id")
	public ResponseEntity<Object> getPlanOrder(
			@ApiParam(
			name = "id",
			type = "String",
			value = "PlanOrder by id",
			example = "PlanOrder:1:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(planOrderBO.getItem(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/createPlan")
	@ApiOperation(value = "Create Plan", notes = "Create Plan")
	public ResponseEntity<Object> createPlan(@RequestBody Object plan) {
		
		return new ResponseEntity<>(planBO.createPlan(plan), HttpStatus.OK);
	}
	
	@PutMapping(value = "/updatePlan")
	@ApiOperation(value = "Update Plan", notes = "Update Plan")
	public ResponseEntity<Object> updatePlan(@RequestBody Object plan) {
		
		return new ResponseEntity<>(planBO.updatePlan(plan), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deletePlan/{id}")
	@ApiOperation(value = "Delete Plan", notes = "Delete Plan")
	public ResponseEntity<Object> deletePlan(@ApiParam(
			name = "id",
			type = "String",
			value = "Plan id",
			example = "Plan:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(planBO.deletePlan(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/createPlanOrder")
	@ApiOperation(value = "Create Plan Order", notes = "Create Plan Order")
	public ResponseEntity<Object> createPlanOrder(@RequestBody Object planOrder) {
		
		return new ResponseEntity<>(planOrderBO.createPlanOrder(planOrder), HttpStatus.OK);
	}
	
	@PutMapping(value = "/updatePlanOrder")
	@ApiOperation(value = "Update Plan Order", notes = "Update Plan Order")
	public ResponseEntity<Object> updatePlanOrder(@RequestBody Object planOrder) {
		
		return new ResponseEntity<>(planOrderBO.updatePlanOrder(planOrder), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deletePlanOrder/{id}")
	@ApiOperation(value = "Delete Plan Order", notes = "Delete Plan Order")
	public ResponseEntity<Object> deletePlanOrder(@ApiParam(
			name = "id",
			type = "String",
			value = "PlanOrder id",
			example = "PlanOrder:1:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(planOrderBO.deletePlanOrder(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/createPlanType")
	@ApiOperation(value = "Create Plan Type", notes = "Create Plan Type")
	public ResponseEntity<Object> createPlanType(@RequestBody Object planType) {
		
		return new ResponseEntity<>(planTypeBO.createPlanType(planType), HttpStatus.OK);
	}
	
	@PutMapping(value = "/updatePlanType")
	@ApiOperation(value = "Update Plan Type", notes = "Update Plan Type")
	public ResponseEntity<Object> updatePlanType(@RequestBody Object planType) {
		
		return new ResponseEntity<>(planTypeBO.updatePlanType(planType), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deletePlanType/{id}")
	@ApiOperation(value = "Delete Plan Type", notes = "Delete Plan Type")
	public ResponseEntity<Object> deletePlanType(@ApiParam(
			name = "id",
			type = "String",
			value = "PlanType id",
			example = "PlanType:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(planTypeBO.deletePlanType(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/createPlanSubtype")
	@ApiOperation(value = "Create Plan Subtype", notes = "Create Plan Subtype")
	public ResponseEntity<Object> createPlanSubtype(@RequestBody Object planSubtype) {
		
		return new ResponseEntity<>(planSubtypeBO.createPlanSubtype(planSubtype), HttpStatus.OK);
	}
	
	@PutMapping(value = "/updatePlanSubtype")
	@ApiOperation(value = "Update Plan Subtype", notes = "Update Plan Subtype")
	public ResponseEntity<Object> updatePlanSubtype(@RequestBody Object planSubtype) {
		
		return new ResponseEntity<>(planSubtypeBO.updatePlanSubtype(planSubtype), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deletePlanSubtype/{id}")
	@ApiOperation(value = "Delete Plan Subtype", notes = "Delete Plan Subtype")
	public ResponseEntity<Object> deletePlanSubtype(@ApiParam(
			name = "id",
			type = "String",
			value = "PlanSubtype id",
			example = "PlanSubtype:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(planSubtypeBO.deletePlanSubtype(id), HttpStatus.OK);
	}

	@PostMapping(value = "/send_plan_schedules")
	@ApiOperation(value = "Send Plan Schedules", notes = "Send Plan Schedules")
	public ResponseEntity<Object> sendPlanScheduleActions(@RequestBody Object planSchedule) {
		
		return new ResponseEntity<>(planScheduleBO.sendPlanScheduleActions(planSchedule), HttpStatus.OK);
	}
	
	@PostMapping(value = "/send_play_plan")
	@ApiOperation(value = "Send Play Plan", notes = "Send Play Plan")
	public ResponseEntity<Boolean> sendPlayPlan(@RequestBody Object planIds) {
		
		return new ResponseEntity<>(planBO.sendPlayPlan(planIds), HttpStatus.OK);
	}

	@PostMapping(value = "/send_execute_plan")
	@ApiOperation(value = "Send Execute Plan", notes = "Send Execute Plan")
	public ResponseEntity<Boolean> sendExecutePlan(@RequestBody Object planExecution) {
		
		return new ResponseEntity<>(planBO.sendExecutePlan(planExecution), HttpStatus.OK);
	}
	
	@GetMapping(value = "/hierarchies")
	@ApiOperation(value = "Get plan hierarchies", notes = "Get plan hierarchies")
	public ResponseEntity<List<Map<String, Object>>> getCCTVScenarioHierarchies() {

		return new ResponseEntity<>(planBO.getPlanHierarchies(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/nodes")
	@ApiOperation(value = "Get plan nodes", notes = "Get plan nodes")
	public ResponseEntity<List<Map<String, Object>>> getPlanNodes() {

		return new ResponseEntity<>(planBO.getPlanNodes(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/node")
	@ApiOperation(value = "Create plan node", notes = "Create plan node")
	public ResponseEntity<Map<String, Object>> createPlanNode(@RequestBody Object planNode) {

		return new ResponseEntity<>(planBO.createPlanNode(planNode), HttpStatus.OK);
	}
	
	
	@DeleteMapping(value = "/node/{id}")
	@ApiOperation(value = "Delete plan node", notes = "Delete plan node")
	public ResponseEntity<Map<String, Object>> deletePlanNode(
			@ApiParam(name = "id", type = "Integer", value = "PlanNode id", example = "1", required = true) @PathVariable Integer id) {

		return new ResponseEntity<>(planBO.deletePlanNode(id), HttpStatus.OK);
	}

}
