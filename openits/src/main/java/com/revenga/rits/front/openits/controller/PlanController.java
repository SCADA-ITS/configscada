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

}
