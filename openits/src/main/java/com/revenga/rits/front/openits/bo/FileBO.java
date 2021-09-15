package com.revenga.rits.front.openits.bo;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.front.openits.model.FileMetadataUI;
import com.revenga.rits.front.openits.model.FileUI;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class FileBO {
	
	private static final String FILE_BASEDIR = "file.basedir";
	
	@Autowired
	private Environment env;
	
	public List<FileUI> getFiles(String [] paths) {
		
		List<FileUI> files = new ArrayList<>();
		
		if (!ArrayUtils.isEmpty(paths)) {
			
			Arrays.stream(paths).forEach(path -> files.addAll(getFiles(path)));
		}
		
		return files;
	}
	
	public List<FileUI> getFiles(String path) {
		
		List<FileUI> files = new ArrayList<>();
		File folder = new File(env.getProperty(FILE_BASEDIR) + File.separator + path);
		
		List<FileMetadataUI> metadatas = getMetadatas(folder);
		
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
		    
			if (file.isFile() && !file.getName().equals(FileMetadataUI.FILE_NAME)) {
				
				FileUI fileUI = new FileUI();
				
				fileUI.setFileName(file.getName());
				fileUI.setFilePath(path);
				fileUI.setFileExtension(getFileExtension(file));
				fileUI.setSize(getFileSize(file));
				fileUI.setLastModified(file.lastModified());
				fileUI.setMetadata(getMetadata(metadatas, file));

				files.add(fileUI);
		    }
		} 
		
		return files;
	}
	
	public FileUI uploadFile(String path, MultipartFile multipartFile) {
		
		// Normalize file name
		FileUI fileUI = null;
		
		try {

			if (multipartFile != null && multipartFile.getOriginalFilename() != null) {
			
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				
				// Check if the file's name contains invalid characters
	            if(!fileName.contains("..")) {
	            	
	                Path targetLocation = Path.of(env.getProperty(FILE_BASEDIR) + File.separator + path + File.separator + fileName);
	                Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
	                
	                // Check if file is created
	                File file = new File(targetLocation.toString());
	                
	                if (file.isFile()) {
	                
	                	fileUI = new FileUI();
	                    
	                    fileUI.setFileName(fileName);
	                    fileUI.setFilePath(path);
	                    fileUI.setFileExtension(getFileExtension(file));
	                    fileUI.setSize(getFileSize(file));
	                    fileUI.setLastModified(file.lastModified());
	                }
	            }
			}
            
        } catch (IOException e) {
            
        	log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
        }

        return fileUI;
	}
	
	public Resource loadFileAsResource(String path, String fileName) {
        
		Resource resource = null;
		
		try {
        	
        	Path filePath = Path.of(env.getProperty(FILE_BASEDIR) + File.separator + path + File.separator + fileName); 

        	resource = new UrlResource(filePath.toUri());
        	
            if(resource.exists()) {
            	
                return resource;
                
            } else {
            	
            	throw new MalformedURLException("File not found: " + fileName); 
            }
        } catch (MalformedURLException e) {
            
        	log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
        }
		
		return null;
    }
	
	private String getFileExtension(File file) {
	    
		String name = file.getName();
		String extension = "";
		int lastIndexOf = name.lastIndexOf(".");
	    
		if (lastIndexOf != -1) {
	        
			extension = name.substring(lastIndexOf + 1);
	    }
		
	    return extension;
	}
	
	private long getFileSize(File file) {
		
		long size = 0;
		
		try {
			
			size = Files.size(Paths.get(file.getAbsolutePath()));
			
		} catch (IOException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return size;
	}
	
	private List<FileMetadataUI> getMetadatas(File folder) {

		List<FileMetadataUI> metadatas = null;
		
		try {

			if (folder != null && folder.isDirectory()) {
				
				File[] listOfFiles = folder.listFiles();
				
				File fileMetadata = Arrays.stream(listOfFiles).filter(e -> e.isFile() && e.getName().equals(FileMetadataUI.FILE_NAME))
						.findFirst().orElse(null);
				
				if (fileMetadata != null) {
					
				    Path path = Paths.get(fileMetadata.getAbsolutePath());
					
					try (Stream<String> lines = Files.lines(path)) {
						
						String data = lines.collect(Collectors.joining("\n"));
						
						ObjectMapper objectMapper = new ObjectMapper();
						
						metadatas = objectMapper.readValue(data, new TypeReference<List<FileMetadataUI>>() {});
					}
				}
			}
			
		} catch (IOException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return metadatas;
	}
	
	private FileMetadataUI getMetadata(List<FileMetadataUI> metadatas, File file) {
		
		if (!CollectionUtils.isEmpty(metadatas) && file != null) {
			
			return metadatas.stream().filter(e -> e.getFileName().equals(file.getName())).findFirst().orElse(null);
		}
		
		return null;
	}
}

