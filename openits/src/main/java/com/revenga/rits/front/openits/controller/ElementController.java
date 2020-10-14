package com.revenga.rits.front.openits.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	public List<Map<String, Object>> getElements() {
		
		return elementBO.getElements();
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get element by id", notes = "Get element by id")
	public Map<String, Object> getElementById(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Element by id",
			example = "Element:33:1",
			required = true) @PathVariable String id) {
		
		return elementBO.getElement(id);
	}
}
