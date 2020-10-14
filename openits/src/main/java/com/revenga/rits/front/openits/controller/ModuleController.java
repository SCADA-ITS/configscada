package com.revenga.rits.front.openits.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openitsdao.model.Module;
import com.revenga.rits.front.openitsdao.service.ModuleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/modules", produces = { "application/json", "application/xml" })
@Api(value = "Modules", tags = { "MODULES" })
public class ModuleController {
	
	@Autowired
	ModuleService moduleService;
	
	@GetMapping
	@ApiOperation(value = "Get all modules", notes = "Get all modules")
	public ResponseEntity<List<Module>> getModules() {
		
		return new ResponseEntity<>(moduleService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get modules by id", notes = "Get modules by id")
	public ResponseEntity<Module> getModuleById(
			@ApiParam(
					name = "id",
					type = "Integer",
					value = "Module identifier",
					example = "1",
					required = true) @PathVariable Integer id) {
		
		return new ResponseEntity<>(moduleService.getById(id), HttpStatus.OK);
	}
}
