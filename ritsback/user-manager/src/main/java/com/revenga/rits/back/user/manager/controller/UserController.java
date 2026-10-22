package com.revenga.rits.back.user.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.back.front.core.model.vo.UserVO;
import com.revenga.rits.back.user.manager.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "api/v1")
@Api(value = "User", tags = { "USER" })
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping(value = "/get")
	@ApiOperation(value = "Get user", notes = "Get user")
	public ResponseEntity<UserVO> get(String id) {
		
		UserVO userVORes = userService.get(id);
		
		if (userVORes != null) {
		
			return new ResponseEntity<>(userVORes, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/create")
	@ApiOperation(value = "Create user", notes = "Create user")
	public ResponseEntity<UserVO> create(@RequestBody UserVO user) {
		
		UserVO userVORes = userService.create(user);
		
		if (userVORes != null) {
		
			return new ResponseEntity<>(userVORes, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
	@PutMapping(value = "/update")
	@ApiOperation(value = "Update user", notes = "Update user")
	public ResponseEntity<UserVO> update(@RequestBody UserVO user) {

		UserVO userVORes = userService.update(user);
		
		if (userVORes != null) {
		
			return new ResponseEntity<>(userVORes, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping(value = "/delete")
	@ApiOperation(value = "Delete user", notes = "Delete user")
	public ResponseEntity<UserVO> delete(@RequestBody UserVO user) {

		UserVO userVORes = userService.delete(user);
		
		if (userVORes != null) {
		
			return new ResponseEntity<>(userVORes, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
