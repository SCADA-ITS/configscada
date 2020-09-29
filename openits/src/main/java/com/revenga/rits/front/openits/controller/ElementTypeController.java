package com.revenga.rits.front.openits.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openits.bo.ElementTypeBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v2/element_type", produces = { "application/json", "application/xml" })
@Api(value = "ElementType", tags = { "ELEMENT TYPE" })
public class ElementTypeController {

	@Autowired
	ElementTypeBO elementTypeBO;
	
	@GetMapping(value = "/list")
	@ApiOperation(value = "Get all elements types by id", notes = "Get all elements types by id")
	public List<Map<String, Object>> getElementTypes() {
		
		return elementTypeBO.getElementTypes();
	}
}
