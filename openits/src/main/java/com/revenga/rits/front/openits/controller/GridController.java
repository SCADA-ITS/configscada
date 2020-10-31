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

import com.revenga.rits.front.openits.bo.GridBO;
import com.revenga.rits.front.openits.model.GridUI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/grids", produces = { "application/json", "application/xml" })
@Api(value = "Grids", tags = { "GRIDS" })
public class GridController {

	@Autowired
	GridBO gridBO;
	
	@GetMapping
	@ApiOperation(value = "Get all grids", notes = "Get all grids")
	public ResponseEntity<List<GridUI>> getGrids() {
		
		return new ResponseEntity<>(gridBO.getGridUIs(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get grid by id", notes = "Get grid by id")
	public ResponseEntity<GridUI> getGrid(
			@ApiParam(
					name = "id",
					type = "Integer",
					value = "Grid identifier",
					example = "2",
					required = true) @PathVariable Integer id) {

		
		return new ResponseEntity<>(gridBO.getGridUI(id, null), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/{params}")
	@ApiOperation(value = "Get grid by id and params", notes = "Get grid by id and params")
	public ResponseEntity<GridUI> getGridParams(
			@ApiParam(
				    name = "id",
				    type = "Integer",
				    value = "Grid identifier",
				    example = "2",
				    required = true) @PathVariable Integer id,
			@ApiParam(
				    name = "params",
				    type = "String",
				    value = "Grid params",
				    example = "ElementType:3",
				    required = true) @PathVariable String params) {
		
		return new ResponseEntity<>(gridBO.getGridUI(id, params), HttpStatus.OK);
	}
}