package com.revenga.rits.front.openits.controller;

import java.util.ArrayList;
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

import com.revenga.rits.front.openits.bo.I18nBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/i18n", produces = { "application/json", "application/xml" })
@Api(value = "i18n", tags = { "I18N" })
public class I18nController {

	@Autowired
	I18nBO i18nBO;
	
	@GetMapping(value = "/locales")
	@ApiOperation(value = "Get all locales", notes = "Get all locales")
	public ResponseEntity<Object> getLocales() {
		
		return new ResponseEntity<>(i18nBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/locales/{id}")
	@ApiOperation(value = "Get locale by code", notes = "Get locale by id")
	public ResponseEntity<Object> getLocale(@ApiParam(
			name = "id",
			type = "String",
			value = "Locale id identifier",
			example = "I18nLocale:es_ES",
			required = true) @PathVariable String id) {
		
		return new ResponseEntity<>(i18nBO.getItem(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/translations/{localeId}")
	@ApiOperation(value = "Get all translations by Locale", notes = "Get all translations by locale")
	public ResponseEntity<Object> getTranslationsByLocale(@ApiParam(
			name = "localeId",
			type = "String",
			value = "Locale id identifier",
			example = "I18nLocale:es_ES",
			required = true) @PathVariable String localeId) {
		
		return new ResponseEntity<>(i18nBO.getTranslationsByLocale(localeId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/translations/{localeId}/{moduleId}")
	@ApiOperation(value = "Get all translations by Locale and Module", notes = "Get all translations by locale and module")
	public ResponseEntity<Object> getTranslationsByLocaleAndModule(
			@ApiParam(
				name = "localeId",
				type = "String",
				value = "Locale id identifier",
				example = "I18nLocale:es_ES",
				required = true) @PathVariable String localeId,
			@ApiParam(
				name = "moduleId",
				type = "String",
				value = "Module id identifier",
				example = "1",
				required = true) @PathVariable Integer moduleId) {
		
		return new ResponseEntity<>(i18nBO.getTranslationsByLocaleAndModule(localeId, moduleId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/translations/{localeId}/{moduleId}/{label}")
	@ApiOperation(value = "Get label translated by Locale and Module", notes = "Get label translated by Locale and Module")
	public ResponseEntity<Object> getLabelTranslated(
			@ApiParam(
				name = "localeId",
				type = "String",
				value = "Locale id identifier",
				example = "I18nLocale:es_ES",
				required = true) @PathVariable String localeId,
			@ApiParam(
				name = "moduleId",
				type = "String",
				value = "Module id identifier",
				example = "1",
				required = true) @PathVariable Integer moduleId,
			@ApiParam(
				name = "label",
				type = "String",
				value = "Label to translate",
				example = "LBL_LABEL_TEST",
				required = true) @PathVariable String label) {		
		
		return new ResponseEntity<>(i18nBO.getLabelTranslated(localeId, moduleId, label), HttpStatus.OK);
	}
	
	@GetMapping(value = "/translations/{localeId}/{moduleId}/{label}/{fields}")
	@ApiOperation(value = "Get label translated by Locale and Module usign fields", notes = "Get label translated by Locale and Module usign fields")
	public ResponseEntity<Object> getLabelTranslatedUsingFields(
			@ApiParam(
				name = "localeId",
				type = "String",
				value = "Locale id identifier",
				example = "I18nLocale:es_ES",
				required = true) @PathVariable String localeId,
			@ApiParam(
				name = "moduleId",
				type = "String",
				value = "Module id identifier",
				example = "1",
				required = true) @PathVariable Integer moduleId,
			@ApiParam(
				name = "label",
				type = "String",
				value = "Label to translate",
				example = "LBL_LABEL_TEST",
				required = true) @PathVariable String label,
			@ApiParam(
				name = "fields",
				type = "String",
				required = true) @PathVariable String fields) {
		
		 String[] arrayFields = fields.split(",");
		 List<String> listaString = new ArrayList<>();
		 
		 for (String string : arrayFields) {
			
			 listaString.add(string);
		}
		
		return new ResponseEntity<>(i18nBO.getLabelTranslatedUsingFields(localeId, moduleId, label, listaString), HttpStatus.OK);
	}	
}
