package com.revenga.rits.front.openits.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openits.bo.CCTVScenarioBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/cctv_scenarios", produces = { "application/json", "application/xml" })
@Api(value = "CCTVScenarios", tags = { "CCTV SCENARIOS" })
public class CCTVScenarioController {

	@Autowired
	CCTVScenarioBO cctvScenarioBO;

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get cctv scenario by id", notes = "Get cctv scenario by id")
	public ResponseEntity<Map<String, Object>> getCCTVScenarioById(
			@ApiParam(name = "id", type = "String", value = "CCTVScenario id", example = "CCTVScenario:1", required = true) @PathVariable String id) {

		return new ResponseEntity<>(cctvScenarioBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/hierarchies")
	@ApiOperation(value = "Get cctv scenario hierarchies", notes = "Get cctv scenario hierarchies")
	public ResponseEntity<List<Map<String, Object>>> getCCTVScenarioHierarcies() {

		return new ResponseEntity<>(cctvScenarioBO.getScenarioHierarchies(), HttpStatus.OK);
	}

	
}