package com.revenga.rits.front.openits.controller;

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

import com.revenga.rits.front.openits.bo.RoleBO;
import com.revenga.rits.front.openits.bo.SystemPermissionBO;
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
	
	@Autowired
	RoleBO roleBO;
	
	@Autowired
	SystemPermissionBO systemPermissionBO;
	
	@GetMapping
	@ApiOperation(value = "Get all users", notes = "Get all users")
	public ResponseEntity<Object> getUsers() {
		
		return new ResponseEntity<>(userBO.getList(), HttpStatus.OK);
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
		
		return new ResponseEntity<>(userBO.getItem(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/create")
	@ApiOperation(value = "Create user", notes = "Create user")
	public ResponseEntity<Object> createUser(@RequestBody Object user) {
		
		return new ResponseEntity<>(userBO.createUser(user), HttpStatus.OK);
	}
	
	@PutMapping(value = "/update")
	@ApiOperation(value = "Update user", notes = "Update user")
	public ResponseEntity<Object> updateUser(@RequestBody Object user) {
		
		return new ResponseEntity<>(userBO.updateUser(user), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	@ApiOperation(value = "Delete user", notes = "Delete user")
	public ResponseEntity<Object> deleteUser(@ApiParam(
			name = "id",
			type = "String",
			value = "User id",
			example = "User:3",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(userBO.deleteUser(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/send_kill_users")
	@ApiOperation(value = "Kill users", notes = "Kill users")
	public ResponseEntity<Object> sendKillUsers(@RequestBody Object input) {
		
		return new ResponseEntity<>(userBO.sendKillUsers(input), HttpStatus.OK);
	}
	
	@GetMapping(value = "/roles")
	@ApiOperation(value = "Get roles", notes = "Get roles")
	public ResponseEntity<Object> getRoles() {
		
		return new ResponseEntity<>(roleBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/role/{id}")
	@ApiOperation(value = "Get role by id", notes = "Get role by id")
	public ResponseEntity<Object> getRole(
			@ApiParam(
			name = "id",
			type = "String",
			value = "Role by id",
			example = "Role:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(roleBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/systemPermissions")
	@ApiOperation(value = "Get system permissions", notes = "Get system permissions")
	public ResponseEntity<Object> getSystemPermissions() {
		
		return new ResponseEntity<>(systemPermissionBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/systemPermission/{id}")
	@ApiOperation(value = "Get system permission by id", notes = "Get system permission by id")
	public ResponseEntity<Object> getSystemPermission(
			@ApiParam(
			name = "id",
			type = "String",
			value = "System permission by id",
			example = "SystemPermission:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(systemPermissionBO.getItem(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/createRole")
	@ApiOperation(value = "Create Role", notes = "Create Role")
	public ResponseEntity<Object> createRole(@RequestBody Object role) {
		
		return new ResponseEntity<>(roleBO.createRole(role), HttpStatus.OK);
	}
	
	@PutMapping(value = "/updateRole")
	@ApiOperation(value = "Update Role", notes = "Update Role")
	public ResponseEntity<Object> updateRole(@RequestBody Object role) {
		
		return new ResponseEntity<>(roleBO.updateRole(role), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deleteRole/{id}")
	@ApiOperation(value = "Delete Role", notes = "Delete Role")
	public ResponseEntity<Object> deleteRole(@ApiParam(
			name = "id",
			type = "String",
			value = "Role id",
			example = "Role:1",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(roleBO.deleteRole(id), HttpStatus.OK);
	}
}