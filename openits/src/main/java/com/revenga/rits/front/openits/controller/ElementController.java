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
@RequestMapping(value = "/api/v2/element", produces = { "application/json", "application/xml" })
@Api(value = "Element", tags = { "ELEMENT" })
public class ElementController {
	
	@Autowired
	ElementBO elementBO;
	
	@GetMapping(value = "/{elementTypeId}")
	@ApiOperation(value = "Get elements by elementTypeId", notes = "Get elements by elementTypeId")
	public List<Map<String, Object>> getElementsByElementTypeId(
			@ApiParam(
					name = "elementTypeId",
					type = "String",
					value = "Element type identifier",
					example = "ElementType:3",
					required = true) @PathVariable String elementTypeId) {
		
		return elementBO.getElementsByElementType(elementTypeId);
	}
}
