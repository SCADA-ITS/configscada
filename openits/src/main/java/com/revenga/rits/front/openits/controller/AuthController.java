package com.revenga.rits.front.openits.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.revenga.rits.front.openits.bo.SessionBO;
import com.revenga.rits.front.openits.model.UserSessionUI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/auth", produces = { "application/json", "application/xml" })
@Api(value = "Auth", tags = { "AUTH" })
public class AuthController {
	
	@Autowired
	SessionBO sessionBO;

	@PostMapping(value = "/login")
	@ApiOperation(value = "login")
	public ResponseEntity<UserSessionUI> login() {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession httpSession = attr.getRequest().getSession(true);
		Map<String, String> headers = Collections.list(attr.getRequest().getHeaderNames()).stream()
				.collect(Collectors.toMap(h -> h.toLowerCase(), attr.getRequest()::getHeader));
	    
		return new ResponseEntity<>(sessionBO.login(headers, httpSession), HttpStatus.OK);
	}
	
	@PostMapping(value = "/logout")
	@ApiOperation(value = "logout")
	public ResponseEntity<UserSessionUI> logout() {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession httpSession = attr.getRequest().getSession(true);
	    
		return new ResponseEntity<>(sessionBO.logout(httpSession), HttpStatus.OK);
	}
	
	@GetMapping(value = "/swagger/login/{user}/{password}")
	@ApiOperation(value = "swagger")
	public ResponseEntity<UserSessionUI> loginSwagger(
			@ApiParam(name = "user", type = "String", value = "user name", example = "pepe", required = true) @PathVariable String user,
			@ApiParam(name = "password", type = "String", value = "pass", example = "1234", required = true) @PathVariable String password) {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession httpSession = attr.getRequest().getSession(true);
		
		return new ResponseEntity<>(sessionBO.login(user, password, httpSession), HttpStatus.OK);
	}
	
	@GetMapping(value = "/swagger/logout")
	@ApiOperation(value = "swagger")
	public ResponseEntity<UserSessionUI> logoutSwagger() {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession httpSession = attr.getRequest().getSession(true);

		return new ResponseEntity<>(sessionBO.logout(httpSession), HttpStatus.OK);
	}
	
	@PostMapping(value = "/keep_alive")
	@ApiOperation(value = "Keep alive", notes = "Get generic data by id")
	public ResponseEntity<UserSessionUI> keepAlive() {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession httpSession = attr.getRequest().getSession(true);
//		Map<String, String> headers = Collections.list(attr.getRequest().getHeaderNames()).stream()
//				.collect(Collectors.toMap(h -> h, attr.getRequest()::getHeader));
		
		UserSessionUI userSession = sessionBO.keepAlive(httpSession);
		
		return new ResponseEntity<>(userSession, userSession != null ? HttpStatus.OK : HttpStatus.FORBIDDEN);
	}
	
	@GetMapping(value = "/sessions")
	@ApiOperation(value = "sessions", notes = "Get list of active sessions")
	public ResponseEntity<List<UserSessionUI>> getSessions() {
		
		List<UserSessionUI> sessions = sessionBO.getUserSessions();
		
		return new ResponseEntity<>(sessions, sessions != null ? HttpStatus.OK : HttpStatus.FORBIDDEN);
	}
}