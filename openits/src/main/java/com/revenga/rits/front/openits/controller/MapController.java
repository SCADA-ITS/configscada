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

import com.revenga.rits.front.openits.bo.MapBO;
import com.revenga.rits.front.openits.model.MapUI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/maps", produces = { "application/json", "application/xml" })
@Api(value = "maps", tags = { "MAPS" })
public class MapController {

	@Autowired
	MapBO mapBO;
	
	@GetMapping
	@ApiOperation(value = "Get all maps", notes = "Get all maps")
	public ResponseEntity<List<MapUI>> getMaps() {

		return new ResponseEntity<>(mapBO.getMaps(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get map by id", notes = "Get map by id")
	public ResponseEntity<MapUI> getMap(
			@ApiParam(
					name = "id",
					type = "Integer",
					value = "map identifier",
					example = "1",
					required = true) @PathVariable Integer id) {

		
		return new ResponseEntity<>(mapBO.getMap(id), HttpStatus.OK);
	}
}