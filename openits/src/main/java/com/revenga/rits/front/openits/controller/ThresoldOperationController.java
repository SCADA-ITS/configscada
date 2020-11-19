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

import com.revenga.rits.front.openits.bo.ThresoldOperationBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/thresoldOperations", produces = { "application/json", "application/xml" })
@Api(value = "ThresoldOperations", tags = { "THRESOLD OPERATIONS" })
public class ThresoldOperationController {
	
	@Autowired
	ThresoldOperationBO thresoldOperationBO;
	
	@GetMapping
	@ApiOperation(value = "Get all threshold operations", notes = "Get all threshold operations")
	public ResponseEntity<Object> getThresholdOperations() {
		
		return new ResponseEntity<>(thresoldOperationBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get threshold operation by id", notes = "Get threshold operation by id")
	public ResponseEntity<Object> getThresholdOperation(
			@ApiParam(
			name = "id",
			type = "String",
			value = "ThresholdOperation by id",
			example = "ThresholdOperation:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(thresoldOperationBO.getItem(id), HttpStatus.OK);
	}

}
