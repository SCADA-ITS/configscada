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
import com.revenga.rits.front.openits.bo.ElementTypeBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/element_types", produces = { "application/json", "application/xml" })
@Api(value = "ElementTypes", tags = { "ELEMENT TYPES" })
public class ElementTypeController {

	@Autowired
	ElementTypeBO elementTypeBO;
	
	@GetMapping
	@ApiOperation(value = "Get all elements types by id", notes = "Get all elements types by id")
	public List<Map<String, Object>> getElementTypes() {
		
		return elementTypeBO.getElementTypes();
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get elements type by id", notes = "Get elements type by id")
	public Map<String, Object> getElementType(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Element by id",
			example = "ElementType:3",
			required = true) @PathVariable String id) {
		
		return elementTypeBO.getElementType(id);
	}
	
	@GetMapping(value = "/{id}/elements")
	@ApiOperation(value = "Get elements by elementTypeId", notes = "Get elements by elementTypeId")
	public List<Map<String, Object>> getElementsByElementTypeId(
			@ApiParam(
					name = "id",
					type = "String",
					value = "Element type identifier",
					example = "ElementType:3",
					required = true) @PathVariable String id) {
		
		return elementTypeBO.getElementsByElementType(id);
	}
}
