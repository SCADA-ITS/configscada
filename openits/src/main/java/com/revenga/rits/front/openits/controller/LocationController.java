package com.revenga.rits.front.openits.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openits.bo.LocationBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/locations", produces = { "application/json", "application/xml" })
@Api(value = "Locations", tags = { "LOCATIONS" })
public class LocationController {
	
	@Autowired
	LocationBO locationBO;
	
	@GetMapping
	@ApiOperation(value = "Get all locations", notes = "Get all locations")
	public ResponseEntity<Object> getLocations() {
		
		return new ResponseEntity<>(locationBO.getLocations(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get location by id", notes = "Get location by id")
	public ResponseEntity<Object> getLocationById(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Location by id",
			example = "Location:33:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(locationBO.getLocation(id), HttpStatus.OK);
	}
}
