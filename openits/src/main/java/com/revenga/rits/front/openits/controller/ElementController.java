package com.revenga.rits.front.openits.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openits.bo.ElementBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/elements", produces = { "application/json", "application/xml" })
@Api(value = "Elements", tags = { "ELEMENTS" })
public class ElementController {
	
	@Autowired
	ElementBO elementBO;
	
	@GetMapping
	@ApiOperation(value = "Get all elements", notes = "Get all elements")
	public ResponseEntity<Object> getElements() {
		
		return new ResponseEntity<>(elementBO.getList(), HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = "Get selected elements", notes = "Get selected elements")
	public ResponseEntity<Object> getElements(@RequestBody List<String> ids) {
		
		return new ResponseEntity<>(elementBO.getElements(ids), HttpStatus.OK);
	}
	
	@PostMapping(value = "/byStretchs")
	@ApiOperation(value = "Get elements by stretchs", notes = "Get elements by strechs")
	public ResponseEntity<Object> getElementsByStretchs(@RequestBody List<String> stretchIds) {
		
		return new ResponseEntity<>(elementBO.getElementsByStretchs(stretchIds, null), HttpStatus.OK);
	}
	
	@PostMapping(value = "/byStretchs/{elementTypeId}")
	@ApiOperation(value = "Get elements by stretchs and elementType", notes = "Get elements by stretchs and elementType")
	public ResponseEntity<Object> getElementsByStretchsElementType(@RequestBody List<String> stretchIds, @ApiParam(
			name = "elementTypeId",
			type = "String",
			value = "Element type id",
			example = "ElementType:33",
			required = true) @PathVariable String elementTypeId) {
		
		return new ResponseEntity<>(elementBO.getElementsByStretchs(stretchIds, elementTypeId), HttpStatus.OK);
	}
	
	@PostMapping(value = "/commands")
	@ApiOperation(value = "Get selected elements only with commands", notes = "Get selected elements only with commands")
	public ResponseEntity<Object> getElementsCommands(@RequestBody List<String> ids) {
		
		return new ResponseEntity<>(elementBO.getElementsCommands(ids), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get element by id", notes = "Get element by id")
	public ResponseEntity<Object> getElementById(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Element by id",
			example = "Element:33:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(elementBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/withoutChilds")
	@ApiOperation(value = "Get element by id without childs", notes = "Get element by id without childs")
	public ResponseEntity<Object> getElementByIdWithoutChilds(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Element by id",
			example = "Element:33:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(elementBO.getElementWithoutChilds(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/hierarchy")
	@ApiOperation(value = "Get hierarchies", notes = "Get hierarchies")
	public ResponseEntity<Object> getHierarchies() {
		
		return new ResponseEntity<>(elementBO.getHierarchies(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/createElement")
	@ApiOperation(value = "Create Element", notes = "Create Element")
	public ResponseEntity<Object> createElement(@RequestBody Object element) {
		
		return new ResponseEntity<>(elementBO.createElement(element), HttpStatus.OK);
	}
	
	@PutMapping(value = "/updateElement")
	@ApiOperation(value = "Update Element", notes = "Update Element")
	public ResponseEntity<Object> updateElement(@RequestBody Object element) {
		
		return new ResponseEntity<>(elementBO.updateElement(element), HttpStatus.OK);
	}
	
	@PostMapping(value = "/cloneElement/{id}")
	@ApiOperation(value = "Clone Element", notes = "Clone Element")
	public ResponseEntity<Object> cloneElement(@ApiParam(
			name = "id",
			type = "String",
			value = "Element id",
			example = "Element:1:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(elementBO.cloneElement(id), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deleteElement/{id}")
	@ApiOperation(value = "Delete Element", notes = "Delete Element")
	public ResponseEntity<Object> deleteElement(@ApiParam(
			name = "id",
			type = "String",
			value = "Element id",
			example = "Element:1:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(elementBO.deleteElement(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/send_command_element")
	@ApiOperation(value = "Send Command Element", notes = "Send Command Element")
	public ResponseEntity<Object> sendCommandElement(@RequestBody Object commands) {
		
 		return new ResponseEntity<>(elementBO.sendCommandElement(commands), HttpStatus.OK);
	}
	
	@PostMapping(value = "/send_command_element_sync")
	@ApiOperation(value = "Send Command Element", notes = "Send Command Element")
	public ResponseEntity<Object> sendCommandElementSync(@RequestBody Object command) {
		
 		return new ResponseEntity<>(elementBO.sendCommandElementSync(command), HttpStatus.OK);
	}
}
