package com.revenga.rits.front.openits.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openits.bo.ManagementAreaBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/management_areas", produces = { "application/json", "application/xml" })
@Api(value = "ManagementAreas", tags = { "MANAGEMENT AREAS" })
public class ManagementAreaController {

	@Autowired
	ManagementAreaBO managementAreaBO;

	@GetMapping
	@ApiOperation(value = "Get all management areas", notes = "Get all management areas")
	public ResponseEntity<Object> getManagementAreas() {

		return new ResponseEntity<>(managementAreaBO.getList(), HttpStatus.OK);
	}
}
