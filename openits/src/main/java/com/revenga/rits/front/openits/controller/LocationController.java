package com.revenga.rits.front.openits.controller;

import java.util.List;

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
	
	@GetMapping(value = "/summary")
	@ApiOperation(value = "Get all locations summary", notes = "Get all locations summary")
	public ResponseEntity<Object> getLocationsSummary() {
		
		return new ResponseEntity<>(locationBO.getListSummary(), HttpStatus.OK);
	}
	
	@GetMapping
	@ApiOperation(value = "Get all locations", notes = "Get all locations")
	public ResponseEntity<Object> getLocations() {
		
		return new ResponseEntity<>(locationBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get location by id", notes = "Get location by id")
	public ResponseEntity<Object> getLocationById(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Location by id",
			example = "Location:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(locationBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/locations/stretch/{id}")
	@ApiOperation(value = "Get locations by stretch id", notes = "Get locations by stretch id")
	public ResponseEntity<Object> getLocationByStretch(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Location by stretch id",
			example = "Stretch:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(locationBO.getLocationByStretch(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/locations/byStretchs")
	@ApiOperation(value = "Get locations by stretchs", notes = "locations by stretchs")
	public ResponseEntity<Object> getLocationsByStretchs(@RequestBody List<String> stretchIds) {
		
		return new ResponseEntity<>(locationBO.getLocationsByStretchs(stretchIds), HttpStatus.OK);
	}
	
	@GetMapping(value = "/roads")
	@ApiOperation(value = "Get roads", notes = "Get roads")
	public ResponseEntity<Object> getRoads() {
		
		return new ResponseEntity<>(roadBO.getList(), HttpStatus.OK);
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
		
		return new ResponseEntity<>(roadBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/stretchs")
	@ApiOperation(value = "Get stretchs", notes = "Get stretchs")
	public ResponseEntity<Object> getStretchs() {
		
		return new ResponseEntity<>(stretchBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "{id}/stretchs")
	@ApiOperation(value = "Get stretchs by location", notes = "Get stretchs by location")
	public ResponseEntity<Object> getStretchsByLocation(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Location id",
			example = "Location:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(stretchBO.getListByLocation(id), HttpStatus.OK);
	}

	@GetMapping(value = "/stretchs/incidentType/{id}")
	@ApiOperation(value = "Get stretchs by incidentType id", notes = "Get stretchs by incident type id")
	public ResponseEntity<Object> getStretchsByIncidentType(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Stretchs by incident type id",
			example = "ImsIncidentType:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(stretchBO.getListByIncidentType(id), HttpStatus.OK);
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
		
		return new ResponseEntity<>(stretchBO.getItem(id), HttpStatus.OK);
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
