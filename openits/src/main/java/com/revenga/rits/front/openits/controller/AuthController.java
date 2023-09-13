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

import com.revenga.rits.front.openits.model.UserSessionUI;
import com.revenga.rits.front.openits.service.user.SessionService;

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
	SessionService sessionService;

	@PostMapping(value = "/login")
	@ApiOperation(value = "login")
	public ResponseEntity<UserSessionUI> login() {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession httpSession = attr.getRequest().getSession(true);
		Map<String, String> headers = Collections.list(attr.getRequest().getHeaderNames()).stream()
				.collect(Collectors.toMap(String::toLowerCase, attr.getRequest()::getHeader));
	    
		return new ResponseEntity<>(sessionService.login(headers, httpSession), HttpStatus.OK);
	}
	
	@PostMapping(value = "/logout")
	@ApiOperation(value = "logout")
	public ResponseEntity<UserSessionUI> logout() {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession httpSession = attr.getRequest().getSession(true);
	    
		return new ResponseEntity<>(sessionService.logout(httpSession), HttpStatus.OK);
	}
	
	@GetMapping(value = "/swagger/login/{user}/{password}")
	@ApiOperation(value = "swagger")
	public ResponseEntity<UserSessionUI> loginSwagger(
			@ApiParam(name = "user", type = "String", value = "user name", example = "pepe", required = true) @PathVariable String user,
			@ApiParam(name = "password", type = "String", value = "pass", example = "1234", required = true) @PathVariable String password) {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession httpSession = attr.getRequest().getSession(true);
		
		return new ResponseEntity<>(sessionService.login(user, password, httpSession), HttpStatus.OK);
	}
	
	@GetMapping(value = "/setManagementArea/{managementAreaId}")
	@ApiOperation(value = "swagger")
	public ResponseEntity<UserSessionUI> setManagementArea(
			@ApiParam(name = "managementAreaId", type = "String", value = "Management area id", example = "ManagementArea:1", required = true) @PathVariable String managementAreaId) {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession httpSession = attr.getRequest().getSession(true);
		
		return new ResponseEntity<>(sessionService.setManagementArea(managementAreaId, httpSession), HttpStatus.OK);
	}
	
	@GetMapping(value = "/swagger/logout")
	@ApiOperation(value = "swagger")
	public ResponseEntity<UserSessionUI> logoutSwagger() {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession httpSession = attr.getRequest().getSession(true);

		return new ResponseEntity<>(sessionService.logout(httpSession), HttpStatus.OK);
	}
	
	@PostMapping(value = "/keep_alive")
	@ApiOperation(value = "Keep alive", notes = "Get generic data by id")
	public ResponseEntity<UserSessionUI> keepAlive() {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession httpSession = attr.getRequest().getSession(true);
		
		UserSessionUI userSession = sessionService.keepAlive(httpSession);
		
		return new ResponseEntity<>(userSession, userSession != null ? HttpStatus.OK : HttpStatus.FORBIDDEN);
	}
	
	@GetMapping(value = "/sessions")
	@ApiOperation(value = "sessions", notes = "Get list of active sessions")
	public ResponseEntity<List<UserSessionUI>> getSessions() {
		
		List<UserSessionUI> sessions = sessionService.getUserSessions();
		
		return new ResponseEntity<>(sessions, sessions != null ? HttpStatus.OK : HttpStatus.FORBIDDEN);
	}
	
	@GetMapping(value = "/session/{sessionId}")
	@ApiOperation(value = "sessions", notes = "Get list of active sessions")
	public ResponseEntity<UserSessionUI> getUserSession(@ApiParam(name = "sessionId", type = "String", value = "sessionId", example = "1234", required = true) @PathVariable String sessionId) {
		
		UserSessionUI session = sessionService.getUserSession(sessionId);
		
		return new ResponseEntity<>(session, session != null ? HttpStatus.OK : HttpStatus.FORBIDDEN);
	}
}