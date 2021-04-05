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

import com.revenga.rits.front.openits.bo.EquipmentViewerBO;
import com.revenga.rits.front.openitsdao.model.EquipViewer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/equip_viewer", produces = { "application/json", "application/xml" })
@Api(value = "Equipment viewer", tags = { "EQUIPMENT VIEWER" })
public class EquipmentViewerController {

	@Autowired
	EquipmentViewerBO equipmentViewerBO;
	
	@GetMapping
	@ApiOperation(value = "Get all equipment viewers", notes = "Get all equipment viewers")
	public ResponseEntity<List<EquipViewer>> getEquipmentViewers() {
		
		return new ResponseEntity<>(equipmentViewerBO.getEquipViewers(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get equipment viewer by id", notes = "Get equipment viewer by id")
	public ResponseEntity<EquipViewer> getEquipmentViewer(
			@ApiParam(
					name = "id",
					type = "Integer",
					value = "Equipment viewer identifier",
					example = "ElementType:1",
					required = true) @PathVariable String id) {

		
		return new ResponseEntity<>(equipmentViewerBO.getEquipViewer(id), HttpStatus.OK);
	}
}