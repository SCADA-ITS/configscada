package com.revenga.rits.back.user.manager.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.back.front.core.model.vo.UserSessionVO;
import com.revenga.rits.back.user.manager.service.AuthenticationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "api/v1")
@Api(value = "Auth", tags = { "AUTH" })
public class AuthController {
	
	@Autowired
	AuthenticationService authenticationService;
	
	@GetMapping(value = "/auth")
	@ApiOperation(value = "Authentification gateway", notes = "Authentification gateway")
	public ResponseEntity<String> auth(@RequestHeader Map<String, String> headers, HttpSession httpSession) {

		if (authenticationService.auth(headers, httpSession.getId())) {

			return new ResponseEntity<>(HttpStatus.OK);
			
		} else {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
		
	@PostMapping(value = "/login")
	@ApiOperation(value = "Request login", notes = "Request login")
	public ResponseEntity<UserSessionVO> login(@RequestHeader Map<String, String> headers, HttpSession httpSession) {

		return new ResponseEntity<>(authenticationService.login(headers, httpSession.getId()), HttpStatus.OK);
	}
	
	@PutMapping(value = "/keepAlive")
	@ApiOperation(value = "Request keepAlive", notes = "Request keepAlive")
	public ResponseEntity<UserSessionVO> keepAlive(@RequestHeader Map<String, String> headers, HttpSession httpSession) {

		UserSessionVO userSessionVORes = authenticationService.keepAlive(headers, httpSession.getId());
		
		if (userSessionVORes != null) {
		
			return new ResponseEntity<>(userSessionVORes, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping(value = "/logout")
	@ApiOperation(value = "Request logout", notes = "Request logout")
	public ResponseEntity<UserSessionVO> logout(@RequestHeader Map<String, String> headers, HttpSession httpSession) {

		UserSessionVO userSessionVORes = authenticationService.logout(headers, httpSession.getId());
		
		if (userSessionVORes != null) {
		
			return new ResponseEntity<>(userSessionVORes, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
