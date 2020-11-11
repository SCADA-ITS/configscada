package com.revenga.rits.front.openits.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openits.bo.ElementBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/elements", produces = { "application/json", "application/xml" })
@Api(value = "Elements", tags = { "ELEMENTS" })
public class ElementController {
	
	@Autowired
	ElementBO elementBO;
	
	@GetMapping
	@ApiOperation(value = "Get all elements", notes = "Get all elements")
	public ResponseEntity<Object> getElements() {
		
		return new ResponseEntity<>(elementBO.getElements(), HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = "Get all elements", notes = "Get all elements")
	public ResponseEntity<Object> getElements(@RequestParam List<String> ids) {
		
		return new ResponseEntity<>(elementBO.getElements(ids), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get element by id", notes = "Get element by id")
	public ResponseEntity<Object> getElementById(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Element by id",
			example = "Element:33:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(elementBO.getElement(id), HttpStatus.OK);
	}
	
}
