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

import com.revenga.rits.front.openits.bo.ElementTypeStateBO;
import com.revenga.rits.front.openits.bo.ElementValueStateBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/element_type_states", produces = { "application/json", "application/xml" })
@Api(value = "ElementTypeStates", tags = { "ELEMENT TYPE STATES" })
public class ElementTypeStateController {
	
	@Autowired
	ElementTypeStateBO elementTypeStateBO;
	
	@Autowired
	ElementValueStateBO elementValueStateBO;
	
	@GetMapping
	@ApiOperation(value = "Get all element types states", notes = "Get all element type states")
	public ResponseEntity<Object> getElementTypeStates() {
		
		return new ResponseEntity<>(elementTypeStateBO.getElementTypeStates(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get element type state by id", notes = "Get element type state by id")
	public ResponseEntity<Object> getElementTypeState(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Element by id",
			example = "ElementTypeState:3:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(elementTypeStateBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/element_values_states")
	@ApiOperation(value = "Get all element values states", notes = "Get all element values states")
	public ResponseEntity<Object> getElementValueStates() {
		
		return new ResponseEntity<>(elementValueStateBO.getList(), HttpStatus.OK);
	}
}
