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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revenga.rits.front.openits.bo.FileBO;
import com.revenga.rits.front.openits.model.FileUI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/files", produces = { "application/json", "application/xml" })
@Api(value = "Files", tags = { "FILES" })
@Log4j2
public class FileController {

	@Autowired
	FileBO fileBO;
	
	@GetMapping(value = "getFiles")
	@ApiOperation(value = "Get all files", notes = "Get all files")
	public ResponseEntity<List<FileUI>> getFiles(
			@RequestParam("paths") List<String> paths) {

		return new ResponseEntity<>(fileBO.getFiles(paths.stream().toArray(String[]::new)), HttpStatus.OK);
	}
	
	@PostMapping("/uploadFile/{path}")
	public ResponseEntity<FileUI> uploadFile(
			@ApiParam(name = "path", type = "String", value = "Path", example = "incidents/1", required = true) @PathVariable String path,
			@RequestParam("upload") MultipartFile multipartFile) {

		return new ResponseEntity<>(fileBO.uploadFile(path, multipartFile), HttpStatus.OK);
	}
	
	@GetMapping("/downloadFile/{path}/{fileName}")
    public ResponseEntity<Resource> downloadFile(
    		@ApiParam(name = "path", type = "String", value = "Path", example = "incidents/1", required = true) @PathVariable String path,
    		@ApiParam(name = "fileName", type = "String", value = "fileName", example = "test.txt", required = true) @PathVariable String fileName, 
    		HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileBO.loadFileAsResource(path, fileName);

        // Try to determine file's content type
        String contentType = null;
        
        try {
        	
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            
        } catch (IOException e) {
        	
            log.error("Could not determine file type.");
            log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
        	
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}