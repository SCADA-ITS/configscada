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

import com.revenga.rits.front.openits.bo.GenericDataBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/generic_datas", produces = { "application/json", "application/xml" })
@Api(value = "GenericData", tags = { "GENERIC DATA" })
public class GenericDataController {

	@Autowired
	GenericDataBO genericDataBO;
	
	@GetMapping
	@ApiOperation(value = "Get all generic datas", notes = "Get all generic datas")
	public ResponseEntity<Object> getGenericDatas() {
	    
		return new ResponseEntity<>(genericDataBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get generic data by id", notes = "Get generic data by id")
	public ResponseEntity<Object> getGenericDataById(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Element by id",
			example = "GenericData:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(genericDataBO.getItem(id), HttpStatus.OK);
	}
}