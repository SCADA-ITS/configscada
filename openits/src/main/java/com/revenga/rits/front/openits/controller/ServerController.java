package com.revenga.rits.front.openits.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openits.bo.ServerBO;
import com.revenga.rits.front.openits.model.ServerTimeUI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/server", produces = { "application/json", "application/xml" })
@Api(value = "Server", tags = { "SERVER" })
public class ServerController {
	
	@Autowired
	ServerBO serverBO;

	@GetMapping(value = "/time")
	@ApiOperation(value = "Get epoch milliseconds", notes = "Returns the number of milliseconds since the epoch of 1970-01-01T00:00:00Z")
	public ResponseEntity<ServerTimeUI> getEpochMilliseconds() {
		
		//return new ResponseEntity<>(new ServerTimeUI(), HttpStatus.OK);
		
		return new ResponseEntity<>(serverBO.getServerTime(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/processes_info")
	@ApiOperation(value = "Get processes info", notes = "Get processes info")
	public ResponseEntity<List<Map<String, Object>>> getProcessesInfo() {
		
		return new ResponseEntity<>(serverBO.getProcessesInfo(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/resources")
	@ApiOperation(value = "Get server resources", notes = "Get server resources")
	public ResponseEntity<List<Object>> getResources(
			@RequestBody Map<String, String> resource) {
		
		return new ResponseEntity<>(serverBO.getResources(resource), HttpStatus.OK);
	}
}