package com.revenga.rits.front.openits.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openitsdao.model.Module;
import com.revenga.rits.front.openitsdao.model.ModuleType;
import com.revenga.rits.front.openitsdao.service.ModuleService;
import com.revenga.rits.front.openitsdao.service.ModuleTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/module_types", produces = { "application/json", "application/xml" })
@Api(value = "ModuleTypes", tags = { "MODULE TYPES" })
public class ModuleTypeController {
	
	@Autowired
	ModuleTypeService moduleTypeService;
	
	@Autowired
	ModuleService moduleService;
	
	@GetMapping
	@ApiOperation(value = "Get all module types", notes = "Get all module types")
	public List<ModuleType> getModuleTypes() {
		
		return moduleTypeService.getAll();
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get modules type by id", notes = "Get modules type by id")
	public ModuleType getModuleTypeById(
			@ApiParam(
					name = "id",
					type = "Integer",
					value = "Module type identifier",
					example = "1",
					required = true) @PathVariable Integer id) {
		
		return moduleTypeService.getById(id);
	}
	
	@GetMapping(value = "/{id}/modules")
	@ApiOperation(value = "Get modules by module type identifier", notes = "Get modules by module type identifier")
	public List<Module> getModulesByModuleTypeId(
			@ApiParam(
					name = "id",
					type = "Integer",
					value = "Module type identifier",
					example = "1",
					required = true) @PathVariable Integer id) {
		
		return moduleService.getByModuleTypeId(id);
	}
}
