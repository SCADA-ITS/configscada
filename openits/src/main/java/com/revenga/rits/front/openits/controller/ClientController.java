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

import com.revenga.rits.front.openits.bo.ClientInfoBO;
import com.revenga.rits.front.openits.model.ClientInfoUI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/client", produces = { "application/json", "application/xml" })
@Api(value = "Client", tags = { "CLIENT" })
public class ClientController {

	@Autowired
	ClientInfoBO clientInfoBO;

	@GetMapping(value = "/info")
	@ApiOperation(value = "Get client info", notes = "Return client info")
	public ResponseEntity<ClientInfoUI> getInfo() {
		
		return new ResponseEntity<>(clientInfoBO.getInfo(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/resources/{folder}")
	@ApiOperation(value = "Get client info", notes = "Return client info")
	public ResponseEntity<List<String>> getResources(
			@ApiParam(name = "folder", type = "String", value = "folder", example = "img", required = true) @PathVariable String folder) {
		
		return new ResponseEntity<>(clientInfoBO.getResources(folder), HttpStatus.OK);
	}
}