package com.revenga.rits.front.openits.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openits.bo.UserBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/users", produces = { "application/json", "application/xml" })
@Api(value = "User", tags = { "USERS" })
public class UserController {

	@Autowired
	UserBO userBO;
	
	@GetMapping
	@ApiOperation(value = "Get all users", notes = "Get all users")
	public ResponseEntity<Object> getUsers() {
		
		return new ResponseEntity<>(userBO.getUsers(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Get user by id", notes = "Get user by id")
	public ResponseEntity<Object> getUserById(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Element by id",
			example = "User:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(userBO.getUser(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/update")
	@ApiOperation(value = "Update user", notes = "Update user")
	public ResponseEntity<Object> updateUser(@RequestParam String user) {
		
		return new ResponseEntity<>(userBO.updateUser(user), HttpStatus.OK);
	}
}