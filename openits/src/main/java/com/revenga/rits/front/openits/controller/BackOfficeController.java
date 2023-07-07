package com.revenga.rits.front.openits.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revenga.rits.front.openits.bo.BackOfficeBO;
import com.revenga.rits.front.openits.model.backoffice.Attachment;
import com.revenga.rits.front.openits.model.backoffice.Request;
import com.revenga.rits.front.openits.model.backoffice.Request.RequestResult;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openitsdao.model.BackOffice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/backoffice", produces = { "application/json", "application/xml" })
@Api(value = "BackOffice", tags = { "BACKOFFICE" })
public class BackOfficeController {

	@Autowired
	BackOfficeBO backOfficeBO;
	
	@GetMapping
	@ApiOperation(value = "Get all backoffices", notes = "Get all backoffices")
	public ResponseEntity<List<BackOffice>> getBackOffices() {
		
		return new ResponseEntity<>(backOfficeBO.getList(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{backOfficeId}")
	@ApiOperation(value = "Get backOffice by id", notes = "Get backOffice by id")
	public ResponseEntity<BackOffice> getBackOffice(
			@ApiParam(name = "backOfficeId", type = "Integer", value = "BackOffice identifier", example = "1", required = true) @PathVariable Integer backOfficeId) {

		return new ResponseEntity<>(backOfficeBO.getItem(backOfficeId), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{backOfficeId}/translate/{label}")
	@ApiOperation(value = "Get translated label from backoffice", notes = "Get translated label from backoffice")
	public ResponseEntity<String> translate(
			@ApiParam(name = "backOfficeId", type = "Integer", value = "BackOffice identifier", example = "1", required = true) @PathVariable Integer backOfficeId,
			@ApiParam(name = "label", type = "String", value = "label", example = "LBL_TEST", required = true) @PathVariable String label) {

		return new ResponseEntity<>(backOfficeBO.translate(backOfficeId, label), HttpStatus.OK);
	}
	
	@GetMapping(path = "/reload/{backOfficeId}")
	@ApiOperation(value = "Reload backOffice by id", notes = "Reload backOffice by id")
	public ResponseEntity<List<Table>> reloadBackOffice(
			@ApiParam(name = "backOfficeId", type = "Integer", value = "BackOffice identifier", example = "1", required = true) @PathVariable Integer backOfficeId) {

		return new ResponseEntity<>(backOfficeBO.reload(backOfficeId), HttpStatus.OK);
	}
	
	@PostMapping(path = "/{backOfficeId}/table/{tableName}")
	@ApiOperation(value = "get table with conditions")
	public ResponseEntity<List<Table>> getTables(
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId,
			@ApiParam(name = "tableName", type = "String", example = "orders", required = true) @PathVariable String tableName,
			@RequestBody(required = false) String conditions) {
		
		return new ResponseEntity<>(backOfficeBO.get(backOfficeId, tableName, conditions), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{backOfficeId}/attachments/{attachmentType}/{tableName}/{id}")
	@ApiOperation(value = "get attachments from table row")
	public ResponseEntity<List<Attachment>> getAttachments(
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId,
			@ApiParam(name = "attachmentType", type = "String", example = "file", required = true) @PathVariable Attachment.Type attachmentType,
			@ApiParam(name = "tableName", type = "String", example = "orders", required = true) @PathVariable String tableName,
			@ApiParam(name = "id", type = "Integer", example = "1", required = true) @PathVariable Integer id) {
		
		return new ResponseEntity<>(backOfficeBO.getAttachments(backOfficeId, attachmentType, tableName, id), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{backOfficeId}/attachments/image/{tableName}/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId, 
			@ApiParam(name = "tableName", type = "String", example = "products", required = true) @PathVariable String tableName,
			@ApiParam(name = "imageId", type = "Integer", example = "1", required = true) @PathVariable Integer imageId) {

		return new ResponseEntity<>(backOfficeBO.downloadImage(backOfficeId, tableName, imageId), HttpStatus.OK);
	}
	
	@PostMapping("{backOfficeId}/attachments/image/upload/{tableName}/{id}/{groupName}/{position}")
	public ResponseEntity<Attachment> uploadImage(
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId,
			@ApiParam(name = "tableName", type = "String", example = "products", required = true) @PathVariable String tableName,
			@ApiParam(name = "id", type = "Integer", example = "1", required = true) @PathVariable Integer id,
			@ApiParam(name = "groupName", type = "String", example = "main", required = true) @PathVariable String groupName,
			@ApiParam(name = "position", type = "String", example = "1", required = true) @PathVariable Integer position,
			@RequestParam("upload") MultipartFile multipartFile) {

		return new ResponseEntity<>(backOfficeBO.uploadImage(backOfficeId, tableName, id, groupName, position, multipartFile), HttpStatus.OK);
	}
	
	@PostMapping("/{backOfficeId}/attachments/image/edit/{tableName}")
	public ResponseEntity<Attachment> editImage(
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId,
			@ApiParam(name = "tableName", type = "String", example = "products", required = true) @PathVariable String tableName,
			@RequestBody(required = false) Attachment attachment) {

		return new ResponseEntity<>(backOfficeBO.editImage(backOfficeId, tableName, attachment), HttpStatus.OK);
	}
	
	@DeleteMapping("/{backOfficeId}/attachments/image/delete/{tableName}/{imageId}")
	public ResponseEntity<Attachment> deleteImage(
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId, 
			@ApiParam(name = "tableName", type = "String", example = "products", required = true) @PathVariable String tableName,
			@ApiParam(name = "imageId", type = "Integer", example = "1", required = true) @PathVariable Integer imageId) {
		
		return new ResponseEntity<>(backOfficeBO.deleteImage(backOfficeId, tableName, imageId), HttpStatus.OK);
	}
	
	@PostMapping(path = "/{backOfficeId}/attachments/download")
	@ApiOperation(value = "Download image by id")
	public ResponseEntity<Resource> downloadAttachmment(
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId,
			@RequestBody(required = true) Attachment attachment) {

		return new ResponseEntity<>(backOfficeBO.downloadAttachment(backOfficeId, attachment), HttpStatus.OK);
	}
	
	
	
	@PostMapping(path = "/{backOfficeId}/persist")
	@ApiOperation(value = "persist data")
	public ResponseEntity<RequestResult> persist(
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId,
			@RequestBody Request request) {
		
		return new ResponseEntity<>(backOfficeBO.persist(backOfficeId, request), HttpStatus.OK);
	}
	
	@PostMapping(path = "/{backOfficeId}/command")
	@ApiOperation(value = "persist data")
	public ResponseEntity<RequestResult> command(
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId,
			@RequestBody Request request) {
		
		return new ResponseEntity<>(backOfficeBO.command(backOfficeId, request), HttpStatus.OK);
	}
	
	@PostMapping(path = "/{backOfficeId}/filler")
	@ApiOperation(value = "On Change field")
	public ResponseEntity<RequestResult> filler(
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId,
			@RequestBody Request request) {
		
		return new ResponseEntity<>(backOfficeBO.filler(backOfficeId, request), HttpStatus.OK);
	}
}