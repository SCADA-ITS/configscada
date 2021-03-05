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

import com.revenga.rits.front.openits.bo.TreeBO;
import com.revenga.rits.front.openits.model.TreeUI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/trees", produces = { "application/json", "application/xml" })
@Api(value = "Trees", tags = { "TREES" })
public class TreeController {

	@Autowired
	TreeBO treeBO;
	
	@GetMapping
	@ApiOperation(value = "Get all trees", notes = "Get all trees")
	public ResponseEntity<List<TreeUI>> getTrees() {
		
		return new ResponseEntity<>(treeBO.getTreeUIs(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get tree by id", notes = "Get tree by id")
	public ResponseEntity<TreeUI> getTree(
			@ApiParam(
					name = "id",
					type = "Integer",
					value = "Tree identifier",
					example = "2",
					required = true) @PathVariable Integer id) {

		
		return new ResponseEntity<>(treeBO.getTreeUI(id), HttpStatus.OK);
	}
}