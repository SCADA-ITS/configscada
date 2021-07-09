package com.revenga.rits.front.openits.controller;

import java.util.List;
import java.util.Map;

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

import com.revenga.rits.front.openits.bo.VmsLibraryBO;
import com.revenga.rits.front.openits.bo.VmsLibraryElementSubtypeBO;
import com.revenga.rits.front.openits.bo.VmsLibraryNodeBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/vms_libraries", produces = { "application/json", "application/xml" })
@Api(value = "VmsLibraries", tags = { "VMS LIBRARIES" })
public class VmsLibraryController {

//	@Autowired

	@Autowired
	VmsLibraryElementSubtypeBO vmsLibraryElementSubtypeBO;

	@Autowired
	VmsLibraryBO vmsLibraryBO;
	
	@Autowired
	VmsLibraryNodeBO vmsLibraryNodeBO;

	@GetMapping
	@ApiOperation(value = "Get all vms_libraries", notes = "Get all vms libraries")
	public ResponseEntity<List<Map<String, Object>>> getVmsLibraries() {

		return new ResponseEntity<>(vmsLibraryBO.getList(), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get vms_libraries by id", notes = "Get vms libraries by id")
	public ResponseEntity<Map<String, Object>> getVmsLibraryById(
			@ApiParam(name = "id", type = "Integer", value = "VmsLibrary id", example = "1", required = true) @PathVariable Integer id) {

		return new ResponseEntity<>(vmsLibraryBO.getItem(id), HttpStatus.OK);
	}

	@GetMapping(value = "/element_subtype")
	@ApiOperation(value = "Get all vms_library_elementSubtype", notes = "Get all elementSubtype with vms libraries")
	public ResponseEntity<List<Map<String, Object>>> getVmsLibraryElementSubtype() {

		return new ResponseEntity<>(vmsLibraryElementSubtypeBO.getList(), HttpStatus.OK);
	}

	@GetMapping(value = "/element_subtype/{id}")
	@ApiOperation(value = "Get vms_libraries by element_subtype_id", notes = "Get vms libraries by element_subtype_id")
	public ResponseEntity<Map<String, Object>> getVmsLibraryByElementSubtype(
			@ApiParam(name = "id", type = "String", value = "element_subtype_id",  example = "ElementSubtype:1:1", required = true) @PathVariable String id) {

		return new ResponseEntity<>(vmsLibraryElementSubtypeBO.getItem(id), HttpStatus.OK);
	}
	
//	@PostMapping(value = "/{id}/node")
//	@ApiOperation(value = "Save library node", notes = "Save library node")
//	public ResponseEntity<Map<String, Object>> saveLibraryNode(
//			@ApiParam(name = "id", type = "Integer", value = "VmsLibrary id", example = "1", required = true) @PathVariable Integer id,
//			@RequestBody Object vmslibraryNode) {
//
//		return new ResponseEntity<>(vmsLibraryNodeBO.save(id, vmslibraryNode), HttpStatus.OK);
//	}
	
	@PostMapping(value = "/{id}/node/{parentNodeIds}")
	@ApiOperation(value = "Save library node and assign to parent node", notes = "Save library node and assign to parent node")
	public ResponseEntity<Map<String, Object>> saveLibraryNodeWithParent(
			@ApiParam(name = "id", type = "Integer", value = "VmsLibrary id", example = "1", required = true) @PathVariable Integer id,
			@ApiParam(name = "parentNodeIds", type = "String", value = "VmsLibraryNode parent ids", example = "1,2", required = true) @PathVariable String parentNodeIds,
			@RequestBody Object vmslibraryNode) {

		return new ResponseEntity<>(vmsLibraryNodeBO.save(id, vmslibraryNode, parentNodeIds), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/node/{id}")
	@ApiOperation(value = "Delete library node", notes = "Delete library node")
	public ResponseEntity<Map<String, Object>> saveLibraryNode(
			@ApiParam(name = "id", type = "Integer", value = "VmsLibraryNode id", example = "1", required = true) @PathVariable Integer id) {

		return new ResponseEntity<>(vmsLibraryNodeBO.delete(id), HttpStatus.OK);
	}

}