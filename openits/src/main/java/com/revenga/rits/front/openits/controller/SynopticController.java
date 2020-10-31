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

import com.revenga.rits.front.openits.bo.SynopticBO;
import com.revenga.rits.front.openits.model.SynopticUI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/synoptics", produces = { "application/json", "application/xml" })
@Api(value = "synoptics", tags = { "SYNOPTICS" })
public class SynopticController {

	@Autowired
	SynopticBO synopticBO;
	
	@GetMapping
	@ApiOperation(value = "Get all synoptics", notes = "Get all synoptics")
	public ResponseEntity<List<SynopticUI>> getSynoptics() {

		return new ResponseEntity<>(synopticBO.getSynoptics(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get synoptic by id", notes = "Get synoptic by id")
	public ResponseEntity<SynopticUI> getSynoptic(
			@ApiParam(
					name = "id",
					type = "Integer",
					value = "synoptic identifier",
					example = "1",
					required = true) @PathVariable Integer id) {

		
		return new ResponseEntity<>(synopticBO.getSynoptic(id), HttpStatus.OK);
	}
}