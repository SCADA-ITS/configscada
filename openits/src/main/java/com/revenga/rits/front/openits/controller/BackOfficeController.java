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

import com.revenga.rits.front.openits.bo.BackOfficeBO;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist.PersistResult;
import com.revenga.rits.front.openitsdao.model.BackOffice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/backoffice", produces = { "application/json", "application/xml" })
@Api(value = "BackOffice", tags = { "BACKOFFICE" })
public class BackOfficeController {

	@Autowired
	BackOfficeBO backOfficeBO;
	
	@GetMapping
	@ApiOperation(value = "Get all backoffices", notes = "Get all backoffices")
	public ResponseEntity<List<BackOffice>> getBackOffices() {
		
		return new ResponseEntity<>(backOfficeBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{backOfficeId}")
	@ApiOperation(value = "Get backOffice by id", notes = "Get backOffice by id")
	public ResponseEntity<BackOffice> getBackOffice(
			@ApiParam(name = "backOfficeId", type = "Integer", value = "BackOffice identifier", example = "1", required = true) @PathVariable Integer backOfficeId) {

		return new ResponseEntity<>(backOfficeBO.getItem(backOfficeId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/reload/{backOfficeId}")
	@ApiOperation(value = "Reload backOffice by id", notes = "Reload backOffice by id")
	public ResponseEntity<List<Table>> reloadBackOffice(
			@ApiParam(name = "backOfficeId", type = "Integer", value = "BackOffice identifier", example = "1", required = true) @PathVariable Integer backOfficeId) {

		return new ResponseEntity<>(backOfficeBO.reload(backOfficeId), HttpStatus.OK);
	}
	
	@PostMapping(path = "/{backOfficeId}/table/{tableName}")
	@ApiOperation(value = "get table with conditions")
	public ResponseEntity<List<Table>> getTables(
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId,
			@ApiParam(name = "tableName", type = "String", example = "orders", required = true) @PathVariable String tableName,
			@RequestBody(required = false) String conditions) {
		
		return new ResponseEntity<>(backOfficeBO.get(backOfficeId, tableName, conditions), HttpStatus.OK);
	}
	
	@PostMapping(path = "/{backOfficeId}/persist")
	@ApiOperation(value = "persist data")
	public ResponseEntity<PersistResult> persist(
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId,
			@RequestBody Persist persist) {
		
		return new ResponseEntity<>(backOfficeBO.persist(backOfficeId, persist), HttpStatus.OK);
	}
	
	@PostMapping(path = "/{backOfficeId}/filler/{columnName}")
	@ApiOperation(value = "On Change field")
	public ResponseEntity<PersistResult> filler(
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId,
			@ApiParam(name = "columnName", type = "String", example = "1", required = true) @PathVariable String columnName,
			@RequestBody Persist persist) {
		
		return new ResponseEntity<>(backOfficeBO.filler(backOfficeId, columnName, persist), HttpStatus.OK);
	}
}