package com.revenga.rits.front.openits.controller;

import java.util.List;

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

import com.revenga.rits.front.openits.bo.SystemParamBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/system_params", produces = { "application/json", "application/xml" })
@Api(value = "SystemParam", tags = { "SYSTEM PARAMS" })
public class SystemParamController {
	
	@Autowired
	SystemParamBO systemParamBO;
	
	@GetMapping
	@ApiOperation(value = "Get all system params", notes = "Get all system params")
	public ResponseEntity<Object> getSystemParams() {
		
		return new ResponseEntity<>(systemParamBO.getList(), HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = "Get selected system params", notes = "Get selected system params")
	public ResponseEntity<Object> getSystemParams(@RequestBody List<String> ids) {
		
		return new ResponseEntity<>(systemParamBO.getSystemParams(ids), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get system param by id", notes = "Get system param by id")
	public ResponseEntity<Object> getElementById(
			@ApiParam(
			name = "id",
			type = "String",
			value = "System Param by id",
			example = "SystemParam:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(systemParamBO.getItem(id), HttpStatus.OK);
	}
}
