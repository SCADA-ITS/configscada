package com.revenga.rits.front.openits.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openits.bo.HelpBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/help", produces = { "application/json", "application/xml" })
@Api(value = "Help", tags = { "HELP" })
public class HelpController {
	
//	@Autowired
//	ModuleService moduleService;
	
	@Autowired
	HelpBO helpBO;
	
	@GetMapping(value = "/{moduleId}/module", produces = MediaType.TEXT_HTML_VALUE)
	@ApiOperation(value = "Get module help by module id", notes = "Get module help by module id")
	public ResponseEntity<String> getHelpByModuleId(
			@ApiParam(
					name = "moduleId",
					type = "Integer",
					value = "Module identifier",
					example = "1",
					required = true) @PathVariable Integer moduleId) {
		
		String html = helpBO.getByModuleId(moduleId);
		
		return new ResponseEntity<>(html, !StringUtils.isEmpty(html) ? HttpStatus.OK: HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/{tag}/tag", produces = MediaType.TEXT_HTML_VALUE)
	@ApiOperation(value = "Get module help by module id", notes = "Get module help by module id")
	public ResponseEntity<String> getHelpByModuleId(
			@ApiParam(
					name = "tag",
					type = "String",
					value = "Tag",
					example = "1234ABC",
					required = true) @PathVariable String tag) {
		
		String html = helpBO.getByTag(tag);
		
		return new ResponseEntity<>(html, !StringUtils.isEmpty(html) ? HttpStatus.OK: HttpStatus.NOT_FOUND);
	}
}
