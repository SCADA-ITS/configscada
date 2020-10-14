package com.revenga.rits.front.openits.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openits.model.ServerTimeUI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/server", produces = { "application/json", "application/xml" })
@Api(value = "Server", tags = { "SERVER" })
public class ServerController {


	@GetMapping(value = "/time")
	@ApiOperation(value = "Get epoch milliseconds", notes = "Returns the number of milliseconds since the epoch of 1970-01-01T00:00:00Z")
	public ResponseEntity<ServerTimeUI> getEpochMilliseconds() {
		
		return new ResponseEntity<>(new ServerTimeUI(), HttpStatus.OK);
	}
}