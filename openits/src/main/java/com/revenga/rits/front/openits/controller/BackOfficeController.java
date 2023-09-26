package com.revenga.rits.front.openits.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/backoffice", produces = { "application/json", "application/xml" })
@Api(value = "BackOffice", tags = { "BACKOFFICE" })
@Log4j2
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
	
	@GetMapping(path = "/{backOfficeId}/table/{tableName}/nodata")
	@ApiOperation(value = "get table with conditions, without row data")
	public ResponseEntity<List<Table>> getTablesNoRows(
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId,
			@ApiParam(name = "tableName", type = "String", example = "orders", required = true) @PathVariable String tableName) {
		
		return new ResponseEntity<>(backOfficeBO.get(backOfficeId, tableName), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{backOfficeId}/attachments/image/{tableName}/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> downloadImage(
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId, 
			@ApiParam(name = "tableName", type = "String", example = "products", required = true) @PathVariable String tableName,
			@ApiParam(name = "imageId", type = "Integer", example = "1", required = true) @PathVariable Integer imageId) {

		return new ResponseEntity<>(backOfficeBO.downloadImage(backOfficeId, tableName, imageId), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{backOfficeId}/attachments/other/{tableName}/{attachmentId}")
	public ResponseEntity<Resource> downloadAttachment(
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId, 
			@ApiParam(name = "tableName", type = "String", example = "products", required = true) @PathVariable String tableName,
			@ApiParam(name = "attachmentId", type = "Integer", example = "1", required = true) @PathVariable Integer attachmentId,
			HttpServletRequest request) {
		
		ResponseEntity<Resource> response = null;
		
		// Load file as Resource
		Resource resource = backOfficeBO.downloadAttachment(backOfficeId, tableName, attachmentId);

		// Try to determine file's content type
		String contentType = null;
		
		if (resource != null) {
			
			try {

				contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

			} catch (IOException e) {

				log.error("Could not determine file type.");
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}

			// Fallback to the default content type if type could not be determined
			if (contentType == null) {

				contentType = "application/octet-stream";
				
			}
			
			response = ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		}
		else {
			
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;

	}
	
	@PostMapping("{backOfficeId}/attachments/{attachmentType}/upload/{tableName}/{id}/{groupName}/{position}")
	public ResponseEntity<Attachment> uploadAttachment(
			@ApiParam(name = "attachmentType", type = "String", example = "image", required = true) @PathVariable String attachmentType,
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId,
			@ApiParam(name = "tableName", type = "String", example = "products", required = true) @PathVariable String tableName,
			@ApiParam(name = "id", type = "Integer", example = "1", required = true) @PathVariable Integer id,
			@ApiParam(name = "groupName", type = "String", example = "main", required = true) @PathVariable String groupName,
			@ApiParam(name = "position", type = "String", example = "1", required = true) @PathVariable Integer position,
			@RequestParam("upload") MultipartFile multipartFile) {

		return new ResponseEntity<>(backOfficeBO.upload(attachmentType, backOfficeId, tableName, id, groupName, position, multipartFile), HttpStatus.OK);
	}
	
	@PostMapping("/{backOfficeId}/attachments/{attachmentType}/edit/{tableName}")
	public ResponseEntity<Attachment> editAttachment(
			@ApiParam(name = "attachmentType", type = "String", example = "image", required = true) @PathVariable String attachmentType,
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId,
			@ApiParam(name = "tableName", type = "String", example = "products", required = true) @PathVariable String tableName,
			@RequestBody(required = false) Attachment attachment) {

		return new ResponseEntity<>(backOfficeBO.editAttachment(attachmentType, backOfficeId, tableName, attachment), HttpStatus.OK);
	}
	
	@DeleteMapping("/{backOfficeId}/attachments/{attachmentType}/delete/{tableName}/{imageId}")
	public ResponseEntity<Attachment> deleteAttachment(
			@ApiParam(name = "attachmentType", type = "String", example = "image", required = true) @PathVariable String attachmentType,
			@ApiParam(name = "backOfficeId", type = "Integer", example = "1", required = true) @PathVariable Integer backOfficeId, 
			@ApiParam(name = "tableName", type = "String", example = "products", required = true) @PathVariable String tableName,
			@ApiParam(name = "imageId", type = "Integer", example = "1", required = true) @PathVariable Integer imageId) {
		
		return new ResponseEntity<>(backOfficeBO.deleteAttachment(attachmentType, backOfficeId, tableName, imageId), HttpStatus.OK);
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