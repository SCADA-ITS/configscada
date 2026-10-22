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

import com.revenga.rits.front.openitsdao.model.MenuGroup;
import com.revenga.rits.front.openitsdao.service.MenuGroupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/menus", produces = { "application/json", "application/xml" })
@Api(value = "Menus", tags = { "MENUS" })
public class MenuController {

	@Autowired
	MenuGroupService menuGroupService;

	@GetMapping
	@ApiOperation(value = "List all menu groups", notes = "Get list of all menu groups")
	public ResponseEntity<List<MenuGroup>> getAll() {

		return new ResponseEntity<>(menuGroupService.getAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Find menu item by id", notes = "Find menu item by id")
	public ResponseEntity<MenuGroup> getById(@ApiParam(value = "identifier", example = "642") @PathVariable Integer id) {
		
		return new ResponseEntity<>(menuGroupService.getById(id), HttpStatus.OK);
	}
}