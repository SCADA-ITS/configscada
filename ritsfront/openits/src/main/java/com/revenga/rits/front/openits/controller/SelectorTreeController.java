package com.revenga.rits.front.openits.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openits.bo.SelectorTreeBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/selector_trees", produces = { "application/json", "application/xml" })
@Api(value = "SelectorTrees", tags = { "SELECTOR TREES" })
public class SelectorTreeController {
	
//	@Autowired
//	selector_treeservice selector_treeservice;
	
	@Autowired
	SelectorTreeBO selectorTreeBO;
	
	@GetMapping
	@ApiOperation(value = "Get all selector_trees", notes = "Get all selector trees")
	public ResponseEntity<List<Map<String, Object>>> getSelectorTrees() {
		
		return new ResponseEntity<>(selectorTreeBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get selector_trees by id", notes = "Get selector tree by id")
	public ResponseEntity<Map<String, Object>> getSelectorTreeById(
			@ApiParam(
					name = "id",
					type = "Integer",
					value = "SelectorTree id",
					example = "1",
					required = true) @PathVariable Integer id) {
		
		return new ResponseEntity<>(selectorTreeBO.getItem(id), HttpStatus.OK);
	}
}