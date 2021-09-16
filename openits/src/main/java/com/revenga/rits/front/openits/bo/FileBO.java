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

import javax.annotation.PostConstruct;

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
	
	@PostConstruct
	private void postConstruct() {
		
		File baseDir = new File(env.getProperty(FILE_BASEDIR));
		
		if (baseDir.exists() && baseDir.isDirectory()) {
			
			log.info("Resources directory is: " + baseDir.getAbsolutePath());
		}
		else {
			
			log.error("Resources directory " + baseDir.getAbsolutePath() + " not found. Create it and reset app");
		}
	}
	
	public List<FileUI> getFiles(List<String> paths) {
		
		List<FileUI> files = new ArrayList<>();
		
		if (!CollectionUtils.isEmpty(paths)) {
			
			paths.forEach(path -> files.addAll(getFiles(path)));
		}
		
		return files;
	}
	
	public List<FileUI> getFiles(String path) {
		
		return getFiles(path, null);
	}
	
	public FileUI uploadFile(String path, MultipartFile multipartFile) {
		
		FileUI fileUI = null;
		
		try {

			if (multipartFile != null && multipartFile.getOriginalFilename() != null) {
			
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				
				// Check if the file's name contains invalid characters
	            if(!fileName.contains("..")) {
	            	
	            	// Check if target directory exists
	            	File targetDir = new File(env.getProperty(FILE_BASEDIR) + File.separator + path);
	            	
	            	if (!targetDir.exists()) {
	            		
	            		targetDir.mkdirs();
	            	}
	            	
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
	
	public List<FileUI> deleteFiles(List<String> paths) {
		
		List<FileUI> filesDeleted = new ArrayList<>();
		
		if (!CollectionUtils.isEmpty(paths)) {
			
			for (String path : paths) {
				
				Path pathFile = Path.of(env.getProperty(FILE_BASEDIR) + File.separator + path); 
				
				try {
					
					File fileDelete = pathFile.toFile();
					
					if (fileDelete.exists() && fileDelete.isFile()) {
						
						FileUI fileUI = new FileUI();
						
	                    fileUI.setFileName(fileDelete.getName());
	                    fileUI.setFilePath(fileDelete.getAbsolutePath());
	                    fileUI.setFileExtension(getFileExtension(fileDelete));
	                    fileUI.setSize(getFileSize(fileDelete));
	                    fileUI.setLastModified(fileDelete.lastModified());
						
	                    Files.delete(pathFile);
	                    	
	                    filesDeleted.add(fileUI);
					}
					
				} catch (IOException e) {
					
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}
		
		return filesDeleted;
	}
	
	private List<FileUI> getFiles(String path, List<FileUI> files) {
		
		if (files == null) {
			
			files = new ArrayList<>();
		}
		
		File folder = new File(env.getProperty(FILE_BASEDIR) + File.separator + path);
		
		if (folder.exists()) {
			
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
				else if (file.isDirectory()) {
					
					getFiles(path + File.separator + file.getName(), files);
				}
			}
		}
		
		return files;
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

