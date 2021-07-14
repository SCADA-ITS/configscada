package com.revenga.rits.front.openits.controller;

import java.util.List;
import java.util.Map;

import com.revenga.rits.front.openits.bo.PasLibraryBO;
import com.revenga.rits.front.openits.bo.PasLibraryElementBO;
import com.revenga.rits.front.openits.bo.PasLibraryNodeBO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/pas_libraries", produces = { "application/json", "application/xml" })
@Api(value = "PasLibraries", tags = { "PAS LIBRARIES" })
public class PasLibraryController {

	@Autowired
	PasLibraryElementBO pasLibraryElementBO;

	@Autowired
	PasLibraryBO pasLibraryBO;
	
	@Autowired
	PasLibraryNodeBO pasLibraryNodeBO;

	@GetMapping
	@ApiOperation(value = "Get all pas_libraries", notes = "Get all pas libraries")
	public ResponseEntity<List<Map<String, Object>>> getPasLibraries() {

		return new ResponseEntity<>(pasLibraryBO.getList(), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get pas_libraries by id", notes = "Get pas libraries by id")
	public ResponseEntity<Map<String, Object>> getPasLibraryById(
			@ApiParam(name = "id", type = "Integer", value = "PasLibrary id", example = "1", required = true) @PathVariable Integer id) {

		return new ResponseEntity<>(pasLibraryBO.getItem(id), HttpStatus.OK);
	}

	@GetMapping(value = "/element")
	@ApiOperation(value = "Get all pas_library_element", notes = "Get all element with pas libraries")
	public ResponseEntity<List<Map<String, Object>>> getPasLibraryElement() {

		return new ResponseEntity<>(pasLibraryElementBO.getList(), HttpStatus.OK);
	}

	@GetMapping(value = "/element/{id}")
	@ApiOperation(value = "Get pas_libraries by element_id", notes = "Get pas libraries by element_id")
	public ResponseEntity<Map<String, Object>> getPasLibraryByElementId(
			@ApiParam(name = "id", type = "String", value = "element_id",  example = "Element:33:1", required = true) @PathVariable String id) {

		return new ResponseEntity<>(pasLibraryElementBO.getItem(id), HttpStatus.OK);
	}
		
	@PostMapping(value = "/{id}/node/{parentNodeIds}")
	@ApiOperation(value = "Save library node and assign to parent node", notes = "Save library node and assign to parent node")
	public ResponseEntity<Map<String, Object>> saveLibraryNodeWithParent(
			@ApiParam(name = "id", type = "Integer", value = "PasLibrary id", example = "1", required = true) @PathVariable Integer id,
			@ApiParam(name = "parentNodeIds", type = "String", value = "PasLibraryNode parent ids", example = "1,2", required = true) @PathVariable String parentNodeIds,
			@RequestBody Object paslibraryNode) {

		return new ResponseEntity<>(pasLibraryNodeBO.save(id, paslibraryNode, parentNodeIds), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/node/{id}")
	@ApiOperation(value = "Delete library node", notes = "Delete library node")
	public ResponseEntity<Map<String, Object>> deleteLibraryNode(
			@ApiParam(name = "id", type = "Integer", value = "PasLibraryNode id", example = "1", required = true) @PathVariable Integer id) {

		return new ResponseEntity<>(pasLibraryNodeBO.delete(id), HttpStatus.OK);
	}

}