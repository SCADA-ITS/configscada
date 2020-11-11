package com.revenga.rits.front.openits.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openits.bo.LocationBO;
import com.revenga.rits.front.openits.bo.RoadBO;
import com.revenga.rits.front.openits.bo.StretchBO;

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
	
	@Autowired
	StretchBO stretchBO;
	
	@Autowired
	RoadBO roadBO;
	
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
	
	@GetMapping(value = "/roads")
	@ApiOperation(value = "Get roads", notes = "Get roads")
	public ResponseEntity<Object> getRoads() {
		
		return new ResponseEntity<>(roadBO.getRoads(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/road/{id}")
	@ApiOperation(value = "Get road by id", notes = "Get road by id")
	public ResponseEntity<Object> getRoad(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Road by id",
			example = "Road:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(roadBO.getRoad(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/stretchs")
	@ApiOperation(value = "Get stretchs", notes = "Get stretchs")
	public ResponseEntity<Object> getStretchs() {
		
		return new ResponseEntity<>(stretchBO.getStretchs(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/stretch/{id}")
	@ApiOperation(value = "Get stretch by id", notes = "Get stretch by id")
	public ResponseEntity<Object> getStretch(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Stretch by id",
			example = "Stretch:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(stretchBO.getStretch(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/stretch/road/{id}")
	@ApiOperation(value = "Get stretch by road id", notes = "Get stretch by road id")
	public ResponseEntity<Object> getStretchByRoad(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Stretch by road id",
			example = "Road:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(stretchBO.getStretchByRoad(id), HttpStatus.OK);
	}
}
