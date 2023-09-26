package com.revenga.rits.front.openits.controller;

import java.util.List;
import java.util.Map;

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

import com.revenga.rits.front.openits.bo.ModuleBO;
import com.revenga.rits.front.openitsdao.model.ModuleValue;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/modules", produces = { "application/json", "application/xml" })
@Api(value = "Modules", tags = { "MODULES" })
public class ModuleController {
	
//	@Autowired
//	ModuleService moduleService;
	
	@Autowired
	ModuleBO moduleBO;
	
	@GetMapping
	@ApiOperation(value = "Get all modules", notes = "Get all modules")
	public ResponseEntity<List<Map<String, Object>>> getModules() {
		
		return new ResponseEntity<>(moduleBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get modules by id", notes = "Get modules by id")
	public ResponseEntity<Map<String, Object>> getModuleById(
			@ApiParam(
					name = "id",
					type = "Integer",
					value = "Module identifier",
					example = "1",
					required = true) @PathVariable Integer id) {
		
		return new ResponseEntity<>(moduleBO.getItem(id), HttpStatus.OK);
	}
	
	@PostMapping(path = "/{id}/module_value/save")
	@ApiOperation(value = "save module value")
	public ResponseEntity<ModuleValue> saveModuleValue(
			@ApiParam(
					name = "id",
					type = "Integer",
					value = "Module identifier",
					example = "1",
					required = true) @PathVariable Integer id,
			@RequestBody(required = false) ModuleValue moduleValue) {
		
		return new ResponseEntity<>(moduleBO.save(id, moduleValue), HttpStatus.OK);
	}
}
